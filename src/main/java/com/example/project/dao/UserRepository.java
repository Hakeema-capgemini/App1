package com.example.project.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.project.vo.UserVO;

@Repository
public interface UserRepository extends JpaRepository<UserVO, Long> {
 
	
}

