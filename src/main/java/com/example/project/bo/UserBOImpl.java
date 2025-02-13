package com.example.project.bo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.project.dto.UserDTO;
import com.example.project.eo.UserEO;

@Service
public class UserBOImpl implements UserBO {

	private static final Logger logger = LoggerFactory.getLogger(UserBOImpl.class);
	@Autowired
	private UserEO userEO;

	@Override
	public UserDTO saveUser(UserDTO userDTO) {
		logger.info("Saving into BO layer:{}", userDTO);
		return userEO.save(userDTO);
	}

	@Override
	public UserDTO getUserById(Long id) {
		logger.info("Fetching the saved user by ID:{}", id);
		return userEO.findById(id);
	}

	@Override
	public boolean HealthCheck() {
		try {
			userEO.findById(1L);
			return true;
		} catch (Exception ex) {
			logger.error("Backend health check failed in BO layer", ex);
			return false;
		}
	}
}