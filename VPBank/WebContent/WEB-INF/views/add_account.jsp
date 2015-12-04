<%@ page import="java.util.List, com.vpbank.models.Client" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>VPBank | Administration console | Adding an account to a client</title>
	<style>
		body {
			font-family: arial, sans-serif;
			font-size: 14px;
		}
	</style>
</head>
<body>
	<h1 style="text-decoration: underline;">VPBank administration console</h1>
	<h1>Add an account to a client</h1>
	<form method="POST" action="add_account">
		<!-- 
		Client ID:
		<input name="client_id" type="text"></input>
		<br /><br />		
		Type of account:
		<select name="account_type">
			<option value="CURRENT">Current account</option>
			<option value="TERM_DEPOSIT">Term deposit</option>
			<option value="LOANS">Loans account</option>
			<option value="OTHER">Other</option>
		</select>
		<br /><br />
		Password:
		<input name="user_PWD" type="text"></input>
		<br /><br /> 
		-->
		<h2>Choose a client to which an account will be added: </h2>
		
		First name:
		<input name="first_name" type="text"></input>
		<br /><br />
		Last name:
		<input name="last_name" type="text"></input>
		<hr />
		<button type="submit">Search in database</button>
		<hr />
	</form>
	<% 
	List<Client> selectedClients = (List<Client>)request.getAttribute("selected_clients");
	if (selectedClients != null) {
	%>
	<table>
		<tr>
			<th>First name</th>
			<th>Last name</th>
			<th>Identity card</th>
			<th>Date of birth</th>
			<th>Gender</th>
			<th>Marital status</th>
		</tr>
		<% for (int i = 0; i < selectedClients.size(); i++) { %>
			<tr>
				<th><%= selectedClients.get(i).getFirstName() %></th>
				<th><%= selectedClients.get(i).getLastName() %></th>
				<th><%= selectedClients.get(i).getPassNum() %></th>
				<th><%= selectedClients.get(i).getDob() %></th>
				<th><%= selectedClients.get(i).getGender() %></th>
				<th><%= selectedClients.get(i).getMaritalStatus() %></th>
			</tr>
		<% } %>
	</table>
	<% } %>
</body>
</html>