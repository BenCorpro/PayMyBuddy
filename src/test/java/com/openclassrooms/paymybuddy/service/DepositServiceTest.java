package com.openclassrooms.paymybuddy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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

import com.openclassrooms.paymybuddy.model.Deposit;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.model.util.Flow;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class DepositServiceTest {

  @Autowired
  private DepositService depositService;
  @Autowired
  private UserService userService;
  
  @AfterAll
  public void cleanDB() {
    depositService.deleteDepositsBySourceUser(userService.getUserById(9).get());
    Deposit depositTestDel = new Deposit(Calendar.getInstance(), "versementDelTest", new BigDecimal(1.23), userService.getUserById(16).get(), Flow.CREDIT);
    depositService.saveDeposit(depositTestDel);
  }
  
  @Test
  public void getDeposits_ReturnsAllDeposits() {
    List<Deposit> results = depositService.getDeposits();
    assertNotNull(results);
    assertEquals(20, results.get(8).getSourceUser().getId());
  }
  
  @Test
  public void getDepositById_CorrectId_ReturnsDeposit() {
    Deposit result = depositService.getDepositById(4).get();
    assertNotNull(result);
    assertEquals(6, result.getSourceUser().getId());
  }
  
  @Test
  public void saveDeposit_CorrectInfos_ReturnsDeposit() {
    Deposit depositRecTest = new Deposit(Calendar.getInstance(), "versementRecTest", new BigDecimal(12.3), userService.getUserById(9).get(), Flow.DEBIT);
    depositRecTest = depositService.saveDeposit(depositRecTest);
    assertNotNull(depositRecTest);
    assertEquals(12.3, depositRecTest.getAmount().doubleValue());
  }
  
  @Test
  public void deleteDepositById_ExistingDeposit() {
  List<Deposit> depositDelTest = depositService.getDepositsBySourceUser(userService.getUserById(16).get());
    depositService.deleteDepositById(depositDelTest.get(0).getId());
    assertTrue(depositService.getDepositsBySourceUser(userService.getUserById(16).get()).isEmpty());
  }
  
  @Transactional
  @Test
  public void addDeposit_CreditCorrectAmount() {
    User userDepositTest = userService.getUserById(24).get();
    depositService.addDeposit("TestSoldeNullDB", new BigDecimal(20), userDepositTest, Flow.CREDIT);
    assertNotNull(depositService.getDepositsBySourceUser(userDepositTest));
    assertEquals(20.00, depositService.getDepositsBySourceUser(userDepositTest).get(0).getAmount().doubleValue());
    assertEquals(20.00, userService.getUserById(24).get().getBalance().doubleValue());
  }
  
  @Transactional
  @Test
  public void addDeposit_DebitSufficientBalance() {
    User userDepositTest = userService.getUserById(17).get();
    depositService.addDeposit("TestDebitSufficient", new BigDecimal(30), userDepositTest, Flow.DEBIT);
    assertNotNull(depositService.getDepositsBySourceUser(userDepositTest));
    assertEquals(30.00, depositService.getDepositsBySourceUser(userDepositTest).get(0).getAmount().doubleValue());
    assertEquals(944.51, userService.getUserById(17).get().getBalance().doubleValue());
  }
  
  @Transactional
  @Test
  public void addDeposit_DebitInsufficientBalance() {
    User userDepositTest = userService.getUserById(13).get();
    boolean result = depositService.addDeposit("TestDebitInsufficient", new BigDecimal(30), userDepositTest, Flow.DEBIT);
    assertFalse(result);
  }
}
