package com.geybriyel.tailsoncamp.controller;

import com.geybriyel.tailsoncamp.dto.ApiResponse;
import com.geybriyel.tailsoncamp.dto.ShelterDetailsRequestDTO;
import com.geybriyel.tailsoncamp.dto.ShelterDetailsResponseDTO;
import com.geybriyel.tailsoncamp.entity.Shelter;
import com.geybriyel.tailsoncamp.enums.StatusCode;
import com.geybriyel.tailsoncamp.service.ShelterDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.geybriyel.tailsoncamp.mapper.ShelterMapper.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/shelters")
public class ShelterController {

    private final ShelterDetailsService shelterService;

    @Operation(summary = "Retrieve all shelters and their details")
    @GetMapping("/all")
    public ApiResponse<List<ShelterDetailsResponseDTO>> retrieveAllShelters() {
        List<Shelter> allShelters = shelterService.getAllShelters();
        List<ShelterDetailsResponseDTO> sheltersList = buildListShelterResDtoFromShelterList(allShelters);
        return new ApiResponse<>(StatusCode.SUCCESS, sheltersList);
    }

    @Operation(summary = "Retrieve all shelters by ID")
    @GetMapping("/id")
    public ApiResponse<ShelterDetailsResponseDTO> retrieveShelterById(@RequestBody Long id) {
        Shelter shelterByShelterId = shelterService.getShelterByShelterId(id);
        ShelterDetailsResponseDTO responseDTO = buildShelterResDtoFromShelterObject(shelterByShelterId);
        return new ApiResponse<>(StatusCode.SUCCESS, responseDTO);
    }

    @Operation(summary = "Retrieve all shelters by city")
    @GetMapping("/city")
    public ApiResponse<List<ShelterDetailsResponseDTO>> retrieveSheltersByCity(@RequestBody String city) {
        List<Shelter> sheltersByCity = shelterService.getSheltersByCity(city);
        if (sheltersByCity.isEmpty()) {
            return new ApiResponse<>(StatusCode.LIST_EMPTY, sheltersByCity);
        }
        List<ShelterDetailsResponseDTO> responseDTOList = buildListShelterResDtoFromShelterList(sheltersByCity);
        return new ApiResponse<>(StatusCode.SUCCESS, responseDTOList);
    }

    @Operation(summary = "Retrieve all shelters by province")
    @GetMapping("/province")
    public ApiResponse<List<ShelterDetailsResponseDTO>> retrieveSheltersByProvince(@RequestBody String province) {
        List<Shelter> sheltersByProvince = shelterService.getSheltersByProvince(province);
        if (sheltersByProvince.isEmpty()) {
            return new ApiResponse<>(StatusCode.LIST_EMPTY, null);
        }
        List<ShelterDetailsResponseDTO> responseDTOList = buildListShelterResDtoFromShelterList(sheltersByProvince);
        return new ApiResponse<>(StatusCode.SUCCESS, responseDTOList);
    }

    @Operation(summary = "Retrieve a shelter by its name")
    @GetMapping("/name")
    public ApiResponse<ShelterDetailsResponseDTO> retrieveShelterByShelterName(@RequestBody String shelterName) {
        Shelter shelterByShelterName = shelterService.getShelterByShelterName(shelterName);
        ShelterDetailsResponseDTO responseDTO = buildShelterResDtoFromShelterObject(shelterByShelterName);
        return new ApiResponse<>(StatusCode.SUCCESS, responseDTO);
    }

    @Operation(summary = "Register a new shelter")
    @PostMapping("/register")
    public ApiResponse<ShelterDetailsResponseDTO> registerShelter(@RequestBody ShelterDetailsRequestDTO shelterDetailsRequestDTO) {
        Shelter shelter = buildShelterObjectFromReqDto(shelterDetailsRequestDTO);
        Shelter savedShelter = shelterService.addShelter(shelter);
        ShelterDetailsResponseDTO responseDTO = buildShelterResDtoFromShelterObject(savedShelter);
        return new ApiResponse<>(StatusCode.SUCCESS, responseDTO);
    }

    @Operation(summary = "Update an existing shelter's details")
    @PostMapping("/update")
    public ApiResponse<ShelterDetailsResponseDTO> updateShelterDetails(@RequestBody ShelterDetailsRequestDTO shelterDetailsRequestDTO) {
        Shelter shelter = buildShelterObjectFromReqDto(shelterDetailsRequestDTO);
        Shelter updatedShelter = shelterService.updateShelter(shelter);
        ShelterDetailsResponseDTO responseDTO = buildShelterResDtoFromShelterObject(updatedShelter);
        return new ApiResponse<>(StatusCode.SUCCESS, responseDTO);
    }

    @Operation(summary = "Retrieve all cities where the shelters are")
    @GetMapping("/all-city")
    public ApiResponse<List<String>> retrieveDistinctCity() {
        List<String> allCity = shelterService.getAllCity();
        return new ApiResponse<>(StatusCode.SUCCESS, allCity);
    }

}
