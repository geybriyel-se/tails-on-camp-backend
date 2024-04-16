package com.geybriyel.tailsoncamp.controller;

import com.geybriyel.tailsoncamp.dto.ApiResponse;
import com.geybriyel.tailsoncamp.dto.ShelterDetailsRequestDTO;
import com.geybriyel.tailsoncamp.dto.ShelterDetailsResponseDTO;
import com.geybriyel.tailsoncamp.entity.Shelter;
import com.geybriyel.tailsoncamp.enums.StatusCode;
import com.geybriyel.tailsoncamp.exception.*;
import com.geybriyel.tailsoncamp.service.ShelterDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
        try {
            Shelter shelterByShelterId = shelterService.getShelterByShelterId(id);
            ShelterDetailsResponseDTO responseDTO = buildShelterResDtoFromShelterObject(shelterByShelterId);
            return new ApiResponse<>(StatusCode.SUCCESS, responseDTO);
        } catch (InvalidShelterIdException e) {
            return new ApiResponse<>(e.getStatusCode(), null);
        } catch (Exception e) {
            return new ApiResponse<>(StatusCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/city")
    public ApiResponse<List<ShelterDetailsResponseDTO>> retrieveSheltersByCity(@RequestBody String city) {
        try {
            List<Shelter> sheltersByCity = shelterService.getSheltersByCity(city);
            if (sheltersByCity.isEmpty()) {
                return new ApiResponse<>(StatusCode.LIST_EMPTY, sheltersByCity);
            }
            List<ShelterDetailsResponseDTO> responseDTOList = buildListShelterResDtoFromShelterList(sheltersByCity);
            return new ApiResponse<>(StatusCode.SUCCESS, responseDTOList);
        } catch (InvalidCityException e) {
            return new ApiResponse<>(e.getStatusCode(), null);
        } catch (Exception e) {
            return new ApiResponse<>(StatusCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/province")
    public ApiResponse<List<ShelterDetailsResponseDTO>> retrieveSheltersByProvince(@RequestBody String province) {
        try {
            List<Shelter> sheltersByProvince = shelterService.getSheltersByProvince(province);
            if (sheltersByProvince.isEmpty()) {
                return new ApiResponse<>(StatusCode.LIST_EMPTY, null);
            }
            List<ShelterDetailsResponseDTO> responseDTOList = buildListShelterResDtoFromShelterList(sheltersByProvince);
            return new ApiResponse<>(StatusCode.SUCCESS, responseDTOList);
        } catch (InvalidProvinceException e) {
            return new ApiResponse<>(e.getStatusCode(), null);
        } catch (Exception e) {
            return new ApiResponse<>(StatusCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/name")
    public ApiResponse<ShelterDetailsResponseDTO> retrieveShelterByShelterName(@RequestBody String shelterName) {
        try {
            Shelter shelterByShelterName = shelterService.getShelterByShelterName(shelterName);
            ShelterDetailsResponseDTO responseDTO = buildShelterResDtoFromShelterObject(shelterByShelterName);
            return new ApiResponse<>(StatusCode.SUCCESS, responseDTO);
        } catch (InvalidShelterNameException e) {
            return new ApiResponse<>(e.getStatusCode(), null);
        } catch (Exception e) {
            return new ApiResponse<>(StatusCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/register")
    public ApiResponse<ShelterDetailsResponseDTO> registerShelter(@RequestBody ShelterDetailsRequestDTO shelterDetailsRequestDTO) {
        Shelter shelter = buildShelterObjectFromReqDto(shelterDetailsRequestDTO);
        try {
            Shelter savedShelter = shelterService.addShelter(shelter);
            ShelterDetailsResponseDTO responseDTO = buildShelterResDtoFromShelterObject(savedShelter);
            return new ApiResponse<>(StatusCode.SUCCESS, responseDTO);
        } catch (DuplicateShelterException e) {
            return new ApiResponse<>(e.getStatusCode(), null);
        } catch (Exception e) {
            return new ApiResponse<>(StatusCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/update")
    public ApiResponse<ShelterDetailsResponseDTO> updateShelterDetails(@RequestBody ShelterDetailsRequestDTO shelterDetailsRequestDTO) {
        Shelter shelter = buildShelterObjectFromReqDto(shelterDetailsRequestDTO);
        try {
            Shelter updatedShelter = shelterService.updateShelter(shelter);
            ShelterDetailsResponseDTO responseDTO = buildShelterResDtoFromShelterObject(updatedShelter);
            return new ApiResponse<>(StatusCode.SUCCESS, responseDTO);
        } catch (InvalidShelterIdException e) {
            return new ApiResponse<>(e.getStatusCode(), null);
        } catch (Exception e) {
            return new ApiResponse<>(StatusCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/all-city")
    public ApiResponse<List<String>> retrieveDistinctCity() {
        List<String> allCity = shelterService.getAllCity();
        return new ApiResponse<>(StatusCode.SUCCESS, allCity);
    }


    private Shelter buildShelterObjectFromReqDto(ShelterDetailsRequestDTO shelterDetailsRequestDTO) {
        Shelter shelter = new Shelter();
        shelter.setShelterId(shelterDetailsRequestDTO.getShelterId());
        shelter.setShelterName(shelterDetailsRequestDTO.getShelterName());
        shelter.setLotBlockHouseBldgNo(shelterDetailsRequestDTO.getLotBlockHouseBldgNo());
        shelter.setStreet(shelterDetailsRequestDTO.getStreet());
        shelter.setSubdivisionVillage(shelterDetailsRequestDTO.getSubdivisionVillage());
        shelter.setBarangay(shelterDetailsRequestDTO.getBarangay());
        shelter.setCity(shelterDetailsRequestDTO.getCity());
        shelter.setProvince(shelterDetailsRequestDTO.getProvince());
        shelter.setCountry(shelterDetailsRequestDTO.getCountry());
        shelter.setZipcode(shelterDetailsRequestDTO.getZipcode());
        shelter.setContactNumber(shelterDetailsRequestDTO.getContactNumber());
        shelter.setEmail(shelterDetailsRequestDTO.getEmail());
        shelter.setWebsite(shelterDetailsRequestDTO.getWebsite());
        return shelter;

    }

    private List<ShelterDetailsResponseDTO> buildListShelterResDtoFromShelterList(List<Shelter> allShelters) {
        return allShelters.stream()
                .map(this::buildShelterResDtoFromShelterObject)
                .collect(Collectors.toList());
    }

    private ShelterDetailsResponseDTO buildShelterResDtoFromShelterObject(Shelter shelter) {
        return ShelterDetailsResponseDTO.builder()
                .shelterId(shelter.getShelterId())
                .shelterName(shelter.getShelterName())
                .lotBlockHouseBldgNo(shelter.getLotBlockHouseBldgNo())
                .street(shelter.getStreet())
                .subdivisionVillage(shelter.getSubdivisionVillage())
                .barangay(shelter.getBarangay())
                .city(shelter.getCity())
                .province(shelter.getProvince())
                .country(shelter.getCountry())
                .zipcode(shelter.getZipcode())
                .contactNumber(shelter.getContactNumber())
                .email(shelter.getEmail())
                .website(shelter.getWebsite())
                .createdAt(shelter.getCreatedAt())
                .build();
    }

}
