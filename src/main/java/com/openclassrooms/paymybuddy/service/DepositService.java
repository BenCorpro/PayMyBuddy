package com.openclassrooms.paymybuddy.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.model.Deposit;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.model.util.Flow;
import com.openclassrooms.paymybuddy.repository.DepositRepository;

@Transactional
@Service
public class DepositService {

  @Autowired
  private DepositRepository depositRepository;
  
  public List<Deposit> getDeposits(){
    return depositRepository.findAll();
  }
  
  public List<Deposit> getDepositsBySourceUser(User user){
    return depositRepository.findBySourceUser(user);
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
  
  public void deleteDepositsBySourceUser(User user) {
    depositRepository.deleteBySourceUser(user);
  }
  
  public boolean addDeposit(String description, BigDecimal amount, User sourceUser, Flow flow) {
    BigDecimal newBalance = new BigDecimal(0);
    switch (flow) {
      case CREDIT : newBalance = sourceUser.getBalance().add(amount);
                     break;
      case DEBIT :  if (sourceUser.getBalance().compareTo(amount) >= 0) {  
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
