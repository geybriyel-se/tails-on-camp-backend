package com.geybriyel.tailsoncamp.controller;

import com.geybriyel.tailsoncamp.dto.ApiResponse;
import com.geybriyel.tailsoncamp.dto.ShelterDetailsRequestDTO;
import com.geybriyel.tailsoncamp.dto.ShelterDetailsResponseDTO;
import com.geybriyel.tailsoncamp.entity.Shelter;
import com.geybriyel.tailsoncamp.enums.StatusCode;
import com.geybriyel.tailsoncamp.service.ShelterDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.geybriyel.tailsoncamp.mapper.ShelterMapper.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/shelters")
public class ShelterController {

    private final ShelterDetailsService shelterService;

    @GetMapping("/all")
    public ApiResponse<List<ShelterDetailsResponseDTO>> retrieveAllShelters() {
        List<Shelter> allShelters = shelterService.getAllShelters();
        List<ShelterDetailsResponseDTO> sheltersList = buildListShelterResDtoFromShelterList(allShelters);
        return new ApiResponse<>(StatusCode.SUCCESS, sheltersList);
    }

    @GetMapping("/id")
    public ApiResponse<ShelterDetailsResponseDTO> retrieveShelterById(@RequestBody Long id) {
        Shelter shelterByShelterId = shelterService.getShelterByShelterId(id);
        ShelterDetailsResponseDTO responseDTO = buildShelterResDtoFromShelterObject(shelterByShelterId);
        return new ApiResponse<>(StatusCode.SUCCESS, responseDTO);
    }

    @GetMapping("/city")
    public ApiResponse<List<ShelterDetailsResponseDTO>> retrieveSheltersByCity(@RequestBody String city) {
        List<Shelter> sheltersByCity = shelterService.getSheltersByCity(city);
        if (sheltersByCity.isEmpty()) {
            return new ApiResponse<>(StatusCode.LIST_EMPTY, sheltersByCity);
        }
        List<ShelterDetailsResponseDTO> responseDTOList = buildListShelterResDtoFromShelterList(sheltersByCity);
        return new ApiResponse<>(StatusCode.SUCCESS, responseDTOList);
    }

    @GetMapping("/province")
    public ApiResponse<List<ShelterDetailsResponseDTO>> retrieveSheltersByProvince(@RequestBody String province) {
        List<Shelter> sheltersByProvince = shelterService.getSheltersByProvince(province);
        if (sheltersByProvince.isEmpty()) {
            return new ApiResponse<>(StatusCode.LIST_EMPTY, null);
        }
        List<ShelterDetailsResponseDTO> responseDTOList = buildListShelterResDtoFromShelterList(sheltersByProvince);
        return new ApiResponse<>(StatusCode.SUCCESS, responseDTOList);
    }

    @GetMapping("/name")
    public ApiResponse<ShelterDetailsResponseDTO> retrieveShelterByShelterName(@RequestBody String shelterName) {
        Shelter shelterByShelterName = shelterService.getShelterByShelterName(shelterName);
        ShelterDetailsResponseDTO responseDTO = buildShelterResDtoFromShelterObject(shelterByShelterName);
        return new ApiResponse<>(StatusCode.SUCCESS, responseDTO);
    }

    @PostMapping("/register")
    public ApiResponse<ShelterDetailsResponseDTO> registerShelter(@RequestBody ShelterDetailsRequestDTO shelterDetailsRequestDTO) {
        Shelter shelter = buildShelterObjectFromReqDto(shelterDetailsRequestDTO);
        Shelter savedShelter = shelterService.addShelter(shelter);
        ShelterDetailsResponseDTO responseDTO = buildShelterResDtoFromShelterObject(savedShelter);
        return new ApiResponse<>(StatusCode.SUCCESS, responseDTO);
    }

    @PostMapping("/update")
    public ApiResponse<ShelterDetailsResponseDTO> updateShelterDetails(@RequestBody ShelterDetailsRequestDTO shelterDetailsRequestDTO) {
        Shelter shelter = buildShelterObjectFromReqDto(shelterDetailsRequestDTO);
        Shelter updatedShelter = shelterService.updateShelter(shelter);
        ShelterDetailsResponseDTO responseDTO = buildShelterResDtoFromShelterObject(updatedShelter);
        return new ApiResponse<>(StatusCode.SUCCESS, responseDTO);
    }

    @GetMapping("/all-city")
    public ApiResponse<List<String>> retrieveDistinctCity() {
        List<String> allCity = shelterService.getAllCity();
        return new ApiResponse<>(StatusCode.SUCCESS, allCity);
    }

}
