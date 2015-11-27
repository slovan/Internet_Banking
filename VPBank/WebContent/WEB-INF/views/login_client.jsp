<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="resources/images/favicon.ico" type="image/x-icon">
<title>The VPBank online banking</title>
<meta charset="UTF-8">
<link href="resources/css/login_client_style.css" rel='stylesheet'
	type='text/css' />
<link href='http://fonts.googleapis.com/css?family=Montserrat:400,700'
	rel='stylesheet' type='text/css'>
</head>
<body>
	<div class="logo"></div>
	<div class="login-block">
		<h1>Your live Internet-Bank</h1>
		<form action="login" method="post">
			<% if ((Boolean)request.getAttribute("username_error")) {%>
			<input name="user_ID" type="text" value="<%= (String)request.getAttribute("user-ID") %>" placeholder="UserID"
				id="username" autofocus/> 
			<p>A user with the entered UserID is not found</p>
			<% } else {%>
			<input name="user_ID" type="text" value="<%= (String)request.getAttribute("user-ID") %>" placeholder="UserID"
				id="username" /> 
			<% } %>
			<% if ((Boolean)request.getAttribute("password_error")) {%>
			<input name="user_password" type="password" value="" 
				placeholder="Password" id="password" autofocus/>
			<p>Entered password is not correct</p>
			<% } else {%>			
			<input name="user_password" type="password" value="" 
				placeholder="Password" id="password" />
			<% } %>
			<input type="submit" value="Submit" id="submitBtn" />
		</form>
	</div>
</body>
</html>