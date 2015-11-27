package com.vpbank.models;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: ClientAccessDetails
 *
 */
@Entity

public class ClientAccessDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	public ClientAccessDetails() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String userID; // login
	private String userPW; // password
	
	@OneToOne
	@JoinColumn(name = "client_fk")
	private Client client;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserPW() {
		return userPW;
	}

	public void setUserPW(String userPW) {
		this.userPW = userPW;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	@Override
	public String toString() {
		return "ClientAccessDetails [id=" + id + ", userID=" + userID + ", userPW=" + userPW + "]";
	}

}
