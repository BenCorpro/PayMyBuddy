package com.openclassrooms.paymybuddy.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.openclassrooms.paymybuddy.dto.DepositDTO;
import com.openclassrooms.paymybuddy.exceptions.UserBalanceAmountException;
import com.openclassrooms.paymybuddy.model.Deposit;
import com.openclassrooms.paymybuddy.model.User;

public interface DepositService {

	List<Deposit> getDeposits();

	Page<Deposit> getDepositsBySourceUser(User user, int page);

	Deposit getDepositById(Integer id);

	Deposit saveDeposit(Deposit deposit);

	void deleteDepositById(Integer id);

	void deleteDepositsBySourceUser(User user);

	boolean addDeposit(User sourceUser, DepositDTO depositDto) throws UserBalanceAmountException;

}