package com.geybriyel.tailsoncamp.controller;

import com.geybriyel.tailsoncamp.dto.ApiResponse;
import com.geybriyel.tailsoncamp.dto.UserRequestDTO;
import com.geybriyel.tailsoncamp.dto.UserResponseDTO;
import com.geybriyel.tailsoncamp.entity.User;
import com.geybriyel.tailsoncamp.enums.StatusCode;
import com.geybriyel.tailsoncamp.exception.InvalidUserIdException;
import com.geybriyel.tailsoncamp.mapper.UserMapper;
import com.geybriyel.tailsoncamp.service.impl.UserDetailsServiceImpl;
import com.geybriyel.tailsoncamp.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.geybriyel.tailsoncamp.mapper.UserMapper.*;

@Slf4j
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserDetailsServiceImpl userDetailsService;

    @GetMapping("/all")
    public ApiResponse<List<UserResponseDTO>> retrieveAllUsers() {
        List<User> allUsers = userDetailsService.getAllUsers();
        if (allUsers.isEmpty()) {
            return new ApiResponse<>(StatusCode.LIST_EMPTY, allUsers);
        }
        List<UserResponseDTO> responseDTOS = buildListUserResponseDtoFromUserList(allUsers);
        return new ApiResponse<>(StatusCode.SUCCESS, responseDTOS);
    }

    @GetMapping("/id")
    public ApiResponse<UserResponseDTO> retrieveUserById(@RequestBody Long id) {
        try {
            User userById = userDetailsService.getUserById(id);
            UserResponseDTO responseDTO = UserMapper.buildUserResponseDtoFromUserObject(userById);
            return new ApiResponse<>(StatusCode.SUCCESS, responseDTO);
        } catch (InvalidUserIdException e) {
            return new ApiResponse<>(e.getStatusCode(), null);
        } catch (Exception e) {
            return new ApiResponse<>(StatusCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/username")
    public ApiResponse<UserResponseDTO> retrieveUserByUsername(@RequestBody String username) {
        try {
            User user = userDetailsService.loadUserByUsername(username);
            UserResponseDTO responseDTO = buildUserResponseDtoFromUserObject(user);
            log.info("Logged in user: {}", SecurityUtils.getLoggedInUser());
            log.info("Retrieved user: {}", user);
            return new ApiResponse<>(StatusCode.SUCCESS, responseDTO);
        } catch (UsernameNotFoundException e) {
            return new ApiResponse<>(StatusCode.INVALID_USERNAME, e.getMessage());
        } catch (Exception e) {
            return new ApiResponse<>(StatusCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/update")
    public ApiResponse<UserResponseDTO> updateUser(@RequestBody UserRequestDTO user) {
        User userToSave = buildUserObjectFromRequestDto(user);
        try {
            User updateUser = userDetailsService.updateUser(userToSave);
            return new ApiResponse<>(StatusCode.SUCCESS, updateUser);
        } catch (InvalidUserIdException e) {
            return new ApiResponse<>(e.getStatusCode(), e.getMessage());
        } catch (Exception e) {
            return new ApiResponse<>(StatusCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}
