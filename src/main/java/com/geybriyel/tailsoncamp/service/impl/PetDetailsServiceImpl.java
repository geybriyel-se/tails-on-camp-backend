package com.geybriyel.tailsoncamp.service.impl;

import com.geybriyel.tailsoncamp.entity.Pet;
import com.geybriyel.tailsoncamp.entity.Shelter;
import com.geybriyel.tailsoncamp.entity.User;
import com.geybriyel.tailsoncamp.repository.PetRepository;
import com.geybriyel.tailsoncamp.service.PetDetailsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PetDetailsServiceImpl implements PetDetailsService {

    private final PetRepository petRepository;

    @Override
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    @Override
    public Optional<Pet> getPetByPetId(Long petId) {
        return petRepository.findPetById(petId);
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
        return petRepository.findByBreed(breed);
    }

    @Transactional
    @Override
    public Pet addPet(Pet pet) {
        return petRepository.save(pet);
    }

    @Override
    public Pet updatePet(Pet pet) {
        Optional<Pet> petByPetId = getPetByPetId(pet.getId());
        Pet retrievedPet = petByPetId.get();
        retrievedPet.setName(pet.getName());
        retrievedPet.setType(pet.getType());
        retrievedPet.setBreed(pet.getBreed());
        retrievedPet.setAge(pet.getAge());
        retrievedPet.setGender(pet.getGender());
        retrievedPet.setSize(pet.getSize());
        retrievedPet.setDescription(pet.getDescription());
        retrievedPet.setImageUrl(pet.getImageUrl());
        retrievedPet.setAvailability(pet.getAvailability());
        retrievedPet.setShelter(pet.getShelter());
        retrievedPet.setAdopter(pet.getAdopter());

        return petRepository.save(retrievedPet);
    }

}
