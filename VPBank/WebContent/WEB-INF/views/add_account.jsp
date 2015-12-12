<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"
	import="java.util.List, com.vpbank.models.Client, 
	com.vpbank.services.AccountGenerator, java.util.Date, 
	java.text.SimpleDateFormat, com.vpbank.models.AccountType,
	com.vpbank.models.Currency"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<link rel="shortcut icon" href="../resources/images/favicon.ico"
    type="image/x-icon">
<%
Client client = (Client)session.getAttribute("client");
if (client == null) { // in that case client is not selected yet
%>
<link rel="stylesheet" type="text/css"
    href="../resources/css/admin_style.css" />  
<%
} else {
%>
<link rel="stylesheet" type="text/css"
    href="../resources/css/admin_extended_style.css" />
<%
}
%>
<link href='http://fonts.googleapis.com/css?family=Montserrat:400,700'
    rel='stylesheet' type='text/css'>
<title>VPBank | Administration console | Adding an account to a client</title>
</head>
<body>
    <div id="wrap">
        <div id="header">
            <img src="../resources/images/logo.png" alt="VPBank - " />
            <h1>Administration console</h1>
        </div>

        <div class="menu">
            <ul>
                <li><a href="../admin">Home</a></li>
                <li><a href="#">Clients</a></li>
                <li><a href="#">Accounts</a></li>
            </ul>
        </div>
        <div id="contentwrap">
            <div id="content">
                
                <!-- Content of this page depends on selection of a client 
                to which an account must be added. 

                Hence, at first should be controlled if information of a selected 
                client is already contained in the current session under the name 
                "client" -->
                
                <%
                    SimpleDateFormat datePrint = new SimpleDateFormat("dd.MM.yyyy"); // create pattern to print dates
                    if (client == null) { // in that case client is not selected yet
                    // need to select a client
                %>
                <h2>Select the client you want to add an account:</h2>
                <form class="form" method="POST" action="add_account">
        
                    <!-- Enter client's name to find him in the database -->
                    
                    <div class="input_field">
                        <label for="first_name">First name:</label>
                        <input name="first_name" type="text"
                            value="<%=request.getAttribute("firstName")%>"
                            pattern="[A-Za-zÁáÄäČčĎďÉéÍíĹĺĽľŇňÔôŔŕŠšŤťÚúÝýŽž]{2,}" required></input>
                        <small> *min 2 characters: only English or Slovak letters</small>
                    </div>
                    <div class="input_field">
                        <label for="last_name">Last name:</label> 
                        <input name="last_name" type="text"
                            value="<%=request.getAttribute("lastName")%>"
                            pattern="[A-Za-zÁáÄäČčĎďÉéÍíĹĺĽľŇňÔôŔŕŠšŤťÚúÝýŽž]{2,}" required></input>
                        <small> *min 2 characters: only English or Slovak letters</small>
                    </div>
                    <br /> <br />
                    <hr />
                    <button name="button_pressed" type="submit" value="search">Search in database</button>
                    <hr />
                </form>
                <%
                    // get a list of clients with similar names    
                    List<Client> selectedClients = (List<Client>)request.getAttribute("selected_clients");
                    if (selectedClients != null) { 
                    // need to make a selection to confirm a client
                %>
                <form class="form" method="POST" action="add_account">
            
                    <!-- Show all clients with the same name to be confirmed -->
            
	               <table>
		                <tr>
		                    <th></th>
		                    <th>First name</th>
		                    <th>Last name</th>
		                    <th>Identity card</th>
		                    <th>Date of birth</th>
		                    <th>Gender</th>
		                    <th>Marital status</th>
		                </tr>
		                <%
		                    if (selectedClients.isEmpty()) {
		                %>
		                <tr>
		                    <td colspan="7">No clients with this name.</td>
		                </tr>
		                <%
		                    } else {
		                        for (int i = 0; i < selectedClients.size(); i++) {
		                %>
		                <tr>
		                    <td>
		                        <input name="client_id" type="radio" 
		                            value="<%= selectedClients.get(i).getId() %>" 
		                            <%if (i == 0) {%>checked<%} %>></input>
		                    </td>
		                    <td><%=selectedClients.get(i).getFirstName()%></td>
		                    <td><%=selectedClients.get(i).getLastName()%></td>
		                    <td><%=selectedClients.get(i).getPassNum()%></td>
                            <td><%=datePrint.format(selectedClients.get(i).getDob()) %>
		                    <% switch(selectedClients.get(i).getGender()) {
		                           case MALE: %><td>Male</td><% break;
		                           case FEMALE: %><td>Female</td><% break;
		                    }
		                    switch(selectedClients.get(i).getMaritalStatus()) {
                                   case SINGLE: %><td>Single</td><% break;
                                   case MARRIED: %><td>Married</td><% break;
                                   case DIVORCED: %><td>Divorced</td><% break;
                                   case WIDOWED: %><td>Widowed</td><% break;
                                   case COHABITING: %><td>Cohabiting</td><% break;
                                   case CIVIL_UNION: %><td>Civil union</td><% break;
                                   case DOMESTIC_PARTNERSHIP: %><td>Domestic partnership</td><% break;
                                   case UNMARRIED_PARTNERS: %><td>Unmarried partners</td><% break;
                            }
                            %>
		                </tr>
		                <%
		                        } // for
		                    } // else
		                %>
	               </table>
	               <br /> <br />
	               <hr />
	               <button name="button_pressed" type="submit" value="select_client">Confirm</button>
	               <hr />
	           </form>
               <%
                   }
               %>
               <%
                   } else { // if the client is already selected
                       // get account number (basic) automatically generated
                       String accountNumberBasic = Long.toString(AccountGenerator.genAccountNumberBasic(client));
                       // must be 10 digits 
                       while (accountNumberBasic.length() < 10) {
                           accountNumberBasic = "0" + accountNumberBasic;
                       }   
               %>
               <h2>Adding an account:</h2>
               <form class="form" method="POST" action="add_account">
                   <div class="input_field">
                       <label for="account_type">Type of the account:</label> 
                       <select name="account_type">
                            <option value="CURRENT" <%if ((AccountType)request.getAttribute("accountType") == AccountType.CURRENT) {%>selected<%} %>>Current account</option>
                            <option value="TERM_DEPOSIT" <%if ((AccountType)request.getAttribute("accountType") == AccountType.TERM_DEPOSIT) {%>selected<%} %>>Term deposit</option>
                            <option value="LOANS" <%if ((AccountType)request.getAttribute("accountType") == AccountType.TERM_DEPOSIT) {%>selected<%} %>>Loans account</option>
                            <option value="OTHER" <%if ((AccountType)request.getAttribute("accountType") == AccountType.TERM_DEPOSIT) {%>selected<%} %>>Other</option>
                       </select>
                   </div>
                   <div class="input_field">
                       <label for="currency">Currency of the account:</label> 
                       <select name="currency">
                            <option value="EUR" <%if ((Currency)request.getAttribute("currency") == Currency.EUR) {%>selected<%} %>>EUR</option>
                            <option value="USD" <%if ((Currency)request.getAttribute("currency") == Currency.USD) {%>selected<%} %>>USD</option>
                            <option value="GBP" <%if ((Currency)request.getAttribute("currency") == Currency.GBP) {%>selected<%} %>>GBP</option>
                            <option value="CHF" <%if ((Currency)request.getAttribute("currency") == Currency.CHF) {%>selected<%} %>>CHF</option>
                            <option value="AUD" <%if ((Currency)request.getAttribute("currency") == Currency.AUD) {%>selected<%} %>>AUD</option>
                            <option value="HUF" <%if ((Currency)request.getAttribute("currency") == Currency.HUF) {%>selected<%} %>>HUF</option>
                            <option value="CZK" <%if ((Currency)request.getAttribute("currency") == Currency.CZK) {%>selected<%} %>>CZK</option>
                            <option value="PLN" <%if ((Currency)request.getAttribute("currency") == Currency.PLN) {%>selected<%} %>>PLN</option>
                            <option value="RUB" <%if ((Currency)request.getAttribute("currency") == Currency.RUB) {%>selected<%} %>>RUB</option>
                            <option value="UAH" <%if ((Currency)request.getAttribute("currency") == Currency.UAH) {%>selected<%} %>>UAH</option>
                       </select>
                   </div>
                   <div class="input_field">
                       <label for="account_number_basic">Account number (basic):</label> 
                       <input name="account_number_basic" type="text"
                           value="<%= accountNumberBasic %>"
                           pattern="[0-9]{10}" required></input>
                       <small> *10 digits</small>
                   </div>
                   <div class="input_field">
                       <label for="balance">Amount on the account:</label> 
                       <input name="balance" type="text"
                           value="<%=request.getAttribute("balance")%>"
                           pattern="\d+(,\d{2})?" required></input>
                       <small> *number in format "1,34"</small>
                   </div>                   
                   <div class="input_field">
                       <label for="date_open">Opened from:</label>
                       <%
                           Date now = new Date(); // current date 
                           SimpleDateFormat dateFormated = new SimpleDateFormat("yyyy-MM-dd");
                       %>
                       <input name="date_open" type="date" value="<%= dateFormated.format(now) %>" required></input> 
                       <small> *date in format dd.mm.yyyy</small>
                   </div>
                   <div class="input_field">
                       <label for="date_close">Valid until:</label>
                       <input name="date_close" type="date" value="<%= dateFormated.format(now) %>" min="<%= dateFormated.format(now) %>" required></input> 
                       <small> *date in format dd.mm.yyyy</small>
                       <%
                           if ((Boolean)request.getAttribute("incorrect_date")) {
                       %>
                               <br><strong>Incorrect date format.</strong>
                       <%
                           } else {
                               if ((Boolean)request.getAttribute("invalid")) {
                       %>
                               <br><strong>With such dates account is not valid.</strong>
                       <%
                               }
                           }
                       %>
                   </div>
                   <div class="input_field">
                       <label for="bank_code">Bank code:</label> 
                       <input name="bank_code" type="text" value="<%=request.getAttribute("bankCode") %>"
                           pattern="[0-9]{4}" required></input>
                       <small> *4 digits</small>
                   </div>
                   <div class="input_field">
                       <label for="swift_code">SWIFT code:</label> 
                       <input name="swift_code" type="text" value="<%=request.getAttribute("swiftCode") %>"
                           pattern="[A-Z]{4,8}" required></input>
                       <small> *4-8 big English letters</small>
                   </div>
                   <br /><br />
                   <hr />
                   <button name="button_pressed" type="submit" value="save_account">Confirm</button>
                   <hr />
               </form>
               <% 
                   }
               %>
               <div style="clear: both;"></div>
           </div> 
           <%
               if (client != null) {
           %>
            <div id="sidebar">
                <form method="POST" action="add_account">
                    <h3><%= client.getFirstName() + " " + client.getLastName() %></h3>
                    <ul>
                        <li>Gender:<br />
                            <% switch(client.getGender()) {
                                   case MALE: %>Male<% break;
                                   case FEMALE: %>Female<% break;
                            }
                            %>
                        </li>
                        <li>Marital status:<br />
                            <% switch(client.getMaritalStatus()) {
                                   case SINGLE: %>Single<% break;
                                   case MARRIED: %>Married<% break;
                                   case DIVORCED: %>Divorced<% break;
                                   case WIDOWED: %>Widowed<% break;
                                   case COHABITING: %>Cohabiting<% break;
                                   case CIVIL_UNION: %>Civil union<% break;
                                   case DOMESTIC_PARTNERSHIP: %>Domestic partnership<% break;
                                   case UNMARRIED_PARTNERS: %>Unmarried partners<% break;
                            }
                            %>
                        </li>
                        <li>Date of birth:<br /><%=datePrint.format(client.getDob()) %></li>
                        <li>Passport number:<br /><%= client.getPassNum() %></li>
                    </ul>
                    <div style="text-align: center;">
                        <button name="button_pressed" type="submit" 
                        value="another_client">Select another client</button>
                    </div>
                </form>
            </div>
            <%
            }
            %>
            <div style="clear: both;"> </div>
        </div>
        <div id="footer">
            <p>
                &copy; Copyright 2016 <a href="#">VPBank</a> | Configured by <a
                    href="https://github.com/slovan">Volodymyr Ponomarenko</a>
            </p>
        </div>
    </div>	
</body>
</html>