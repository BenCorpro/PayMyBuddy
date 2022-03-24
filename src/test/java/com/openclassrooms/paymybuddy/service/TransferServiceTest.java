package com.openclassrooms.paymybuddy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.paymybuddy.model.Transfer;
import com.openclassrooms.paymybuddy.model.User;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class TransferServiceTest {

  @Autowired
  private TransferService transferService;
  @Autowired
  private UserService userService;
  
  @AfterAll
  public void cleanDB() {
    transferService.deleteTransfersBySourceUser(userService.getUserById(8).get());
    Transfer transferTestDel = new Transfer(Calendar.getInstance(), "virementDelTest", new BigDecimal(123), userService.getUserById(11).get(), userService.getUserById(13).get(), new BigDecimal(1.23));
    transferService.saveTransfer(transferTestDel);
  }
  
  @Test
  public void getTransfers_ReturnsAllTransfers() {
    List<Transfer> results = transferService.getTransfers();
    assertNotNull(results);
    assertEquals(2, results.get(1).getSourceUser().getId());
  }
  
  @Test
  public void getTransferById_CorrectId_ReturnsTransfer() {
    Transfer result = transferService.getTransferById(3).get();
    assertNotNull(result);
    assertEquals(5, result.getPayeeUser().getId());
  }
  
  @Test
  public void saveTransfer_CorrectInfos_ReturnsTransfer() {
    Transfer transferRecTest = new Transfer(Calendar.getInstance(), "virementDelTest", new BigDecimal(123), userService.getUserById(8).get(), userService.getUserById(14).get(), new BigDecimal(1.23));
    transferRecTest = transferService.saveTransfer(transferRecTest);
    assertNotNull(transferRecTest);
    assertEquals(123, transferRecTest.getAmount().intValue());
  }
  
  @Test
  public void deleteTransferById_ExistingTransfer() {
  List<Transfer> transferDelTest = transferService.getTransfersBySourceUser(userService.getUserById(11).get());
    transferService.deleteTransferById(transferDelTest.get(0).getId());
    assertTrue(transferService.getTransfersBySourceUser(userService.getUserById(11).get()).isEmpty());
  }
  
  @Transactional
  @Test
  public void addTransfer_friendWithFounds() {
    User sourceUserTransferTest = userService.getUserById(19).get();
    User payeeUserTransferTest = userService.getUserById(20).get();
    transferService.addTransfer("TestTransferFriendandFounds", new BigDecimal(50), sourceUserTransferTest, payeeUserTransferTest);
    assertNotNull(transferService.getTransfersBySourceUser(sourceUserTransferTest));
    assertEquals(50.00, transferService.getTransfersBySourceUser(sourceUserTransferTest).get(0).getAmount().doubleValue());
    assertEquals(924.51, userService.getUserById(19).get().getBalance().doubleValue());
    assertEquals(1024.26, userService.getUserById(20).get().getBalance().doubleValue());
  }
  
  @Transactional
  @Test
  public void addTransfer_notFriend() {
    User sourceUserTransferTest = userService.getUserById(18).get();
    User payeeUserTransferTest = userService.getUserById(19).get();
    assertFalse(transferService.addTransfer("TestTransferNotFriends", new BigDecimal(50), sourceUserTransferTest, payeeUserTransferTest));
    assertTrue(transferService.getTransfersBySourceUser(sourceUserTransferTest).isEmpty());
  }
  
  @Transactional
  @Test
  public void addTransfer_noSufficientFounds() {
    User sourceUserTransferTest = userService.getUserById(13).get();
    User payeeUserTransferTest = userService.getUserById(14).get();
    assertFalse(transferService.addTransfer("TestTransferNoSufficientFounds", new BigDecimal(50), sourceUserTransferTest, payeeUserTransferTest));
    assertTrue(transferService.getTransfersBySourceUser(sourceUserTransferTest).isEmpty());
  }
}
