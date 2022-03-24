package com.openclassrooms.paymybuddy.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.UserService;

@Controller
public class UserController {
  
  @Autowired
  private UserService userService;
  
  @GetMapping("/user/")
  public String home(Model model, @RequestParam(name="page", defaultValue="0") int page) {
    Page<User> pageUsers = userService.getUsers(page);
    model.addAttribute("users", pageUsers.getContent());
    model.addAttribute("pages", new int[pageUsers.getTotalPages()]);
    model.addAttribute("currentPage", page);
    return "home2";
  }
  
  @GetMapping("/user/userhome")
  public String chercher(Model model, @RequestParam(name="page", defaultValue="0") int page,
                                  @RequestParam(name="motCle", defaultValue="") String email) {
    Page<User> pageUsers = userService.getUsersByEmail(email, page);
    model.addAttribute("users", pageUsers.getContent());
    model.addAttribute("pages", new int[pageUsers.getTotalPages()]);
    model.addAttribute("currentPage", page);
    return "home2";
  }
  
  @GetMapping("/admin/delete")
  public String delete(int id, int page, String motCle) {
    userService.deleteUserById(id);
    return "redirect:/user/userhome?page="+page+"&motCle="+motCle;
  }
  
  @GetMapping("/admin/formUtilisateur")
  public String form(Model model) {
    model.addAttribute("user", new User());
    return "FormUtilisateur";
  }
  
  @GetMapping("/admin/edit")
  public String edit(Model model, int id) {
    User user = userService.getUserById(id).get();
    model.addAttribute("user", user);
    return "EditUtilisateur";
  }
  
  @GetMapping("/user/{userId}")
  public String getUser(Model model, @PathVariable("userId") final int userId) {
    User user = userService.getUserById(userId).get();
    model.addAttribute("user", user);
    return "home2";
  }
  
  @GetMapping("/connexions/{userId}")
  public String getConnections(Model model, @PathVariable("userId") final int userId) {
    List<User> connexions = userService.getConnections(userId);
    model.addAttribute("connexions", connexions);
    return "connections";
  }
  
  @PostMapping("/admin/saveUser")
  public String saveUser(@Valid User user, BindingResult bindingResult, Model model) {
    if(bindingResult.hasErrors()) return "FormUtilisateur";
    userService.saveUser(user);
    return "redirect:/user/";
  }

  @GetMapping("/")
  public String defaut() {
    return "redirect:/user/";
  }
  
  @GetMapping("/403")
  public String notAutorized() {
    return "403";
  }
  
  @GetMapping("/login")
  public String login() {
    return "login";
  }
}
