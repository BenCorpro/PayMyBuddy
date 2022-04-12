package com.openclassrooms.paymybuddy.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.openclassrooms.paymybuddy.dto.TransferDTO;
import com.openclassrooms.paymybuddy.exceptions.UserBalanceAmountException;
import com.openclassrooms.paymybuddy.model.Transfer;
import com.openclassrooms.paymybuddy.model.User;

public interface TransferService {

	List<Transfer> getTransfers();

	Page<Transfer> getTransfersBySourceUser(User user, int page);

	Optional<Transfer> getTransferById(Integer id);

	Transfer saveTransfer(Transfer transfer);

	void deleteTransferById(Integer id);

	void deleteTransfersBySourceUser(User user);

	boolean addTransfer(User sourceUser, TransferDTO transferDto)
			throws UserBalanceAmountException, NoSuchElementException;

}