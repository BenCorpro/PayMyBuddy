package com.openclassrooms.paymybuddy.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.constants.Constants;
import com.openclassrooms.paymybuddy.model.Deposit;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.DepositRepository;

@Service
public class DepositService {

  @Autowired
  private DepositRepository depositRepository;
  
  public List<Deposit> getDeposits(){
    return depositRepository.findAll();
  }
  
  public Optional<Deposit> getDepositById(Integer id){
    return depositRepository.findById(id);
  }
  
  public Deposit saveDeposit(Deposit deposit) {
    return depositRepository.save(deposit);
  }
  
  public void deleteDepositById(Integer id) {
    depositRepository.deleteById(id);
  }
  
  public boolean addDeposit(String description, BigDecimal amount, User sourceUser, String flow) {
    BigDecimal newBalance = new BigDecimal(0);
    switch (flow) {
      case Constants.CREDIT : newBalance = sourceUser.getBalance().add(amount);
                     break;
      case Constants.DEBIT :  if (sourceUser.getBalance().compareTo(amount) >= 0) {  
                       newBalance = sourceUser.getBalance().subtract(amount);
                     } else {
                       return false;
                     }
                     break;
    }
    sourceUser.setBalance(newBalance);
    
    Deposit newDeposit = new Deposit();
    newDeposit.setAmount(amount);
    newDeposit.setDateTime(Calendar.getInstance());
    newDeposit.setDescription(description);
    newDeposit.setFlow(flow);
    sourceUser.addDeposit(newDeposit);
    this.saveDeposit(newDeposit);
    
    
    return true;
  }

}
