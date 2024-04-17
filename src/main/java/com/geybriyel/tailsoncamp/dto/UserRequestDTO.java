package com.geybriyel.tailsoncamp.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequestDTO {

    private Long userId;

    private String email;

    private String username;

    private String firstName;

    private String lastName;

    private String address;

    private String phoneNumber;

}
