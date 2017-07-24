<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"
    import="java.util.List, java.util.Calendar, com.vpbank.models.Client, 
    com.vpbank.services.AccountGenerator, java.util.Date, 
    java.text.SimpleDateFormat, com.vpbank.models.AccountType,
    com.vpbank.models.Currency"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<link rel="shortcut icon" href="../resources/images/favicon.ico"
    type="image/x-icon">
<link rel="stylesheet" type="text/css"
    href="../resources/css/admin_style.css" />  
<link href='http://fonts.googleapis.com/css?family=Montserrat:400,700'
    rel='stylesheet' type='text/css'>
<title>VPBank | Administration console | Removing a client</title>
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
                <li><a href="table_clients">Clients</a></li>
                <li><a href="table_accounts">Accounts</a></li>
            </ul>
        </div>
        <div id="contentwrap">
            <div id="content">
                
                <!-- Content of this page depends on selection of a client 
                to which an account must be added. -->
                
                <%
                 // create pattern to print dates
                SimpleDateFormat datePrint = new SimpleDateFormat("dd.MM.yyyy"); 
                %>
                <h2>Select the client you want to remove:</h2>
                <form class="form" method="POST" action="remove_client">
        
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
                <form class="form" method="POST" action="remove_client">
            
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
                   <button name="button_pressed" type="submit" value="remove">Remove</button>
                   <hr />
               </form>
               <%
                   }
               %>
               
           </div> 
           <div style="clear: both;"></div>
        </div>
        <div id="footer">
            <p>
                &copy; Copyright 2016 <a href="../admin">VPBank</a> | Configured by <a
                    href="https://github.com/slovan">Volodymyr Ponomarenko</a>
            </p>
        </div>
    </div>  
</body>
</html>