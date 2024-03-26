package com.geybriyel.tailsoncamp.service.impl;

import com.geybriyel.tailsoncamp.entity.User;
import com.geybriyel.tailsoncamp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    public boolean isUsernameTaken(String username) {
        return repository.existsByUsername(username);
    }

    public boolean isEmailTaken(String email) {
        return repository.existsByEmail(email);
    }

    public User saveUser(User user) {
        return repository.save(user);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }


}
