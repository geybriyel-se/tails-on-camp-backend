package com.geybriyel.tailsoncamp.mapper;

import com.geybriyel.tailsoncamp.dto.PetDetailsRequestDTO;
import com.geybriyel.tailsoncamp.dto.PetDetailsResponseDTO;
import com.geybriyel.tailsoncamp.entity.Pet;
import com.geybriyel.tailsoncamp.entity.Shelter;
import com.geybriyel.tailsoncamp.service.ShelterDetailsService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PetMapper {

    public static Pet buildPetFromPetRequestDto(PetDetailsRequestDTO petDetailsRequestDTO, Shelter shelter) {
//        Shelter shelterByShelterId = shelterDetailsService.getShelterByShelterId(petDetailsRequestDTO.getShelterId());
        Pet pet = new Pet();
        pet.setId(petDetailsRequestDTO.getId());
        pet.setName(petDetailsRequestDTO.getName());
        pet.setType(petDetailsRequestDTO.getType());
        pet.setBreed(petDetailsRequestDTO.getBreed());
        pet.setAge(petDetailsRequestDTO.getAge());
        pet.setGender(petDetailsRequestDTO.getGender());
        pet.setSize(petDetailsRequestDTO.getSize());
        pet.setDescription(petDetailsRequestDTO.getDescription());
        pet.setImageUrl(petDetailsRequestDTO.getImageUrl());
        pet.setAvailability(petDetailsRequestDTO.getAvailability());
        pet.setShelter(shelter);
        return pet;
    }

    public static PetDetailsResponseDTO buildPetResponseDtoFromPetObject(Pet pet) {
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

    public static List<PetDetailsResponseDTO> buildListPetResponseDtoFromPetList(List<Pet> allPets) {
        return allPets.stream()
                .map(PetMapper::buildPetResponseDtoFromPetObject)
                .collect(Collectors.toList());
    }
}
