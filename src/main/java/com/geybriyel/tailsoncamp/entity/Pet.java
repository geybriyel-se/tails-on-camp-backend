package com.geybriyel.tailsoncamp.entity;

import com.geybriyel.tailsoncamp.listener.PetEntityListener;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.Instant;

@Entity
@Data
@Table(name = "pet_details")
@EntityListeners(PetEntityListener.class)
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pet_id")
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Type cannot be blank")
    private String type;

    private String breed;

    private Integer age;

    private String gender;

    private String size;

    private String description;

    private String imageUrl;

    private Integer availability;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User adopter;

    private Instant createdAt;

    private Instant updatedAt;
}
