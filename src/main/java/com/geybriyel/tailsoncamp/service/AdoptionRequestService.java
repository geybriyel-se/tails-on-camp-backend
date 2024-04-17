package com.geybriyel.tailsoncamp.service;

import com.geybriyel.tailsoncamp.entity.AdoptionRequest;
import com.geybriyel.tailsoncamp.entity.Pet;
import com.geybriyel.tailsoncamp.entity.User;

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
    boolean exists(AdoptionRequest adoptionRequest);

    AdoptionRequest saveRequest(AdoptionRequest adoptionRequest);

}
