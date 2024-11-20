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

    @Override
    public String healthCheck() {
        return Constants.APPLICATION_RUNS_SUCCESSFULLY;
    }
}
