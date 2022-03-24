package com.openclassrooms.paymybuddy.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.UserRepository;

@Transactional
@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;
  
  public Page<User> getUsers(int page){
    return userRepository.findAll(PageRequest.of(page, 3));
  }
  
  public Optional<User> getUserById(Integer id){
    return userRepository.findById(id);
  }
  
  public Page<User> getUsersByEmail(String email, int page){
    return userRepository.findByEmailContains(email, PageRequest.of(page, 3));
  }
  
  public Optional<User> getUserByEmail(String email){
    return userRepository.findByEmail(email);
  }
  
  public boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }
  
  public User saveUser(User user) {
    if(this.existsByEmail(user.getEmail())) return null;
    return userRepository.save(user);
  }
  
  public User updateUser(User user) {
    return userRepository.save(user);
  }
  
  public void deleteUserById(Integer id) {
    userRepository.deleteById(id);
  }
  
  public void deleteUserByEmail(String email) {
    userRepository.deleteByEmail(email);
  }
  
  public boolean addFriend(User user, User newFriend) {
    if(this.getConnections(user.getId()).contains(newFriend)) return false;
    return user.addConnection(newFriend);
  }
  
  public List<User> getConnections(int userId){
    User currentUser = userRepository.findById(userId).get();
    return currentUser.getConnections();
  }
  
}
