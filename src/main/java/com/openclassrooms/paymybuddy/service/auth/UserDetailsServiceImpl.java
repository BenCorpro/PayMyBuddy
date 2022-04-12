package com.openclassrooms.paymybuddy.service.auth;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.dto.RegisterDTO;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;
  
  @Override 
  public UserDetails loadUserByUsername(String email)
      throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email).get();
    if(user==null) {
      throw new UsernameNotFoundException("user: " + email + " not found!");
    }
    return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
  }

  public User saveUser(RegisterDTO newUserDto) {
    User newUser = new User(newUserDto.getEmail(),passwordEncoder.encode(newUserDto.getPassword()),newUserDto.getUsername());
    return userRepository.save(newUser);
  }
  
}
