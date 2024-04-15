package com.geybriyel.tailsoncamp.controller;

import com.geybriyel.tailsoncamp.dto.ApiResponse;
import com.geybriyel.tailsoncamp.dto.PetDetailsRequestDTO;
import com.geybriyel.tailsoncamp.dto.PetDetailsResponseDTO;
import com.geybriyel.tailsoncamp.entity.Pet;
import com.geybriyel.tailsoncamp.entity.Shelter;
import com.geybriyel.tailsoncamp.enums.StatusCode;
import com.geybriyel.tailsoncamp.service.PetDetailsService;
import com.geybriyel.tailsoncamp.service.ShelterDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/pets")
@RequiredArgsConstructor
public class PetController {

    private final PetDetailsService petService;

    private final ShelterDetailsService shelterService;

    @GetMapping("/all")
    public ApiResponse<List<PetDetailsResponseDTO>> retrieveAllPets() {
        List<Pet> allPets = petService.getAllPets();
        List<PetDetailsResponseDTO> petsList = buildListPetResponseDtoFromPetList(allPets);
        return new ApiResponse<>(StatusCode.SUCCESS, petsList);
    }

    @GetMapping("/id")
    public ApiResponse<PetDetailsResponseDTO> retrievePetById(@RequestBody Long id) {
        Optional<Pet> petByPetId = petService.getPetByPetId(id);
        Pet pet = petByPetId.get();
        PetDetailsResponseDTO petDto = buildPetResponseDtoFromPetObject(pet);
        return new ApiResponse<>(StatusCode.SUCCESS, petDto);
    }

/*    @GetMapping("/adopter")
    public ApiResponse<List<PetDetailsResponseDTO>> retrievePetsByAdopter(@RequestBody User user) {
        List<Pet> petsByAdopter = petService.getPetsByAdopter(user);
        List<PetDetailsResponseDTO> petsList = buildListPetResponseDtoFromPetList(petsByAdopter);
        return new ApiResponse<>(StatusCode.SUCCESS, petsList);
    }*/

/*    @GetMapping("/shelter")
    public ApiResponse<List<PetDetailsResponseDTO>> retrievePetsByShelter(@RequestBody Shelter shelter) {
        List<Pet> petsByShelter = petService.getPetsByShelter(shelter);
        List<PetDetailsResponseDTO> petsList = buildListPetResponseDtoFromPetList(petsByShelter);
        return new ApiResponse<>(StatusCode.SUCCESS, petsList);
    }*/

    @GetMapping("/breed")
    public ApiResponse<List<PetDetailsResponseDTO>> retrievePetsByBreed(@RequestBody String breed) {
        List<Pet> petsByBreed = petService.getPetsByBreed(breed);
        List<PetDetailsResponseDTO> petsList = buildListPetResponseDtoFromPetList(petsByBreed);
        return new ApiResponse<>(StatusCode.SUCCESS, petsList);
    }

    @PostMapping("/register")
    public ApiResponse<PetDetailsResponseDTO> registerPet(@RequestBody PetDetailsRequestDTO petDetailsRequestDTO) {
        Pet pet = buildPetFromPetRequestDto(petDetailsRequestDTO);
        Pet addedPet = petService.addPet(pet);
        PetDetailsResponseDTO petResponse = buildPetResponseDtoFromPetObject(addedPet);
        return new ApiResponse<>(StatusCode.SUCCESS, petResponse);
    }

    @PostMapping("/update")
    public ApiResponse<PetDetailsResponseDTO> updatePetDetails(@RequestBody PetDetailsRequestDTO pet) {
        Pet petToUpdate = buildPetFromPetRequestDto(pet);
        Pet updatedPet = petService.updatePet(petToUpdate);
        PetDetailsResponseDTO petResponse = buildPetResponseDtoFromPetObject(updatedPet);
        return new ApiResponse<>(StatusCode.SUCCESS, petResponse);
    }


    private Pet buildPetFromPetRequestDto(PetDetailsRequestDTO petDetailsRequestDTO) {
        Optional<Shelter> shelterByShelterId = shelterService.getShelterByShelterId(petDetailsRequestDTO.getShelterId());
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
        pet.setShelter(shelterByShelterId.get());
        return pet;
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
