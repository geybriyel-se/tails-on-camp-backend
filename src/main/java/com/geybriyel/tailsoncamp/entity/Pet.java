package com.geybriyel.tailsoncamp.entity;

import com.geybriyel.tailsoncamp.utility.PetEntityListener;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.Fetch;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "pet_details")
@EntityListeners(PetEntityListener.class)
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pet_id")
    private Long id;

//    @NotBlank(message = "Name must not be blank")
    private String name;

    private String type;

    private String breed;

    private Integer age;

    private String gender;

    private String size;

    private String description;

    private String imageUrl;

    private String availability;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User adopter;

    private Instant createdAt;

    private Instant updatedAt;
}
