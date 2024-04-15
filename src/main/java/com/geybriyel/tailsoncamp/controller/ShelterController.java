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
import java.util.Optional;
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
        Optional<Shelter> shelterByShelterId = shelterService.getShelterByShelterId(id);
        ShelterDetailsResponseDTO responseDTO = buildShelterResDtoFromShelterObject(shelterByShelterId.get());
        return new ApiResponse<>(StatusCode.SUCCESS, responseDTO);
    }

    @GetMapping("/city")
    public ApiResponse<List<ShelterDetailsResponseDTO>> retrieveSheltersByCity(@RequestBody String city) {
        List<Shelter> sheltersByCity = shelterService.getSheltersByCity(city);
        List<ShelterDetailsResponseDTO> responseDTOList = buildListShelterResDtoFromShelterList(sheltersByCity);
        return new ApiResponse<>(StatusCode.SUCCESS, responseDTOList);
    }

    @GetMapping("/province")
    public ApiResponse<List<ShelterDetailsResponseDTO>> retrieveSheltersByProvince(@RequestBody String province) {
        List<Shelter> sheltersByProvince = shelterService.getSheltersByProvince(province);
        List<ShelterDetailsResponseDTO> responseDTOList = buildListShelterResDtoFromShelterList(sheltersByProvince);
        return new ApiResponse<>(StatusCode.SUCCESS, responseDTOList);
    }

    @GetMapping("/name")
    public ApiResponse<ShelterDetailsResponseDTO> retrieveShelterByShelterName(@RequestBody String shelterName) {
        Optional<Shelter> shelterByShelterName = shelterService.getShelterByShelterName(shelterName);
        ShelterDetailsResponseDTO responseDTO = buildShelterResDtoFromShelterObject(shelterByShelterName.get());
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
                .build();
    }

}
