package com.kirusanth.ems.service;

import com.kirusanth.ems.dto.AuthRequestDTO;
import com.kirusanth.ems.dto.AuthResponseDTO;
import com.kirusanth.ems.dto.UserDTO;

import java.util.List;

public interface UserService {
    AuthResponseDTO authenticate(AuthRequestDTO authRequestDTO);
    UserDTO registerUser(UserDTO userDTO);
    UserDTO getUserById(Long id);
    UserDTO getUserByUsername(String username);
    List<UserDTO> getAllUsers();
    UserDTO updateUser(Long id, UserDTO userDTO);
    void deleteUser(Long id);
}
