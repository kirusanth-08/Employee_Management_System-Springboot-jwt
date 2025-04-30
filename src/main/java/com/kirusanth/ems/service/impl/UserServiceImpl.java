package com.kirusanth.ems.service.impl;

import com.kirusanth.ems.dto.AuthRequestDTO;
import com.kirusanth.ems.dto.AuthResponseDTO;
import com.kirusanth.ems.dto.UserDTO;
import com.kirusanth.ems.exception.ResourceNotFoundException;
import com.kirusanth.ems.model.Role;
import com.kirusanth.ems.model.User;
import com.kirusanth.ems.repository.UserRepository;
import com.kirusanth.ems.security.JwtTokenProvider;
import com.kirusanth.ems.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public UserServiceImpl(UserRepository userRepository, 
                           PasswordEncoder passwordEncoder, 
                           AuthenticationManager authenticationManager, 
                           JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public AuthResponseDTO authenticate(AuthRequestDTO authRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequestDTO.getUsername(),
                        authRequestDTO.getPassword()
                )
        );
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        
        User user = userRepository.findByUsername(authRequestDTO.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", authRequestDTO.getUsername()));
                
        return new AuthResponseDTO(token, user.getUsername(), user.getEmail());
    }

    @Override
    public UserDTO registerUser(UserDTO userDTO) {
        // Check if username or email already exists
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new RuntimeException("Username already taken!");
        }
        
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("Email already in use!");
        }
        
        // Create new user
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setFullName(userDTO.getFullName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        
        // Set default role if none provided
        if (userDTO.getRoles() == null || userDTO.getRoles().isEmpty()) {
            user.setRoles(Collections.singleton(Role.EMPLOYEE));
        } else {
            user.setRoles(userDTO.getRoles());
        }
        
        user.setActive(true);
        
        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return convertToDTO(user);
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        return convertToDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        
        // Update fields
        user.setFullName(userDTO.getFullName());
        
        if (userDTO.getRoles() != null && !userDTO.getRoles().isEmpty()) {
            user.setRoles(userDTO.getRoles());
        }
        
        // If active status is changed
        user.setActive(userDTO.isActive());
        
        User updatedUser = userRepository.save(user);
        return convertToDTO(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        userRepository.delete(user);
    }
    
    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        dto.setRoles(user.getRoles());
        dto.setActive(user.isActive());
        return dto;
    }
}
