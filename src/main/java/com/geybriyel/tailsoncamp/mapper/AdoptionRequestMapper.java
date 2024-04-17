package com.geybriyel.tailsoncamp.mapper;

import com.geybriyel.tailsoncamp.dto.AdoptionRequestRequestDTO;
import com.geybriyel.tailsoncamp.dto.AdoptionRequestResponseDTO;
import com.geybriyel.tailsoncamp.dto.PetDetailsResponseDTO;
import com.geybriyel.tailsoncamp.dto.UserResponseDTO;
import com.geybriyel.tailsoncamp.entity.AdoptionRequest;
import com.geybriyel.tailsoncamp.entity.Pet;
import com.geybriyel.tailsoncamp.entity.User;
import com.geybriyel.tailsoncamp.exception.InvalidPetIdException;
import com.geybriyel.tailsoncamp.exception.InvalidUserIdException;
import com.geybriyel.tailsoncamp.service.PetDetailsService;
import com.geybriyel.tailsoncamp.service.impl.UserDetailsServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

import static com.geybriyel.tailsoncamp.mapper.PetMapper.buildPetResponseDtoFromPetObject;
import static com.geybriyel.tailsoncamp.mapper.UserMapper.buildUserResponseDtoFromUserObject;

public class AdoptionRequestMapper {

    public static AdoptionRequestResponseDTO buildAdoptionResponseDtoFromAdoptionObject(AdoptionRequest req) {
        PetDetailsResponseDTO petResDto = buildPetResponseDtoFromPetObject(req.getPet());
        UserResponseDTO userResDto = buildUserResponseDtoFromUserObject(req.getAdopter());

        return AdoptionRequestResponseDTO.builder()
                .pet(petResDto)
                .adopter(userResDto)
                .status(req.getStatus())
                .createdAt(req.getCreatedAt())
                .updatedAt(req.getUpdatedAt())
                .build();
    }

    public static List<AdoptionRequestResponseDTO> buildListResponseDtoFromObjectList(List<AdoptionRequest> allAdoptionRequests) {
        return allAdoptionRequests.stream()
                .map(AdoptionRequestMapper::buildAdoptionResponseDtoFromAdoptionObject)
                .collect(Collectors.toList());
    }

    // TODO: Find a way to not pass services as arguments. this is against dependency injection
    public static AdoptionRequest buildAdoptionRequestObjectFromAdoptionRequestDto(AdoptionRequestRequestDTO request, UserDetailsServiceImpl userDetailsService, PetDetailsService petDetailsService) throws InvalidUserIdException, InvalidPetIdException {
        try {
            User user = userDetailsService.getUserById(request.getUserId());
            Pet pet = petDetailsService.getPetByPetId(request.getPetId());
            AdoptionRequest adoptionRequest = new AdoptionRequest();
            adoptionRequest.setAdopter(user);
            adoptionRequest.setPet(pet);
            return adoptionRequest;
        } catch (InvalidUserIdException | InvalidPetIdException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error building adoption request from DTO: " + e.getMessage(), e);
        }
    }

}
