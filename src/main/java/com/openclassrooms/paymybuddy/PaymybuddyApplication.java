package com.openclassrooms.paymybuddy;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.openclassrooms.paymybuddy.constants.Constants;
import com.openclassrooms.paymybuddy.model.Deposit;
import com.openclassrooms.paymybuddy.model.Transfer;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.DepositService;
import com.openclassrooms.paymybuddy.service.TransferService;
import com.openclassrooms.paymybuddy.service.UserService;

@SpringBootApplication
public class PaymybuddyApplication implements CommandLineRunner{

  @Autowired
  private UserService userService;
  
  @Autowired
  private TransferService transferService;
  
  @Autowired
  private DepositService depositService;
  
	public static void main(String[] args) {
		SpringApplication.run(PaymybuddyApplication.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception{
	  
	  User testSourceUser = userService.getUserById(111).get();
	  User testPayeeUser = userService.getUserById(114).get();
	  transferService.addTransfer("test transfer", new BigDecimal(30), testSourceUser, testPayeeUser);
	  
	}
}
