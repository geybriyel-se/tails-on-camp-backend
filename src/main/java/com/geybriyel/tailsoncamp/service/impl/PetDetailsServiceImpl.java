package com.geybriyel.tailsoncamp.service.impl;

import com.geybriyel.tailsoncamp.entity.Pet;
import com.geybriyel.tailsoncamp.entity.Shelter;
import com.geybriyel.tailsoncamp.entity.User;
import com.geybriyel.tailsoncamp.exception.InvalidBreedException;
import com.geybriyel.tailsoncamp.exception.PetNotFoundException;
import com.geybriyel.tailsoncamp.repository.PetRepository;
import com.geybriyel.tailsoncamp.service.PetDetailsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetDetailsServiceImpl implements PetDetailsService {

    private final PetRepository petRepository;

    @Override
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    @Override
    public Pet getPetByPetId(Long petId) {
        return petRepository.findPetById(petId)
                .orElseThrow(PetNotFoundException::new);
    }

    @Override
    public List<Pet> getPetsByAdopter(User user) {
        return petRepository.findPetByAdopter(user);
    }

    @Override
    public List<Pet> getPetsByShelter(Shelter shelter) {
        return petRepository.findByShelter(shelter);
    }

    @Override
    public List<Pet> getPetsByBreed(String breed) {
        if (getAllPetBreeds().contains(breed)) {
            return petRepository.findByBreed(breed);
        } else {
            throw new InvalidBreedException();
        }
    }

    @Transactional
    @Override
    public Pet addPet(Pet pet) {
        return petRepository.save(pet);
    }

    @Override
    public Pet updatePet(Pet pet) {
        Pet petByPetId = getPetByPetId(pet.getId());
        petByPetId.setName(pet.getName());
        petByPetId.setType(pet.getType());
        petByPetId.setBreed(pet.getBreed());
        petByPetId.setAge(pet.getAge());
        petByPetId.setGender(pet.getGender());
        petByPetId.setSize(pet.getSize());
        petByPetId.setDescription(pet.getDescription());
        petByPetId.setImageUrl(pet.getImageUrl());
        petByPetId.setAvailability(pet.getAvailability());
        petByPetId.setShelter(pet.getShelter());
        petByPetId.setAdopter(pet.getAdopter());

        return petRepository.save(petByPetId);
    }

    @Override
    public List<String> getAllPetBreeds() {
        return petRepository.findDistinctBreed();
    }

}
