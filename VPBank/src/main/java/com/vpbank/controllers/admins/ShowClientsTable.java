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

import com.vpbank.models.Client;
import com.vpbank.services.ClientService;

/**
 * Servlet implementation class ShowClientsTable. 
 * Aimed to show list of all accounts in database to administrator of VPBank. 
 * Function of sorting of the list is realized
 */
@WebServlet("/admin/table_clients")
public class ShowClientsTable extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @EJB
    private ClientService cs;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowClientsTable() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher("../WEB-INF/views/TableClients.jsp");
        List<Client> listClients = this.cs.getAllClients(); // get list of all clients of the Bank
        request.setAttribute("listOfClients", listClients);
        view.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher("../WEB-INF/views/TableClients.jsp");
        // get list of all clients to be sorted
        List<Client> listClients = this.cs.getAllClients(); 
        
        // get parameters sent by jsp form (checkbox)
        String lastName = request.getParameter("last_name"); // can be only null or "selected"
        String firstName = request.getParameter("first_name"); // can be only null or "selected"
        String dob = request.getParameter("dob"); // can be only null or "selected"
        
        // conditions of right sorting
        if ((lastName != null) && (firstName != null) && (dob != null)) {
            // if all checkboxes were selected
            listClients.sort(Client.getByLastName().thenComparing(Client.getByFirstName())
                    .thenComparing(Client.getByDateOfBirth()));
        } else if ((lastName != null) && (firstName != null)) {
         // if should be sorted only by names (both first and last)
            listClients.sort(Client.getByLastName().thenComparing(Client.getByFirstName()));
        } else if ((lastName != null) && (dob != null)) {
         // by last name and date of birth
            listClients.sort(Client.getByLastName().thenComparing(Client.getByDateOfBirth()));
        } else if ((firstName != null) && (dob != null)) {
         // by first name and date of birth
            listClients.sort(Client.getByFirstName().thenComparing(Client.getByDateOfBirth()));
        } else if (lastName != null) {
         // sort only by last name
            listClients.sort(Client.getByLastName());
        } else if (firstName != null) {
         // only by first name
            listClients.sort(Client.getByFirstName());
        } else if (dob != null) {
            // only by date of birth
            listClients.sort(Client.getByDateOfBirth());
        }
        
        request.setAttribute("listOfClients", listClients);
        view.forward(request, response);
    }
}
