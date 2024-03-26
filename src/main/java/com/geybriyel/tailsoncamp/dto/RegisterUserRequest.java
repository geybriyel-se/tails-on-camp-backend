package com.geybriyel.tailsoncamp.dto;

import lombok.Data;

@Data
public class RegisterUserRequest {

    private String email;

    private String username;

    private String password;

}
