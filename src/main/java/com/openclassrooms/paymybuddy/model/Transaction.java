package com.openclassrooms.paymybuddy.model;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class Transaction {
  
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="id")
  private int id;
  
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name="horodatage")
  private Calendar dateTime;
  
  @Column(name="description")
  private String description;
  
  @Column(name="montant")
  private BigDecimal amount;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="utilisateur_source")
  private User sourceUser;
  
  
  public User getSourceUser() {
    return sourceUser;
  }

  public void setSourceUser(User sourceUser) {
    this.sourceUser = sourceUser;
  }

  public Calendar getDateTime() {
    return dateTime;
  }

  public void setDateTime(Calendar dateTime) {
    this.dateTime = dateTime;
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
