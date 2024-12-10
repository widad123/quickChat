package com.springproject.quickchat.service;

import com.springproject.quickchat.dto.UserDTO;
import com.springproject.quickchat.model.User;
import com.springproject.quickchat.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createUser(UserDTO userDTO) {

        userRepository.findByUsername(userDTO.getUsername()).ifPresent(existingUser -> {
            throw new IllegalStateException("Username already exists: " + userDTO.getUsername());
        });

        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());

        User user = User.create(
                null,
                userDTO.getUsername(),
                encodedPassword,
                userDTO.getEmail(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>());

        userRepository.saveUser(user);
    }
}