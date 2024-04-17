package com.geybriyel.tailsoncamp.mapper;

import com.geybriyel.tailsoncamp.dto.ShelterDetailsRequestDTO;
import com.geybriyel.tailsoncamp.dto.ShelterDetailsResponseDTO;
import com.geybriyel.tailsoncamp.entity.Shelter;

import java.util.List;
import java.util.stream.Collectors;

public class ShelterMapper {
    public static Shelter buildShelterObjectFromReqDto(ShelterDetailsRequestDTO shelterDetailsRequestDTO) {
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

    public static ShelterDetailsResponseDTO buildShelterResDtoFromShelterObject(Shelter shelter) {
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

    public static List<ShelterDetailsResponseDTO> buildListShelterResDtoFromShelterList(List<Shelter> allShelters) {
        return allShelters.stream()
                .map(ShelterMapper::buildShelterResDtoFromShelterObject)
                .collect(Collectors.toList());
    }
}
