package com.vpbank.services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.vpbank.models.Client;
import com.vpbank.models.ClientAccessDetails;

/**
 * Session Bean implementation class ClientService. For persisting Client and
 * ClientAccessDetails Entity Beans
 * 
 * @author Volodymyr Ponomarenko
 * @version 1.0
 */
@Stateless
@LocalBean
public class ClientService {

    @PersistenceContext(unitName = "VPBank")
    private EntityManager em;

    /**
     * Default constructor.
     */
    public ClientService() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Add client's personal and log in information to database
     * 
     * @param c
     *            client (personal information) to be persisted
     * @param cad
     *            client's access details (log in information) to be persisted
     */
    public void addClient(Client c, ClientAccessDetails cad) {
        this.em.persist(cad);
        this.em.persist(c);
    }

    /**
     * Remove client from database
     * 
     * @param clientId
     *            id of client will be removed
     * @return true - if removed successfully; false - if unsuccessfully
     */
    public boolean removeClient(int clientId) {
        TypedQuery<Client> cQuery = this.em.createNamedQuery("Client.findById", Client.class);
        cQuery.setParameter("id", clientId);
        try {
            Client client = cQuery.getSingleResult();
            this.em.remove(client); // client access details and accounts will
                                    // be deleted automatically
            return true; // if removed
        } catch (NoResultException exc) {
            return false; // if cannot remove
        }

    }

    /**
     * Get all clients from database
     * 
     * @return list of all clients; empty list if no one clients in database
     */
    public List<Client> getAllClients() {
        TypedQuery<Client> clientsQuery = this.em.createQuery("SELECT client FROM Client client", Client.class);
        List<Client> clientsList = clientsQuery.getResultList();
        return clientsList;
    }

    /**
     * Find clients in database by their name. At first, try to find clients by
     * both name and surname. Then, if no one such client in database, try look
     * for clients only by surname
     * 
     * @param firstName first name of client
     * @param lastName last name (surname) of client
     * @return list of found clients 
     */
    public List<Client> getClientsByName(String firstName, String lastName) {
        // try to find clients by both name and surname
        TypedQuery<Client> clientsQuery = this.em.createNamedQuery("Client.findByFullName", Client.class);
        clientsQuery.setParameter("lastName", lastName);
        clientsQuery.setParameter("firstName", firstName);
        List<Client> clientsList = clientsQuery.getResultList();
        if (clientsList.isEmpty()) { // no one such client
            // try to find only by surname
            clientsQuery = this.em.createNamedQuery("Client.findByLastName", Client.class);
            clientsQuery.setParameter("lastName", lastName);
            clientsList = clientsQuery.getResultList();
        }
        return clientsList;
    }


    /**
     * Find client by his ID in database
     *     
     * @param id client's id key in database 
     * @return Client object if client with such id exists; null - if does not exist
     */
    public Client getClientById(int id) {
        TypedQuery<Client> cQuery = this.em.createNamedQuery("Client.findById", Client.class);
        cQuery.setParameter("id", id);
        Client client;
        try {
            client = cQuery.getSingleResult();
        } catch (NoResultException exc) {
            client = null;
        }
        return client;
    }

    /**
     * Get all clients' access details (log in information) from database
     * 
     * @return list of all clients' access details; empty list if no one clients in database
     */
    public List<ClientAccessDetails> getClientAccessDetails() {
        TypedQuery<ClientAccessDetails> query = this.em.createQuery("SELECT cad FROM ClientAccessDetails cad",
                ClientAccessDetails.class);
        List<ClientAccessDetails> results = query.getResultList();
        return results;
    }

    
    /**
     * Check if such loginUsername is already exists in database
     * 
     * @param loginUsername login username, existing of which in database should be checked
     * @return true - if user with such username already exists; false - if does not
     */
    public boolean isLoginUsernameOccupied(String loginUsername) {
        TypedQuery<ClientAccessDetails> cadQuery = this.em.createNamedQuery("ClientAccessDetails.findByLoginUsername",
                ClientAccessDetails.class);
        cadQuery.setParameter("loginUsername", loginUsername);
        try {
            cadQuery.getSingleResult(); // get cad from DB if exists
            return true;
        } catch (NoResultException exc) {
            return false;
        }
    }


    /**
     * Check if the client with a such passNum is already exists in database
     * 
     * @param passNum passport (identity card) number, existing of which in database should be checked
     * @return true - if user with such passName already exists; false - if does not
     */
    public boolean isPassNumOccupied(String passNum) {
        TypedQuery<Client> cQuery = this.em.createNamedQuery("Client.findByPassNumber", Client.class);
        cQuery.setParameter("passNum", passNum);
        try {
            cQuery.getSingleResult(); // get client from DB if exists
            return true;
        } catch (NoResultException exc) {
            return false;
        }
    }
}
