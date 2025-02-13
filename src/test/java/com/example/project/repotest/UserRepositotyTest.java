package com.example.project.repotest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.project.dao.UserRepository;
import com.example.project.vo.UserVO;

@DataJpaTest
public class UserRepositotyTest {
	@Autowired
	private UserRepository userRepository;

	private UserVO userVO;

	@BeforeEach
	void setUp() {
		userVO = new UserVO();
		userVO.setName("Rizwana");
		userVO.setAge(24);
	}

	@Test
	void testSaveUser() {

		UserVO savedUser = userRepository.save(userVO);

		assertNotNull(savedUser);
		assertNotNull(savedUser.getId()); // ID should be auto-generated
		assertEquals("Rizwana", savedUser.getName());
	}

	@Test
	void testFindById_Success() {
		// Save the user
		UserVO savedUser = userRepository.save(userVO);

		// Retrieve the user
		Optional<UserVO> foundUser = userRepository.findById(savedUser.getId());

		// Verify the user is found
		assertTrue(foundUser.isPresent());
		assertEquals(savedUser.getId(), foundUser.get().getId());
		assertEquals("Rizwana", foundUser.get().getName());
	}

	@Test
	void testFindById_NotFound() {
		Optional<UserVO> foundUser = userRepository.findById(999L);
		assertFalse(foundUser.isPresent());
	}

	@Test
	void testDeleteUser() {
		// Save the user
		UserVO savedUser = userRepository.save(userVO);

		// Delete the user
		userRepository.deleteById(savedUser.getId());

		// Verify the user is deleted
		Optional<UserVO> deletedUser = userRepository.findById(savedUser.getId());
		assertFalse(deletedUser.isPresent());
	}

	@Test
	void testFindAllUsers() {
		UserVO user1 = new UserVO();
		user1.setName("Rizwana");
		user1.setAge(24);

		UserVO user2 = new UserVO();
		user2.setName("Hakeema");
		user2.setAge(23);

		userRepository.save(user1);
		userRepository.save(user2);

		// Verify all users are retrieved
		Iterable<UserVO> users = userRepository.findAll();
		assertNotNull(users);
		assertTrue(((Collection<?>) users).size() >= 2);
	}

}
