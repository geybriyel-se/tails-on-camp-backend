package com.geybriyel.tailsoncamp.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PetAdoptionRequestRequestDTO {

    private Long petId;

    private String name;



}
