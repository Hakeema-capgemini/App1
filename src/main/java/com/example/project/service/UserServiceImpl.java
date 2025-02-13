package com.example.project.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project.bo.UserBO;
import com.example.project.dto.UserDTO;
import com.example.project.mapper.UserMapper;
import com.example.project.util.Constants;
import com.example.project.vo.UserVO;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserBO userBO;

	@Override
	public UserVO createUser(UserVO userVO) {
		UserDTO userDTO = UserMapper.INSTANCE.toDTO(userVO);
		logger.info(Constants.MAPPING_USER_LOG);
		UserDTO savedUser = userBO.saveUser(userDTO);
		return UserMapper.INSTANCE.toVO(savedUser);
	}

	
	@Override
	public UserVO getUser(Long id) {
		UserDTO userDTO = userBO.getUserById(id);
		if (userDTO == null) {
			throw new RuntimeException(Constants.USER_NOT_FOUND);
		}
		return UserMapper.INSTANCE.toVO(userDTO);
	}
	public UserVO fallbackGetUser(Long userId, Throwable ex) {
	    try {
	        // Log the exception causing the fallback to trigger
	        logger.error("Fallback triggered for userId: {} due to exception: {}", userId, ex.getMessage(), ex);
	        UserVO userVO = new UserVO();
	        userVO.setId(userId); 
	        userVO.setName("Default User");
	        userVO.setAge(0);  
	        return userVO;

	    } catch (Exception fallbackEx) {
	        // Log unexpected exceptions during the fallback process
	        logger.error("An error occurred in the fallback method for userId: {}, exception: {}", userId, fallbackEx.getMessage(), fallbackEx);
	        return new UserVO();
	    }
	}

	@Override
	public boolean HealthCheck() {
		try {
			userBO.getUserById(1L);
			logger.info(Constants.HEALTH_CHECK_MESSAGE);
			return true;
		} catch (Exception ex) {
			logger.error("Backend health check failed", ex);
			return false;
		}
		
		
	}
}
