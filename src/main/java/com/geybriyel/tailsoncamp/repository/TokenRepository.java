package com.geybriyel.tailsoncamp.repository;

import com.geybriyel.tailsoncamp.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query("""
        SELECT t from Token t INNER JOIN User u
        ON t.user.userId = u.userId
        WHERE t.user.userId = :userId AND t.isLoggedOut = false
    """)
    List<Token> findAllTokenByUser(Long userId);

    Optional<Token> findByToken(String token);

}
