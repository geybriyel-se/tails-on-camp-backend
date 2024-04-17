package com.geybriyel.tailsoncamp.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserResponseDTO {

    private Long userId;

    private String username;

    private String email;

    private String firstName;

    private String lastName;

    private String address;

    private String phoneNumber;

}
