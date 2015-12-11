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
 * Servlet implementation class AddAccountToClient
 */
@WebServlet("/admin/add_account")
public class AddAccountToClient extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private ClientService cs;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddAccountToClient() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher view = request.getRequestDispatcher("../WEB-INF/views/add_account.jsp");
        request.setAttribute("firstName", "");
        request.setAttribute("lastName", "");
        view.forward(request, response);

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Client> selectedClients;
        HttpSession session = request.getSession();
        RequestDispatcher view = request.getRequestDispatcher("../WEB-INF/views/add_account.jsp");

        if (request.getParameter("client_order_in_list") == null) {
            String clientFirstName = request.getParameter("first_name");
            String clientLastName = request.getParameter("last_name");

            if ((clientFirstName.equals("")) && (clientLastName.equals(""))) {

                request.setAttribute("empty_error", true);
                view.forward(request, response);
            }

            selectedClients = this.cs.getClientsByName(clientFirstName, clientLastName);
            session.setAttribute("selected_clients", selectedClients);
            view.forward(request, response);
        } else {
            Integer orderInList = Integer.parseInt(request.getParameter("client_order_in_list"));
            selectedClients = (List<Client>)session.getAttribute("selected_clients");
            Client client = selectedClients.get(orderInList);
            session.setAttribute("client", client);
            view.forward(request, response);
        }
    }

}
