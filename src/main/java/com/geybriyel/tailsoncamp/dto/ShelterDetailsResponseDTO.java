package com.geybriyel.tailsoncamp.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShelterDetailsResponseDTO {

    private Long shelterId;

    private String shelterName;

    private String lotBlockHouseBldgNo;

    private String street;

    private String subdivisionVillage;

    private String barangay;

    private String city;

    private String province;

    private String country;

    private String zipcode;

    private String contactNumber;

    private String email;

    private String website;

}
