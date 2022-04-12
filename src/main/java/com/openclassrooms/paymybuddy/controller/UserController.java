package com.openclassrooms.paymybuddy.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.openclassrooms.paymybuddy.dto.UserDTO;
import com.openclassrooms.paymybuddy.exceptions.UserConnectionException;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.UserService;

@Controller
public class UserController {
  
  @Autowired
  private UserService userService;
  
  @GetMapping("/user")
  public String home(Model model, @RequestParam(name="page", defaultValue="0") int page) {
    Page<User> pageUsers = userService.getUsers(page);
    model.addAttribute("usersDto", pageUsers.getContent());
    model.addAttribute("pages", new int[pageUsers.getTotalPages()]);
    model.addAttribute("currentPage", page);
     
    return "home";
  }
  
  @GetMapping("/userSearch")
  public String chercher(Model model, @RequestParam(name="page", defaultValue="0") int page,
                                  @RequestParam(name="motCle", defaultValue="") String email) {
    Page<UserDTO> pageUsersDTO = userService.getUsersDTOByEmail(email, page);
    model.addAttribute("users", pageUsersDTO.getContent());
    model.addAttribute("pages", new int[pageUsersDTO.getTotalPages()]);
    model.addAttribute("currentPage", page);
    model.addAttribute("motCle", email);
    return "home";
  }
  
  @GetMapping("/addFriend")
  public String addFriend(Authentication authentication, String email, int page, String motCle, Model model) {
   User currentUser = userService.getUserByEmail(authentication.getName()).get();
   User newFriend = userService.getUserByEmail(email).get();
   try {userService.addFriend(currentUser, newFriend); 
   } catch (UserConnectionException ucEx) {
     model.addAttribute("connection", ucEx.getMessage());
     return "home";
   }
   return "redirect:/transfer";
  }
  
  @PostMapping("/addBankAccount")
  public String addBankAccount(Authentication authentication, String accountNumber) {
   User currentUser = userService.getUserByEmail(authentication.getName()).get();
   userService.addBankAccount(currentUser, accountNumber); 
   return "redirect:/profile";
  }

  
}
