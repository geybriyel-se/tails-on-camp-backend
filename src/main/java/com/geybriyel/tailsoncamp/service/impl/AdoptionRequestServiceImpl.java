package com.geybriyel.tailsoncamp.service.impl;

import com.geybriyel.tailsoncamp.entity.AdoptionRequest;
import com.geybriyel.tailsoncamp.entity.Pet;
import com.geybriyel.tailsoncamp.entity.User;
import com.geybriyel.tailsoncamp.enums.AdoptionRequestStatus;
import com.geybriyel.tailsoncamp.exception.*;
import com.geybriyel.tailsoncamp.repository.AdoptionRequestRepository;
import com.geybriyel.tailsoncamp.service.AdoptionRequestService;
import com.geybriyel.tailsoncamp.service.PetDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdoptionRequestServiceImpl implements AdoptionRequestService {

    private final AdoptionRequestRepository adoptionRequestRepository;

    private final PetDetailsService petDetailsService;

    @Override
    public List<AdoptionRequest> getAllAdoptionRequests() {
        return adoptionRequestRepository.findAll();
    }

    @Override
    public AdoptionRequest getAdoptionRequestByAdoptionId(Long adoptionId) {
        return adoptionRequestRepository.findByAdoptionId(adoptionId)
                .orElseThrow(InvalidAdoptionRequestIdException::new);
    }

    @Override
    public List<AdoptionRequest> getAdoptionRequestsByAdopter(User user) {
        return adoptionRequestRepository.findAdoptionRequestByAdopter(user);
    }

    @Override
    public List<AdoptionRequest> getAdoptionRequestsByPet(Pet pet) {
        return adoptionRequestRepository.findAdoptionRequestByPet(pet);
    }

    @Override
    public boolean existsRequestByUserToPet(AdoptionRequest adoptionRequest) {
        User user = adoptionRequest.getAdopter();
        Pet pet = adoptionRequest.getPet();
        return adoptionRequestRepository.existsByAdopterAndPet(user, pet);
    }

    @Override
    public AdoptionRequest getAdoptionRequestByUserAndPet(AdoptionRequest adoptionRequest) {
        User user = adoptionRequest.getAdopter();
        Pet pet = adoptionRequest.getPet();
        return adoptionRequestRepository.findAdoptionRequestByAdopterAndPet(user, pet);
    }

    @Override
    public AdoptionRequest saveRequest(AdoptionRequest adoptionRequest) {
        Pet requestPet = adoptionRequest.getPet();
        Pet retrievedPet = petDetailsService.getPetByPetId(requestPet.getId());

        Integer availability = retrievedPet.getAvailability();
        if (availability == 0) {
            throw new PetNotAvailableException();
        }

        if (availability == 2) {
            throw new PetOnHoldException();
        }

        AdoptionRequest requestByUserAndPet = getAdoptionRequestByUserAndPet(adoptionRequest);
        if (requestByUserAndPet == null) {
            adoptionRequest.setStatus(AdoptionRequestStatus.PENDING);
            return adoptionRequestRepository.save(adoptionRequest);
        }

        if (requestByUserAndPet.getStatus().equals(AdoptionRequestStatus.REJECTED)) {
            log.info("An existing request by user to the same pet was already rejected.");
            throw new RejectedAdoptionRequestException();
        } else {
            throw new DuplicateAdoptionRequestException();
        }

    }

    @Override
    public AdoptionRequest approveRequest(Long requestId) {
        AdoptionRequest retrievedRequest = getAdoptionRequestByAdoptionId(requestId);
        if (retrievedRequest.getPet().getAvailability() == 0) {
            throw new PetNotAvailableException();
        }

        List<AdoptionRequest> requestList = getAdoptionRequestsByPet(retrievedRequest.getPet());
        requestList.forEach(req -> {
            AdoptionRequestStatus status = req.getStatus();
            if (status.equals(AdoptionRequestStatus.APPROVED)) {
                throw new AnotherReqUndergoingFinalizationException();
            }

            if (!req.getAdoptionId().equals(requestId)) {
                if (status.equals(AdoptionRequestStatus.PENDING)) {
                    holdRequest(req.getAdoptionId());
                    log.info("Request ID {} put on hold. Request from another user with ID {} is currently approved and undergoing finalization.", requestId, req.getAdoptionId());
                }
            }
        });

        retrievedRequest.setStatus(AdoptionRequestStatus.APPROVED);
        Pet pet = petDetailsService.getPetByPetId(retrievedRequest.getPet().getId());
        pet.setAvailability(2);
        petDetailsService.updatePet(pet);
        return adoptionRequestRepository.save(retrievedRequest);
    }

    @Override
    public AdoptionRequest rejectRequest(Long requestId) {
        AdoptionRequest adoptionRequestByAdoptionId = getAdoptionRequestByAdoptionId(requestId);
        adoptionRequestByAdoptionId.setStatus(AdoptionRequestStatus.REJECTED);
        return adoptionRequestRepository.save(adoptionRequestByAdoptionId);
    }

    @Override
    public AdoptionRequest cancelRequest(Long requestId) {
        AdoptionRequest adoptionRequestByAdoptionId = getAdoptionRequestByAdoptionId(requestId);
        adoptionRequestByAdoptionId.setStatus(AdoptionRequestStatus.CANCELLED);
        return adoptionRequestRepository.save(adoptionRequestByAdoptionId);
    }

    @Override
    public AdoptionRequest holdRequest(Long requestId) {
        AdoptionRequest adoptionRequestByAdoptionId = getAdoptionRequestByAdoptionId(requestId);
        adoptionRequestByAdoptionId.setStatus(AdoptionRequestStatus.ON_HOLD);
        return adoptionRequestRepository.save(adoptionRequestByAdoptionId);
    }

    @Override
    public AdoptionRequest completeRequest(Long requestId) {
        AdoptionRequest adoptionRequestByAdoptionId = getAdoptionRequestByAdoptionId(requestId);
        Pet pet = petDetailsService.getPetByPetId(adoptionRequestByAdoptionId.getPet().getId());
        if (pet.getAvailability() == 0) {
            throw new PetNotAvailableException();
        }

        if (existsRequestByUserToPet(adoptionRequestByAdoptionId)) {
            AdoptionRequest requestByUserAndPet = getAdoptionRequestByUserAndPet(adoptionRequestByAdoptionId);
            AdoptionRequestStatus status = requestByUserAndPet.getStatus();
            if (status.equals(AdoptionRequestStatus.REJECTED)) {
                throw new RejectedAdoptionRequestException();
            }

        }
        AdoptionRequestStatus requestStatus = adoptionRequestByAdoptionId.getStatus();
        if (!requestStatus.equals(AdoptionRequestStatus.APPROVED)) {
            throw new RequestNotApprovedException();
        }

        adoptionRequestByAdoptionId.setStatus(AdoptionRequestStatus.COMPLETED);
        List<AdoptionRequest> requestsForPet = adoptionRequestRepository.findAdoptionRequestByPet(adoptionRequestByAdoptionId.getPet());
        requestsForPet.forEach(req -> {
            if (!req.getAdoptionId().equals(requestId)) {
                if (req.getStatus().equals(AdoptionRequestStatus.ON_HOLD)) {
                    closeRequest(req.getAdoptionId());
                    log.info("Request ID {} closed. Another user completed adoption process for the same pet", req.getAdoptionId());
                }
            }
        });
        pet.setAvailability(0);
        petDetailsService.updatePet(pet);
        return adoptionRequestRepository.save(adoptionRequestByAdoptionId);

    }

    @Override
    public AdoptionRequest closeRequest(Long requestId) {
        AdoptionRequest adoptionRequestByAdoptionId = getAdoptionRequestByAdoptionId(requestId);
        adoptionRequestByAdoptionId.setStatus(AdoptionRequestStatus.CLOSED);
        return adoptionRequestRepository.save(adoptionRequestByAdoptionId);
    }

}
