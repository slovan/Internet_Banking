package com.vpbank.controllers.admins;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.vpbank.models.Account;
import com.vpbank.models.AccountType;
import com.vpbank.models.Client;
import com.vpbank.services.AccountGenerator;
import com.vpbank.services.AccountService;

/**
 * Servlet implementation class SaveAccount
 */
@WebServlet("/admin/SaveAccount")
public class SaveAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	AccountService as;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SaveAccount() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Account account = new Account(); // create an object to be saved in
											// database

		// set attributes of object account
		// account type
		account.setAccountType(AccountType.valueOf(request.getParameter("account_type")));
		
		// currency
		account.setCurrency(request.getParameter("currency"));
		
		// amount
		account.setAmount(Double.parseDouble(request.getParameter("amount")));
		
		// basic account number
		String strAccountNumberBasic = request.getParameter("account_number_basic");
		long accountNumberBasic = Long.parseLong(strAccountNumberBasic);
		account.setAccountNumberBasic(accountNumberBasic);
		
		// prefix account number
		int accountNumberPrefix = 0;
		// if account with similar basic number exists, generate random prefix number
		List<Account> accountsWithSimilarBasicNumber = as.getAccountsWithSimilarBasicNumber(accountNumberBasic);
		if (!accountsWithSimilarBasicNumber.isEmpty()) {
			boolean flag = false; // become true when account number is unique
			do {
				Random rand = new Random();
				accountNumberPrefix = AccountGenerator.genAccountNumberPrefix(rand.nextInt(100)+1);
				// check uniqueness
				flag = true;
				for (Account acc : accountsWithSimilarBasicNumber) {
					if (acc.getAccountNumberPrefix() == accountNumberPrefix)
						flag = false; //if is not unique
				}
			} while (!flag);
			account.setAccountNumberPrefix(accountNumberPrefix);
		} else
			account.setAccountNumberPrefix(accountNumberPrefix);
		
		// bank code
		String strBankCode = request.getParameter("bank_code");
		short bankCode = Short.parseShort(strBankCode);
		account.setBankCode(bankCode);
		
		// SWIFT code
		account.setSWIFTcode(request.getParameter("swift_code"));
		
		// IBAN number
		String accountNumberIBAN = AccountGenerator.genAccountNumberIBAN(accountNumberBasic, accountNumberPrefix, bankCode, "SK");
		account.setAccountNumberIBAN(accountNumberIBAN);
		
		// account name
		// get string form of the accountNumberPrefix
		// 4 in the next line means length of country code (2 symbs) + 2 check digits
		String strAccountNumberPrefix = accountNumberIBAN.substring(4 + strBankCode.length(), accountNumberIBAN.length() - strAccountNumberBasic.length());
		account.setAccountName(strAccountNumberPrefix + "-" + strAccountNumberBasic + "/" + strBankCode);
		
		// date of open
		String strDateOpen = request.getParameter("date_open");
		String dateOpenArray[] = strDateOpen.split("\\-");
		String year = dateOpenArray[0];
		String month = dateOpenArray[1];
		String day = dateOpenArray[2];
		Calendar calOpen = Calendar.getInstance();
		calOpen.set(Calendar.YEAR, Integer.parseInt(year));
		calOpen.set(Calendar.MONTH, Integer.parseInt(month)-1); // because months 0-11
		calOpen.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
		Date dateOpen = calOpen.getTime();
		account.setDateOpen(dateOpen);
		
		// date of close
		String strDateClose = request.getParameter("date_close");
		String dateCloseArray[] = strDateClose.split("\\-");
		year = dateCloseArray[0];
		month = dateCloseArray[1];
		day = dateCloseArray[2];
		Calendar calClose = Calendar.getInstance();
		calClose.set(Calendar.YEAR, Integer.parseInt(year));
		calClose.set(Calendar.MONTH, Integer.parseInt(month)-1); // because months 0-11
		calClose.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
		Date dateClose = calClose.getTime();
		account.setDateClose(dateClose);
		
		// get owner of the account from this session
		HttpSession session = request.getSession();
		Client client = (Client) session.getAttribute("client");
		
		// add account to client's list of accounts
		as.addAccountToClient(account, client);
	}

}
