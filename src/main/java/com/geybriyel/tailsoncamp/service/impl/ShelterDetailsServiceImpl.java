package com.geybriyel.tailsoncamp.service.impl;

import com.geybriyel.tailsoncamp.entity.Shelter;
import com.geybriyel.tailsoncamp.exception.*;
import com.geybriyel.tailsoncamp.repository.ShelterRepository;
import com.geybriyel.tailsoncamp.service.ShelterDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ShelterDetailsServiceImpl implements ShelterDetailsService {

    private final ShelterRepository shelterRepository;

    @Override
    public List<Shelter> getAllShelters() {
        return shelterRepository.findAll();
    }

    @Override
    public Shelter getShelterByShelterId(Long id) {
        return shelterRepository.findShelterByShelterId(id)
                .orElseThrow(ShelterIdNotFound::new);
    }

    @Override
    public List<Shelter> getSheltersByCity(String city) {
        if (getAllCity().contains(city)) {
            return shelterRepository.findShelterByCity(city);
        } else {
            throw new InvalidCityException();
        }
    }

    @Override
    public List<Shelter> getSheltersByProvince(String province) {
        if (getAllProvince().contains(province)) {
            return shelterRepository.findShelterByProvince(province);
        } else {
            throw new InvalidProvinceException();
        }

    }

    @Override
    public Shelter getShelterByShelterName(String shelterName) {
        return shelterRepository.findShelterByShelterName(shelterName)
                .orElseThrow(InvalidShelterNameException::new);
    }

    @Override
    public Shelter addShelter(Shelter shelter) {
        if (exists(shelter)) {
            throw new DuplicateShelterException();
        }
        return shelterRepository.save(shelter);
    }

    @Override
    public Shelter updateShelter(Shelter shelter) {
        Shelter retrievedShelter = getShelterByShelterId(shelter.getShelterId());
        retrievedShelter.setShelterName(shelter.getShelterName());
        retrievedShelter.setLotBlockHouseBldgNo(shelter.getLotBlockHouseBldgNo());
        retrievedShelter.setStreet(shelter.getStreet());
        retrievedShelter.setSubdivisionVillage(shelter.getSubdivisionVillage());
        retrievedShelter.setBarangay(shelter.getBarangay());
        retrievedShelter.setCity(shelter.getCity());
        retrievedShelter.setProvince(shelter.getProvince());
        retrievedShelter.setCountry(shelter.getCountry());
        retrievedShelter.setZipcode(shelter.getZipcode());
        retrievedShelter.setContactNumber(shelter.getContactNumber());
        retrievedShelter.setEmail(shelter.getEmail());
        retrievedShelter.setWebsite(shelter.getWebsite());
        retrievedShelter.setCreatedAt(shelter.getCreatedAt());
        return shelterRepository.save(retrievedShelter);
    }

    @Override
    public List<String> getAllCity() {
        return shelterRepository.findDistinctCity();
    }

    @Override
    public List<String> getAllProvince() {
        return shelterRepository.findDistinctProvince();
    }

    @Override
    public boolean exists(Shelter shelter) {
        String shelterName = shelter.getShelterName();
        String city = shelter.getCity();
        return shelterRepository.existsByShelterNameAndCity(shelterName, city);
    }


}
