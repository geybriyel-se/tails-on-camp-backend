package com.geybriyel.tailsoncamp.listener;


import com.geybriyel.tailsoncamp.entity.Shelter;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.Instant;

public class ShelterEntityListener {

    @PrePersist
    public void beforePersist(Shelter shelter) {
        shelter.setCreatedAt(Instant.now());
    }

    @PreUpdate

    public void beforeUpdate(Shelter shelter) {
        shelter.setUpdatedAt(Instant.now());
    }
}
