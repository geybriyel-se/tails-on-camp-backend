package com.geybriyel.tailsoncamp.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class AdoptionRequestRequestDTO {

    private Long userId;

    private Long petId;

}
