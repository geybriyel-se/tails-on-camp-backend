package com.geybriyel.tailsoncamp.service;


import com.geybriyel.tailsoncamp.entity.Shelter;

import java.util.List;

public interface ShelterDetailsService {

    List<Shelter> getAllShelters();

    Shelter getShelterByShelterId(Long id);

    List<Shelter> getSheltersByCity(String city);

    List<Shelter> getSheltersByProvince(String province);

    Shelter getShelterByShelterName(String shelterName);

    Shelter addShelter(Shelter shelter);

    Shelter updateShelter(Shelter shelter);

    List<String> getAllCity();

    List<String> getAllProvince();

    /**
     * Checks for duplicate based on Shelter's shelterName and city fields.
     * @param shelter
     * @return true if duplicate exists; false if not
     */
    boolean exists(Shelter shelter);

}
