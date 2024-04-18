package com.geybriyel.tailsoncamp.entity;

import com.geybriyel.tailsoncamp.listener.AdoptionRequestEntityListener;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Data
@Table(name = "adoption_request_details")
@EntityListeners(AdoptionRequestEntityListener.class)
public class AdoptionRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long adoptionId;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "adopter_id", referencedColumnName = "user_id")
    private User adopter;

    private String status;

    private Instant createdAt;

    private Instant updatedAt;
}
