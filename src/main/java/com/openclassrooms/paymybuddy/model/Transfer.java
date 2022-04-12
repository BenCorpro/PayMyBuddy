package com.openclassrooms.paymybuddy.model;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="virement")
public class Transfer extends Transaction {
  
  private static final long serialVersionUID = 4863808798882822612L;

  @Column(name="frais", scale=10, precision=2, nullable=false)
  private BigDecimal fee;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="utilisateur_destinataire", nullable=false)
  private User payeeUser;

  
  public Transfer() {
  }

  public Transfer(Calendar dateTime, String description, BigDecimal amount, User sourceUser, User payeeUser, BigDecimal fee) {
  super(dateTime, description, amount, sourceUser);
  this.payeeUser = payeeUser;
  this.fee = fee;
  }
  
  
  public User getPayeeUser() {
    return payeeUser;
  }

  public void setPayeeUser(User payeeUser) {
    this.payeeUser = payeeUser;
  }

  public BigDecimal getFee() {
    return fee;
  }

  public void setFee(BigDecimal fee) {
    this.fee = fee;
  }
  
  
}
