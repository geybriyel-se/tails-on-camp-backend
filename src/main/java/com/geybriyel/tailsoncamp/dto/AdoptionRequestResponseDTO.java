package com.geybriyel.tailsoncamp.dto;

import com.geybriyel.tailsoncamp.entity.Pet;
import com.geybriyel.tailsoncamp.entity.User;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Builder
@Data
public class AdoptionRequestResponseDTO {

    private PetDetailsResponseDTO pet;

    private UserResponseDTO adopter;

    private String status;

    private Instant createdAt;

    private Instant updatedAt;

}
