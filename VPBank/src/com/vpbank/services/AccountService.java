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
 * Session Bean implementation class AccountService. For persisting Account
 * Entity Bean
 * 
 * @author Volodymyr Ponomarenko
 * @version 1.0
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

    /**
     * Get all accounts from database
     * 
     * @return list of all accounts in database; if no one account in database -
     *         return empty list
     */
    public List<Account> getAllAccounts() {
        TypedQuery<Account> accountsQuery = this.em.createQuery("SELECT account FROM Account account", Account.class);
        List<Account> accountsList = accountsQuery.getResultList();
        return accountsList;
    }

    /**
     * Add account to client's list of accounts and persist both
     * 
     * @param account
     *            account which will be persisted
     * @param clientId
     *            id of future owner of account
     * @return true - if persisted successfully; false - if unsuccessfully
     */
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

    /**
     * Find and return accounts with the similar basic numbers to given by the
     * parameter
     * 
     * @param accountNumberBasic
     *            account number (basic) to be compared with account numbers in
     *            database
     * @return list of accounts with the similar basic numbers; if no one
     *         account such accounts in database - return empty list
     */
    public List<Account> getAccountsWithSimilarBasicNumber(long accountNumberBasic) {
        TypedQuery<Account> accountQuery = this.em.createNamedQuery("findAccountsByBasicNumber", Account.class);
        accountQuery.setParameter("accountNumberBasic", accountNumberBasic);
        List<Account> listAccounts = accountQuery.getResultList();
        System.out.println(listAccounts);
        return listAccounts;
    }
}
