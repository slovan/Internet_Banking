package com.vpbank.models;

import java.io.Serializable;
import java.text.Collator;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

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
 * Entity implementation class for Entity: Account. 
 * Represents persistent data of accounts maintained in database
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
    
    private static Comparator<Account> byDateOfClose = new Comparator<Account>() {
        public int compare(Account acc1, Account acc2) {
            Date dateClose1 = acc1.getDateClose();
            Date dateClose2 = acc2.getDateClose();
            return dateClose1.compareTo(dateClose2);
        }
    };
    
    private static Comparator<Account> byAccountType = new Comparator<Account>() {
        public int compare(Account acc1, Account acc2) {
            AccountType accountType1 = acc1.getAccountType();
            AccountType accountType2 = acc2.getAccountType();
            return accountType1.compareTo(accountType2);
        }
    };
    
    private static Comparator<Account> byBalance = new Comparator<Account>() {
        public int compare(Account acc1, Account acc2) {
            Double balance1 = acc1.getAmount();
            Double balance2 = acc2.getAmount();
            return balance2.compareTo(balance1);
        }
    };
    
    private static Comparator<Account> byOwnerLastName = new Comparator<Account>() {
        public int compare(Account acc1, Account acc2) {
            Client c1 = acc1.getOwnerOfAccount();
            Client c2 = acc2.getOwnerOfAccount();
            Collator skCollator = Collator.getInstance(new Locale("sk_SK"));
            skCollator.setStrength(Collator.SECONDARY);
            String lastName1 = c1.getLastName();
            String lastName2 = c2.getLastName();
            return skCollator.compare(lastName1, lastName2);
        }
    };
    
    private static Comparator<Account> byOwnerFirstName = new Comparator<Account>() {
        public int compare(Account acc1, Account acc2) {
            Client c1 = acc1.getOwnerOfAccount();
            Client c2 = acc2.getOwnerOfAccount();
            Collator skCollator = Collator.getInstance(new Locale("sk_SK"));
            skCollator.setStrength(Collator.SECONDARY);
            String firstName1 = c1.getFirstName();
            String firstName2 = c2.getFirstName();
            return skCollator.compare(firstName1, firstName2);
        }
    };
    
    private static Comparator<Account> byOwnerName = Account.byOwnerLastName.thenComparing(Account.byOwnerFirstName);

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

    public static Comparator<Account> getByDateOfClose() {
        return byDateOfClose;
    }

    public static void setByDateOfClose(Comparator<Account> byDateOfClose) {
        Account.byDateOfClose = byDateOfClose;
    }

    public static Comparator<Account> getByAccountType() {
        return byAccountType;
    }

    public static void setByAccountType(Comparator<Account> byAccountType) {
        Account.byAccountType = byAccountType;
    }

    public static Comparator<Account> getByBalance() {
        return byBalance;
    }

    public static void setByBalance(Comparator<Account> byBalance) {
        Account.byBalance = byBalance;
    }

    public static Comparator<Account> getByOwnerName() {
        return byOwnerName;
    }

    public static void setByOwnerName(Comparator<Account> byOwnerName) {
        Account.byOwnerName = byOwnerName;
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
