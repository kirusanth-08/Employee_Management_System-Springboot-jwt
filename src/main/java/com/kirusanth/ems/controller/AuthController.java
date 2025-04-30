package com.kirusanth.ems.controller;

import com.kirusanth.ems.dto.AuthRequestDTO;
import com.kirusanth.ems.dto.AuthResponseDTO;
import com.kirusanth.ems.dto.UserDTO;
import com.kirusanth.ems.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    private final UserService userService;
    
    public AuthController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> authenticateUser(@RequestBody AuthRequestDTO authRequestDTO) {
        return ResponseEntity.ok(userService.authenticate(authRequestDTO));
    }
    
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.registerUser(userDTO), HttpStatus.CREATED);
    }
}
