package com.openclassrooms.paymybuddy.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="virement")
public class Transfer extends Transaction {
  
  @Column(name="frais")
  private BigDecimal fee;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="utilisateur_destinataire")
  private User payeeUser;

  
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
