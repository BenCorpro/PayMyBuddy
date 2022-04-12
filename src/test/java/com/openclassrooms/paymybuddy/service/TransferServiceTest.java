package com.openclassrooms.paymybuddy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import org.springframework.data.domain.Page;

import com.openclassrooms.paymybuddy.dto.TransferDTO;
import com.openclassrooms.paymybuddy.exceptions.UserBalanceAmountException;
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
	Page<Transfer> transferDelTest = transferService.getTransfersBySourceUser(userService.getUserById(11).get(), 0);
	List<Transfer> listTransferDelTest = transferDelTest.getContent();
    transferService.deleteTransferById(listTransferDelTest.get(0).getId());
    assertTrue(transferService.getTransfersBySourceUser(userService.getUserById(11).get(), 0).isEmpty());
  }
  
  @Transactional
  @Test
  public void addTransfer_friendWithFounds() throws UserBalanceAmountException{
    User sourceUserTransferTest = userService.getUserById(20).get();
    User payeeUserTransferTest = userService.getUserById(19).get();
    TransferDTO transferDtoTest = new TransferDTO();
    transferDtoTest.setAmount(new BigDecimal(50));
    transferDtoTest.setDescription("TestTransferFriendandFounds");
    transferDtoTest.setPayeeUserId(payeeUserTransferTest.getId());
    transferService.addTransfer(sourceUserTransferTest, transferDtoTest);
    assertNotNull(transferService.getTransfersBySourceUser(sourceUserTransferTest, 0));
    assertEquals(50.00, transferService.getTransfersBySourceUser(sourceUserTransferTest, 0).toList().get(2).getAmount().doubleValue());
    assertEquals(924.94, userService.getUserById(20).get().getBalance().doubleValue());
    assertEquals(105.49, userService.getUserById(19).get().getBalance().doubleValue());
  }
  
  @Transactional
  @Test
  public void addTransfer_notFriend() throws UserBalanceAmountException{
    User sourceUserTransferTest = userService.getUserById(18).get();
    User payeeUserTransferTest = userService.getUserById(19).get();
    TransferDTO transferDtoTest = new TransferDTO();
    transferDtoTest.setAmount(new BigDecimal(50));
    transferDtoTest.setDescription("TestTransferNotFriends");
    transferDtoTest.setPayeeUserId(payeeUserTransferTest.getId());
    assertFalse(transferService.addTransfer(sourceUserTransferTest, transferDtoTest));
    assertTrue(transferService.getTransfersBySourceUser(sourceUserTransferTest, 0).isEmpty());
  }
  
  @Transactional
  @Test
  public void addTransfer_noSufficientFounds() throws UserBalanceAmountException{
    User sourceUserTransferTest = userService.getUserById(13).get();
    User payeeUserTransferTest = userService.getUserById(14).get();
    TransferDTO transferDtoTest = new TransferDTO();
    transferDtoTest.setAmount(new BigDecimal(50));
    transferDtoTest.setDescription("TestTransferNoSufficientFounds");
    transferDtoTest.setPayeeUserId(payeeUserTransferTest.getId());
    assertThrows(UserBalanceAmountException.class,() -> transferService.addTransfer(sourceUserTransferTest, transferDtoTest));
  }
}
