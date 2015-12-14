package com.vpbank.models;

import java.io.Serializable;
import java.text.Collator;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * Entity implementation class for Entity: Client
 *
 */

@Entity
@NamedQueries({
    @NamedQuery(name = "Client.findByFullName", query = "SELECT c FROM Client c WHERE c.lastName LIKE :lastName AND c.firstName LIKE :firstName"),
    @NamedQuery(name = "Client.findByFirstName", query = "SELECT c FROM Client c WHERE c.firstName LIKE :firstName"),
    @NamedQuery(name = "Client.findByLastName", query = "SELECT c FROM Client c WHERE c.lastName LIKE :lastName"),
    @NamedQuery(name = "Client.findByPassNumber", query = "SELECT c FROM Client c WHERE c.passNum LIKE :passNum"),
    @NamedQuery(name = "Client.findById", query = "SELECT c FROM Client c WHERE c.id = :id")
    })
public class Client implements Serializable {

    @Transient
    private static final long serialVersionUID = 1L;

    public Client() {
        super();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String firstName;
    private String lastName;
    private String passNum;

    @Temporal(TemporalType.DATE)
    private Date dob;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;

    @OneToOne(mappedBy = "client", cascade = CascadeType.REMOVE)
    private ClientAccessDetails clientAccessDetails;

    @OneToMany(mappedBy = "ownerOfAccount", cascade = CascadeType.REMOVE)
    private List<Account> accounts;

    private static Comparator<Client> byFirstName = new Comparator<Client>() {
        public int compare(Client c1, Client c2) {
            Collator skCollator = Collator.getInstance(new Locale("sk_SK"));
            skCollator.setStrength(Collator.SECONDARY);
            String firstName1 = c1.getFirstName();
            String firstName2 = c2.getFirstName();
            return skCollator.compare(firstName1, firstName2);
        }
    };

    private static Comparator<Client> byLastName = new Comparator<Client>() {
        public int compare(Client c1, Client c2) {
            Collator skCollator = Collator.getInstance(new Locale("sk_SK"));
            skCollator.setStrength(Collator.SECONDARY);
            String lastName1 = c1.getLastName();
            String lastName2 = c2.getLastName();
            return skCollator.compare(lastName1, lastName2);
        }
    };

    private static Comparator<Client> byDateOfBirth = new Comparator<Client>() {
        public int compare(Client c1, Client c2) {
            Date dob1 = c1.getDob();
            Date dob2 = c2.getDob();
            return dob2.compareTo(dob1);
        }
    };

    private static Comparator<Client> byGender = new Comparator<Client>() {
        public int compare(Client c1, Client c2) {
            Gender gender1 = c1.getGender();
            Gender gender2 = c2.getGender();
            return gender1.compareTo(gender2);
        }
    };

    private static Comparator<Client> byMaritalStatus = new Comparator<Client>() {
        public int compare(Client c1, Client c2) {
            MaritalStatus ms1 = c1.getMaritalStatus();
            MaritalStatus ms2 = c2.getMaritalStatus();
            return ms1.compareTo(ms2);
        }
    };

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassNum() {
        return this.passNum;
    }

    public void setPassNum(String passNum) {
        this.passNum = passNum;
    }

    public Date getDob() {
        return this.dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Gender getGender() {
        return this.gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public MaritalStatus getMaritalStatus() {
        return this.maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public ClientAccessDetails getClientAccessDetails() {
        return this.clientAccessDetails;
    }

    public void setClientAccessDetails(ClientAccessDetails clientAccessDetails) {
        this.clientAccessDetails = clientAccessDetails;
    }

    public List<Account> getAccounts() {
        return this.accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
    
    public static Comparator<Client> getByFirstName() {
        return byFirstName;
    }

    public static void setByFirstName(Comparator<Client> byFirstName) {
        Client.byFirstName = byFirstName;
    }

    public static Comparator<Client> getByLastName() {
        return byLastName;
    }

    public static void setByLastName(Comparator<Client> byLastName) {
        Client.byLastName = byLastName;
    }

    public static Comparator<Client> getByDateOfBirth() {
        return byDateOfBirth;
    }

    public static void setByDateOfBirth(Comparator<Client> byDateOfBirth) {
        Client.byDateOfBirth = byDateOfBirth;
    }

    public static Comparator<Client> getByGender() {
        return byGender;
    }

    public static void setByGender(Comparator<Client> byGender) {
        Client.byGender = byGender;
    }

    public static Comparator<Client> getByMaritalStatus() {
        return byMaritalStatus;
    }

    public static void setByMaritalStatus(Comparator<Client> byMaritalStatus) {
        Client.byMaritalStatus = byMaritalStatus;
    }

    @Override
    public String toString() {
        return "Client [id=" + this.id + ", firstName=" + this.firstName + ", lastName=" + this.lastName + ", passNum=" + this.passNum
                + ", dob=" + this.dob + ", gender=" + this.gender + ", maritalStatus=" + this.maritalStatus + ", clientAccessDetails="
                + this.clientAccessDetails + ", accounts=" + this.accounts + "]";
    }

}
