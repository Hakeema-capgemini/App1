package com.example.project.service;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.example.project.bo.UserBO;
import com.example.project.dto.UserDTO;
import com.example.project.mapper.UserMapper;
import com.example.project.util.Constants;
import com.example.project.vo.UserVO;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

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
	@Async
	public CompletableFuture<List<UserVO>> getAllUsersAsync() {
		long startTime = System.currentTimeMillis();
		List<UserDTO> userDTOs = userBO.getAllUsers();
		List<UserVO> userVOs = userDTOs.stream()
				.map(UserMapper.INSTANCE::toVO)
				.collect(Collectors.toList());
		long endTime = System.currentTimeMillis();
		logger.info("Async getAllUsers completed in {} ms", (endTime - startTime));
		return CompletableFuture.completedFuture(userVOs);
	}

	@Override
	@CircuitBreaker(name ="userServiceCB" , fallbackMethod ="fallbackGetUser")
	public UserVO getUser(Long id) {
		logger.info("Fetching user details for ID: {}", id);
		UserDTO userDTO = userBO.getUserById(id);
		if (userDTO == null) {
			logger.warn("User not found for ID: {}", id);
			return fallbackGetUser(id, new RuntimeException(Constants.USER_NOT_FOUND));
		}

		return UserMapper.INSTANCE.toVO(userDTO);
	}
	public UserVO fallbackGetUser(Long id, Throwable ex) {
		logger.error("Fallback triggered for userId: {} due to: {}", id, ex.getMessage());

		UserVO userVO = new UserVO();
		userVO.setId(id);
		userVO.setName("User Does not exists");
		userVO.setAge(0);

		return userVO;
	}

	@Override
	public List<UserVO> getAllUsers() {
		List<UserDTO> userDTOS = userBO.getAllUsers();
		return userDTOS.stream()
				.map(UserMapper.INSTANCE::toVO)
				.collect(Collectors.toList());
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