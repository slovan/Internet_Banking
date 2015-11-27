package com.vpbank.controllers.clients;

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

import com.vpbank.models.ClientAccessDetails;
import com.vpbank.services.ClientService;

/**
 * Servlet implementation class LogInClient
 */
@WebServlet(description = "Servlet for ensuring clients' login to InternetBanking", urlPatterns = { "/login" })
public class LogInClient extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	ClientService cs;

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
		RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/login_client.jsp");
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
		boolean flag = false;
		String userID = request.getParameter("user_ID");
		request.setAttribute("user-ID", userID);
		String userPassword = request.getParameter("user_password");
		request.setAttribute("username_error", true);
		request.setAttribute("password_error", false);

		List<ClientAccessDetails> cadList = (List<ClientAccessDetails>) cs.getClientAccessDetails();
		ClientAccessDetails cad = null;
		for (int i = 0; i < cadList.size(); i++) {
			if (cadList.get(i).getUserID().equals(userID)) {
				System.out.println("user_ID: " + userID);
				request.setAttribute("username_error", false);
				if (cadList.get(i).getUserPW().equals(userPassword)) {
					System.out.println("user_password: " + userPassword);
					flag = true;
					cad = cadList.get(i);							
				} else {
					request.setAttribute("password_error", true);
				}
				break;
			}
		}
		if (flag) {
			HttpSession session = request.getSession();
			session.setMaxInactiveInterval(60);
			session.setAttribute("checking", cad);
			response.sendRedirect("index");
		} else {
			RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/login_client.jsp");
			view.forward(request, response);
		}
	}

}
