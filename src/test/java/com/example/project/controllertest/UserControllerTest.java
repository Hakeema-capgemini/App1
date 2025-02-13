//package com.example.project.controllertest;
//
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import com.example.project.controller.UserController;
//import com.example.project.service.UserService;
//import com.example.project.vo.UserVO;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//@ExtendWith(MockitoExtension.class)
//@WebMvcTest(UserController.class)
//public class UserControllerTest {
//	@Autowired
//	private MockMvc mockMvc;
//
//	@MockBean
//	private UserService userService;
//
//	@Autowired
//	private ObjectMapper objectMapper;
//
//	@Test
//	void testCreateUser() throws Exception {
//		UserVO userVO = new UserVO();
//		userVO.setId(1L);
//		userVO.setName("Rizwana");
//		userVO.setAge(24);
//
//		mockMvc.perform(post("/app1/api/users/create").contentType(MediaType.APPLICATION_JSON)
//				.content(objectMapper.writeValueAsString(userVO))).andExpect(status().isOk());
//	}
//
//	@Test
//	void testGetUser() throws Exception {
//		mockMvc.perform(get("/app1/api/users/retrieve/1")).andExpect(status().isOk());
//	}
//
//	@Test
//	void testHealthCheck() throws Exception {
//		when(userService.HealthCheck()).thenReturn(true);
//
//		mockMvc.perform(get("/app1/api/users/health").contentType(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk()).andExpect(content().string("Backend call successful"));
//
//		verify(userService, times(1)).HealthCheck();
//	}
//}
