package com.geybriyel.tailsoncamp.mapper;

import com.geybriyel.tailsoncamp.dto.UserRequestDTO;
import com.geybriyel.tailsoncamp.dto.UserResponseDTO;
import com.geybriyel.tailsoncamp.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserResponseDTO buildUserResponseDtoFromUserObject(User user) {
        return UserResponseDTO.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .address(user.getAddress())
                .build();
    }

    public static List<UserResponseDTO> buildListUserResponseDtoFromUserList(List<User> users) {
        return users.stream()
                .map(UserMapper::buildUserResponseDtoFromUserObject)
                .collect(Collectors.toList());
    }

    public static User buildUserObjectFromRequestDto(UserRequestDTO userRequestDTO) {
        User user = new User();
        user.setUserId(userRequestDTO.getUserId());
        user.setEmail(userRequestDTO.getEmail());
        user.setUsername(userRequestDTO.getUsername());
        user.setFirstName(userRequestDTO.getFirstName());
        user.setLastName(userRequestDTO.getLastName());
        user.setAddress(userRequestDTO.getAddress());
        user.setPhoneNumber(userRequestDTO.getPhoneNumber());
        return user;
    }
}
