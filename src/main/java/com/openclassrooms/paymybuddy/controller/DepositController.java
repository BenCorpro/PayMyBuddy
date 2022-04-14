package com.openclassrooms.paymybuddy.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.openclassrooms.paymybuddy.dto.DepositDTO;
import com.openclassrooms.paymybuddy.exceptions.UserBalanceAmountException;
import com.openclassrooms.paymybuddy.model.Deposit;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.DepositService;
import com.openclassrooms.paymybuddy.service.UserService;

@Controller
public class DepositController {

  @Autowired
  private DepositService depositService;
  @Autowired
  private UserService userService;

  @GetMapping("/profile")
  public String profile(Model model, Authentication authentication, @RequestParam(name="page", defaultValue="0") int page) {
    User currentUser = userService.getUserByEmail(authentication.getName());
    Page<Deposit> pageDeposits = depositService.getDepositsBySourceUser(currentUser, page);
    model.addAttribute("deposits", pageDeposits.getContent());
    model.addAttribute("pages", new int[pageDeposits.getTotalPages()]);
    model.addAttribute("currentPage", page);
    model.addAttribute("bankAccount",currentUser.getBankAccount());
    model.addAttribute("balance",currentUser.getBalance());
    model.addAttribute("newDeposit", new DepositDTO());
    return "profile";
  }

  @PostMapping("/profile")
  public String saveDeposit(Authentication authentication, @Valid @ModelAttribute("newDeposit") DepositDTO depositDto, BindingResult bindingResult) {
    if(bindingResult.hasErrors()) return "profile";
    User currentUser = userService.getUserByEmail(authentication.getName());
    try { depositService.addDeposit(currentUser, depositDto);
    } catch (UserBalanceAmountException ubaEx) {
      bindingResult.rejectValue("amount", ubaEx.getMessage(), ubaEx.getMessage());
      return "profile";
    }
    return "redirect:/profile";
  }
}
