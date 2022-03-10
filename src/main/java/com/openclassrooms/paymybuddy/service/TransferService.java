package com.openclassrooms.paymybuddy.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.model.Transfer;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.TransferRepository;
import com.openclassrooms.paymybuddy.util.FeeCalculator;

@Service
public class TransferService {

  @Autowired
  private TransferRepository transferRepository;
  
  public List<Transfer> getTransfers() {
    return transferRepository.findAll();
  }
  
  public Optional<Transfer> getTransferById(Integer id){
    return transferRepository.findById(id);
  }
  
  public Transfer saveTransfer(Transfer transfer){
    return transferRepository.save(transfer);
  }
  
  public void deleteTransferById(Integer id) {
    transferRepository.deleteById(id);
  }
  
  public boolean addTransfer(String description, BigDecimal amount, User sourceUser, User payeeUser) {
    BigDecimal fee = new BigDecimal(0);
    if (sourceUser.getBalance().compareTo(amount) >= 0) {
      fee = FeeCalculator.feeCalculator(amount);
      sourceUser.setBalance(sourceUser.getBalance().subtract(amount));
      payeeUser.setBalance(payeeUser.getBalance().add(amount.subtract(fee)));
    } else {
      return false;
    }
    
    Transfer newTransfer = new Transfer();
    newTransfer.setAmount(amount);
    newTransfer.setFee(fee);
    newTransfer.setDateTime(Calendar.getInstance());
    newTransfer.setDescription(description);
    newTransfer.setSourceUser(sourceUser);
    newTransfer.setPayeeUser(payeeUser);
    sourceUser.addTransfer(newTransfer);
    this.saveTransfer(newTransfer);
    
    return true;
  }
  
}
