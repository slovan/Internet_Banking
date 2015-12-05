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
	private int id;

	private long accountNumberBasic;

	private int accountNumberPrefix;

	private short bankCode;

	private String SWIFTcode;

	private String accountNumberIBAN;

	private String accountName;

	private double amount;

	private String currency;

	@Enumerated(EnumType.STRING)
	private AccountType accountType;

	@Temporal(TemporalType.DATE)
	private Date dateOpen;

	@Temporal(TemporalType.DATE)
	private Date dateClose;

	@ManyToOne
	@JoinColumn(name = "client_fk")
	private Client ownerOfAccount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getAccountNumberBasic() {
		return accountNumberBasic;
	}

	public void setAccountNumberBasic(long accountNumberBasic) {
		this.accountNumberBasic = accountNumberBasic;
	}

	public int getAccountNumberPrefix() {
		return accountNumberPrefix;
	}

	public void setAccountNumberPrefix(int accountNumberPrefix) {
		this.accountNumberPrefix = accountNumberPrefix;
	}

	public short getBankCode() {
		return bankCode;
	}

	public void setBankCode(short bankCode) {
		this.bankCode = bankCode;
	}

	public String getSWIFTcode() {
		return SWIFTcode;
	}

	public void setSWIFTcode(String sWIFTcode) {
		SWIFTcode = sWIFTcode;
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

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
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
		return "Account [id=" + id + ", accountNumberBasic=" + accountNumberBasic + ", accountNumberPrefix="
				+ accountNumberPrefix + ", bankCode=" + bankCode + ", SWIFTcode=" + SWIFTcode + ", accountNumberIBAN="
				+ accountNumberIBAN + ", accountName=" + accountName + ", amount=" + amount + ", currency=" + currency
				+ ", accountType=" + accountType + ", dateOpen=" + dateOpen + ", dateClose=" + dateClose
				+ ", ownerOfAccount=" + ownerOfAccount + "]";
	}

}
