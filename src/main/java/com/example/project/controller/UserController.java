package com.example.project.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.service.UserService;
import com.example.project.util.Constants;
import com.example.project.vo.UserVO;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {

	   private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	   @Autowired
	    private UserService userService;

	    @PostMapping("/create")
	    public UserVO createUser(@Valid @RequestBody UserVO userVO) {
	    	logger.info(Constants.USER_CREATION_LOG);
	        return userService.createUser(userVO);
	    }

	    @GetMapping("/retrieve/{id}")
	    public UserVO getUser(@PathVariable("id") Long id) {
	    	logger.info(Constants.RETRIEVE_USER_LOG);
	        return userService.getUser(id);
	    }

	    @GetMapping("/health")
	    public String healthCheck() {
	    	logger.info(Constants.HEALTH_CHECK_MESSAGE);
	        return userService.healthCheck();
	    }
	}