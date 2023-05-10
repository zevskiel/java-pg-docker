package com.chandra.javadocker.userapp;

import com.chandra.javadocker.userapp.dao.User;
import com.chandra.javadocker.userapp.dao.UserRepository;
import com.chandra.javadocker.userapp.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class UserAppApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private UserService userService;

	@Test
	public void create_user_success() throws Exception {
		final User user = new User().setId(1L).setName("test").setPassword("Pasd123").setPhoneNumber("0812636211");

		Mockito.when(userService.validateUserData(user.getName(), user.getPhoneNumber(), user.getPassword()))
				.thenReturn(true);

		mockMvc.perform(post("/api/v1/user/register", 42L)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(user)))
				.andExpect(status().isCreated());
	}

	@Test
	public void login_user_success() throws Exception {
		User user = new User().setId(1L).setName("test").setPassword("Pasd123").setPhoneNumber("0812636211");
		Mockito.when(userService.getUserByPhoneNumberAndPassword(user.getPhoneNumber(), user.getPassword()))
				.thenReturn(user);

		MvcResult result = mockMvc.perform(post("/api/v1/user/login", 42L)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(user)))
				.andExpect(status().isOk())
				.andReturn();

		assertThat(result.getResponse().getContentAsString()).isNotEmpty();
	}

	@Test
	public void get_user_name_success() throws Exception {
		User user = new User().setId(1L).setName("test").setPassword("Pasd123").setPhoneNumber("0812636211");
		String token = "Bearer eyJhbGciOiJSUzI1NiJ9.eyJwaG9uZU51bWJlciI6IjA4MTI2MzYyMTEiLCJleHAiOjE2ODM3MjM0OTB9.Blj5ZKAjMHzjF4Drpiu_FzFU5H4EK4L2hcGMZSCnaB8drdng50E34wh-NGAhk2GkkrdAPBxNlj9vn6bh9s9NmQ";

		Mockito.when(userService.getUserByPhoneNumber(user.getPhoneNumber()))
				.thenReturn(user);

		MvcResult result = mockMvc.perform(get("/api/v1/user/name", 42L)
				.contentType("application/json")
				.header("Authorization", token))
				.andExpect(status().isOk())
				.andReturn();

		assertThat(result.getResponse().getContentAsString()).isEqualTo("test");
	}

	@Test
	public void update_user_name_success() throws Exception {
		String token = "Bearer eyJhbGciOiJSUzI1NiJ9.eyJwaG9uZU51bWJlciI6IjA4MTI2MzYyMTEiLCJleHAiOjE2ODM3MjM0OTB9.Blj5ZKAjMHzjF4Drpiu_FzFU5H4EK4L2hcGMZSCnaB8drdng50E34wh-NGAhk2GkkrdAPBxNlj9vn6bh9s9NmQ";
		Optional<User> oldUser = Optional.ofNullable(
				new User().setId(1L).setName("test1").setPassword("Pasd123").setPhoneNumber("0812636211"));
		User newUser = new User().setId(1L).setName("test2").setPassword("Pasd123").setPhoneNumber("0812636211");

		Mockito.when(userService.getUserByPhoneNumber(oldUser.get().getPhoneNumber()))
				.thenReturn(oldUser.get());
		Mockito.when(userRepository.findByName(oldUser.get().getName()))
				.thenReturn(oldUser);

		mockMvc.perform(post("/api/v1/user/update/name", 42L)
				.contentType("application/json")
				.header("Authorization", token)
				.content(objectMapper.writeValueAsString(newUser)))
				.andExpect(status().isOk());
	}
}
