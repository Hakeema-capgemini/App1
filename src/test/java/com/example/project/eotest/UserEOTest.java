package com.example.project.eotest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.project.dao.UserRepository;
import com.example.project.dto.UserDTO;
import com.example.project.eo.UserEOImpl;
import com.example.project.mapper.UserMapper;
import com.example.project.util.Constants;
import com.example.project.vo.UserVO;

@ExtendWith(MockitoExtension.class)
public class UserEOTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private UserMapper userMapper;

	@InjectMocks
	private UserEOImpl userEO;

	private UserVO userVO;
	private UserDTO userDTO;

	@BeforeEach
	void setUp() {
		userVO = new UserVO();
		userVO.setId(1L);
		userVO.setName("John");
		userVO.setAge(25);

		userDTO = new UserDTO();
		userDTO.setId(1L);
		userDTO.setName("John");
		userDTO.setAge(25);

	}

	@Test
	void testSave_Success() {
		when(userMapper.toVO(userDTO)).thenReturn(userVO);
		when(userRepository.save(userVO)).thenReturn(userVO);
		when(userMapper.toDTO(userVO)).thenReturn(userDTO);

		UserDTO result = userEO.save(userDTO);

		assertNotNull(result);
		assertEquals(userDTO.getId(), result.getId());
		verify(userRepository, times(1)).save(userVO);
	}

	@Test
	void testFindById_Success() {
		when(userRepository.findById(1L)).thenReturn(Optional.of(userVO));
		when(userMapper.toDTO(userVO)).thenReturn(userDTO);

		UserDTO result = userEO.findById(1L);

		assertNotNull(result);
		assertEquals(userDTO.getId(), result.getId());
		verify(userRepository, times(1)).findById(1L);
	}

	@Test
	void testFindById_NotFound() {
		when(userRepository.findById(1L)).thenReturn(Optional.empty());

		RuntimeException exception = assertThrows(RuntimeException.class, () -> userEO.findById(1L));

		assertEquals(Constants.USER_NOT_FOUND, exception.getMessage());
		verify(userRepository, times(1)).findById(1L);
	}
}
