package com.geybriyel.tailsoncamp.service;

import com.geybriyel.tailsoncamp.entity.AdoptionRequest;
import com.geybriyel.tailsoncamp.entity.Pet;
import com.geybriyel.tailsoncamp.entity.User;
import com.geybriyel.tailsoncamp.enums.AdoptionRequestStatus;

import java.util.List;

public interface AdoptionRequestService {

    List<AdoptionRequest> getAllAdoptionRequests();

    AdoptionRequest getAdoptionRequestByAdoptionId(Long adoptionId);

    List<AdoptionRequest> getAdoptionRequestsByAdopter(User user);

    List<AdoptionRequest> getAdoptionRequestsByPet(Pet pet);

    /**
     * Checks if an adoption request of User to Pet already exists
     * Uses fields Pet and Adopter of the AdoptionRequest class
     * @param adoptionRequest
     * @return true if request already exists
     */
    boolean existsRequestByUserToPet(AdoptionRequest adoptionRequest);

    AdoptionRequest getAdoptionRequestByUserAndPet(AdoptionRequest adoptionRequest);

    AdoptionRequest saveRequest(AdoptionRequest adoptionRequest);

    AdoptionRequest approveRequest(Long requestId);

    AdoptionRequest rejectRequest(Long requestId);

    AdoptionRequest cancelRequest(Long requestId);

    AdoptionRequest holdRequest(Long requestId);

    AdoptionRequest completeRequest(Long requestId);

    AdoptionRequest closeRequest(Long requestId);
}
