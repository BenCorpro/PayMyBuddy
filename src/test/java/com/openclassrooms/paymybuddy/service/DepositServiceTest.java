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

import com.openclassrooms.paymybuddy.constants.Constants;
import com.openclassrooms.paymybuddy.model.Deposit;
import com.openclassrooms.paymybuddy.model.Transfer;

@SpringBootTest
public class DepositServiceTest {

  @Autowired
  DepositService depositService;
  @Autowired
  private UserService userService;
  
  @Test
  public void getDeposits_ReturnsAllTransfers() {
    List<Deposit> results = depositService.getDeposits();
    assertNotNull(results);
    assertEquals(16, results.size());
    assertEquals(4, results.get(1).getSourceUser().getId());
  }
  
  @Test
  public void getDepositById_CorrectId_ReturnsDeposit() {
    Deposit result = depositService.getDepositById(4).get();
    assertNotNull(result);
    assertEquals(6, result.getSourceUser().getId());
  }
  
  @Test
  public void saveDeposit_CorrectInfos_ReturnsDeposit() {
    Deposit newDeposit = new Deposit();
    newDeposit.setSourceUser(userService.getUserById(16).get());
    newDeposit.setDateTime(Calendar.getInstance());
    newDeposit.setDescription("versementTest");
    newDeposit.setAmount(new BigDecimal(36));
    newDeposit.setFlow(Constants.DEBIT);
    newDeposit = depositService.saveDeposit(newDeposit);
    assertNotNull(newDeposit);
    assertEquals(36, newDeposit.getAmount().intValue());
  }
  
  @Test
  public void deleteDepositById_ExistingDeposit() {
    depositService.deleteDepositById(6);
    assertTrue(depositService.getDepositById(6).isEmpty());
  }
}
