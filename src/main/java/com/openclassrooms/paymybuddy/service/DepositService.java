package com.openclassrooms.paymybuddy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.model.Deposit;
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

}
