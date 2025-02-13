package com.example.project.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.project.bo.UserBO;
import com.example.project.dto.UserDTO;
import com.example.project.service.UserServiceImpl;
import com.example.project.util.Constants;
import com.example.project.vo.UserVO;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	@Mock
	private UserBO userBO;

	@InjectMocks
	private UserServiceImpl userService;

	private UserVO userVO;
	private UserDTO userDTO;

	@BeforeEach
	void setUp() {
		userVO = new UserVO();
		userVO.setId(1L);
		userVO.setName("Rizwana");
		userVO.setAge(24);

		userDTO = new UserDTO();
		userDTO.setId(1L);
		userDTO.setName("Rizwana");
		userDTO.setAge(24);
	}

	@Test
	void testCreateUser_Success() {
		when(userBO.saveUser(any(UserDTO.class))).thenReturn(userDTO);

		UserVO result = userService.createUser(userVO);

		assertNotNull(result);
		assertEquals(userVO.getId(), result.getId());
		assertEquals(userVO.getName(), result.getName());
		assertEquals(userVO.getAge(), result.getAge());
		verify(userBO, times(1)).saveUser(any(UserDTO.class));
	}

	@Test
	void testGetUser_Success() {
		when(userBO.getUserById(1L)).thenReturn(userDTO);

		UserVO result = userService.getUser(1L);

		assertNotNull(result);
		assertEquals(userVO.getId(), result.getId());
		assertEquals(userVO.getName(), result.getName());
		assertEquals(userVO.getAge(), result.getAge());
		verify(userBO, times(1)).getUserById(1L);
	}

	@Test
	void testGetUser_NotFound() {
		when(userBO.getUserById(1L)).thenReturn(null);

		RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.getUser(1L));

		assertEquals(Constants.USER_NOT_FOUND, exception.getMessage());
		verify(userBO, times(1)).getUserById(1L);
	}

	@Test
	void testHealthCheck() {
		when(userBO.getUserById(1L)).thenReturn(new UserDTO());

		boolean result = userService.HealthCheck();

		assertTrue(result);
		verify(userBO, times(1)).getUserById(1L);
	}
}