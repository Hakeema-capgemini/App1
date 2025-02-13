package com.example.project.eo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.project.dao.UserRepository;
import com.example.project.dto.UserDTO;
import com.example.project.mapper.UserMapper;
import com.example.project.util.Constants;
import com.example.project.vo.UserVO;

@Component
public class UserEOImpl implements UserEO {
	private static final Logger logger = LoggerFactory.getLogger(UserEOImpl.class);

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private final UserMapper userMapper;

	// @Autowired
	public UserEOImpl(UserRepository userRepository, UserMapper userMapper) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
	}

	@Override
	public UserDTO save(UserDTO userDTO) {
		logger.info("Saving user in the EO layer.");
		UserVO userVO = userMapper.toVO(userDTO);
		UserVO savedVO = userRepository.save(userVO);
		return userMapper.toDTO(savedVO);
	}

	@Override
	public UserDTO findById(Long id) {
		logger.info("Retrieving user by ID in the EO layer. ID: {}", id);
		UserVO userVO = userRepository.findById(id).orElseThrow(() -> new RuntimeException(Constants.USER_NOT_FOUND));
		return userMapper.toDTO(userVO);
	}
}