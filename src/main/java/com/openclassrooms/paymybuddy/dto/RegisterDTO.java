package com.openclassrooms.paymybuddy.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegisterDTO {

  @Size(min=5, max=45)
  @NotNull
  @Email
  private String email;
  
  @Size(min=8, max=255)
  @NotNull
  private String password;
  
  @Size(min=3, max=45)
  @NotNull
  private String username;

  
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
  
  
}
