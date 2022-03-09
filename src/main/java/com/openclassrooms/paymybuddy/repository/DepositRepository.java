package com.openclassrooms.paymybuddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.paymybuddy.model.Deposit;

@Repository
public interface DepositRepository extends JpaRepository<Deposit, Integer>{

}
