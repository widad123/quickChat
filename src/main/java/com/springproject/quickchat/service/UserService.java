package com.springproject.quickchat.service;

import com.springproject.quickchat.Entity.UserEntity;
import com.springproject.quickchat.dto.LoginDTO;
import com.springproject.quickchat.dto.UserDTO;
import com.springproject.quickchat.model.User;
import com.springproject.quickchat.repository.UserRepository;
import com.springproject.quickchat.utils.JwtAuthentication;
import com.springproject.quickchat.utils.JwtUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public String validateLogin(LoginDTO loginDTO, JwtUtil jwtUtil) {
        UserEntity userEntity = userRepository.findByUsername(loginDTO.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        if (!passwordEncoder.matches(loginDTO.getPassword(), userEntity.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        return jwtUtil.generateToken(userEntity.getUsername(),userEntity.getId());
    }

    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getDetails() instanceof JwtAuthentication) {
            JwtAuthentication details = (JwtAuthentication) authentication.getDetails();
            return (Long) details.getPrincipal();
        }
        throw new IllegalStateException("User ID not found in security context");
    }
}