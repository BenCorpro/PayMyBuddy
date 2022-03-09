package com.openclassrooms.paymybuddy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.paymybuddy.model.Transfer;
import com.openclassrooms.paymybuddy.model.User;

@SpringBootTest
public class TransferServiceTest {

  @Autowired
  private TransferService transferService;
  @Autowired
  private UserService userService;
  
  @Test
  public void getTransfers_ReturnsAllTransfers() {
    List<Transfer> results = transferService.getTransfers();
    assertNotNull(results);
    assertEquals(15, results.size());
    assertEquals(2, results.get(1).getSourceUser());
  }
  
  @Test
  public void getTransferById_CorrectId_ReturnsTransfer() {
    Transfer result = transferService.getTransferById(3).get();
    assertNotNull(result);
    assertEquals(5, result.getPayeeUser().getId());
  }
  
  @Test
  public void saveTransfer_CorrectInfos_ReturnsTransfer() {
    Transfer newTransfer = new Transfer();
    newTransfer.setSourceUser(userService.getUserById(15).get());
    newTransfer.setPayeeUser(userService.getUserById(9).get());
    newTransfer.setDateTime(Calendar.getInstance());
    newTransfer.setDescription("virementTest");
    newTransfer.setAmount(new BigDecimal(42));
    newTransfer.setFee(new BigDecimal(5));
    newTransfer = transferService.saveTransfer(newTransfer);
    assertNotNull(newTransfer);
    assertEquals(42, newTransfer.getAmount().intValue());
  }
  
  @Test
  public void deleteTransferById_ExistingTransfer() {
    transferService.deleteTransferById(5);
    assertTrue(transferService.getTransferById(5).isEmpty());
  }
}
