package com.openclassrooms.paymybuddy.controller;

import java.util.List;
import java.util.NoSuchElementException;

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

import com.openclassrooms.paymybuddy.dto.TransferDTO;
import com.openclassrooms.paymybuddy.exceptions.UserBalanceAmountException;
import com.openclassrooms.paymybuddy.model.Transfer;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.TransferService;
import com.openclassrooms.paymybuddy.service.UserService;

@Controller
public class TransferController {

  @Autowired
  private TransferService transferService;
  @Autowired
  private UserService userService;
  
  @GetMapping("/transfer")
  public String transfer(Model model, Authentication authentication, @RequestParam(name="page", defaultValue="0") int page) {
    User currentUser = userService.getUserByEmail(authentication.getName());
    List<User> connectionList = userService.getConnections(currentUser.getId());
    Page<Transfer> pageTransfers = transferService.getTransfersBySourceUser(currentUser, page);
    model.addAttribute("connectionList", connectionList);
    model.addAttribute("transfers", pageTransfers.getContent());
    model.addAttribute("pages", new int[pageTransfers.getTotalPages()]);
    model.addAttribute("currentPage", page);
    model.addAttribute("newTransfer", new TransferDTO());
    return "transfer";
  }
  
  @PostMapping("/transfer")
  public String saveTransfer(Authentication authentication, @Valid @ModelAttribute("newTransfer") TransferDTO transferDto, BindingResult bindingResult, Model model) {
    if(bindingResult.hasErrors()) return "transfer";
    User currentUser = userService.getUserByEmail(authentication.getName());
    try { transferService.addTransfer(currentUser, transferDto);
    } catch (UserBalanceAmountException ubaEx){
      bindingResult.rejectValue("amount", ubaEx.getMessage(), ubaEx.getMessage());
      return "transfer";
    } catch (NoSuchElementException nseEx) {
      bindingResult.rejectValue("payeeUserId", nseEx.getMessage(), "This user isn't registered");
      return "transfer";
    }
    return "redirect:/transfer";
    }

}
