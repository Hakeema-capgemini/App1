package com.example.project.bo;

import org.springframework.stereotype.Service;

import com.example.project.dto.UserDTO;

public interface UserBO {

	UserDTO saveUser(UserDTO userDTO);

	UserDTO getUserById(Long id);

	boolean HealthCheck();

}
