package com.vpbank.services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.vpbank.models.Account;
import com.vpbank.models.Client;

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
    
    // get all accounts from database
    public List<Account> getAllAccounts() {
        TypedQuery<Account> accountsQuery = this.em.createQuery("SELECT account FROM Account account", Account.class);
        List<Account> accountsList = accountsQuery.getResultList();
        return accountsList;
    }

    public boolean addAccountToClient(Account account, int clientId) {
        // create a query to find a client by his id
        TypedQuery<Client> clientQuery = this.em.createNamedQuery("Client.findById", Client.class);
        clientQuery.setParameter("id", clientId);
        try {
            Client client = clientQuery.getSingleResult();
            this.em.persist(account);
            // set owner of the account
            account.setOwnerOfAccount(client);
            // add account to client's list of accounts
            List<Account> clientAccounts = client.getAccounts();
            clientAccounts.add(account);
            client.setAccounts(clientAccounts);
            return true;
        } catch (NoResultException exc) { // if there is no client with such id
            return false;
        } 
    }

    public List<Account> getAccountsWithSimilarBasicNumber(long accountNumberBasic) {
        TypedQuery<Account> accountQuery = this.em.createNamedQuery("findAccountsByBasicNumber", Account.class);
        accountQuery.setParameter("accountNumberBasic", accountNumberBasic);
        List<Account> listAccounts = accountQuery.getResultList();
        System.out.println(listAccounts);
        return listAccounts;
    }
}
