package com.geybriyel.tailsoncamp.repository;

import com.geybriyel.tailsoncamp.entity.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShelterRepository extends JpaRepository<Shelter, Long> {

    List<Shelter> findAll();

    Optional<Shelter> findShelterByShelterId(Long id);

    List<Shelter> findShelterByCity(String city);

    List<Shelter> findShelterByProvince(String province);

    Optional<Shelter> findShelterByShelterName(String name);

    Shelter save(Shelter shelter);

    @Query("SELECT DISTINCT city FROM Shelter")
    List<String> findDistinctCity();

    @Query("SELECT DISTINCT province FROM Shelter")
    List<String> findDistinctProvince();

    boolean existsByShelterNameAndCity(String shelterName, String city);

}
