package com.example.project.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.project.dto.UserDTO;
import com.example.project.eo.UserEO;
@Component
@Service
public class UserBOImpl implements UserBO {
  
	
	 @Autowired
	    private UserEO userEO;
     @Override
	    public UserDTO saveUser(UserDTO userDTO) {
	        return userEO.save(userDTO);
	    }

	    @Override
	    public UserDTO getUserById(Long id) {
	        return userEO.findById(id);
	    
 }
}