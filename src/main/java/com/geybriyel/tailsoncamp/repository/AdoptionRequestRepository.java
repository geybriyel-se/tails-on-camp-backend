package com.geybriyel.tailsoncamp.repository;

import com.geybriyel.tailsoncamp.entity.AdoptionRequest;
import com.geybriyel.tailsoncamp.entity.Pet;
import com.geybriyel.tailsoncamp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdoptionRequestRepository extends JpaRepository<AdoptionRequest, Long> {

    List<AdoptionRequest> findAll();

    Optional<AdoptionRequest> findByAdoptionId(Long id);

    List<AdoptionRequest> findAdoptionRequestByAdopter(User user);

    List<AdoptionRequest> findAdoptionRequestByPet(Pet pet);

    AdoptionRequest save(AdoptionRequest adoptionRequest);

    boolean existsByAdopterAndPet(User user, Pet pet);

}
