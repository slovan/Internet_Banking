package com.vpbank.services;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.vpbank.models.Account;

/**
 * Session Bean implementation class AccountService
 */
@Stateless
@LocalBean
public class AccountService {
	
	@PersistenceContext(unitName = "VPBank")
	private EntityManager em;

    /**
     * Default constructor. 
     */
    public AccountService() {
        // TODO Auto-generated constructor stub
    }
    
    public void addAccount(Account acc) {
    	em.persist(acc);
    }

}
