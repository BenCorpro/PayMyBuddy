package com.openclassrooms.paymybuddy.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class TransferDTO {
  
  @NotNull
  private int payeeUserId;
  
  private String description;
  
  @NotNull
  @Positive
  private BigDecimal amount;

  
  public int getPayeeUserId() {
    return payeeUserId;
  }

  public void setPayeeUserId(int payeeUserId) {
    this.payeeUserId = payeeUserId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }
  
  
}
