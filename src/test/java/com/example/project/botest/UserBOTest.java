package com.example.project.botest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.project.bo.UserBOImpl;
import com.example.project.dto.UserDTO;
import com.example.project.eo.UserEO;

@ExtendWith(MockitoExtension.class)
public class UserBOTest {

	@Mock
	private UserEO userEO;

	@InjectMocks
	private UserBOImpl userBO;

	private UserDTO userDTO;

	@BeforeEach
	void setUp() {
		userDTO = new UserDTO();
		userDTO.setId(1L);
		userDTO.setName("Rizwana");
		userDTO.setAge(24);
	}

	@Test
	void testSaveUser_Success() {
		// Mock EO save method
		when(userEO.save(any(UserDTO.class))).thenReturn(userDTO);

		UserDTO result = userBO.saveUser(userDTO);

		// Assertions
		assertNotNull(result);
		assertEquals(userDTO.getId(), result.getId());
		assertEquals(userDTO.getName(), result.getName());
		assertEquals(userDTO.getAge(), result.getAge());
	}

	@Test
	void testGetUserById_Success() {
		// Mock EO findById method
		when(userEO.findById(1L)).thenReturn(userDTO);

		UserDTO result = userBO.getUserById(1L);

		// Assertions
		assertNotNull(result);
		assertEquals(userDTO.getId(), result.getId());
		assertEquals(userDTO.getName(), result.getName());
		assertEquals(userDTO.getAge(), result.getAge());
	}

	@Test
	void testHealthCheck() {
		when(userEO.findById(anyLong())).thenReturn(userDTO);

		boolean result = userBO.HealthCheck();

		assertTrue(result);
		verify(userEO, times(1)).findById(anyLong());
	}
}
