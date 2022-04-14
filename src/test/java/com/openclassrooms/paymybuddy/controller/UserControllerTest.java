package com.openclassrooms.paymybuddy.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import com.openclassrooms.paymybuddy.config.UserDetailsServiceTest;
import com.openclassrooms.paymybuddy.dto.TransferDTO;
import com.openclassrooms.paymybuddy.dto.UserDTO;
import com.openclassrooms.paymybuddy.model.Transfer;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.UserService;

@WebMvcTest(controllers=UserController.class)
@Import(UserDetailsServiceTest.class)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean 
	private UserService userService;
	
	private User userControllerTestA;
	private User userControllerTestB;
	private User userControllerTestC;
	private User userControllerTestD;
	private Page<User> pageUsersTest;
	private UserDTO userDtoTest;
	
	@BeforeEach
	public void setup() {
		userControllerTestA = new User("testControllers1@email.com", "password", "userTest1");
		userControllerTestB = new User("testControllers2@email.com", "password", "userTest2");
		userControllerTestC = new User("testControllers3@email.com", "password", "userTest3");
		userControllerTestD = new User("testControllers4@email.com", "password", "userTest4");
		List<User> userTestList = new ArrayList<User>();
		userTestList.add(userControllerTestB);
		userTestList.add(userControllerTestC);
		userTestList.add(userControllerTestD);
		pageUsersTest = streamToPage(userTestList.stream(), Pageable.ofSize(3));
	}

	public static <T> Page<T> streamToPage(Stream<T> stream, Pageable pageable) {
		List<T> result = new LinkedList<T>();
	    AtomicLong current = new AtomicLong();
	    long from = pageable.getOffset();
	    long to = from + pageable.getPageSize();
	     stream.forEach(instance -> {
	         var index = current.get();
	         if(index >= from && index < to) {
	             result.add(instance);
	         }
	         current.incrementAndGet();
	      });
	     return new PageImpl<T>(result, pageable, current.get());
	  }
	
	@Test
	@WithUserDetails("testControllers@email.com")
	public void testGetUser() throws Exception{
		when(userService.getUsers(anyInt())).thenReturn(pageUsersTest);
		mockMvc.perform(get("/user"))
				.andExpect(status().isOk())
				.andExpect(view().name("home"))
				.andExpect(model().attributeExists("users"));
	}
	
	@Test
	@WithUserDetails("testControllers@email.com")
	public void testAddFriend() throws Exception{
		when(userService.getUserByEmail("testControllers@email.com")).thenReturn(userControllerTestA);
		when(userService.getUserByEmail("testControllers2@email.com")).thenReturn(userControllerTestA);
		when(userService.addFriend(userControllerTestA, userControllerTestB)).thenReturn(true);
		mockMvc.perform(get("/addFriend")
				.queryParam("page", "0")
				.queryParam("email", "testControllers2@email.com"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/transfer"));

	}
	
	@Test
	@WithUserDetails("testControllers@email.com")
	public void testAddBankAccount() throws Exception{
		when(userService.getUserByEmail("testControllers@email.com")).thenReturn(userControllerTestA);
		mockMvc.perform(post("/addBankAccount")
					.param("accountNumber", "112564686")
					.with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/profile"));

	}
}
