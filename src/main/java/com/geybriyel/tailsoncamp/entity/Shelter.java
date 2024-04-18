package com.geybriyel.tailsoncamp.entity;


import com.geybriyel.tailsoncamp.listener.ShelterEntityListener;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.Instant;

@Entity
@Data
@Table(name = "shelter_details")
@EntityListeners(ShelterEntityListener.class)
public class Shelter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "shelter_id")
    private Long shelterId;

    @NotBlank(message = "Shelter name cannot be blank")
    private String shelterName;

    private String lotBlockHouseBldgNo;

    private String street;

    private String subdivisionVillage;

    private String barangay;

    @NotBlank(message = "City cannot be blank")
    private String city;

    @NotBlank(message = "Province cannot be blank")
    private String province;

    private String country;

    private String zipcode;

    @NotBlank(message = "Contact number cannot be blank")
    private String contactNumber;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Please provide a valid email address.", regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")
    private String email;

    private String website;

    private Instant createdAt;

    private Instant updatedAt;

}
