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

	private Long accountNumberBasic;
	
	private Integer accountNumberPrefix;
	
	private String bankCode;

	private String accountNumberIBAN;

	private String accountName;

	private Double amount;

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

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
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

	public Long getAccountNumberBasic() {
		return accountNumberBasic;
	}

	public void setAccountNumberBasic(Long accountNumberBasic) {
		this.accountNumberBasic = accountNumberBasic;
	}

	public Integer getAccountNumberPrefix() {
		return accountNumberPrefix;
	}

	public void setAccountNumberPrefix(Integer accountNumberPrefix) {
		this.accountNumberPrefix = accountNumberPrefix;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	

}
