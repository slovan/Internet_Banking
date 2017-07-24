package com.vpbank.controllers.clients;

import java.io.IOException;

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
 * Servlet implementation class LogInClient. 
 * Aimed to provide log in function
 */
@WebServlet(description = "Servlet for ensuring clients' login to InternetBanking", urlPatterns = { "/login" })
public class LogInClient extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private ClientService cs;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogInClient() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/LogInPage.jsp");
        request.setAttribute("username_error", false);
        request.setAttribute("password_error", false);
        request.setAttribute("user-ID", "");
        view.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Client clientData = null; // data of a client wants to log in
        
        String userID = request.getParameter("user_ID");
        request.setAttribute("user-ID", userID);
        String userPassword = request.getParameter("user_password");
        
        if (this.cs.isLoginUsernameOccupied(userID)) {
            request.setAttribute("username_error", false);
            clientData = this.cs.getClientByAccessDetails(userID, userPassword);
            if (clientData == null) {
                request.setAttribute("password_error", true);
                RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/LogInPage.jsp");
                view.forward(request, response);
            } else {
                request.setAttribute("password_error", false);
                HttpSession session = request.getSession();
                session.setMaxInactiveInterval(1000);
                session.setAttribute("checking", clientData);
                response.sendRedirect("index");
            }
        } else {
            request.setAttribute("username_error", true);
            request.setAttribute("password_error", false);
            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/LogInPage.jsp");
            view.forward(request, response);
        }
    }
}
