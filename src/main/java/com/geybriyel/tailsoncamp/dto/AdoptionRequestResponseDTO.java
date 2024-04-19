package com.geybriyel.tailsoncamp.dto;

import com.geybriyel.tailsoncamp.entity.Pet;
import com.geybriyel.tailsoncamp.entity.User;
import com.geybriyel.tailsoncamp.enums.AdoptionRequestStatus;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Builder
@Data
public class AdoptionRequestResponseDTO {

    private PetDetailsResponseDTO pet;

    private UserResponseDTO adopter;

    private AdoptionRequestStatus status;

    private Instant createdAt;

    private Instant updatedAt;

}
