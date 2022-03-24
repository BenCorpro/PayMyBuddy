package com.openclassrooms.paymybuddy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.paymybuddy.model.Transfer;
import com.openclassrooms.paymybuddy.model.User;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Integer>{

  public List<Transfer> findBySourceUser(User user);
  
  public List<Transfer> findByPayeeUser(User user);
  
  public void deleteBySourceUser(User user);
  
  public void deleteByPayeeUser(User user);
  
}
