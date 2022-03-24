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

import com.openclassrooms.paymybuddy.model.User;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class UserServiceTest {
  
  @Autowired
  private UserService userService;
  
  @AfterAll
  public void cleanDB() {
    userService.deleteUserByEmail("utilisateurRecTest@mail.com");
    User userTestDel = new User("utilisateurDelTest@mail.com", "deltest123", "delTesteur");
    userService.saveUser(userTestDel);
  }
  /**
  @Test
  public void getUsers_ReturnsAllUsers() {
    List<User> results = userService.getUsers();
    assertNotNull(results);
    assertEquals("JacobBoyd", results.get(1).getUsername());
  }
  **/
  @Test
  public void getUserById_CorrectId_ReturnsUser() {
    User result = userService.getUserById(3).get();
    assertNotNull(result);
    assertEquals("TenleyBoyd", result.getUsername());
  }
  
  @Test
  public void saveUser_CorrectInfos_ReturnsUser() {
    User userRecTest = new User("utilisateurRecTest@mail.com", "restest123", "recTesteur");
    userRecTest = userService.saveUser(userRecTest);
    assertNotNull(userRecTest);
    assertEquals("restest123", userRecTest.getPassword());
  }
  
  @Test
  public void deleteUser_ExistingUser() {
  User userDelTest = userService.getUserByEmail("utilisateurDelTest@mail.com").get();
    userService.deleteUserById(userDelTest.getId());
    assertTrue(userService.getUserByEmail("utilisateurDelTest@mail.com").isEmpty());
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
  
  @Transactional
  @Test
  public void addFriend_ExistingUser() {
    User userFriendTest = userService.getUserById(6).get();
    userService.addFriend(userFriendTest, userService.getUserById(15).get());
    assertNotNull(userService.getConnections(userFriendTest.getId()));
    assertTrue(userService.getConnections(userFriendTest.getId()).contains(userService.getUserById(15).get()));
  }
  
  @Transactional
  @Test
  public void getConnections_NoFriends() {
    User userFriendTest = userService.getUserById(6).get();
    List<User> friendListTest = userService.getConnections(userFriendTest.getId());
    assertTrue(friendListTest.isEmpty());
  }
  
}
