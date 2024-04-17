package com.geybriyel.tailsoncamp.service.impl;

import com.geybriyel.tailsoncamp.entity.User;
import com.geybriyel.tailsoncamp.exception.InvalidUserIdException;
import com.geybriyel.tailsoncamp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;


    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User getUserById(Long id) {
        return repository.findByUserId(id)
                .orElseThrow(InvalidUserIdException::new);
    }

    public boolean isUsernameTaken(String username) {
        return repository.existsByUsername(username);
    }

    public boolean isEmailTaken(String email) {
        return repository.existsByEmail(email);
    }

    public User saveUser(User user) {
        return repository.save(user);
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User updateUser(User user) throws UsernameNotFoundException{
        User retrievedUser = getUserById(user.getUserId());
        retrievedUser.setEmail(user.getEmail());
        retrievedUser.setUsername(user.getUsername());
        retrievedUser.setFirstName(user.getFirstName());
        retrievedUser.setLastName(user.getLastName());
        retrievedUser.setAddress(user.getAddress());
        retrievedUser.setPhoneNumber(user.getPhoneNumber());
        return repository.save(retrievedUser);
    }

}
