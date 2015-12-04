package com.vpbank.models;

import java.io.Serializable;
import java.text.Collator;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Client
 *
 */
@NamedQuery(name = "findClientsByName", query = "SELECT c FROM Client c WHERE c.lastName LIKE :lastName AND c.firstName LIKE :firstName")
@Entity

public class Client implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;

	public Client() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String firstName;
	private String lastName;
	private String passNum;

	@Temporal(TemporalType.DATE)
	private Date dob;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	@Enumerated(EnumType.STRING)
	private MaritalStatus maritalStatus;

	@OneToOne(mappedBy = "client")
	private ClientAccessDetails clientAccessDetails;

	@OneToMany(mappedBy = "ownerOfAccount")
	private List<Account> accounts;
	
	public static Comparator<Client> byFirstName = new Comparator<Client>() {
		public int compare(Client c1, Client c2) {
			Collator skCollator = Collator.getInstance(new Locale("sk_SK"));
			skCollator.setStrength(Collator.SECONDARY);
			String firstName1 = c1.getFirstName();
			String firstName2 = c2.getFirstName();
			return skCollator.compare(firstName1, firstName2);
		}
	};
	
	public static Comparator<Client> byLastName = new Comparator<Client>() {
		public int compare(Client c1, Client c2) {
			Collator skCollator = Collator.getInstance(new Locale("sk_SK"));
			skCollator.setStrength(Collator.SECONDARY);
			String lastName1 = c1.getLastName();
			String lastName2 = c2.getLastName();			
			return skCollator.compare(lastName1, lastName2);
		}
	};
	
	public static Comparator<Client> byDateOfBirth = new Comparator<Client>() {
		public int compare(Client c1, Client c2) {
			Date dob1 = c1.getDob();
			Date dob2 = c2.getDob();
			return dob1.compareTo(dob2);
		}
	};
	
	public static Comparator<Client> byGender = new Comparator<Client>() {
		public int compare(Client c1, Client c2) {
			Gender gender1 = c1.getGender();
			Gender gender2 = c2.getGender();
			return gender1.compareTo(gender2);
		}
	};
	
	public static Comparator<Client> byMaritalStatus = new Comparator<Client>() {
		public int compare(Client c1, Client c2) {
			MaritalStatus ms1 = c1.getMaritalStatus();
			MaritalStatus ms2 = c2.getMaritalStatus();
			return ms1.compareTo(ms2);
		}
	};

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassNum() {
		return passNum;
	}

	public void setPassNum(String passNum) {
		this.passNum = passNum;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public MaritalStatus getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(MaritalStatus maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public ClientAccessDetails getClientAccessDetails() {
		return clientAccessDetails;
	}

	public void setClientAccessDetails(ClientAccessDetails clientAccessDetails) {
		this.clientAccessDetails = clientAccessDetails;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	@Override
	public String toString() {
		return "Client [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", passNum=" + passNum
				+ ", dob=" + dob + ", gender=" + gender + ", maritalStatus=" + maritalStatus + ", clientAccessDetails="
				+ clientAccessDetails + ", accounts=" + accounts + "]";
	}

}
