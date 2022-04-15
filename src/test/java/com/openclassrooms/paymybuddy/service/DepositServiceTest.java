package com.openclassrooms.paymybuddy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.openclassrooms.paymybuddy.dto.DepositDTO;
import com.openclassrooms.paymybuddy.exceptions.UserAccountException;
import com.openclassrooms.paymybuddy.exceptions.UserBalanceAmountException;
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
    depositService.deleteDepositsBySourceUser(userService.getUserById(9));
    depositService.deleteDepositsBySourceUser(userService.getUserById(8));
    Deposit depositTestDel = new Deposit(Calendar.getInstance(), "versementDelTest", new BigDecimal(1.23), userService.getUserById(16), Flow.CREDIT);
    depositService.saveDeposit(depositTestDel);
  }
  
  @Test
  public void getDeposits_ReturnsAllDeposits() {
    List<Deposit> results = depositService.getDeposits();
    assertNotNull(results);
    assertEquals(15, results.get(8).getSourceUser().getId());
  }
  
  @Test
  public void getDepositById_CorrectId_ReturnsDeposit() {
    Deposit result = depositService.getDepositById(4);
    assertNotNull(result);
    assertEquals(6, result.getSourceUser().getId());
  }
  
  @Test
  public void saveDeposit_CorrectInfos_ReturnsDeposit() {
    Deposit depositRecTest = new Deposit(Calendar.getInstance(), "versementRecTest", new BigDecimal(12.3), userService.getUserById(9), Flow.DEBIT);
    depositRecTest = depositService.saveDeposit(depositRecTest);
    assertNotNull(depositRecTest);
    assertEquals(12.3, depositRecTest.getAmount().doubleValue());
  }
  
  @Test
  public void deleteDepositById_ExistingDeposit() {
	Page<Deposit> depositDelTest = depositService.getDepositsBySourceUser(userService.getUserById(16), 0);
    List<Deposit> listDepositDelTest = depositDelTest.getContent();
	depositService.deleteDepositById(listDepositDelTest.get(0).getId());
    assertTrue(depositService.getDepositsBySourceUser(userService.getUserById(16), 0).isEmpty());
  }
  
  @Transactional
  @Test
  public void addDeposit_CreditCorrectAmount() throws UserBalanceAmountException, UserAccountException{
    User userDepositTest = userService.getUserById(8);
    DepositDTO depositDtoTest = new DepositDTO();
    depositDtoTest.setAmount(new BigDecimal(20));
    depositDtoTest.setDescription("TestSoldeNullDB");
    depositDtoTest.setFlow(Flow.CREDIT);
    depositService.addDeposit(userDepositTest, depositDtoTest);
    assertNotNull(depositService.getDepositsBySourceUser(userDepositTest, 0));
    assertEquals(20.00, depositService.getDepositsBySourceUser(userDepositTest, 0).toList().get(0).getAmount().doubleValue());
    assertEquals(29.51, userService.getUserById(8).getBalance().doubleValue());
  }
  
  @Transactional
  @Test
  public void addDeposit_DebitSufficientBalance() throws UserBalanceAmountException, UserAccountException{
    User userDepositTest = userService.getUserById(17);
    DepositDTO depositDtoTest = new DepositDTO();
    depositDtoTest.setAmount(new BigDecimal(30));
    depositDtoTest.setDescription("TestDebitSufficient");
    depositDtoTest.setFlow(Flow.DEBIT);
    depositService.addDeposit(userDepositTest, depositDtoTest);
    assertNotNull(depositService.getDepositsBySourceUser(userDepositTest, 0));
    assertEquals(30.00, depositService.getDepositsBySourceUser(userDepositTest, 0).toList().get(0).getAmount().doubleValue());
    assertEquals(944.51, userService.getUserById(17).getBalance().doubleValue());
  }
  
  @Transactional
  @Test
  public void addDeposit_DebitInsufficientBalance() throws UserBalanceAmountException{
    User userDepositTest = userService.getUserById(13);
    DepositDTO depositDtoTest = new DepositDTO();
    depositDtoTest.setAmount(new BigDecimal(30));
    depositDtoTest.setDescription("TestDebitInsufficient");
    depositDtoTest.setFlow(Flow.DEBIT);
    assertThrows(UserBalanceAmountException.class, () -> depositService.addDeposit(userDepositTest, depositDtoTest));
  }
}
