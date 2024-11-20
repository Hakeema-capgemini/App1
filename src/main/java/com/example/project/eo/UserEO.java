package com.example.project.eo;


import org.springframework.stereotype.Service;

import com.example.project.dto.UserDTO;

@Service
public interface UserEO {
	UserDTO save(UserDTO userDTO);
    UserDTO findById(Long id);
		    
	}	    

