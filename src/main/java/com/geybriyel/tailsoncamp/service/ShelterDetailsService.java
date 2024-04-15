package com.geybriyel.tailsoncamp.service;


import com.geybriyel.tailsoncamp.entity.Shelter;

import java.util.List;
import java.util.Optional;

public interface ShelterDetailsService {

    List<Shelter> getAllShelters();

    Optional<Shelter> getShelterByShelterId(Long id);

    List<Shelter> getSheltersByCity(String city);

    List<Shelter> getSheltersByProvince(String province);

    Optional<Shelter> getShelterByShelterName(String shelterName);

    Shelter addShelter(Shelter shelter);

    Shelter updateShelter(Shelter shelter);

}
