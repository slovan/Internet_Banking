<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="resources/images/favicon.ico"
	type="image/x-icon">
<link rel="stylesheet" type="text/css"
	href="resources/css/admin_style.css" />
<link href='http://fonts.googleapis.com/css?family=Montserrat:400,700'
	rel='stylesheet' type='text/css'>
<title>VPBank | Administration console</title>
</head>
<body>
	<div id="wrap">

		<div id="header">
			<img src="resources/images/logo.png" alt="VPBank - " />
			<h1>Administration console</h1>
		</div>

		<div class="menu">
			<ul>
				<li><a href="#">Home</a></li>
				<li><a href="#">Clients</a></li>
				<li><a href="#">Accounts</a></li>
			</ul>
		</div>
		<div id="contentwrap">
			<div id="content">
			    <h2><% if (session.getAttribute("status") == null) {%>
				Welcome to VPBank administration console, dear admin! Here
					you are able to:<%} else {%><%= session.getAttribute("status") %><% } %></h2>
					<% session.setAttribute("status", null); %>
				<ol class="bullet">
					<li><a href="admin/new_client">Add a new client</a></li>
					<li><a href="#">Edit client's data</a></li>
					<li><a href="#">Remove a client from database</a></li>
					<li><a href="admin/add_account">Add a new account to a client</a></li>
					<li><a href="#">Edit an account</a></li>
					<li><a href="#">Remove an account</a></li>
				</ol>
			</div>
			<div style="clear: both;"></div>
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