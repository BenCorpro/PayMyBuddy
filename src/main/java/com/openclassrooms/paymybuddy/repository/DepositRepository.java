package com.openclassrooms.paymybuddy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.paymybuddy.model.Deposit;
import com.openclassrooms.paymybuddy.model.User;

@Repository
public interface DepositRepository extends JpaRepository<Deposit, Integer>{

  public List<Deposit> findBySourceUser(User user);
  
  public void deleteBySourceUser(User user);
  
}
