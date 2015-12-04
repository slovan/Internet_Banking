package com.vpbank.services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
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
    	em.persist(cad);
    	em.persist(c);
    }
    
    public List<Client> getClients(String firstName, String lastName) {
    	TypedQuery<Client> clientsQuery = em.createNamedQuery("findClientsByName", Client.class);
    	clientsQuery.setParameter("lastName", lastName);
    	clientsQuery.setParameter("firstName", firstName);
    	List<Client> clientsList = clientsQuery.getResultList();
    	return clientsList;
    }
    
    public List<ClientAccessDetails> getClientAccessDetails(){
    	TypedQuery<ClientAccessDetails> query = em.createQuery("SELECT cad FROM ClientAccessDetails cad", ClientAccessDetails.class);
    	List<ClientAccessDetails> results = query.getResultList();
    	return results;
    }

}
