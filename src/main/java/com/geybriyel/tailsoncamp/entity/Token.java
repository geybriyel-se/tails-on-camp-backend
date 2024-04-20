package com.geybriyel.tailsoncamp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "jwt_details")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long id;

    private String token;

    private boolean isLoggedOut;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
