package com.openclassrooms.paymybuddy.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.openclassrooms.paymybuddy.dto.RegisterDTO;
import com.openclassrooms.paymybuddy.exceptions.UserAccountException;
import com.openclassrooms.paymybuddy.exceptions.UserConnectionException;
import com.openclassrooms.paymybuddy.model.User;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class UserServiceTest {
  
  @Autowired
  private UserService userService;
  
  @AfterAll
  public void cleanDB() {
    userService.deleteUserByEmail("utilisateurRecTest@email.com");
    userService.deleteUserByEmail("utilisateurNewTest@email.com");
    User userTestDel = new User("utilisateurDelTest@email.com", "deltest123", "delTesteur");
    userService.saveUser(userTestDel);
  }

  @Test
  public void getUsers_ReturnsAllUsers() {
    Page<User> results = userService.getUsers(0);
    List<User> listResults = results.getContent();
    assertNotNull(results);
    assertEquals("JacobBoyd", listResults.get(1).getUsername());
  }

  @Test
  public void getUserById_CorrectId_ReturnsUser() {
    User result = userService.getUserById(3);
    assertNotNull(result);
    assertEquals("TenleyBoyd", result.getUsername());
  }
  
  @Test
  public void saveUser_CorrectInfos_ReturnsUser() {
    User userRecTest = new User("utilisateurRecTest@email.com", "restest123", "recTesteur");
    userRecTest = userService.saveUser(userRecTest);
    assertNotNull(userRecTest);
    assertEquals("restest123", userRecTest.getPassword());
  }
  
  @Test
  public void deleteUser_ExistingUser() {
  User userDelTest = userService.getUserByEmail("utilisateurDelTest@email.com");
    userService.deleteUserById(userDelTest.getId());
    assertNull(userService.getUserByEmail("utilisateurDelTest@email.com"));
  }
  
  @Test
  public void getUsersByEmail_ExistingUsers() {
    Page<User> usersResultPage = userService.getUsersByEmail("te", 0);
    List<User> usersResultList = usersResultPage.toList();
    assertNotNull(usersResultList);
    assertEquals(3, usersResultList.size());
    assertEquals("TenleyBoyd", usersResultList.get(0).getUsername());
  }
  
  @Test
  public void getUsersByEmail_NoUserMatching() {
    Page<User> usersResultPage = userService.getUsersByEmail("nomatch", 0);
    List<User> usersResultList = usersResultPage.toList();
    assertNotNull(usersResultList);
    assertEquals(0, usersResultList.size());
  }
  
  @Test
  public void newUser_NotExisting_returnsUser() throws UserAccountException{
	  RegisterDTO registerDtoTest = new RegisterDTO();
	  registerDtoTest.setEmail("utilisateurNewTest@email.com");
	  registerDtoTest.setPassword("12345678");
	  registerDtoTest.setUsername("utilisateurNewTest");
	  User userNewTest = userService.newUser(registerDtoTest);
	  assertNotNull(userNewTest);
	  assertEquals("utilisateurNewTest", userNewTest.getUsername());
  }
  
  
  @Test
  public void addBankAccount_AccountNumberAdded() {
	  User userAccountTest = userService.getUserById(10);
	  userService.addBankAccount(userAccountTest, "20202020");
	  assertNotNull(userAccountTest.getBankAccount());
	  assertTrue(userAccountTest.getBankAccount().equals("20202020"));
  }
  
  @Transactional
  @Test
  public void addFriend_ExistingUser() throws UserConnectionException{
    User userFriendTest = userService.getUserById(6);
    userService.addFriend(userFriendTest, userService.getUserById(15));
    assertNotNull(userService.getConnections(userFriendTest.getId()));
    assertTrue(userService.getConnections(userFriendTest.getId()).contains(userService.getUserById(15)));
  }
  
  
  @Test
  public void getConnections_NoFriends() {
    User userFriendTest = userService.getUserById(7);
    List<User> friendListTest = userService.getConnections(userFriendTest.getId());
    assertTrue(friendListTest.isEmpty());
  }
  
}
