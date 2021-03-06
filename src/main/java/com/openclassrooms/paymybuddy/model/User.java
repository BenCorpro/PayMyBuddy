package com.openclassrooms.paymybuddy.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
public class User implements Serializable {
  
  private static final long serialVersionUID = 2982260233979389424L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="id")
  private int id;
  
  @Column(name="email", unique=true, length=45, nullable=false)
  private String email;

  @Column(name="mot_de_passe", length=255, nullable=false)
  private String password;
  
  @Column(name="nom_utilisateur", length=45, nullable=false)
  private String username;
  
  @Column(name="solde", scale=10, precision=2)
  private BigDecimal balance = new BigDecimal(0);
  
  @Column(name="compte_bancaire", length=100)
  private String bankAccount = "Enter a bank account to make deposits";
  
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
            fetch = FetchType.LAZY)
  private List<Deposit> deposits;
  
  
  public User() {
  }

  public User(String email, String password, String username) {
  this.email = email;
  this.password = password;
  this.username = username;
  }
  
  public User(int id, String email, String password, String username, BigDecimal balance, String bankAccount,
		List<User> connections, List<Transfer> transfers, List<Deposit> deposits) {
	this.id = id;
	this.email = email;
	this.password = password;
	this.username = username;
	this.balance = balance;
	this.bankAccount = bankAccount;
	this.connections = connections;
	this.transfers = transfers;
	this.deposits = deposits;
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
