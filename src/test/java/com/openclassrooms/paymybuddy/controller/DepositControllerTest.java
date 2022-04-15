package com.openclassrooms.paymybuddy.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.when;
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
import com.openclassrooms.paymybuddy.dto.DepositDTO;
import com.openclassrooms.paymybuddy.model.Deposit;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.model.util.Flow;
import com.openclassrooms.paymybuddy.service.DepositService;
import com.openclassrooms.paymybuddy.service.UserService;

@WebMvcTest(controllers = DepositController.class)
@Import(UserDetailsServiceTest.class)
public class DepositControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private DepositService depositService;
	@MockBean 
	private UserService userService;
	
	private User userControllerTest;
	private Page<Deposit> pageDepositsTest;
	private DepositDTO depositDtoTest;
	
	@BeforeEach
	public void setup() {
	userControllerTest = new User("testControllers@email.com", "password", "userTest");
	Deposit depositTest1 = new Deposit(Calendar.getInstance(), "testDeposit1", new BigDecimal(22), userControllerTest, Flow.CREDIT);
	Deposit depositTest2 = new Deposit(Calendar.getInstance(), "testDeposit2", new BigDecimal(10), userControllerTest, Flow.DEBIT);
	Deposit depositTest3 = new Deposit(Calendar.getInstance(), "testDeposit3", new BigDecimal(35), userControllerTest, Flow.CREDIT);
	List<Deposit> depositTestList = new ArrayList<Deposit>();
	depositTestList.add(depositTest1);
	depositTestList.add(depositTest2);
	depositTestList.add(depositTest3);
	pageDepositsTest = streamToPage(depositTestList.stream(), Pageable.ofSize(3));
	depositDtoTest = new DepositDTO();
	depositDtoTest.setAmount(depositTest1.getAmount());
	depositDtoTest.setDescription(depositTest1.getDescription());
	depositDtoTest.setFlow(depositTest1.getFlow());
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
	public void testGetProfile() throws Exception{
		when(userService.getUserByEmail("testControllers@email.com")).thenReturn(userControllerTest);
		when(depositService.getDepositsBySourceUser(userControllerTest, 0)).thenReturn(pageDepositsTest);
		mockMvc.perform(get("/profile"))
				.andExpect(status().isOk())
				.andExpect(view().name("profile"))
				.andExpect(model().attributeExists("deposits"))
				.andExpect(model().attributeExists("bankAccount"))
				.andExpect(model().attributeExists("balance"));
	}
	
	@Test
	@WithUserDetails("testControllers@email.com")
	public void testSaveDeposit() throws Exception{
		when(userService.getUserByEmail("testControllers@email.com")).thenReturn(userControllerTest);
		when(depositService.addDeposit(userControllerTest, depositDtoTest)).thenReturn(true);
		mockMvc.perform(post("/profile")
					.param("flow", "CREDIT")
					.param("amount", "25")
					.param("description", "controllerDepositTest")
					.with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/profile"));

	}

}
