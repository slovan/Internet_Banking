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
 * Session Bean implementation class ClientService
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

    public void addClient(Client c, ClientAccessDetails cad) {
        this.em.persist(cad);
        this.em.persist(c);
    }
    
    // remove client from database
    public boolean removeClient(int clientId) {
        TypedQuery<Client> cQuery = this.em.createNamedQuery("Client.findById", Client.class);
        cQuery.setParameter("id", clientId);
        try {
            Client client = cQuery.getSingleResult();
            this.em.remove(client); // client access details and accounts will be deleted automatically
            return true; // if removed
        } catch (NoResultException exc) {
            return false; // if cannot remove
        } 

    }
    
    // get all clients from database
    public List<Client> getAllClients() {
        TypedQuery<Client> clientsQuery = this.em.createQuery("SELECT client FROM Client client", Client.class);
        List<Client> clientsList = clientsQuery.getResultList();
        return clientsList;
    }

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
    
    // find client by his ID in database
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

    public List<ClientAccessDetails> getClientAccessDetails() {
        TypedQuery<ClientAccessDetails> query = this.em.createQuery("SELECT cad FROM ClientAccessDetails cad",
                ClientAccessDetails.class);
        List<ClientAccessDetails> results = query.getResultList();
        return results;
    }
    
    // checks if such loginUsername is already exists in database
    public boolean isLoginUsernameOccupied(String loginUsername) {
        TypedQuery<ClientAccessDetails> cadQuery = this.em.createNamedQuery("ClientAccessDetails.findByLoginUsername", ClientAccessDetails.class);
        cadQuery.setParameter("loginUsername", loginUsername);
        try {
            cadQuery.getSingleResult(); // get cad from DB if exists
            return true;
        } catch (NoResultException exc) {
            return false;
        }
    }
    
    // checks if the client with a such passNum is already exists in database
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
