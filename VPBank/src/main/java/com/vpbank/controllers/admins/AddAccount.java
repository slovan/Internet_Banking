package com.vpbank.controllers.admins;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.vpbank.models.Account;
import com.vpbank.models.AccountType;
import com.vpbank.models.Client;
import com.vpbank.models.Currency;
import com.vpbank.services.AccountGenerator;
import com.vpbank.services.AccountService;
import com.vpbank.services.ClientService;

/**
 * Servlet implementation class AddAccountToClient. 
 * Aimed to add new account to database by administrator of VPBank
 */
@WebServlet("/admin/add_account")
public class AddAccount extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private ClientService cs;
    @EJB
    private AccountService as;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddAccount() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher view = request.getRequestDispatcher("../WEB-INF/views/AddNewAccount.jsp");
        // set default attributes for the request
        request.setAttribute("firstName", "");
        request.setAttribute("lastName", "");
        request.setAttribute("accountType", AccountType.CURRENT);
        request.setAttribute("currency", Currency.EUR);
        request.setAttribute("balance", "0,00");
        request.setAttribute("bankCode", "1723");
        request.setAttribute("swiftCode", "VPBSKUA");
        request.setAttribute("invalid", false);
        request.setAttribute("incorrect_date", false);
        
        view.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        RequestDispatcher page = request.getRequestDispatcher("../WEB-INF/views/AddNewAccount.jsp");
        HttpSession session = request.getSession(); // get access to session
        
        // if form was sent after pressing button with value "search"
        // (owner of future account is not selected yet)
        if (request.getParameter("button_pressed").equals("search")) {
            // get client's name from jsp
            String clientFirstName = request.getParameter("first_name");
            if (clientFirstName == null) { // in case of null value in text field
                clientFirstName = "";
            }
            String clientLastName = request.getParameter("last_name");
            if (clientLastName == null) {
                clientLastName = "";
            }
            
            // find in database possible clients, one of which will be owner of account
            List<Client> selectedClients = this.cs.getClientsByName(clientFirstName, clientLastName);
            request.setAttribute("selected_clients", selectedClients);
            request.setAttribute("firstName", clientFirstName);
            request.setAttribute("lastName", clientLastName);
            page.forward(request, response);
        }
        
        // if form was sent after pressing button with value "select_client"
        // owner of future account is just selected by his id
        if (request.getParameter("button_pressed").equals("select_client")) {
            // get selected client's from jsp
            if (request.getParameter("client_id") == null) { // if empty form will be sent
                request.setAttribute("firstName", "");
                request.setAttribute("lastName", "");
                page.forward(request, response);
            } else {
                int clientId = Integer.parseInt(request.getParameter("client_id"));
                // get client from database by his id
                Client client = this.cs.getClientById(clientId);
                           
                // set default attributes for the request
                session.setAttribute("client", client); // attribute for session, be used few times
                request.setAttribute("accountType", AccountType.CURRENT);
                request.setAttribute("currency", Currency.EUR);
                request.setAttribute("balance", "0,00");
                request.setAttribute("bankCode", "1723");
                request.setAttribute("swiftCode", "VPBSKUA");
                request.setAttribute("invalid", false);
                request.setAttribute("incorrect_date", false);
                
                // and in case of null value of "client" attribute (e.g. because of the end of session)
                // show empty raws of client's name
                request.setAttribute("firstName", "");
                request.setAttribute("lastName", "");
                
                page.forward(request, response);
            }
        }
        
        // if form was sent after pressing button with value "another_client"
        // to select another client
        if (request.getParameter("button_pressed").equals("another_client")) {
            session.setAttribute("client", null);
            request.setAttribute("firstName", "");
            request.setAttribute("lastName", "");
            page.forward(request, response);     
        }
        
        // if form was sent after pressing button with value "save_account"
        // this form contain attributes of the account will be created
        if (request.getParameter("button_pressed").equals("save_account")) {            
            Client client = (Client)session.getAttribute("client");
            request.setAttribute("error", false); // needed to catch errors
            if (client == null) {
                // return to selection of client
                request.setAttribute("firstName", "");
                request.setAttribute("lastName", "");
                page.forward(request, response); 
            } else {
                // get sent from jsp parameters
                    // account type 
                AccountType accountType = AccountType.valueOf(request.getParameter("account_type"));     
                    // currency
                Currency currency = Currency.valueOf(request.getParameter("currency"));                
                    // amount on the account
                String strBalance = request.getParameter("balance");
                DecimalFormat outNums = new DecimalFormat("#0.00");
                double balance = 0;
                try {
                    balance = outNums.parse(strBalance).doubleValue();
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }             
                    // basic account number
                String strAccountNumberBasic = request.getParameter("account_number_basic");
                long accountNumberBasic = Long.parseLong(strAccountNumberBasic);                
                    // prefix account number                       
                int accountNumberPrefix = 0;
                        // if account with similar basic number exists, generate random prefix number
                List<Account> accountsWithSimilarBasicNumber = this.as.getAccountsWithSimilarBasicNumber(accountNumberBasic);
                if (!accountsWithSimilarBasicNumber.isEmpty()) {
                    boolean flag = false; // become true when account number is unique
                    do {
                        Random rand = new Random();
                        accountNumberPrefix = AccountGenerator.genAccountNumberPrefix(rand.nextInt(100) + 1);
                        // check uniqueness
                        flag = true;
                        for (Account acc : accountsWithSimilarBasicNumber) {
                            if (acc.getAccountNumberPrefix() == accountNumberPrefix) {
                                flag = false; // if is not unique
                            }
                        }
                    } while (!flag);
                }
                    // bank code
                String strBankCode = request.getParameter("bank_code");
                short bankCode = Short.parseShort(strBankCode);
                    // SWIFT code
                String swiftCode = request.getParameter("swift_code");
                    // IBAN number
                String accountNumberIBAN = AccountGenerator.genAccountNumberIBAN(accountNumberBasic, accountNumberPrefix, bankCode, "SK");
                    // account name 
                        // get string form of the accountNumberPrefix
                String strAccountNumberPrefix = accountNumberIBAN.substring(4 + strBankCode.length(), // 4 in this line means length of country code (2 symbs) + 2 check digits
                        accountNumberIBAN.length() - strAccountNumberBasic.length());
                String accountName = strAccountNumberPrefix + "-" + strAccountNumberBasic + "/" + strBankCode;
                    // dates of open and close
                Date dateOpen = null; // variable of date when account open
                Date dateClose = null; // variable of date when account close
                String strDateOpen = request.getParameter("date_open"); // get as a string row
                String strDateClose = request.getParameter("date_close");
                        // controller for correct input
                            // for browsers support calendar (e.g. Chrome)
                String pattern1 = "^\\d{4}\\-\\d{1,2}\\-\\d{1,2}$"; 
                Pattern raw1 = Pattern.compile(pattern1);
                Matcher matchOpen1 = raw1.matcher(strDateOpen);
                Matcher matchClose1 = raw1.matcher(strDateClose);
                if (matchOpen1.find() && matchClose1.find()) {
                    request.setAttribute("incorrect_date", false);
                    String[] arrayDateOpen = strDateOpen.split("\\-"); // make a string array
                    String[] arrayDateClose = strDateClose.split("\\-"); // make a string array
                    Calendar calDateOpen = Calendar.getInstance(); // init calendar
                    Calendar calDateClose = Calendar.getInstance();
                    // set values of calendar attributes
                    calDateOpen.set(Calendar.YEAR, Integer.parseInt(arrayDateOpen[0]));
                    calDateOpen.set(Calendar.MONTH, Integer.parseInt(arrayDateOpen[1]) - 1); // months 0-11
                    calDateOpen.set(Calendar.DAY_OF_MONTH, Integer.parseInt(arrayDateOpen[2]));
                    calDateClose.set(Calendar.YEAR, Integer.parseInt(arrayDateClose[0]));
                    calDateClose.set(Calendar.MONTH, Integer.parseInt(arrayDateClose[1]) - 1);
                    calDateClose.set(Calendar.DAY_OF_MONTH, Integer.parseInt(arrayDateClose[2]));
                    // get Date instances contain dates of open and close
                    dateOpen = calDateOpen.getTime();
                    dateClose = calDateClose.getTime();
                    // now check time validity of the account
                    Calendar today = Calendar.getInstance(); // current date
                    today.set(Calendar.HOUR_OF_DAY, 23);
                    today.set(Calendar.MINUTE, 59);
                    today.set(Calendar.SECOND, 59);
                    Calendar tomorrow = Calendar.getInstance();
                    tomorrow.set(Calendar.DAY_OF_MONTH, today.get(Calendar.DAY_OF_MONTH) + 1);
                    tomorrow.set(Calendar.HOUR_OF_DAY, 0);
                    tomorrow.set(Calendar.MINUTE, 0);
                    if ((calDateOpen.compareTo(today) >= 0) || (tomorrow.compareTo(calDateClose) >= 0)) {
                        request.setAttribute("error", true);
                        request.setAttribute("invalid", true);
                    } else {
                        request.setAttribute("invalid", false);
                    }
                } else {
                            // for browsers don't support calendar (e.g. Firefox)
                    String pattern2 = "^\\d{1,2}\\.\\d{1,2}\\.\\d{4}$"; 
                    Pattern raw2 = Pattern.compile(pattern2);
                    Matcher matchOpen2 = raw2.matcher(strDateOpen);
                    Matcher matchClose2 = raw2.matcher(strDateClose);
                    if (matchOpen2.find() && matchClose2.find()) {
                        request.setAttribute("incorrect_date", false);
                        String[] arrayDateOpen = strDateOpen.split("\\."); // make a string array
                        String[] arrayDateClose = strDateClose.split("\\.");
                        Calendar calDateOpen = Calendar.getInstance(); // init calendar
                        Calendar calDateClose = Calendar.getInstance();
                        // set values of calendar attributes
                        calDateOpen.set(Calendar.YEAR, Integer.parseInt(arrayDateOpen[2]));
                        calDateOpen.set(Calendar.MONTH, Integer.parseInt(arrayDateOpen[1]) - 1); // months 0-11
                        calDateOpen.set(Calendar.DAY_OF_MONTH, Integer.parseInt(arrayDateOpen[0]));
                        calDateClose.set(Calendar.YEAR, Integer.parseInt(arrayDateClose[2]));
                        calDateClose.set(Calendar.MONTH, Integer.parseInt(arrayDateClose[1]) - 1);
                        calDateClose.set(Calendar.DAY_OF_MONTH, Integer.parseInt(arrayDateClose[0]));
                        // get Date instances contain dates of open and close
                        dateOpen = calDateOpen.getTime();
                        dateClose = calDateClose.getTime();
                        // now check time validity of the account
                        Calendar today = Calendar.getInstance(); // current date
                        today.set(Calendar.HOUR_OF_DAY, 23);
                        today.set(Calendar.MINUTE, 59);
                        today.set(Calendar.SECOND, 59);
                        Calendar tomorrow = Calendar.getInstance();
                        tomorrow.set(Calendar.DAY_OF_MONTH, today.get(Calendar.DAY_OF_MONTH) + 1);
                        tomorrow.set(Calendar.HOUR_OF_DAY, 0);
                        tomorrow.set(Calendar.MINUTE, 0);
                        if ((calDateOpen.compareTo(today) >= 0) || (tomorrow.compareTo(calDateClose) >= 0)) {
                            request.setAttribute("error", true);
                            request.setAttribute("invalid", true);
                        } else {
                            request.setAttribute("invalid", false);
                        }
                        
                    } else {
                        request.setAttribute("error", true);
                        request.setAttribute("incorrect_date", true);
                        request.setAttribute("invalid", false);
                    }
                }       
                
                // if there were some errors
                if ((Boolean)request.getAttribute("error")) {
                    // set attributes for the request, not to be needed enter them again
                    request.setAttribute("accountType", accountType);
                    request.setAttribute("currency", currency);
                    request.setAttribute("balance", strBalance);
                    request.setAttribute("bankCode", strBankCode);
                    request.setAttribute("swiftCode", swiftCode);
                    page.forward(request, response);
                } else {
                    // set attributes of object account
                    Account account = new Account(); // create an object to be saved in database
                        // account type
                    account.setAccountType(accountType);
                        // currency
                    account.setCurrency(currency);
                        // amount
                    account.setAmount(balance);
                        // basic account number
                    account.setAccountNumberBasic(accountNumberBasic);
                        // prefix account number
                    account.setAccountNumberPrefix(accountNumberPrefix);
                        // bank code
                    account.setBankCode(bankCode);
                        // SWIFT code
                    account.setSWIFTcode(swiftCode);
                        // IBAN number
                    account.setAccountNumberIBAN(accountNumberIBAN);
                        // account name
                    account.setAccountName(accountName);
                        // date of open
                    account.setDateOpen(dateOpen);
                        // date of close
                    account.setDateClose(dateClose);
                    
                    // set owner of the account and write to database
                    int clientId = client.getId(); // take id of client for searching in DB
                    if (this.as.addAccountToClient(account, clientId)) {
                        // redirect to home page
                        session.setAttribute("status", "New account was added to the client successfully!");
                        session.setAttribute("client", null);
                        response.sendRedirect("../admin");
                    } else {
                        // if cannot persist an account
                        // return to selection of client
                        session.setAttribute("client", null);
                        request.setAttribute("firstName", "");
                        request.setAttribute("lastName", "");
                        page.forward(request, response); 
                    }
                }
            } 
        }
    }
}
