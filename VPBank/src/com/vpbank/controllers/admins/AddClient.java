package com.vpbank.controllers.admins;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.vpbank.models.Client;
import com.vpbank.models.ClientAccessDetails;
import com.vpbank.models.Gender;
import com.vpbank.models.MaritalStatus;
import com.vpbank.services.ClientService;

/**
 * Servlet implementation class AddClient
 */
@WebServlet("/admin/new_client")
public class AddClient extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	ClientService cs;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddClient() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher view = request.getRequestDispatcher("../WEB-INF/views/new_client.jsp");
		view.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ClientAccessDetails cad = new ClientAccessDetails();
		
		String userID = request.getParameter("user_ID");
		System.out.println(userID);
		cad.setUserID(userID);
		
		String userPWD = request.getParameter("user_PWD");
		System.out.println(userPWD);
		cad.setUserPW(userPWD);
		
		
		
		Client c = new Client();
		
		String firstName = request.getParameter("first_name");
		c.setFirstName(firstName);
		
		String lastName = request.getParameter("last_name");
		c.setLastName(lastName);
		
		String passNum = request.getParameter("pass_num");
		c.setPassNum(passNum);
		
		String dob_raw = request.getParameter("dob");
		String dobArray[] = dob_raw.split("\\-");
		String year = dobArray[0];
		String month = dobArray[1];
		String day = dobArray[2];
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(year));
		cal.set(Calendar.MONTH, Integer.parseInt(month)-1); // because months 0-11
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
		Date dob = cal.getTime();
		System.out.println(dob);
		c.setDob(dob);
		
		/*String dob_raw = request.getParameter("dob");
		String dobArray[] = dob_raw.split("\\/");
		String pattern = "^\\d{1,2}\\/\\d{1,2}\\/\\d{4}$";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(dob_raw);
		if (m.find()) {
			String month = dobArray[0];
			String day = dobArray[1];
			String year = dobArray[2];
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, Integer.parseInt(year));
			cal.set(Calendar.MONTH, Integer.parseInt(month));
			cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
			Date dob = cal.getTime();
			System.out.println(dob);
			c.setDob(dob);
			//request.setAttribute("dob", dob_raw);
		} else {
			System.out.println("invalid date of birth");
			request.setAttribute("errors", true);
			request.setAttribute("date_format_error", true);
			request.setAttribute("dob", dob_raw);
			if (dob_raw.length() == 0) {
				request.setAttribute("dob", "");
			}
		}		*/
		
		String gender = request.getParameter("gender");
		c.setGender(Gender.valueOf(gender));
		
		String maritalStatus = request.getParameter("marital_status");
		c.setMaritalStatus(MaritalStatus.valueOf(maritalStatus));



		c.setClientAccessDetails(cad);
		cad.setClient(c);

		cs.addClient(c, cad);
		
		System.out.println(c);
		System.out.println(cad);
		System.out.println(request.getParameter("find_stream"));
		
		// if user press the button "Confirm and add an account"
		// redirect to adding an account
		if (request.getParameter("find_stream").equals("confirm_and_continue")) {
			HttpSession session = request.getSession();
			session.setAttribute("client", c);
			response.sendRedirect("add_account");
		}
	}

}
