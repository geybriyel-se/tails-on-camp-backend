package com.geybriyel.tailsoncamp.utility;

import com.geybriyel.tailsoncamp.entity.Pet;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.Instant;

public class PetEntityListener {

    @PrePersist
    public void beforePersist(Pet pet) {
        pet.setCreatedAt(Instant.now());
    }

    @PreUpdate
    public void beforeUpdate(Pet pet) {
        pet.setUpdatedAt(Instant.now());
    }

}
