package com.openclassrooms.paymybuddy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.paymybuddy.model.Deposit;
import com.openclassrooms.paymybuddy.model.User;

@Repository
public interface DepositRepository extends JpaRepository<Deposit, Integer>{

  public Page<Deposit> findBySourceUser(User user, Pageable pageable);

  public void deleteBySourceUser(User user);
}
