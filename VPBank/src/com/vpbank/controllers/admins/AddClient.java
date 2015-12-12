package com.vpbank.controllers.admins;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
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
    private ClientService cs;

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

        // set default attributes for the request
        request.setAttribute("loginUsername", "");
        request.setAttribute("loginPassword", "");
        request.setAttribute("firstName", "");
        request.setAttribute("lastName", "");
        request.setAttribute("passNum", "");
        request.setAttribute("dobStr", "");
        request.setAttribute("gender", Gender.MALE);
        request.setAttribute("maritalStatus", MaritalStatus.SINGLE);
        request.setAttribute("username_exists", false);
        request.setAttribute("client_exists", false);
        request.setAttribute("under_age", false);
        request.setAttribute("incorrect_date", false);

        RequestDispatcher visual = request.getRequestDispatcher("../WEB-INF/views/new_client.jsp");
        visual.forward(request, response);

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // this method will be executed when a form from new_client.jsp will be
        // sent

        // instances for client's data
        ClientAccessDetails cad = new ClientAccessDetails();
        Client c = new Client();

        // needed to catch errors
        request.setAttribute("error", false);

        // get client's login information from jsp:
        // username
        String loginUsername = request.getParameter("login_username");
        // if such username is already exists in database
        if (this.cs.isLoginUsernameOccupied(loginUsername)) {
            request.setAttribute("error", true);
            request.setAttribute("username_exists", true);
        } else {
            request.setAttribute("username_exists", false);
        }

        // password
        String loginPassword = request.getParameter("login_password");

        // get client's data from jsp
        // first name
        String firstName = request.getParameter("first_name");
        System.out.println(firstName);

        // last name
        String lastName = request.getParameter("last_name");

        // passport number
        String passNum = request.getParameter("pass_num");
        if (this.cs.isPassNumOccupied(passNum)) {
            request.setAttribute("error", true);
            request.setAttribute("client_exists", true);
        } else {
            request.setAttribute("client_exists", false);
        }

        // get date of birth
        Date dob = null; // variable of date of birth
        String dobStr = request.getParameter("dob"); // get as a string row
        // controller for correct input
                            // for browsers support calendar (e.g. Chrome)
        String pattern1 = "^\\d{4}\\-\\d{1,2}\\-\\d{1,2}$"; 
        Pattern raw1 = Pattern.compile(pattern1);
        Matcher match1 = raw1.matcher(dobStr);
        if (match1.find()) {
            request.setAttribute("incorrect_date", false);
            String[] dobArray = dobStr.split("\\-"); // make a string array
            String year = dobArray[0]; 
            String month = dobArray[1]; 
            String day = dobArray[2]; 
            Calendar dobCal = Calendar.getInstance(); // init calendar
            // set values of calendar attributes
            dobCal.set(Calendar.YEAR, Integer.parseInt(year));
            dobCal.set(Calendar.MONTH, Integer.parseInt(month) - 1); // months 0-11
            dobCal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
            // get Date instance contains date of birth
            dob = dobCal.getTime();
            // now check if person is of the full legal age
            Calendar today = Calendar.getInstance(); // current date
            int age = today.get(Calendar.YEAR) - dobCal.get(Calendar.YEAR);
            // if person does not have a birthday this year:
            if (today.get(Calendar.MONTH) < dobCal.get(Calendar.MONTH)) {
                age--;
            } else if (today.get(Calendar.MONTH) == dobCal.get(Calendar.MONTH)
                    && today.get(Calendar.DAY_OF_MONTH) < dobCal.get(Calendar.DAY_OF_MONTH)) {
                age--;
            }
            if (age < 18) {
                request.setAttribute("error", true);
                request.setAttribute("under_age", true);
            } else {
                request.setAttribute("under_age", false);
            }
        } else {
                            // for browsers don't support calendar (e.g. Firefox)
            String pattern2 = "^\\d{1,2}\\.\\d{1,2}\\.\\d{4}$"; 
            Pattern raw2 = Pattern.compile(pattern2);
            Matcher match2 = raw2.matcher(dobStr);
            if (match2.find()) {
                request.setAttribute("incorrect_date", false);
                String[] dobArray = dobStr.split("\\."); // make a string array
                String day = dobArray[0];
                String month = dobArray[1];
                String year = dobArray[2];
                Calendar dobCal = Calendar.getInstance(); // init calendar
                // set values of calendar attributes
                dobCal.set(Calendar.YEAR, Integer.parseInt(year));
                dobCal.set(Calendar.MONTH, Integer.parseInt(month) - 1); // months 0-11
                dobCal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
                // get Date instance contains date of birth
                dob = dobCal.getTime();
                // now check if person is of the full legal age
                Calendar today = Calendar.getInstance(); // current date
                int age = today.get(Calendar.YEAR) - dobCal.get(Calendar.YEAR);
                // if person does not have a birthday this year:
                if (today.get(Calendar.MONTH) < dobCal.get(Calendar.MONTH)) {
                    age--;
                } else if (today.get(Calendar.MONTH) == dobCal.get(Calendar.MONTH)
                        && today.get(Calendar.DAY_OF_MONTH) < dobCal.get(Calendar.DAY_OF_MONTH)) {
                    age--;
                }
                if (age < 18) {
                    request.setAttribute("error", true);
                    request.setAttribute("under_age", true);
                } else {
                    request.setAttribute("under_age", false);
                }
            } else {
                request.setAttribute("error", true);
                request.setAttribute("incorrect_date", true);
            }
        }       

        // gender
        String genderStr = request.getParameter("gender");
        Gender gender = Gender.valueOf(genderStr);

        // marital status
        String maritalStatusStr = request.getParameter("marital_status");
        MaritalStatus maritalStatus = MaritalStatus.valueOf(maritalStatusStr);

        // if there were some errors
        if ((Boolean)request.getAttribute("error")) {
            RequestDispatcher view = request.getRequestDispatcher("../WEB-INF/views/new_client.jsp");
            // set attributes for the request, not to be needed enter them again
            request.setAttribute("loginUsername", loginUsername);
            request.setAttribute("loginPassword", loginPassword);
            request.setAttribute("firstName", firstName);
            request.setAttribute("lastName", lastName);
            request.setAttribute("passNum", passNum);
            request.setAttribute("dobStr", dobStr);
            request.setAttribute("gender", gender);
            request.setAttribute("maritalStatus", maritalStatus);
            view.forward(request, response);
        } else {
            // set attributes of client access details
            cad.setLoginUsername(loginUsername);
            cad.setLoginPassword(loginPassword);
            cad.setClient(c); // set client to the login details

            // set client's attributes
            c.setFirstName(firstName);
            c.setLastName(lastName);
            c.setPassNum(passNum);
            c.setDob(dob);
            c.setGender(gender);
            c.setMaritalStatus(maritalStatus);
            c.setClientAccessDetails(cad); // set login details for client

            // save in database
            this.cs.addClient(c, cad);

            // if user press the button "Confirm"
            // redirect to home page
            if (request.getParameter("find_stream").equals("confirm_only")) {
                HttpSession session = request.getSession();
                session.setAttribute("status", "New client was added successfully!");
                response.sendRedirect("../admin");
            }
            
            // if user press the button "Confirm and add an account"
            // redirect to adding an account
            if (request.getParameter("find_stream").equals("confirm_and_continue")) {
                HttpSession session = request.getSession();
                session.setAttribute("client", c);
                response.sendRedirect("add_account");
            }
        }
    }
}
