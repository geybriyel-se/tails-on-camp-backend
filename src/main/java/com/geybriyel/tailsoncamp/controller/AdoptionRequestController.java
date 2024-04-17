package com.geybriyel.tailsoncamp.controller;

import com.geybriyel.tailsoncamp.dto.*;
import com.geybriyel.tailsoncamp.entity.AdoptionRequest;
import com.geybriyel.tailsoncamp.entity.Pet;
import com.geybriyel.tailsoncamp.entity.User;
import com.geybriyel.tailsoncamp.enums.StatusCode;
import com.geybriyel.tailsoncamp.exception.*;
import com.geybriyel.tailsoncamp.service.AdoptionRequestService;
import com.geybriyel.tailsoncamp.service.PetDetailsService;
import com.geybriyel.tailsoncamp.service.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.geybriyel.tailsoncamp.mapper.AdoptionRequestMapper.*;
import static com.geybriyel.tailsoncamp.mapper.PetMapper.buildListPetResponseDtoFromPetList;
import static com.geybriyel.tailsoncamp.mapper.UserMapper.buildListUserResponseDtoFromUserList;

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
            AdoptionRequestResponseDTO responseDTO = buildAdoptionResponseDtoFromAdoptionObject(adoptionRequestByAdoptionId);
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

    @PostMapping("/create")
    public ApiResponse<AdoptionRequestResponseDTO> createRequest(@RequestBody AdoptionRequestRequestDTO requestDTO) {
        try {
            AdoptionRequest adoptionRequest = buildAdoptionRequestObjectFromAdoptionRequestDto(requestDTO, userDetailsService, petDetailsService);
            AdoptionRequest saveRequest = adoptionRequestService.saveRequest(adoptionRequest);
            AdoptionRequestResponseDTO responseDTO = buildAdoptionResponseDtoFromAdoptionObject(saveRequest);
            return new ApiResponse<>(StatusCode.SUCCESS, responseDTO);
        } catch (PetNotAvailableException e) {
            return new ApiResponse<>(e.getStatusCode(), null);
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

    private List<User> getUsersFromAdoptionRequestList(List<AdoptionRequest> adoptionRequestsByPet) {
        return adoptionRequestsByPet.stream()
                .map(AdoptionRequest::getAdopter)
                .collect(Collectors.toList());
    }

}