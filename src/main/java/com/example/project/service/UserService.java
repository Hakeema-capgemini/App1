package com.example.project.service;

import org.springframework.stereotype.Service;

import com.example.project.vo.UserVO;
@Service
public interface UserService {
	
	UserVO createUser(UserVO userVO);
    UserVO getUser(Long id);
    String healthCheck();

	
}
