package com.geybriyel.tailsoncamp.service;

import com.geybriyel.tailsoncamp.entity.Pet;
import com.geybriyel.tailsoncamp.entity.Shelter;
import com.geybriyel.tailsoncamp.entity.User;

import java.util.List;

public interface PetDetailsService {

    List<Pet> getAllPets();

    Pet getPetByPetId(Long petId);

    List<Pet> getPetsByAdopter(User user);

    List<Pet> getPetsByShelter(Shelter shelter);

    List<Pet> getPetsByBreed(String breed);

    Pet addPet(Pet pet);

    Pet updatePet(Pet pet);

    List<String> getAllPetBreeds();
}
