<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="java.util.List, com.vpbank.models.Client, com.vpbank.services.AccountGenerator, java.util.Date, java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>VPBank | Administration console | Adding an account to a
	client</title>
<style>
body {
	font-family: arial, sans-serif;
	font-size: 14px;
}
</style>
</head>
<body>
	<h1 style="text-decoration: underline;">VPBank administration
		console</h1>
	<h1>Add an account to a client</h1>

	<!-- 
	Content of this page depends on selection of a client to which an account must be added 
	
	Hence, at first should be controlled if information of a selected client 
	is already contained in the current session under the name "client" 
	-->
	
	<%
		Client client = (Client) session.getAttribute("client");
		if (client == null) { // in that case client is not selected yet
			// need to select a client
	%>
		<form method="POST" action="add_account">
		
			<!-- Enter client's name to find him in the database -->
		
			<h2>Choose a client to which an account will be added:</h2>
			First name: <input name="first_name" type="text"></input> <br /> <br />
			Last name: <input name="last_name" type="text"></input>
			<hr />
			<button type="submit">Search in database</button>
			<hr />
		</form>
		<%
			List<Client> selectedClients = (List<Client>)session.getAttribute("selected_clients");
				if (selectedClients != null) { // have a list of clients with similar names
					// need to make a selection to confirm a client
		%>
		<form method="POST" action="add_account">
			
			<!-- Show all clients with the same name to be confirmed -->
			
			<table border="1">
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
					for (int i = 0; i < selectedClients.size(); i++) {
				%>
				<tr>
					<td><input name="client_order_in_list" type="radio"
						value="<%=i%>"></td>
					<td><%=selectedClients.get(i).getFirstName()%></td>
					<td><%=selectedClients.get(i).getLastName()%></td>
					<td><%=selectedClients.get(i).getPassNum()%></td>
					<td><%=selectedClients.get(i).getDob()%></td>
					<td><%=selectedClients.get(i).getGender()%></td>
					<td><%=selectedClients.get(i).getMaritalStatus()%></td>
				</tr>
				<%
					}
				%>
			</table>
			<hr />
			<button type="submit">Confirm</button>
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
		<h3>Name: <%= client.getFirstName() + " " + client.getLastName() %></h3>
		<h3>Gender: <%= client.getGender() %></h3>
		<h3>Marital status: <%= client.getMaritalStatus() %></h3>
		<h3>Date of birth: <%= client.getDob() %></h3>
		<h3>Identity card (passport) number: <%= client.getPassNum() %></h3>
		<hr />
		<button type="submit">Select another client</button>
		<hr />
		<h2>Adding an account:</h2>
		<form method="POST" action="SaveAccount">
			Type of the account:
			<select name="account_type">
				<option value="CURRENT">Current account</option>
				<option value="TERM_DEPOSIT">Term deposit</option>
				<option value="LOANS">Loans account</option>
				<option value="OTHER">Other</option>
			</select>
			<br /><br />
			Currency of the account:
			<input name="currency" type="text" value="EUR"></input>
			<br /><br />
			Account number (basic):
			<input name="account_number_basic" type="text" value="<%= accountNumberBasic %>"></input>
			<br /><br />	
			Amount on the account:
			<input name="amount" type="text"></input>
			<br /><br />
			Opened from:
			<% 
				Date now = new Date(); // current date 
				SimpleDateFormat dateFormated = new SimpleDateFormat("yyyy-MM-dd");
			%>
			<input type="date" name="date_open" value="<%= dateFormated.format(now) %>"></input>
			<br /><br />
			Valid until:
			<input type="date" name="date_close" value="<%= dateFormated.format(now) %>" min="<%= dateFormated.format(now) %>"></input>
			<br /><br />
			Bank code:
			<input name="bank_code" type="text" value="1723"></input>
			<br /><br />
			SWIFT code:
			<input name="swift_code" type="text" value="VPBSKUA"></input>
			<br /><br />
			<hr />
			<button type="submit">Confirm</button>
			<hr />
		</form>
	<% 
	}
	%>
</body>
</html>