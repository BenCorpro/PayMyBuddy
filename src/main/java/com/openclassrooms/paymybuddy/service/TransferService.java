package com.openclassrooms.paymybuddy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.model.Transfer;
import com.openclassrooms.paymybuddy.repository.TransferRepository;

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
  
}
