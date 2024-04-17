package com.geybriyel.tailsoncamp.listener;

import com.geybriyel.tailsoncamp.entity.AdoptionRequest;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.Instant;

public class AdoptionRequestEntityListener {

    @PrePersist
    public void beforePersist(AdoptionRequest adoptionRequest) {
        adoptionRequest.setCreatedAt(Instant.now());
    }

    @PreUpdate
    public void beforeUpdate(AdoptionRequest adoptionRequest) {
        adoptionRequest.setUpdatedAt(Instant.now());
    }

}
