package com.openclassrooms.paymybuddy.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.openclassrooms.paymybuddy.model.util.Flow;

public class DepositDTO {

  private String description;
  
  @NotNull
  @Positive
  private BigDecimal amount;
  
  @NotNull
  private Flow flow;

  
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

  public Flow getFlow() {
    return flow;
  }

  public void setFlow(Flow flow) {
    this.flow = flow;
  }
  
  
}
