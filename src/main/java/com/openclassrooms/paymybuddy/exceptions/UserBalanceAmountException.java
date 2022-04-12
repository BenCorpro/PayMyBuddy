package com.openclassrooms.paymybuddy.exceptions;

public class UserBalanceAmountException extends Exception {

  private static final long serialVersionUID = 8084538073211990396L;

  public UserBalanceAmountException(String message) {
    super(message);
  }
 
}
