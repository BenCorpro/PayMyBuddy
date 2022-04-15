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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.openclassrooms.paymybuddy.model.Transfer;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.TransferService;
import com.openclassrooms.paymybuddy.service.UserService;

@WebMvcTest(controllers=TransferController.class)
@Import(UserDetailsServiceTest.class)
public class TransferControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean 
	private UserService userService;
	@MockBean 
	private TransferService transferService;
	
	private User sourceUserControllerTest;
	private User destUserControllerTest;
	private Page<Transfer> pageTransfersTest;
	private TransferDTO transferDtoTest;
	private List<User> controlConnectionListTest;
	
	@BeforeEach
	public void setup() {
	sourceUserControllerTest = new User("testControllers1@email.com", "password", "userTest1");
	destUserControllerTest = new User("testControllers2@email.com", "password", "userTest2");
	Transfer transferTest1 = new Transfer(Calendar.getInstance(), "testTransfer1", new BigDecimal(42), sourceUserControllerTest, destUserControllerTest,new BigDecimal(0.42));
	Transfer transferTest2 = new Transfer(Calendar.getInstance(), "testTransfer2", new BigDecimal(12), sourceUserControllerTest, destUserControllerTest,new BigDecimal(0.12));
	Transfer transferTest3 = new Transfer(Calendar.getInstance(), "testTransfer3", new BigDecimal(58), sourceUserControllerTest, destUserControllerTest,new BigDecimal(0.58));
	List<Transfer> transferTestList = new ArrayList<Transfer>();
	transferTestList.add(transferTest1);
	transferTestList.add(transferTest2);
	transferTestList.add(transferTest3);
	pageTransfersTest = streamToPage(transferTestList.stream(), Pageable.ofSize(3));
	transferDtoTest = new TransferDTO();
	transferDtoTest.setAmount(transferTest1.getAmount());
	transferDtoTest.setDescription(transferTest1.getDescription());
	transferDtoTest.setPayeeUserId(1);
	controlConnectionListTest = new ArrayList<User>();
	controlConnectionListTest.add(destUserControllerTest);
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
	public void testGetTransfer() throws Exception{
		when(userService.getUserByEmail("testControllers@email.com")).thenReturn(sourceUserControllerTest);
		when(userService.getConnections(anyInt())).thenReturn(controlConnectionListTest);
		when(transferService.getTransfersByAnyUsers(sourceUserControllerTest, sourceUserControllerTest, 0)).thenReturn(pageTransfersTest);
		mockMvc.perform(get("/transfer"))
				.andExpect(status().isOk())
				.andExpect(view().name("transfer"))
				.andExpect(model().attributeExists("transfers"))
				.andExpect(model().attributeExists("connectionList"));
	}
	
	@Test
	@WithUserDetails("testControllers@email.com")
	public void testSaveTransfer() throws Exception{
		when(userService.getUserByEmail("testControllers@email.com")).thenReturn(sourceUserControllerTest);
		when(transferService.addTransfer(sourceUserControllerTest, transferDtoTest)).thenReturn(true);
		mockMvc.perform(post("/transfer")
					.param("payeeUserId", "1")
					.param("amount", "38")
					.param("description", "controllerTransferTest")
					.with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/transfer"));

	}
	
}
