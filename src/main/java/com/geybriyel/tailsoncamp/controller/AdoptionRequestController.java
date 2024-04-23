package com.geybriyel.tailsoncamp.controller;

import com.geybriyel.tailsoncamp.dto.*;
import com.geybriyel.tailsoncamp.entity.AdoptionRequest;
import com.geybriyel.tailsoncamp.entity.Pet;
import com.geybriyel.tailsoncamp.entity.User;
import com.geybriyel.tailsoncamp.enums.StatusCode;
import com.geybriyel.tailsoncamp.service.AdoptionRequestService;
import com.geybriyel.tailsoncamp.service.PetDetailsService;
import com.geybriyel.tailsoncamp.service.impl.UserDetailsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.geybriyel.tailsoncamp.mapper.AdoptionRequestMapper.*;
import static com.geybriyel.tailsoncamp.mapper.PetMapper.buildListPetResponseDtoFromPetList;
import static com.geybriyel.tailsoncamp.mapper.UserMapper.buildListUserResponseDtoFromUserList;

@RestController
@RequestMapping("/adoptions")
@RequiredArgsConstructor
@Slf4j
public class AdoptionRequestController {

    private final AdoptionRequestService adoptionRequestService;
    private final UserDetailsServiceImpl userDetailsService;
    private final PetDetailsService petDetailsService;

    @Operation(summary = "Retrieve all adoption requests")
    @GetMapping("/all")
    public ApiResponse<List<AdoptionRequestResponseDTO>> retrieveAllAdoptionRequests() {
        List<AdoptionRequest> allAdoptionRequests = adoptionRequestService.getAllAdoptionRequests();

        if (allAdoptionRequests.isEmpty()) {
            return new ApiResponse<>(StatusCode.LIST_EMPTY, allAdoptionRequests);
        }

        List<AdoptionRequestResponseDTO> responseDTOS = buildListResponseDtoFromObjectList(allAdoptionRequests);
        return new ApiResponse<>(StatusCode.SUCCESS, responseDTOS);
    }

    @Operation(summary = "Retrieve an adoption requests by its ID")
    @GetMapping("/id")
    public ApiResponse<AdoptionRequestResponseDTO> retrieveAdoptionRequestsByAdoptionId(@RequestBody Long id) {
        AdoptionRequest adoptionRequestByAdoptionId = adoptionRequestService.getAdoptionRequestByAdoptionId(id);
        AdoptionRequestResponseDTO responseDTO = buildAdoptionResponseDtoFromAdoptionObject(adoptionRequestByAdoptionId);
        return new ApiResponse<>(StatusCode.SUCCESS, responseDTO);
    }

    @Operation(summary = "Retrieve all adoption requests created by the User")
    @GetMapping("/adopter")
    public ApiResponse<List<PetDetailsResponseDTO>> retrieveAdoptionRequestsByAdopter(@RequestBody Long userId) {
        User user = userDetailsService.getUserById(userId);
        List<AdoptionRequest> adoptionRequestsByAdopter = adoptionRequestService.getAdoptionRequestsByAdopter(user);
        if (adoptionRequestsByAdopter.isEmpty()) {
            return new ApiResponse<>(StatusCode.LIST_EMPTY, adoptionRequestsByAdopter);
        }
        List<Pet> petsRequestByUser = adoptionRequestsByAdopter.stream()
                .map(AdoptionRequest::getPet)
                .collect(Collectors.toList());

        List<PetDetailsResponseDTO> responseDTOS = buildListPetResponseDtoFromPetList(petsRequestByUser);
        return new ApiResponse<>(StatusCode.SUCCESS, responseDTOS);
    }

    @Operation(summary = "Retrieve all adoption requests created for the Pet")
    @GetMapping("/pet")
    public ApiResponse<List<AdoptionRequestResponseDTO>> retrieveAdoptionRequestsByPet(@RequestBody PetAdoptionRequestRequestDTO pet) {
        Pet petByPetId = petDetailsService.getPetByPetId(pet.getPetId());
        List<AdoptionRequest> adoptionRequestsByPet = adoptionRequestService.getAdoptionRequestsByPet(petByPetId);
        if (adoptionRequestsByPet.isEmpty()) {
            return new ApiResponse<>(StatusCode.LIST_EMPTY, adoptionRequestsByPet);
        }

        List<User> userList = getUsersFromAdoptionRequestList(adoptionRequestsByPet);
        List<UserResponseDTO> responseDTOS = buildListUserResponseDtoFromUserList(userList);
        return new ApiResponse<>(StatusCode.SUCCESS, responseDTOS);
    }

    @Operation(
            summary = "Create a new adoption request",
            description = "Creating an adoption request will only be possible for pets with availability of 1. " +
                    "Successfully created request will automatically be tag as 'Pending'. " +
                    "The system checks if a request was previously initiated by the user to the same pet. " +
                    "If the previous request was rejected, the current request will automatically be rejected. " +
                    "If it was not rejected, the creation of the request will not push through as it will be detected as a duplicate request."
    )
    @PostMapping("/create")
    public ApiResponse<AdoptionRequestResponseDTO> createRequest(@RequestBody AdoptionRequestRequestDTO requestDTO) {
        AdoptionRequest adoptionRequest = buildAdoptionRequestObjectFromAdoptionRequestDto(requestDTO, userDetailsService, petDetailsService);
        AdoptionRequest saveRequest = adoptionRequestService.saveRequest(adoptionRequest);
        AdoptionRequestResponseDTO responseDTO = buildAdoptionResponseDtoFromAdoptionObject(saveRequest);
        return new ApiResponse<>(StatusCode.SUCCESS, responseDTO);
    }

    private List<User> getUsersFromAdoptionRequestList(List<AdoptionRequest> adoptionRequestsByPet) {
        return adoptionRequestsByPet.stream()
                .map(AdoptionRequest::getAdopter)
                .collect(Collectors.toList());
    }

    @Operation(
            summary = "Approve an adoption request",
            description = "Approving will only be successful if the pet is still available for adoption. " +
                    "If a request for the same pet, but from another user, has already been approved, the current request can't be approved but it will be put on hold. " +
                    "However, if there are no conflicts, the request will be approved and the other ongoing requests for the same pet initiated by other users will be put on hold. " +
                    "Similarly, the availability of the pet will be set to 'on-hold'."
    )
    @PostMapping("/approve")
    public ApiResponse<Object> approveRequest(@RequestBody Long requestId) {
        AdoptionRequest adoptionRequest = adoptionRequestService.approveRequest(requestId);
        AdoptionRequestResponseDTO responseDTO = buildAdoptionResponseDtoFromAdoptionObject(adoptionRequest);
        return new ApiResponse<>(StatusCode.SUCCESS, responseDTO);
    }

    @Operation(summary = "Reject the request")
    @PostMapping("/reject")
    public ApiResponse<Object> rejectRequest(@RequestBody Long requestId) {
        AdoptionRequest adoptionRequest = adoptionRequestService.rejectRequest(requestId);
        AdoptionRequestResponseDTO responseDTO = buildAdoptionResponseDtoFromAdoptionObject(adoptionRequest);
        return new ApiResponse<>(StatusCode.SUCCESS, responseDTO);
    }

    @Operation(summary = "Cancel the request")
    @PostMapping("/cancel")
    public ApiResponse<Object> cancelRequest(@RequestBody Long requestId) {
        AdoptionRequest adoptionRequest = adoptionRequestService.cancelRequest(requestId);
        AdoptionRequestResponseDTO responseDTO = buildAdoptionResponseDtoFromAdoptionObject(adoptionRequest);
        return new ApiResponse<>(StatusCode.SUCCESS, responseDTO);
    }

    @Operation(summary = "Put the request on hold")
    @PostMapping("/hold")
    public ApiResponse<Object> holdRequest(@RequestBody Long requestId) {
        AdoptionRequest adoptionRequest = adoptionRequestService.holdRequest(requestId);
        AdoptionRequestResponseDTO responseDTO = buildAdoptionResponseDtoFromAdoptionObject(adoptionRequest);
        return new ApiResponse<>(StatusCode.SUCCESS, responseDTO);
    }

    @Operation(
            summary = "Complete the request",
            description = "Completed requests are requests that have been previously approved and now finalized. " +
                    "Only requests that went through the 'Approved' state can be posted as complete as this is a crucial step in the adoption process. " +
                    "Ultimately, if the current status of the request is rejected, it cannot be completed. " +
                    "After the request is completed, other requests for the same pet initiated by other users will automatically be tagged as 'Closed', " +
                    "and the pet will no longer be available for adoption.")
    @PostMapping("/complete")
    public ApiResponse<Object> completeRequest(@RequestBody Long requestId) {
        AdoptionRequest adoptionRequest = adoptionRequestService.completeRequest(requestId);
        AdoptionRequestResponseDTO responseDTO = buildAdoptionResponseDtoFromAdoptionObject(adoptionRequest);
        return new ApiResponse<>(StatusCode.SUCCESS, responseDTO);
    }

    @PostMapping("/close")
    @Operation(summary = "Close the request")
    public ApiResponse<Object> closeRequest(@RequestBody Long requestId) {
        AdoptionRequest adoptionRequest = adoptionRequestService.closeRequest(requestId);
        AdoptionRequestResponseDTO responseDTO = buildAdoptionResponseDtoFromAdoptionObject(adoptionRequest);
        return new ApiResponse<>(StatusCode.SUCCESS, responseDTO);
    }

}