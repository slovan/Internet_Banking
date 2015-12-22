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
import javax.servlet.http.HttpSession;

import com.vpbank.models.Client;
import com.vpbank.services.ClientService;

/**
 * Servlet implementation class RemoveClient
 * Aimed to remove a client from database by administrator of VPBank
 */
@WebServlet("/admin/remove_client")
public class RemoveClient extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private ClientService cs;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveClient() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher("../WEB-INF/views/RemoveClient.jsp");
        // set default attributes for the request
        request.setAttribute("firstName", "");
        request.setAttribute("lastName", "");
        request.setAttribute("selected_clients", null);
      
        view.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher("../WEB-INF/views/RemoveClient.jsp");
        
        // if form was sent after pressing button with value "search"
        if (request.getParameter("button_pressed").equals("search")) {
            // get client's name from jsp
            String clientFirstName = request.getParameter("first_name");
            if (clientFirstName == null) { // in case of null value in text field
                clientFirstName = "";
            }
            String clientLastName = request.getParameter("last_name");
            if (clientLastName == null) { // in case of null value in text field
                clientLastName = "";
            }
            
            // find in database possible clients
            List<Client> selectedClients = this.cs.getClientsByName(clientFirstName, clientLastName);
            request.setAttribute("selected_clients", selectedClients);
            request.setAttribute("firstName", clientFirstName);
            request.setAttribute("lastName", clientLastName);
            view.forward(request, response);
        }
        
        // if form was sent after pressing button with value "remove"
        if (request.getParameter("button_pressed").equals("remove")) {
            // get selected client's from jsp
            if (request.getParameter("client_id") == null) { // if empty form will be sent
                request.setAttribute("firstName", "");
                request.setAttribute("lastName", "");
                view.forward(request, response);
            } else {
                int clientId = Integer.parseInt(request.getParameter("client_id"));
                // find client from database by his id and remove him
                if (this.cs.removeClient(clientId)) {
                    HttpSession session = request.getSession(); // get access to session
                    session.setAttribute("status", "All client's information was removed successfully!");
                    response.sendRedirect("../admin");
                } else {
                    request.setAttribute("firstName", "");
                    request.setAttribute("lastName", "");
                    view.forward(request, response);
                }
            } 
        }
    }

}
