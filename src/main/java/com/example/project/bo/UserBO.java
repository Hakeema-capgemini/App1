package com.example.project.bo;

import org.springframework.stereotype.Service;

import com.example.project.dto.UserDTO;

import java.util.List;

public interface UserBO {

	UserDTO saveUser(UserDTO userDTO);

	UserDTO getUserById(Long id);

	boolean HealthCheck();

    List<UserDTO> getAllUsers();
}
