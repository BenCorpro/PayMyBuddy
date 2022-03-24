package com.openclassrooms.paymybuddy.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
import javax.transaction.Transactional;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name="utilisateur")
public class User implements Serializable {
  
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="id")
  private int id;
  
  @Size(min=5, max=45)
  @NotNull
  @Email
  @Column(name="email", unique=true, length=45, nullable=false)
  private String email;
  
  @Size(min=8, max=255)
  @NotNull
  @Column(name="mot_de_passe", length=45, nullable=false)
  private String password;
  
  @Size(min=3, max=45)
  @NotNull
  @Column(name="nom_utilisateur", length=45, nullable=false)
  private String username;
  
  @Column(name="solde", scale=10, precision=2)
  private BigDecimal balance;
  
  @Column(name="compte_bancaire", length=100)
  private String bankAccount;
  
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name="connexion",
      joinColumns = @JoinColumn(name="utilisateur_id", nullable=false),
      inverseJoinColumns = @JoinColumn(name="ami_id", nullable=false)
  )
  private List<User> connections;
  
  @OneToMany(mappedBy="payeeUser",
             fetch = FetchType.LAZY)
  private List<Transfer> transfers;
  
  @OneToMany(mappedBy="sourceUser",
            fetch = FetchType.LAZY,
             cascade = CascadeType.REMOVE)
  private List<Deposit> deposits;
  
  
  public User() {
  }

  public User(String email, String password, String username) {
  this.email = email;
  this.password = password;
  this.username = username;
  }
  
  
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

  public boolean addConnection(User newConnection) {
    return this.connections.add(newConnection);
  }
  
  public void addDeposit(Deposit newDeposit) {
    this.deposits.add(newDeposit);
    newDeposit.setSourceUser(this);
  }
  
  public void addTransfer(Transfer newTransfer) {
    this.transfers.add(newTransfer);
    newTransfer.setSourceUser(this);
  }

  
  @Override
  public int hashCode() {
    return Objects.hash(email);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    User other = (User) obj;
    return Objects.equals(email, other.email);
  }
  
  
}
