package com.openclassrooms.paymybuddy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.paymybuddy.model.Transfer;
import com.openclassrooms.paymybuddy.model.User;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Integer>{

  public Page<Transfer> findBySourceUser(User user, Pageable pageable);
  
  public Page<Transfer> findBySourceUserOrPayeeUser(User sourceUser, User payeeUser, Pageable pageable);
  
  public void deleteBySourceUser(User user);
}
