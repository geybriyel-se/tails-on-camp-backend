package com.geybriyel.tailsoncamp.repository;

import com.geybriyel.tailsoncamp.entity.Pet;
import com.geybriyel.tailsoncamp.entity.Shelter;
import com.geybriyel.tailsoncamp.entity.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findAll();

    Optional<Pet> findPetById(Long id);

    List<Pet> findPetByAdopter(User user);

    List<Pet> findByShelter(Shelter shelter);

    List<Pet> findByBreed(String breed);

    Pet save(Pet pet);

    @Query("SELECT DISTINCT breed FROM Pet")
    List<String> findDistinctBreed();

    boolean existsByNameAndTypeAndGenderAndBreed(String name, String type, String gender, String breed);

    /**
     *
     * @param availability 0: not available / already adopted, 1: available 2: on-hold
     * @return
     */
    List<Pet> findByAvailability(Integer availability);



}
