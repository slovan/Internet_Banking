<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.List, com.vpbank.models.Client, com.vpbank.models.Account, 
    java.text.SimpleDateFormat, java.text.DecimalFormat"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="../resources/images/favicon.ico"
    type="image/x-icon">
<%
List<Account> listAccounts = (List<Account>)request.getAttribute("listOfAccounts");
if (listAccounts.isEmpty()) { // in that case no accounts in database
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
<title>VPBank | Administration console | Accounts of the Bank</title>
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
                if (listAccounts.isEmpty()) { // in that case no accounts in database
                %>
                <h2>No accounts in database yet!</h2>
                <% 
                } else {
                    SimpleDateFormat datePrint = new SimpleDateFormat("dd.MM.yyyy"); // create pattern to print dates
                %>
                <h2>Here is the list of accounts of VPBank</h2>
                <table>
                    <tr>
                        <th>No.</th>
                        <th>Account</th>
                        <th>Type</th>
                        <th>Owner</th>
                        <th>Currency</th>
                        <th>Balance</th>
                        <th>Date of close</th>
                    </tr>
                    <%
                    for (int i = 0; i < listAccounts.size(); i++) {
                     // to show numbers in determined format 
                        DecimalFormat outNums = new DecimalFormat("#0.00");
                    %>
                    <tr>
                        <td><%= (i+1) %></td>
                        <td><%=listAccounts.get(i).getAccountName() %></td>
                        <% switch(listAccounts.get(i).getAccountType()) {
                                   case CURRENT: %><td>Current account</td><% break;
                                   case TERM_DEPOSIT: %><td>Term deposit</td><% break;
                                   case LOANS: %><td>Loans</td><% break;
                                   case OTHER: %><td>Other</td><% break;
                            }
                        %>
                        <td><%=listAccounts.get(i).getOwnerOfAccount().getLastName() + " " + listAccounts.get(i).getOwnerOfAccount().getFirstName() %></td>
                        <td><%=listAccounts.get(i).getCurrency() %></td>
                        <td><%=outNums.format(listAccounts.get(i).getAmount()) %></td>
                        <td><%=datePrint.format(listAccounts.get(i).getDateClose()) %></td>
                    </tr>
                    <%
                    } // for
                    %>
                </table>
                <div style="clear: both;"></div>
            </div> 

            <div id="sidebar">
                <form method="POST" action="table_accounts">
                    <h3>Sort accounts by:</h3>
                    <br />
                    <label class="checkbox"><input type="checkbox" name="owner_name"> owner's name</label>
                    <br />
                    <label class="checkbox"><input type="checkbox" name="account_type"> type of account</label>
                    <br />
                    <label class="checkbox"><input type="checkbox" name="date_close"> date of close</label>
                    <br />
                    <label class="checkbox"><input type="checkbox" name="balance"> balance</label>
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