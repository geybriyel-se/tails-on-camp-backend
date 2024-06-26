package com.geybriyel.tailsoncamp.controller;

import com.geybriyel.tailsoncamp.dto.ApiResponse;
import com.geybriyel.tailsoncamp.dto.PetDetailsRequestDTO;
import com.geybriyel.tailsoncamp.dto.PetDetailsResponseDTO;
import com.geybriyel.tailsoncamp.entity.Pet;
import com.geybriyel.tailsoncamp.entity.Shelter;
import com.geybriyel.tailsoncamp.enums.StatusCode;
import com.geybriyel.tailsoncamp.service.PetDetailsService;
import com.geybriyel.tailsoncamp.service.ShelterDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import static com.geybriyel.tailsoncamp.mapper.PetMapper.*;

@Slf4j
@RestController
@RequestMapping("/pets")
@RequiredArgsConstructor
public class PetController {

    private final PetDetailsService petService;
    private final ShelterDetailsService shelterService;

    @Operation(summary = "Retrieve all pets in the database")
    @GetMapping("/all")
    public ApiResponse<List<PetDetailsResponseDTO>> retrieveAllPets() {
        List<Pet> allPets = petService.getAllPets();
        if (allPets.isEmpty()) {
            return new ApiResponse<>(StatusCode.LIST_EMPTY, allPets);
        }
        List<PetDetailsResponseDTO> petsList = buildListPetResponseDtoFromPetList(allPets);
        return new ApiResponse<>(StatusCode.SUCCESS, petsList);
    }

    @Operation(summary = "Retrieve a pet by its ID")
    @GetMapping("/id")
    public ApiResponse<PetDetailsResponseDTO> retrievePetById(@RequestBody Long id) {
        Pet petByPetId = petService.getPetByPetId(id);
        PetDetailsResponseDTO petDto = buildPetResponseDtoFromPetObject(petByPetId);
        return new ApiResponse<>(StatusCode.SUCCESS, petDto);
    }

    @Operation(summary = "Retrieve all pets in the database by its breed")
    @GetMapping("/breed")
    public ApiResponse<List<PetDetailsResponseDTO>> retrievePetsByBreed(@RequestBody String breed) {
        List<Pet> petsByBreed = petService.getPetsByBreed(breed);
        if (petsByBreed.isEmpty()) {
            return new ApiResponse<>(StatusCode.LIST_EMPTY, petsByBreed);
        }
        List<PetDetailsResponseDTO> petsList = buildListPetResponseDtoFromPetList(petsByBreed);
        return new ApiResponse<>(StatusCode.SUCCESS, petsList);
    }

    @Operation(
            summary = "Register a new pet",
            description = "Duplicate pets will not be added to the database."
    )
    @PostMapping("/register")
    public ApiResponse<PetDetailsResponseDTO> registerPet(@Valid @RequestBody PetDetailsRequestDTO petDetailsRequestDTO) {
        Shelter shelter = shelterService.getShelterByShelterId(petDetailsRequestDTO.getShelterId());
        Pet pet = buildPetFromPetRequestDto(petDetailsRequestDTO, shelter);
        Pet addedPet = petService.addPet(pet);
        PetDetailsResponseDTO petResponse = buildPetResponseDtoFromPetObject(addedPet);
        return new ApiResponse<>(StatusCode.SUCCESS, petResponse);
    }

    @Operation(summary = "Update the details of the pet")
    @PostMapping("/update")
    public ApiResponse<PetDetailsResponseDTO> updatePetDetails(@RequestBody PetDetailsRequestDTO pet) {
        Shelter shelter = shelterService.getShelterByShelterId(pet.getShelterId());
        Pet petToUpdate = buildPetFromPetRequestDto(pet, shelter);
        Pet updatedPet = petService.updatePet(petToUpdate);
        PetDetailsResponseDTO petResponse = buildPetResponseDtoFromPetObject(updatedPet);
        return new ApiResponse<>(StatusCode.SUCCESS, petResponse);
    }

    @Operation(summary = "Retrieve all the breeds of the pets present in the database")
    @GetMapping("/all-breeds")
    public ApiResponse<Set<String>> retrieveAllBreeds() {
        List<String> allPetBreeds = petService.getAllPetBreeds();
        return new ApiResponse<>(StatusCode.SUCCESS, allPetBreeds);
    }

}
