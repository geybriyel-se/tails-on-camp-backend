package com.geybriyel.tailsoncamp.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserAdoptionRequestRequestDTO {

    private Long userId;

    private String username;

    private String email;

}
