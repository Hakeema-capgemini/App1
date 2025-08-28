package com.example.project.service;
import com.example.project.vo.UserVO;
import java.util.concurrent.CompletableFuture;
import java.util.List;

public interface UserService {

	UserVO createUser(UserVO userVO);
	UserVO getUser(Long id);
    List<UserVO> getAllUsers();
	boolean HealthCheck();
	CompletableFuture<List<UserVO>> getAllUsersAsync();
}