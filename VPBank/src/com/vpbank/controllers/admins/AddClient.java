package com.vpbank.controllers.admins;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vpbank.models.Client;
import com.vpbank.models.ClientAccessDetails;
import com.vpbank.models.Gender;
import com.vpbank.models.MaritalStatus;
import com.vpbank.services.ClientService;

/**
 * Servlet implementation class AddClient
 */
@WebServlet("/AddClient")
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Client c = new Client();		
		c.setFirstName("Volodymyr");		
		c.setLastName("Ponomarenko");		
		c.setPassNum("ET677600");		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 1992);
		cal.set(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH, 3);		
		Date dob = cal.getTime();
		c.setDob(dob);		
		c.setGender(Gender.MALE);		
		c.setMaritalStatus(MaritalStatus.SINGLE);
		
		ClientAccessDetails cad = new ClientAccessDetails();
		cad.setUserID("123456789");
		cad.setUserPW("student");
		
		c.setClientAccessDetails(cad);
		cad.setClient(c);
		
		System.out.println(c);
		System.out.println(cad);
		
		cs.addClient(c, cad);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
