package com.vpbank.controllers.admins;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vpbank.models.Account;
import com.vpbank.services.AccountService;

/**
 * Servlet implementation class ShowAccountsTable. 
 * Aimed to show list of all accounts in database to administrator of VPBank. 
 * Function of sorting of the list is realized
 */
@WebServlet("/admin/table_accounts")
public class ShowAccountsTable extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private AccountService as;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowAccountsTable() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher("../WEB-INF/views/TableAccounts.jsp");
        List<Account> listAccounts = this.as.getAllAccounts();
        request.setAttribute("listOfAccounts", listAccounts);
        view.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher("../WEB-INF/views/TableAccounts.jsp");
        // get list of all accounts to be sorted
        List<Account> listAccounts = this.as.getAllAccounts();

        // get parameters sent by jsp form (checkbox)
        String ownerName = request.getParameter("owner_name"); // can be only
                                                               // null or "on"
        String accountType = request.getParameter("account_type"); // can be
                                                                   // only null
                                                                   // or "on"
        String dateClose = request.getParameter("date_close"); // can be only
                                                               // null or "on"
        String balance = request.getParameter("balance"); // can be only null or
                                                          // "on"

        // conditions of right sorting
        if ((ownerName != null) && (accountType != null) && (dateClose != null) && (balance != null)) {
            // if all checkboxes were selected
            listAccounts.sort(Account.getByOwnerName().thenComparing(Account.getByAccountType())
                    .thenComparing(Account.getByDateOfClose()).thenComparing(Account.getByBalance()));
        } else if ((ownerName != null) && (accountType != null) && (dateClose != null)) {
            // if 3 checkboxes were selected
            listAccounts.sort(Account.getByOwnerName().thenComparing(Account.getByAccountType())
                    .thenComparing(Account.getByDateOfClose()));
            
        } else if ((ownerName != null) && (accountType != null) && (balance != null)) {
            // if 3 checkboxes were selected
            listAccounts.sort(Account.getByOwnerName().thenComparing(Account.getByAccountType())
                    .thenComparing(Account.getByBalance()));
            
        } else if ((ownerName != null) && (dateClose != null) && (balance != null)) {
            // if 3 checkboxes were selected
            listAccounts.sort(Account.getByOwnerName().thenComparing(Account.getByDateOfClose())
                    .thenComparing(Account.getByBalance()));
            
        } else if ((accountType != null) && (dateClose != null) && (balance != null)) {
            // if 3 checkboxes were selected
            listAccounts.sort(Account.getByAccountType().thenComparing(Account.getByDateOfClose())
                    .thenComparing(Account.getByBalance()));
            
        } else if ((ownerName != null) && (accountType != null)) {
            // if 2 checkboxes were selected
            listAccounts.sort(Account.getByOwnerName().thenComparing(Account.getByAccountType()));
        } else if ((ownerName != null) && (dateClose != null)) {
            // if 2 checkboxes were selected
            listAccounts.sort(Account.getByOwnerName().thenComparing(Account.getByDateOfClose()));
        } else if ((ownerName != null) && (balance != null)) {
            // if 2 checkboxes were selected
            listAccounts.sort(Account.getByOwnerName().thenComparing(Account.getByBalance()));
        } else if ((accountType != null) && (dateClose != null)) {
            // if 2 checkboxes were selected
            listAccounts.sort(Account.getByAccountType().thenComparing(Account.getByDateOfClose()));
        } else if ((accountType != null) && (balance != null)) {
            // if 2 checkboxes were selected
            listAccounts.sort(Account.getByAccountType().thenComparing(Account.getByBalance()));
        } else if ((dateClose != null) && (balance != null)) {
            // if 2 checkboxes were selected
            listAccounts.sort(Account.getByDateOfClose().thenComparing(Account.getByBalance()));
        } else if (ownerName != null) {
            // if 1 checkbox were selected
            listAccounts.sort(Account.getByOwnerName());
        } else if (accountType != null) {
            // if 1 checkbox were selected
            listAccounts.sort(Account.getByAccountType());
        } else if (dateClose != null) {
            // if 1 checkbox were selected
            listAccounts.sort(Account.getByDateOfClose());
        } else if (balance != null) {
            // if 1 checkbox were selected
            listAccounts.sort(Account.getByBalance());
        } 

        request.setAttribute("listOfAccounts", listAccounts);
        view.forward(request, response);
    }

}
