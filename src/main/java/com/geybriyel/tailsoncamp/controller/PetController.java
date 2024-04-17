package com.geybriyel.tailsoncamp.controller;

import com.geybriyel.tailsoncamp.dto.ApiResponse;
import com.geybriyel.tailsoncamp.dto.PetDetailsRequestDTO;
import com.geybriyel.tailsoncamp.dto.PetDetailsResponseDTO;
import com.geybriyel.tailsoncamp.entity.Pet;
import com.geybriyel.tailsoncamp.entity.Shelter;
import com.geybriyel.tailsoncamp.enums.StatusCode;
import com.geybriyel.tailsoncamp.exception.DuplicatePetException;
import com.geybriyel.tailsoncamp.exception.InvalidBreedException;
import com.geybriyel.tailsoncamp.exception.InvalidPetIdException;
import com.geybriyel.tailsoncamp.service.PetDetailsService;
import com.geybriyel.tailsoncamp.service.ShelterDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import static com.geybriyel.tailsoncamp.mapper.PetMapper.*;

@RestController
@RequestMapping("/v1/pets")
@RequiredArgsConstructor
public class PetController {

    private final PetDetailsService petService;

    private final ShelterDetailsService shelterService;

    @GetMapping("/all")
    public ApiResponse<List<PetDetailsResponseDTO>> retrieveAllPets() {
        List<Pet> allPets = petService.getAllPets();
        if (allPets.isEmpty()) {
            return new ApiResponse<>(StatusCode.LIST_EMPTY, allPets);
        }
        List<PetDetailsResponseDTO> petsList = buildListPetResponseDtoFromPetList(allPets);
        return new ApiResponse<>(StatusCode.SUCCESS, petsList);
    }

    @GetMapping("/id")
    public ApiResponse<PetDetailsResponseDTO> retrievePetById(@RequestBody Long id) {
        try {
            Pet petByPetId = petService.getPetByPetId(id);
            PetDetailsResponseDTO petDto = buildPetResponseDtoFromPetObject(petByPetId);
            return new ApiResponse<>(StatusCode.SUCCESS, petDto);
        } catch (InvalidPetIdException exception) {
            return new ApiResponse<>(exception.getStatusCode(), null);
        } catch (Exception e) {
            return new ApiResponse<>(StatusCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/breed")
    public ApiResponse<List<PetDetailsResponseDTO>> retrievePetsByBreed(@RequestBody String breed) {
        try {
            List<Pet> petsByBreed = petService.getPetsByBreed(breed);
            if (petsByBreed.isEmpty()) {
                return new ApiResponse<>(StatusCode.LIST_EMPTY, petsByBreed);
            }
            List<PetDetailsResponseDTO> petsList = buildListPetResponseDtoFromPetList(petsByBreed);
            return new ApiResponse<>(StatusCode.SUCCESS, petsList);
        } catch (InvalidBreedException e) {
            return new ApiResponse<>(e.getStatusCode(), null);
        } catch (Exception e) {
            return new ApiResponse<>(StatusCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/register")
    public ApiResponse<PetDetailsResponseDTO> registerPet(@RequestBody PetDetailsRequestDTO petDetailsRequestDTO) {
        Shelter shelter = shelterService.getShelterByShelterId(petDetailsRequestDTO.getShelterId());
        Pet pet = buildPetFromPetRequestDto(petDetailsRequestDTO, shelter);
        try {
            Pet addedPet = petService.addPet(pet);
            PetDetailsResponseDTO petResponse = buildPetResponseDtoFromPetObject(addedPet);
            return new ApiResponse<>(StatusCode.SUCCESS, petResponse);
        } catch (DuplicatePetException e) {
            return new ApiResponse<>(e.getStatusCode(), null);
        } catch (Exception e) {
            return new ApiResponse<>(StatusCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/update")
    public ApiResponse<PetDetailsResponseDTO> updatePetDetails(@RequestBody PetDetailsRequestDTO pet) {
        Shelter shelter = shelterService.getShelterByShelterId(pet.getShelterId());
        Pet petToUpdate = buildPetFromPetRequestDto(pet, shelter);
        try {
            Pet updatedPet = petService.updatePet(petToUpdate);
            PetDetailsResponseDTO petResponse = buildPetResponseDtoFromPetObject(updatedPet);
            return new ApiResponse<>(StatusCode.SUCCESS, petResponse);
        } catch (InvalidPetIdException e) {
            return new ApiResponse<>(e.getStatusCode(), null);
        } catch (Exception e) {
            return new ApiResponse<>(StatusCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * @return A List of Strings that contains all the distinct pet breeds in the database
     */
    @GetMapping("/all-breeds")
    public ApiResponse<Set<String>> retrieveAllBreeds() {
        List<String> allPetBreeds = petService.getAllPetBreeds();
        return new ApiResponse<>(StatusCode.SUCCESS, allPetBreeds);
    }

}
