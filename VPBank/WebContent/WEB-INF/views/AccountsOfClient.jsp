<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.vpbank.models.*, java.util.List, java.text.SimpleDateFormat, java.text.DecimalFormat"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<link rel="shortcut icon" href="resources/images/favicon.ico"
	type="image/x-icon">
<link rel="stylesheet" type="text/css"
	href="resources/css/main_style.css" />
<link href='http://fonts.googleapis.com/css?family=Montserrat:400,700'
	rel='stylesheet' type='text/css'>
<title>VPBank | online banking</title>
</head>
<body>
	<div id="wrap">

		<div id="header">
			<img src="resources/images/logo.png" alt="VPBank - " />
			<h1>Your live Internet-Bank</h1>
		</div>

		<div class="menu">
			<ul>
				<li><a href="index">Home</a></li>
				<li><a href="accounts">Accounts</a></li>
				<li><a href="#">Payments</a></li>

			</ul>
			<ul class="logout">
				<li><a href="logout">logout</a></li>
			</ul>
		</div>
		<%
			// to show numbers in determined format	
			DecimalFormat outNums = new DecimalFormat("#0.00");
			
			ClientAccessDetails cad = (ClientAccessDetails) request.getAttribute("login_info");
			String firstName = (String) cad.getClient().getFirstName();
			String lastName = (String) cad.getClient().getLastName();
			String prefix; // variable with correct prefix to name of male/female
			if (cad.getClient().getGender() == Gender.MALE) { // detect gender of client
				prefix = "Mr."; // if male
			} else {
				prefix = "Ms."; // if female
			}
		%>
		<div id="contentwrap">
			<div id="content">
			<%
			// get list of all accounts of the current client
			List<Account> accountList = cad.getClient().getAccounts();
			
			// to show date in a good format
			SimpleDateFormat dateFormated = new SimpleDateFormat("dd.MM.yyyy");
			%>
				<h2>Current accounts:</h2>
				<table>
					<tr>
						<th>Ordinal</th>
						<th>Account</th>
						<th>IBAN</th>
						<th>Balance</th>
						<th>Currency</th>
						<th>Valid until</th>
					</tr>
					<%
					int ordinal = 0; // variable of ordinal number of the account
					boolean noSuchTypeOfAccounts = true; // to make possible print 
														// a message in table if the client 
														// does not have such account
					// loop to select all current accounts
					for (Account accVar : accountList) {
						if (accVar.getAccountType() == AccountType.CURRENT) {
							// following code is only for current accounts
							ordinal++;
							noSuchTypeOfAccounts = false;
					%>
					<tr>
						<td><%= ordinal %></td>
						<td><%= accVar.getAccountName() %></td>
						<td><%= accVar.getAccountNumberIBAN() %></td>
						<td><%= outNums.format(accVar.getAmount()) %></td>
						<td><%= accVar.getCurrency() %></td>
						<td><%= dateFormated.format(accVar.getDateClose()) %></td>
					</tr>
					<% 
						} // end if
					} // end for
					if (noSuchTypeOfAccounts) {
					%>
					<tr>
						<td colspan="6">No current accounts.</td>
					</tr>
					<% 
					}
					%>
				</table>
				
				<h2>Term deposits:</h2>
				<table>
					<tr>
						<th>Ordinal</th>
						<th>Account</th>
						<th>IBAN</th>
						<th>Balance</th>
						<th>Currency</th>
						<th>Valid until</th>
					</tr>
					<%
					ordinal = 0; // variable of ordinal number of the account
					noSuchTypeOfAccounts = true;
					// loop to select all current accounts
					for (Account accVar : accountList) {
						if (accVar.getAccountType() == AccountType.TERM_DEPOSIT) {
							// following code is only for current accounts
							ordinal++;
							noSuchTypeOfAccounts = false;
					%>
					<tr>
						<td><%= ordinal %></td>
						<td><%= accVar.getAccountName() %></td>
						<td><%= accVar.getAccountNumberIBAN() %></td>
						<td><%= outNums.format(accVar.getAmount()) %></td>
						<td><%= accVar.getCurrency() %></td>
						<td><%= dateFormated.format(accVar.getDateClose()) %></td>
					</tr>
					<% 
						} // end if
					} // end for
					if (noSuchTypeOfAccounts) {
					%>
					<tr>
						<td colspan="6">No term deposit accounts.</td>
					</tr>
					<% 
					}
					%>
				</table>
				
				<h2>Loans:</h2>
				<table>
					<tr>
						<th>Ordinal</th>
						<th>Account</th>
						<th>IBAN</th>
						<th>Balance</th>
						<th>Currency</th>
						<th>Valid until</th>
					</tr>
					<%
					ordinal = 0; // variable of ordinal number of the account
					noSuchTypeOfAccounts = true;
					// loop to select all current accounts
					for (Account accVar : accountList) {
						if (accVar.getAccountType() == AccountType.LOANS) {
							// following code is only for current accounts
							ordinal++;
							noSuchTypeOfAccounts = false;
					%>
					<tr>
						<td><%= ordinal %></td>
						<td><%= accVar.getAccountName() %></td>
						<td><%= accVar.getAccountNumberIBAN() %></td>
						<td><%= outNums.format(accVar.getAmount()) %></td>
						<td><%= accVar.getCurrency() %></td>
						<td><%= dateFormated.format(accVar.getDateClose()) %></td>
					</tr>
					<% 
						} // end if
					} // end for
					if (noSuchTypeOfAccounts) {
					%>
					<tr>
						<td colspan="6">No loans.</td>
					</tr>
					<% 
					}
					%>
				</table>
				
				<h2>Other accounts:</h2>
				<table>
					<tr>
						<th>Ordinal</th>
						<th>Account</th>
						<th>IBAN</th>
						<th>Balance</th>
						<th>Currency</th>
						<th>Valid until</th>
					</tr>
					<%
					ordinal = 0; // variable of ordinal number of the account
					noSuchTypeOfAccounts = true;
					// loop to select all current accounts
					for (Account accVar : accountList) {
						if (accVar.getAccountType() == AccountType.OTHER) {
							// following code is only for current accounts
							ordinal++;
							noSuchTypeOfAccounts = false;
					%>
					<tr>
						<td><%= ordinal %></td>
						<td><%= accVar.getAccountName() %></td>
						<td><%= accVar.getAccountNumberIBAN() %></td>
						<td><%= outNums.format(accVar.getAmount()) %></td>
						<td><%= accVar.getCurrency() %></td>
						<td><%= dateFormated.format(accVar.getDateClose()) %></td>
					</tr>
					<% 
						} // end if
					} // end for
					if (noSuchTypeOfAccounts) {
					%>
					<tr>
						<td colspan="6">No other accounts.</td>
					</tr>
					<% 
					}
					%>
				</table>
				<div style="clear: both;"></div>
			</div>

			<div id="sidebar">
				<h3><%=prefix + " " + firstName + " " + lastName%></h3>
				<ul>
					<li><a href="#">Make a payment</a></li>
					<li><a href="#">Check my loans</a></li>
					<li><a href="#">Look at my deposits</a></li>
					<li><a href="#">My settings</a></li>
				</ul>
			</div>
			<div style="clear: both;"></div>
		</div>

		<div id="footer">
			<p>
				&copy; Copyright 2016 <a href="index">VPBank</a> | Configured by <a
					href="https://github.com/slovan">Volodymyr Ponomarenko</a>
			</p>
		</div>

	</div>

</body>
</html>