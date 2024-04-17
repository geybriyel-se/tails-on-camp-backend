package com.geybriyel.tailsoncamp.controller;

import com.geybriyel.tailsoncamp.dto.*;
import com.geybriyel.tailsoncamp.entity.AdoptionRequest;
import com.geybriyel.tailsoncamp.entity.Pet;
import com.geybriyel.tailsoncamp.entity.User;
import com.geybriyel.tailsoncamp.enums.StatusCode;
import com.geybriyel.tailsoncamp.exception.DuplicateAdoptionRequestException;
import com.geybriyel.tailsoncamp.exception.InvalidAdoptionRequestIdException;
import com.geybriyel.tailsoncamp.exception.InvalidPetIdException;
import com.geybriyel.tailsoncamp.exception.InvalidUserIdException;
import com.geybriyel.tailsoncamp.service.AdoptionRequestService;
import com.geybriyel.tailsoncamp.service.PetDetailsService;
import com.geybriyel.tailsoncamp.service.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/adoptions")
@RequiredArgsConstructor
@Slf4j
public class AdoptionRequestController {

    private final AdoptionRequestService adoptionRequestService;

    private final UserDetailsServiceImpl userDetailsService;

    private final PetDetailsService petDetailsService;

    @GetMapping("/all")
    public ApiResponse<List<AdoptionRequestResponseDTO>> retrieveAllAdoptionRequests() {
        List<AdoptionRequest> allAdoptionRequests = adoptionRequestService.getAllAdoptionRequests();

        if (allAdoptionRequests.isEmpty()) {
            return new ApiResponse<>(StatusCode.LIST_EMPTY, allAdoptionRequests);
        }

        List<AdoptionRequestResponseDTO> responseDTOS = buildListResponseDtoFromObjectList(allAdoptionRequests);
        return new ApiResponse<>(StatusCode.SUCCESS, responseDTOS);
    }

    @GetMapping("/id")
    public ApiResponse<AdoptionRequestResponseDTO> retrieveAdoptionRequestsByAdoptionId(@RequestBody Long id) {
        try {
            AdoptionRequest adoptionRequestByAdoptionId = adoptionRequestService.getAdoptionRequestByAdoptionId(id);
            AdoptionRequestResponseDTO responseDTO = buildResponseDtoFromObject(adoptionRequestByAdoptionId);
            return new ApiResponse<>(StatusCode.SUCCESS, responseDTO);
        } catch (InvalidAdoptionRequestIdException e) {
            return new ApiResponse<>(e.getStatusCode(), null);
        } catch (Exception e) {
            return new ApiResponse<>(StatusCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /*
     * The current implementation checks if the user exists by USERNAME ONLY.
     * Incorrect/Invalid  email and/or userId still returns Success
     *
     * @param userRequest
     * @return List of Adoption Requests of the User
     */
    @GetMapping("/adopter")
    public ApiResponse<List<PetDetailsResponseDTO>> retrieveAdoptionRequestsByAdopter(@RequestBody UserAdoptionRequestRequestDTO userRequest) {
        try {
            User user = userDetailsService.loadUserByUsername(userRequest.getUsername());
            List<AdoptionRequest> adoptionRequestsByAdopter = adoptionRequestService.getAdoptionRequestsByAdopter(user);
            if (adoptionRequestsByAdopter.isEmpty()) {
                return new ApiResponse<>(StatusCode.LIST_EMPTY, adoptionRequestsByAdopter);
            }
            List<Pet> petsRequestByUser = adoptionRequestsByAdopter.stream()
                    .map(AdoptionRequest::getPet)
                    .collect(Collectors.toList());

            List<PetDetailsResponseDTO> responseDTOS = buildListPetResponseDtoFromPetList(petsRequestByUser);
            return new ApiResponse<>(StatusCode.SUCCESS, responseDTOS);
        } catch (UsernameNotFoundException e) {
            return new ApiResponse<>(StatusCode.USER_DOES_NOT_EXIST, e.getMessage());
        } catch (Exception e) {
            return new ApiResponse<>(StatusCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/pet")
    public ApiResponse<List<AdoptionRequestResponseDTO>> retrieveAdoptionRequestsByPet(@RequestBody PetAdoptionRequestRequestDTO pet) {
        try {
            Pet petByPetId = petDetailsService.getPetByPetId(pet.getPetId());
            List<AdoptionRequest> adoptionRequestsByPet = adoptionRequestService.getAdoptionRequestsByPet(petByPetId);
            if (adoptionRequestsByPet.isEmpty()) {
                return new ApiResponse<>(StatusCode.LIST_EMPTY, adoptionRequestsByPet);
            }

            List<User> userList = getUsersFromAdoptionRequestList(adoptionRequestsByPet);
            List<UserResponseDTO> responseDTOS = buildListUserResponseDtoFromUserList(userList);
            return new ApiResponse<>(StatusCode.SUCCESS, responseDTOS);
        } catch (InvalidPetIdException e) {
            return new ApiResponse<>(e.getStatusCode(), null);
        } catch (Exception e) {
            return new ApiResponse<>(StatusCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private List<User> getUsersFromAdoptionRequestList(List<AdoptionRequest> adoptionRequestsByPet) {
        return adoptionRequestsByPet.stream()
                .map(AdoptionRequest::getAdopter)
                .collect(Collectors.toList());
    }

    @PostMapping("/create")
    public ApiResponse<AdoptionRequestResponseDTO> createRequest(@RequestBody AdoptionRequestRequestDTO requestDTO) {
        AdoptionRequest adoptionRequest = buildAdoptionRequestObjectFromAdoptionRequestDto(requestDTO);
        try {
            AdoptionRequest saveRequest = adoptionRequestService.saveRequest(adoptionRequest);
            AdoptionRequestResponseDTO responseDTO = buildResponseDtoFromObject(saveRequest);
            return new ApiResponse<>(StatusCode.SUCCESS, responseDTO);
        } catch (InvalidUserIdException e) {
            return new ApiResponse<>(e.getStatusCode(), null);
        } catch (InvalidPetIdException e) {
            return new ApiResponse<>(e.getStatusCode(), null);
        } catch (DuplicateAdoptionRequestException e) {
            return new ApiResponse<>(e.getStatusCode(), null);
        } catch (Exception e) {
            return new ApiResponse<>(StatusCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private AdoptionRequest buildAdoptionRequestObjectFromAdoptionRequestDto(@RequestBody AdoptionRequestRequestDTO request) {
        AdoptionRequest adoptionRequest = new AdoptionRequest();
        try {
            User user = userDetailsService.getUserById(request.getUserId());
            Pet pet = petDetailsService.getPetByPetId(request.getPetId());
            adoptionRequest.setAdopter(user);
            adoptionRequest.setPet(pet);
            return adoptionRequest;
        } catch (InvalidUserIdException | InvalidPetIdException e) {
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Error building adoption request from DTO: " + e.getMessage(), e);
        }
    }



    private List<AdoptionRequestResponseDTO> buildListResponseDtoFromObjectList(List<AdoptionRequest> allAdoptionRequests) {
        return allAdoptionRequests.stream()
                .map(this::buildResponseDtoFromObject)
                .collect(Collectors.toList());
    }

    private AdoptionRequestResponseDTO buildResponseDtoFromObject(AdoptionRequest req) {
        PetDetailsResponseDTO petResDto = buildPetResponseDtoFromPetObject(req.getPet());
        UserResponseDTO userResDto = buildUserResponseDtoFromUserObject(req.getAdopter());

        return AdoptionRequestResponseDTO.builder()
                .pet(petResDto)
                .adopter(userResDto)
                .status(req.getStatus())
                .createdAt(req.getCreatedAt())
                .updatedAt(req.getUpdatedAt())
                .build();
    }

    private List<UserResponseDTO> buildListUserResponseDtoFromUserList(List<User> users) {
        return users.stream()
                .map(this::buildUserResponseDtoFromUserObject)
                .collect(Collectors.toList());
    }

    private UserResponseDTO buildUserResponseDtoFromUserObject(User user) {
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

    private PetDetailsResponseDTO buildPetResponseDtoFromPetObject(Pet pet) {
        return PetDetailsResponseDTO.builder()
                .id(pet.getId())
                .name(pet.getName())
                .type(pet.getType())
                .breed(pet.getBreed())
                .age(pet.getAge())
                .gender(pet.getGender())
                .size(pet.getSize())
                .description(pet.getDescription())
                .imageUrl(pet.getImageUrl())
                .availability(pet.getAvailability())
                .shelter(pet.getShelter())
                .adopter(pet.getAdopter())
                .createdAt(pet.getCreatedAt())
                .build();
    }

    private List<PetDetailsResponseDTO> buildListPetResponseDtoFromPetList(List<Pet> allPets) {
        return allPets.stream()
                .map(this::buildPetResponseDtoFromPetObject)
                .collect(Collectors.toList());
    }
}
