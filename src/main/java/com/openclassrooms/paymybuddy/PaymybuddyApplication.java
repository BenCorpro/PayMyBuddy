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
	  
	  List<User> users = userService.getUsers();
	  userService.addFriend(users.get(1), users.get(2));
	  
	  //List<Deposit> deposits = depositService.getDeposits();
	  //depositService.deleteDepositById(19);
	  
	  /**Transfer testTransfer = new Transfer();
	  testTransfer.setDebtor(users.get(1));
	  testTransfer.setCreditor(users.get(3));
	  testTransfer.setFee(new BigDecimal(3.5));
	  testTransfer.setDateTime(Calendar.getInstance());
	  testTransfer.setDescription("test a la con");
	  testTransfer.setAmount(new BigDecimal(35));
	  transferService.saveTransfer(testTransfer);**/
	  
	  /**Deposit testDeposit = new Deposit();
	  testDeposit.setDebtor(users.get(1));
	  testDeposit.setBankAccount("684351");
	  testDeposit.setDateTime(Calendar.getInstance());
	  testDeposit.setDescription("test depot orphan");
	  testDeposit.setAmount(new BigDecimal(45));
    depositService.saveDeposit(testDeposit);**/
	  
	  //Optional<User> optUser = userService.getUserById(3);
	  //User userId3 = optUser.get();
	  //userId3.setBalance(new BigDecimal(20));
	  //User userId1 = optUser.get();
	  //System.out.println(userId1.getUsername());
	  
	  /**User newUser = new User();
	  newUser.setUsername("branlos");
	  newUser.setEmail("bgbranlos@loufia.com");
	  newUser.setPassword("braggjack");
	  
	  User newUserBis = new User();
	  newUserBis.setUsername("claire");
	  newUserBis.setEmail("cloic@mail.com");
	  newUserBis.setPassword("cloclaire");
	  
	  Set<User> UserSetTest = new HashSet<User>();
	  UserSetTest.add(newUserBis);
	  
	  newUser.setConnections(UserSetTest);
	  
	  userService.saveUser(newUser);**/
	  
	  
	  
	  //newUser = userService.saveUser(newUser);
	  //userService.saveUser(userId3);
	  
	  //userService.getUsers().forEach(user -> System.out.println(user.getUsername()));
	  //userId3 = userService.getUserById(3).get();
	  //System.out.println(userId3.getBalance());
	  
	  //userService.deleteUserById(3);
	  //Iterable<User> users = userService.getUsers();
    //users.forEach(user -> System.out.println(user.getUsername()));
	  
	}
}
