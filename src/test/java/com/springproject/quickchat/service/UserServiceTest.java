package com.springproject.quickchat.service;

import static org.junit.jupiter.api.Assertions.*;


import com.springproject.quickchat.dto.UserDTO;
import com.springproject.quickchat.repository.InMemoryUserRepository;
import com.springproject.quickchat.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository = new InMemoryUserRepository();
        passwordEncoder = new BCryptPasswordEncoder();
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    void testCreateUser_successfulRegistration() {
        UserDTO userDTO = new UserDTO("johndoe", "password123", "johndoe@example.com");

        userService.createUser(userDTO);

        assertEquals(1, userRepository.findAll().size());
        assertTrue(userRepository.findByUsername("johndoe").isPresent());
        assertTrue(passwordEncoder.matches("password123", userRepository.findByUsername("johndoe").get().getPassword()));
    }

    @Test
    void testCreateUser_duplicateUsername() {
        UserDTO userDTO1 = new UserDTO("johndoe", "password123", "johndoe@example.com");
        UserDTO userDTO2 = new UserDTO("johndoe", "anotherpassword", "john2@example.com");

        userService.createUser(userDTO1);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            userService.createUser(userDTO2);
        });

        assertEquals("Username already exists: johndoe", exception.getMessage());
        assertEquals(1, userRepository.findAll().size());
    }

    @Test
    void testCreateUser_emptyFields() {
        UserDTO userDTO = new UserDTO("", "", "");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(userDTO);
        });

        assertEquals("Username cannot be null or blank", exception.getMessage());
        assertEquals(0, userRepository.findAll().size());
    }

    @Test
    void testPasswordEncoding() {
        UserDTO userDTO = new UserDTO("johndoe", "mypassword", "johndoe@example.com");

        userService.createUser(userDTO);

        String encodedPassword = userRepository.findByUsername("johndoe").get().getPassword();
        assertNotEquals("mypassword", encodedPassword);
        assertTrue(passwordEncoder.matches("mypassword", encodedPassword));
    }
}
