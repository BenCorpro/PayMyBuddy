package com.openclassrooms.paymybuddy.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.openclassrooms.paymybuddy.dto.RegisterDTO;
import com.openclassrooms.paymybuddy.exceptions.UserAccountException;
import com.openclassrooms.paymybuddy.service.UserService;

@Controller
public class LoginController {

  @Autowired
  private UserService userService;
  
  
  @GetMapping("/login")
  public String getLogin() {
    return "login";
  }
  
  @GetMapping("/")
  public String defaut() {
    return "redirect:/login";
  }

  @GetMapping("/home")
  public String getHome() {
    return "home";
  }
  
  @GetMapping("/registration") 
  public String registration(Model model){
    model.addAttribute("register", new RegisterDTO());
    return "registration";
  }
  
  @PostMapping("/registration")
  public String registerUser(@Valid @ModelAttribute("register") RegisterDTO register, BindingResult bindingResult, Model model) {
    if(bindingResult.hasErrors()) return "registration";
    try{userService.newUser(register);
    } catch (UserAccountException uaEx) {
      bindingResult.rejectValue("email", uaEx.getMessage(), uaEx.getMessage());
      return "registration";
    }
    return "redirect:/login";
  }
  
}
