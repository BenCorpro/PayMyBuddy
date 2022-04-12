package com.openclassrooms.paymybuddy.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.dto.DepositDTO;
import com.openclassrooms.paymybuddy.exceptions.UserBalanceAmountException;
import com.openclassrooms.paymybuddy.model.Deposit;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.DepositRepository;
import com.openclassrooms.paymybuddy.service.DepositService;

@Service
public class DepositServiceImpl implements DepositService {

  @Autowired
  private DepositRepository depositRepository;
  
  @Override
public List<Deposit> getDeposits(){
    return depositRepository.findAll();
  }
  
  @Override
public Page<Deposit> getDepositsBySourceUser(User user, int page){
    return depositRepository.findBySourceUser(user, PageRequest.of(page, 3));
  }
  
  @Override
public Optional<Deposit> getDepositById(Integer id){
    return depositRepository.findById(id);
  }
  
  @Override
@Transactional
  public Deposit saveDeposit(Deposit deposit) {
    return depositRepository.save(deposit);
  }
  
  @Override
@Transactional
  public void deleteDepositById(Integer id) {
    depositRepository.deleteById(id);
  }
  
  @Override
@Transactional
  public void deleteDepositsBySourceUser(User user) {
	 depositRepository.deleteBySourceUser(user);
  }
  
  @Override
@Transactional
  public boolean addDeposit(User sourceUser, DepositDTO depositDto) throws UserBalanceAmountException{
    BigDecimal amount = depositDto.getAmount();
    BigDecimal newBalance = new BigDecimal(0);
    switch (depositDto.getFlow()) {
      case CREDIT : newBalance = sourceUser.getBalance().add(amount);
                     break;
      case DEBIT :  if (sourceUser.getBalance().compareTo(amount) >= 0) {  
                       newBalance = sourceUser.getBalance().subtract(amount);
                     } else {
                       throw new UserBalanceAmountException("Funds not sufficient to make deposit");
                     }
                     break;
    }
    sourceUser.setBalance(newBalance);
    
    Deposit newDeposit = new Deposit();
    newDeposit.setAmount(amount);
    newDeposit.setDateTime(Calendar.getInstance());
    newDeposit.setDescription(depositDto.getDescription());
    newDeposit.setFlow(depositDto.getFlow());
    sourceUser.addDeposit(newDeposit);
    this.saveDeposit(newDeposit);
    
    
    return true;
  }

}
