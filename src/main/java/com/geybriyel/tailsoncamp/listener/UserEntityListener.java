package com.geybriyel.tailsoncamp.listener;

import com.geybriyel.tailsoncamp.entity.User;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.Instant;

public class UserEntityListener {

    @PrePersist
    public void beforePersist(User user) {
        user.setCreatedAt(Instant.now());
    }

    @PreUpdate
    public void beforeUpdate(User user) {
        user.setUpdatedAt(Instant.now());
    }


}
