package com.vpbank.controllers.clients;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.vpbank.models.ClientAccessDetails;

/**
 * Servlet implementation class ManePage
 */
@WebServlet("/index")
public class MainPage extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainPage() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        ClientAccessDetails cad = (ClientAccessDetails)session.getAttribute("checking");
        if (cad == null) {
            response.sendRedirect("login");
        } else {
            request.setAttribute("login_info", cad);
            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/index_page.jsp");
            view.forward(request, response);
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        this.doGet(request, response);
    }

}
