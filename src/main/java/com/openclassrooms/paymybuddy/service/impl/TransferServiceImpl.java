package com.openclassrooms.paymybuddy.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.dto.TransferDTO;
import com.openclassrooms.paymybuddy.exceptions.UserBalanceAmountException;
import com.openclassrooms.paymybuddy.model.Transfer;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.TransferRepository;
import com.openclassrooms.paymybuddy.repository.UserRepository;
import com.openclassrooms.paymybuddy.service.TransferService;
import com.openclassrooms.paymybuddy.util.FeeCalculator;

@Service
public class TransferServiceImpl implements TransferService {

  @Autowired
  private TransferRepository transferRepository;
  @Autowired
  private UserRepository userRepository;
  
  @Override
public List<Transfer> getTransfers() {
    return transferRepository.findAll();
  }
  
  @Override
public Page<Transfer> getTransfersBySourceUser(User user, int page){
    return transferRepository.findBySourceUser(user, PageRequest.of(page, 3));
  }
  
  @Override
  public Page<Transfer> getTransfersByAnyUsers(User sourceUser, User payeeUser,int page){
	    return transferRepository.findBySourceUserOrPayeeUser(sourceUser, payeeUser,PageRequest.of(page, 3));
	  }
  
  @Override
public Transfer getTransferById(Integer id){
    return transferRepository.findById(id).orElse(null);
  }
  
  @Override
@Transactional
  public Transfer saveTransfer(Transfer transfer){
    return transferRepository.save(transfer);
  }
  
  @Override
@Transactional
  public void deleteTransferById(Integer id) {
    transferRepository.deleteById(id);
  }
  
  @Override
@Transactional
  public void deleteTransfersBySourceUser(User user){
	transferRepository.deleteBySourceUser(user);
  }
  
  @Override
@Transactional
  public boolean addTransfer(User sourceUser, TransferDTO transferDto) throws UserBalanceAmountException, NoSuchElementException{
    User payeeUser = userRepository.findById(transferDto.getPayeeUserId()).orElseThrow();
    if(!sourceUser.getConnections().contains(payeeUser)) return false;
    BigDecimal amount = transferDto.getAmount();
    BigDecimal fee = FeeCalculator.feeCalculator(amount);
    BigDecimal totalAmount = amount.add(fee);
    if (sourceUser.getBalance().compareTo(totalAmount) >= 0) {
      sourceUser.setBalance(sourceUser.getBalance().subtract(totalAmount));
      payeeUser.setBalance(payeeUser.getBalance().add(amount));
    } else {
      throw new UserBalanceAmountException("Funds not sufficient to send transfer");
    }
    
    Transfer newTransfer = new Transfer();
    newTransfer.setAmount(amount);
    newTransfer.setFee(fee);
    newTransfer.setDateTime(Calendar.getInstance());
    newTransfer.setDescription(transferDto.getDescription());
    newTransfer.setSourceUser(sourceUser);
    newTransfer.setPayeeUser(payeeUser);
    sourceUser.addTransfer(newTransfer);
    this.saveTransfer(newTransfer);
    
    return true;
  }
  
}
