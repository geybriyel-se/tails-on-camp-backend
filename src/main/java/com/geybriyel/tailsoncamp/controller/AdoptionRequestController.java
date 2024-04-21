package com.geybriyel.tailsoncamp.controller;

import com.geybriyel.tailsoncamp.dto.*;
import com.geybriyel.tailsoncamp.entity.AdoptionRequest;
import com.geybriyel.tailsoncamp.entity.Pet;
import com.geybriyel.tailsoncamp.entity.User;
import com.geybriyel.tailsoncamp.enums.StatusCode;
import com.geybriyel.tailsoncamp.service.AdoptionRequestService;
import com.geybriyel.tailsoncamp.service.PetDetailsService;
import com.geybriyel.tailsoncamp.service.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        AdoptionRequest adoptionRequestByAdoptionId = adoptionRequestService.getAdoptionRequestByAdoptionId(id);
        AdoptionRequestResponseDTO responseDTO = buildAdoptionResponseDtoFromAdoptionObject(adoptionRequestByAdoptionId);
        return new ApiResponse<>(StatusCode.SUCCESS, responseDTO);
    }

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

    @PostMapping("/approve")
    public ApiResponse<Object> approveRequest(@RequestBody Long requestId) {
        AdoptionRequest adoptionRequest = adoptionRequestService.approveRequest(requestId);
        AdoptionRequestResponseDTO responseDTO = buildAdoptionResponseDtoFromAdoptionObject(adoptionRequest);
        return new ApiResponse<>(StatusCode.SUCCESS, responseDTO);
    }

    @PostMapping("/reject")
    public ApiResponse<Object> rejectRequest(@RequestBody Long requestId) {
        AdoptionRequest adoptionRequest = adoptionRequestService.rejectRequest(requestId);
        AdoptionRequestResponseDTO responseDTO = buildAdoptionResponseDtoFromAdoptionObject(adoptionRequest);
        return new ApiResponse<>(StatusCode.SUCCESS, responseDTO);
    }

    @PostMapping("/cancel")
    public ApiResponse<Object> cancelRequest(@RequestBody Long requestId) {
        AdoptionRequest adoptionRequest = adoptionRequestService.cancelRequest(requestId);
        AdoptionRequestResponseDTO responseDTO = buildAdoptionResponseDtoFromAdoptionObject(adoptionRequest);
        return new ApiResponse<>(StatusCode.SUCCESS, responseDTO);
    }

    @PostMapping("/hold")
    public ApiResponse<Object> holdRequest(@RequestBody Long requestId) {
        AdoptionRequest adoptionRequest = adoptionRequestService.holdRequest(requestId);
        AdoptionRequestResponseDTO responseDTO = buildAdoptionResponseDtoFromAdoptionObject(adoptionRequest);
        return new ApiResponse<>(StatusCode.SUCCESS, responseDTO);
    }

    @PostMapping("/complete")
    public ApiResponse<Object> completeRequest(@RequestBody Long requestId) {
        AdoptionRequest adoptionRequest = adoptionRequestService.completeRequest(requestId);
        AdoptionRequestResponseDTO responseDTO = buildAdoptionResponseDtoFromAdoptionObject(adoptionRequest);
        return new ApiResponse<>(StatusCode.SUCCESS, responseDTO);
    }

    @PostMapping("/close")
    public ApiResponse<Object> closeRequest(@RequestBody Long requestId) {
        AdoptionRequest adoptionRequest = adoptionRequestService.closeRequest(requestId);
        AdoptionRequestResponseDTO responseDTO = buildAdoptionResponseDtoFromAdoptionObject(adoptionRequest);
        return new ApiResponse<>(StatusCode.SUCCESS, responseDTO);
    }

}