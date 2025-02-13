package com.example.project;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.project.servicetest.UserServiceTest;

@SpringBootTest(classes = UserServiceTest.class)
public class ProjectApplicationTests {

	@Test
	void contextLoads() {
	}

}
