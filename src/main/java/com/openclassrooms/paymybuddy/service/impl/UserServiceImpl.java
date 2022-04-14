package com.openclassrooms.paymybuddy.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.dto.RegisterDTO;
import com.openclassrooms.paymybuddy.dto.UserDTO;
import com.openclassrooms.paymybuddy.exceptions.UserAccountException;
import com.openclassrooms.paymybuddy.exceptions.UserConnectionException;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.UserRepository;
import com.openclassrooms.paymybuddy.service.UserService;
import com.openclassrooms.paymybuddy.service.auth.UserDetailsServiceImpl;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserDetailsServiceImpl userDetailsService;
  

  @Override
public Page<User> getUsers(int page){
    return userRepository.findAll(PageRequest.of(page, 3));
  }
  
  @Override
public User getUserById(Integer id){
    return userRepository.findById(id).orElse(null);
  }
  
  @Override
public Page<UserDTO> getUsersDTOByEmail(String email, int page){
    return userRepository.findByEmailContains(email, PageRequest.of(page, 3)).map(user -> new UserDTO(user.getEmail(), user.getUsername()));
  }
  
  @Override
public Page<User> getUsersByEmail(String email, int page){
	    return userRepository.findByEmailContains(email, PageRequest.of(page, 3));
	  }
  
  @Override
public User getUserByEmail(String email){
    return userRepository.findByEmail(email).orElse(null);
  }
  
  @Override
public boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }
  
  @Override
@Transactional
  public User saveUser(User user) {
    if(this.existsByEmail(user.getEmail())) return null;
    return userRepository.save(user);
  }
  
  @Override
@Transactional
  public User newUser(RegisterDTO newUserDto) throws UserAccountException {
    if(this.existsByEmail(newUserDto.getEmail())) {
      throw new UserAccountException("An account is already registered with this email!");
    }
    return userDetailsService.saveUser(newUserDto);
  }
  
  @Override
@Transactional
  public User updateUser(User user) {
    return userRepository.save(user);
  }
  
  @Override
@Transactional
  public void deleteUserById(Integer id) {
    userRepository.deleteById(id);
  }
  
  @Override
@Transactional
  public void deleteUserByEmail(String email) {
	  userRepository.deleteByEmail(email);
  }
  
  @Override
@Transactional
  public void addBankAccount(User user, String accountNumber) {
    user.setBankAccount(accountNumber);
  }
  
  @Override
@Transactional
  public boolean addFriend(User user, User newFriend) throws UserConnectionException {
    if(user.equals(newFriend)) {
      throw new UserConnectionException("You can't add yourself as a buddy!");
    } else if (this.getConnections(user.getId()).contains(newFriend)){
      throw new UserConnectionException("This contact is already in your list of buddies!");
    }
    return user.addConnection(newFriend);
  }

  @Override
@Transactional
  public List<User> getConnections(int userId){
    User currentUser = userRepository.findById(userId).get();
    return currentUser.getConnections();
  }

  
}
