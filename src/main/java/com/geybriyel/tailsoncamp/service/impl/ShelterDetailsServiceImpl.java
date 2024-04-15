package com.geybriyel.tailsoncamp.service.impl;

import com.geybriyel.tailsoncamp.entity.Shelter;
import com.geybriyel.tailsoncamp.repository.ShelterRepository;
import com.geybriyel.tailsoncamp.service.ShelterDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ShelterDetailsServiceImpl implements ShelterDetailsService {

    private final ShelterRepository shelterRepository;

    @Override
    public List<Shelter> getAllShelters() {
        return shelterRepository.findAll();
    }

    @Override
    public Optional<Shelter> getShelterByShelterId(Long id) {
        return shelterRepository.findShelterByShelterId(id);
    }

    @Override
    public List<Shelter> getSheltersByCity(String city) {
        return shelterRepository.findShelterByCity(city);
    }

    @Override
    public List<Shelter> getSheltersByProvince(String province) {
        return shelterRepository.findShelterByProvince(province);
    }

    @Override
    public Optional<Shelter> getShelterByShelterName(String shelterName) {
        return shelterRepository.findShelterByShelterName(shelterName);
    }

    @Override
    public Shelter addShelter(Shelter shelter) {
        return shelterRepository.save(shelter);
    }

    @Override
    public Shelter updateShelter(Shelter shelter) {
        Optional<Shelter> shelterByShelterId = getShelterByShelterId(shelter.getShelterId());
        Shelter retrievedShelter = shelterByShelterId.get();
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

}
