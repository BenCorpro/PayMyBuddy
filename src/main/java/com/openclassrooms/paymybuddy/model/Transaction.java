package com.openclassrooms.paymybuddy.model;

import java.io.Serializable;
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
public abstract class Transaction implements Serializable {
  
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="id")
  private int id;
  
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name="horodatage", nullable=false)
  private Calendar dateTime;
  
  @Column(name="description", length=255)
  private String description;
  
  @Column(name="montant", scale=10, precision=2, nullable=false)
  private BigDecimal amount;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="utilisateur_source", nullable=false)
  private User sourceUser;
  
  
  public Transaction() {
  }

  public Transaction(Calendar dateTime, String description, BigDecimal amount, User sourceUser) {
  this.dateTime = dateTime;
  this.description = description;
  this.amount = amount;
  this.sourceUser = sourceUser;
  }

  
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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

  public User getSourceUser() {
    return sourceUser;
  }

  public void setSourceUser(User sourceUser) {
    this.sourceUser = sourceUser;
  }
  
   
}
