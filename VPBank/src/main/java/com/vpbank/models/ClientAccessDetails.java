package com.vpbank.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

/**
 * Entity implementation class for Entity: ClientAccessDetails.
 * Represents persistent data of log in clients' information maintained in database
 *
 */
@Entity
@NamedQuery(name = "ClientAccessDetails.findByLoginUsername", query = "SELECT cad FROM ClientAccessDetails cad WHERE cad.loginUsername LIKE :loginUsername")
public class ClientAccessDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    public ClientAccessDetails() {
        super();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String loginUsername; // login
    private String loginPassword; // password

    @OneToOne
    @JoinColumn(name = "client_fk")
    private Client client;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginUsername() {
        return this.loginUsername;
    }

    public void setLoginUsername(String loginUsername) {
        this.loginUsername = loginUsername;
    }

    public String getLoginPassword() {
        return this.loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "ClientAccessDetails [id=" + this.id + ", loginUsername=" + this.loginUsername + ", loginPassword=" + this.loginPassword
                + ", client=" + this.client + "]";
    }
}
