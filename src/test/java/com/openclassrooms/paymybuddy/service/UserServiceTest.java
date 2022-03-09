package com.openclassrooms.paymybuddy.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.paymybuddy.model.User;

@SpringBootTest
public class UserServiceTest {
  
  @Autowired
  private UserService userService;
  
  @Test
  public void getUsers_ReturnsAllUsers() {
    List<User> results = userService.getUsers();
    assertNotNull(results);
    assertEquals(23, results.size());
    assertEquals("JacobBoyd", results.get(1).getUsername());
  }
  
  @Test
  public void getUserById_CorrectId_ReturnsUser() {
    User result = userService.getUserById(3).get();
    assertNotNull(result);
    assertEquals("TenleyBoyd", result.getUsername());
  }
  
  @Test
  public void saveUser_CorrectInfos_ReturnsUser() {
    User newUser = new User();
    newUser.setEmail("neotstr@mail.com");
    newUser.setPassword("calimero26");
    newUser.setUsername("NouveauTesteur");
    newUser.setBalance(new BigDecimal(55));
    newUser.setBankAccount("creditagricole36");
    newUser = userService.saveUser(newUser);
    assertNotNull(newUser);
    assertEquals("calimero26", newUser.getPassword());
  }
  
  @Test
  public void deleteUser_ExistingUser() {
    userService.deleteUserById(5);
    assertTrue(userService.getUserById(5).isEmpty());
  }

}
