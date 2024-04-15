package com.geybriyel.tailsoncamp.service;

import com.geybriyel.tailsoncamp.entity.Pet;
import com.geybriyel.tailsoncamp.entity.Shelter;
import com.geybriyel.tailsoncamp.entity.User;

import java.util.List;
import java.util.Optional;

public interface PetDetailsService {

    List<Pet> getAllPets();

    Optional<Pet> getPetByPetId(Long petId);

    List<Pet> getPetsByAdopter(User user);

    List<Pet> getPetsByShelter(Shelter shelter);

    List<Pet> getPetsByBreed(String breed);

    Pet addPet(Pet pet);

    Pet updatePet(Pet pet);
}
