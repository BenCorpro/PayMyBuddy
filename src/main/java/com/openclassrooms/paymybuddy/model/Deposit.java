package com.openclassrooms.paymybuddy.model;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.openclassrooms.paymybuddy.model.util.Flow;

@Entity
@Table(name="versement")
public class Deposit extends Transaction {
  
  @Enumerated(EnumType.STRING)
  @Column(name="mouvement", length=6, nullable=false)
  private Flow flow;

  
  public Deposit() {
  }

  public Deposit(Calendar dateTime, String description, BigDecimal amount, User sourceUser, Flow flow) {
  super(dateTime, description, amount, sourceUser);
  this.flow = flow;
  }
  
  
  public Flow getFlow() {
    return flow;
  }

  public void setFlow(Flow flow) {
    this.flow = flow;
  }
  
  
}
