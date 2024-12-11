package com.springproject.quickchat.controller;

import com.springproject.quickchat.dto.LoginDTO;
import com.springproject.quickchat.dto.UserDTO;
import com.springproject.quickchat.service.UserService;
import com.springproject.quickchat.utils.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;


    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDTO userDTO) {
        userService.createUser(userDTO);
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        String token = userService.validateLogin(loginDTO, jwtUtil);
        return ResponseEntity.ok(token);
    }

}
