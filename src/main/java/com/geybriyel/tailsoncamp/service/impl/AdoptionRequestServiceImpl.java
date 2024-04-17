package com.geybriyel.tailsoncamp.service.impl;

import com.geybriyel.tailsoncamp.entity.AdoptionRequest;
import com.geybriyel.tailsoncamp.entity.Pet;
import com.geybriyel.tailsoncamp.entity.User;
import com.geybriyel.tailsoncamp.exception.DuplicateAdoptionRequestException;
import com.geybriyel.tailsoncamp.exception.InvalidAdoptionRequestIdException;
import com.geybriyel.tailsoncamp.exception.PetNotAvailableException;
import com.geybriyel.tailsoncamp.repository.AdoptionRequestRepository;
import com.geybriyel.tailsoncamp.repository.PetRepository;
import com.geybriyel.tailsoncamp.service.AdoptionRequestService;
import com.geybriyel.tailsoncamp.service.PetDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public boolean exists(AdoptionRequest adoptionRequest) {
        User user = adoptionRequest.getAdopter();
        Pet pet = adoptionRequest.getPet();
        return adoptionRequestRepository.existsByAdopterAndPet(user, pet);
    }

    @Override
    public AdoptionRequest saveRequest(AdoptionRequest adoptionRequest) {
        List<Pet> allAvailablePets = petDetailsService.getAllAvailablePets();
        Pet pet = adoptionRequest.getPet();
        if (!allAvailablePets.contains(pet)) {
            throw new PetNotAvailableException();
        }

        if (exists(adoptionRequest)) {
            throw new DuplicateAdoptionRequestException();
        }
        adoptionRequest.setStatus("Pending");
        return adoptionRequestRepository.save(adoptionRequest);
    }

}
