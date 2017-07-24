<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.List, com.vpbank.models.Client, com.vpbank.models.Account, 
    java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="../resources/images/favicon.ico"
    type="image/x-icon">
<%
List<Client> listClients = (List<Client>)request.getAttribute("listOfClients");
if (listClients.isEmpty()) { // in that case no clients in database
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
<title>VPBank | Administration console | Clients of the Bank</title>
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
                <%
                if (listClients.isEmpty()) { // in that case no clients in database
                %>
                <h2>No clients in database yet!</h2>
                <% 
                } else {
                    SimpleDateFormat datePrint = new SimpleDateFormat("dd.MM.yyyy"); // create pattern to print dates
                %>
                <h2>Here is the list of clients of VPBank</h2>
                <table>
                    <tr>
                        <th>No.</th>
                        <th>Last name</th>
                        <th>First name</th>
                        <th>Passport number</th>
                        <th>Date of birth</th>
                        <th>Login</th>
                        <th>Accounts</th>
                    </tr>
                    <%
                    for (int i = 0; i < listClients.size(); i++) {
                    %>
                    <tr>
                        <td><%= (i+1) %></td>
                        <td><%=listClients.get(i).getLastName() %></td>
                        <td><%=listClients.get(i).getFirstName() %></td>
                        <td><%=listClients.get(i).getPassNum() %></td>
                        <td><%=datePrint.format(listClients.get(i).getDob()) %></td>
                        <td><%=listClients.get(i).getClientAccessDetails().getLoginUsername() %></td>
                        <td>
                        <% 
                        List<Account> accounts = listClients.get(i).getAccounts();
                        if (accounts.isEmpty()) {
                        %>Does not have any accounts<%
                        } else {
                            for (Account account : accounts) {
                        %>
                        <%= account.getAccountName() + "<br />" %>
                        <%
                            } // foreach
                        } // if-else
                        %>
                        </td>
                    </tr>
                    <%
                    } // for
                    %>
                </table>
                <div style="clear: both;"></div>
            </div> 

            <div id="sidebar">
                <form method="POST" action="table_clients">
                    <h3>Sort clients by:</h3>
                    <br />
                    <label class="checkbox"><input type="checkbox" name="last_name" value="selected"> last name</label>
                    <br />
                    <label class="checkbox"><input type="checkbox" name="first_name" value="selected"> first name</label>
                    <br />
                    <label class="checkbox"><input type="checkbox" name="dob" value="selected"> date of birth</label>
                    <br /><br />
                    <div style="text-align: center;">
                        <button type="submit">Select</button>
                    </div>
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