package com.vpbank.services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
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

	public void addAccountToClient(Account account, Client client) {
		em.persist(account);
		
		// set owner of the account
		account.setOwnerOfAccount(client);
		
		// add account to client's list of accounts
		List<Account> clientAccounts = client.getAccounts();
		clientAccounts.add(account);
		client.setAccounts(clientAccounts);
	}

	public List<Account> getAccountsWithSimilarBasicNumber(long accountNumberBasic) {
		TypedQuery<Account> accountQuery = em.createNamedQuery("findAccountsByBasicNumber", Account.class);
		accountQuery.setParameter("accountNumberBasic", accountNumberBasic);
		List<Account> listAccounts = accountQuery.getResultList();
		System.out.println(listAccounts);
		return listAccounts;
	}
}
