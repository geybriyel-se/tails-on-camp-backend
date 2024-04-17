package com.geybriyel.tailsoncamp.entity;


import com.geybriyel.tailsoncamp.listener.ShelterEntityListener;
import jakarta.persistence.*;
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

    private Instant createdAt;

    private Instant updatedAt;

}
