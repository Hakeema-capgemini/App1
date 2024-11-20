package com.example.project.eo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project.dao.UserRepository;
import com.example.project.dto.UserDTO;
import com.example.project.mapper.UserMapper;
import com.example.project.util.Constants;
import com.example.project.vo.UserVO;
@Service

public class UserEOImpl implements UserEO {
    private static final Logger log = LoggerFactory.getLogger(UserEOImpl.class);

	@Autowired
    private UserRepository userRepository;

    private final UserMapper userMapper;
    @Autowired
    public UserEOImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
	@Override
    public UserDTO save(UserDTO userDTO) {
        log.info("Saving user in the EO layer.");
        
        // Convert DTO to VO
        UserVO userVO = userMapper.toVO(userDTO);
        
        // Save to the database
        UserVO savedVO = userRepository.save(userVO);
        
        // Convert VO back to DTO
        return userMapper.toDTO(savedVO);
    }

    @Override
    public UserDTO findById(Long id) {
        log.info("Retrieving user by ID in the EO layer. ID: {}", id);
        
        // Find by ID
        UserVO userVO = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(Constants.USER_NOT_FOUND));
        
        // Convert VO to DTO
        return userMapper.toDTO(userVO);
    }
}