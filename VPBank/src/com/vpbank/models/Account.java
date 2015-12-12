package com.vpbank.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * Entity implementation class for Entity: Account
 *
 */
@NamedQuery(name = "findAccountsByBasicNumber", query = "SELECT acc FROM Account acc WHERE acc.accountNumberBasic = :accountNumberBasic")
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

    private String codeSWIFT;

    private String accountNumberIBAN;

    private String accountName;

    private double amount;

    @Enumerated(EnumType.STRING)
    private Currency currency;

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
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getAccountNumberBasic() {
        return this.accountNumberBasic;
    }

    public void setAccountNumberBasic(long accountNumberBasic) {
        this.accountNumberBasic = accountNumberBasic;
    }

    public int getAccountNumberPrefix() {
        return this.accountNumberPrefix;
    }

    public void setAccountNumberPrefix(int accountNumberPrefix) {
        this.accountNumberPrefix = accountNumberPrefix;
    }

    public short getBankCode() {
        return this.bankCode;
    }

    public void setBankCode(short bankCode) {
        this.bankCode = bankCode;
    }

    public String getSWIFTcode() {
        return this.codeSWIFT;
    }

    public void setSWIFTcode(String sWIFTcode) {
        this.codeSWIFT = sWIFTcode;
    }

    public String getAccountNumberIBAN() {
        return this.accountNumberIBAN;
    }

    public void setAccountNumberIBAN(String accountNumberIBAN) {
        this.accountNumberIBAN = accountNumberIBAN;
    }

    public String getAccountName() {
        return this.accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public AccountType getAccountType() {
        return this.accountType;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Date getDateOpen() {
        return this.dateOpen;
    }

    public void setDateOpen(Date dateOpen) {
        this.dateOpen = dateOpen;
    }

    public Date getDateClose() {
        return this.dateClose;
    }

    public void setDateClose(Date dateClose) {
        this.dateClose = dateClose;
    }

    public Client getOwnerOfAccount() {
        return this.ownerOfAccount;
    }

    public void setOwnerOfAccount(Client ownerOfAccount) {
        this.ownerOfAccount = ownerOfAccount;
    }

    @Override
    public String toString() {
        return "Account [id=" + this.id + ", accountNumberBasic=" + this.accountNumberBasic + ", accountNumberPrefix="
                + this.accountNumberPrefix + ", bankCode=" + this.bankCode + ", SWIFTcode=" + this.codeSWIFT + ", accountNumberIBAN="
                + this.accountNumberIBAN + ", accountName=" + this.accountName + ", amount=" + this.amount + ", currency=" + this.currency
                + ", accountType=" + this.accountType + ", dateOpen=" + this.dateOpen + ", dateClose=" + this.dateClose
                + ", ownerOfAccount=" + this.ownerOfAccount + "]";
    }

}
