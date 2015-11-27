package com.vpbank.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Account
 *
 */
@Entity

public class Account implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;

	public Account() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private Long accountNumber;

	private String accountNumberIBAN;

	private String accountName;

	private Integer amount;

	@Enumerated(EnumType.STRING)
	private AccountType accountType;

	@Temporal(TemporalType.DATE)
	private Date dateOpen;

	@Temporal(TemporalType.DATE)
	private Date dateClose;

	@ManyToOne
	@JoinColumn(name = "client_fk")
	private Client ownerOfAccount;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountNumberIBAN() {
		return accountNumberIBAN;
	}

	public void setAccountNumberIBAN(String accountNumberIBAN) {
		this.accountNumberIBAN = accountNumberIBAN;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public Date getDateOpen() {
		return dateOpen;
	}

	public void setDateOpen(Date dateOpen) {
		this.dateOpen = dateOpen;
	}

	public Date getDateClose() {
		return dateClose;
	}

	public void setDateClose(Date dateClose) {
		this.dateClose = dateClose;
	}

	public Client getOwnerOfAccount() {
		return ownerOfAccount;
	}

	public void setOwnerOfAccount(Client ownerOfAccount) {
		this.ownerOfAccount = ownerOfAccount;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", accountNumber=" + accountNumber + ", accountNumberIBAN=" + accountNumberIBAN
				+ ", accountName=" + accountName + ", amount=" + amount + ", accountType=" + accountType + ", dateOpen="
				+ dateOpen + ", dateClose=" + dateClose + ", ownerOfAccount=" + ownerOfAccount + "]";
	}

}
