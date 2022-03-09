package com.openclassrooms.paymybuddy.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="utilisateur")
public class User {
  
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="id")
  private int id;
  
  @Column(name="email")
  private String email;
  
  @Column(name="mot_de_passe")
  private String password;
  
  @Column(name="nom_utilisateur")
  private String username;
  
  @Column(name="solde")
  private BigDecimal balance;
  
  @Column(name="compte_bancaire")
  private String bankAccount;
  
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name="connexion",
      joinColumns = @JoinColumn(name="utilisateur_id"),
      inverseJoinColumns = @JoinColumn(name="ami_id")
  )
  private List<User> connections;
  
  @OneToMany(mappedBy="payeeUser",
             fetch = FetchType.LAZY)
  private List<Transfer> transfers;
  
  @OneToMany(mappedBy="sourceUser",
            fetch = FetchType.LAZY,
             cascade = CascadeType.ALL)
  private List<Deposit> deposits;
  
  
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

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

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  public String getBankAccount() {
    return bankAccount;
  }

  public void setBankAccount(String bankAccount) {
    this.bankAccount = bankAccount;
  }

  public List<User> getConnections() {
    return new ArrayList<>(this.connections);
  }

  public void setConnections(List<User> connections) {
    this.connections = connections;
  }

  public List<Transfer> getTransfers() {
    return new ArrayList<>(this.transfers);
  }

  public void setTransfers(List<Transfer> transfers) {
    this.transfers = transfers;
  }

  public List<Deposit> getDeposits() {
    return new ArrayList<>(this.deposits);
  }

  public void setDeposits(List<Deposit> deposits) {
    this.deposits = deposits;
  }

  public void addConnections(User newConnection) {
    connections.add(newConnection);
  }
  
}
