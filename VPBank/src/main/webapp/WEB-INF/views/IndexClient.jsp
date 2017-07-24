<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.vpbank.models.*"%>
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
			Client clientData = (Client) request.getAttribute("client_data");
			String firstName = (String) clientData.getFirstName();
			String lastName = (String) clientData.getLastName();
			String prefix; // variable with correct prefix to name of male/female
			if (clientData.getGender() == Gender.MALE) { // detect gender of client
				prefix = "Mr."; // if male
			} else {
				prefix = "Ms."; // if female
			}
		%>
		<div id="contentwrap">

			<div id="content">

				<h2>Welcome to VPBank online services!</h2>

				<div style="float: left; padding: 10px 20px 20px 0px;">
					<img src="resources/images/business.png" alt="bank_building" />
				</div>
				<p>
					Dear
					<%=prefix + " " + firstName + " " + lastName + "!"%><br>We are
					pleased to welcome You to the VPBank internet-application for providing
					online banking services.<br>With VPBank Internet-banking You are 
					able to view the status	of Your current accounts, deposits, 
					available credit facilities as well as to make payments via the Internet.
				</p>	
				<p>Satisfaction of our customers with the quality of assignable services is 
				the most important thing for us, so we are constantly working hard to maintain 
				it at the proper level. Due to this vision, VPBank is now one of the largest 
				and most reliable banks in the Slovak Republic.</p>
				<p>Thank You for your trust in choosing VPBank to use banking services.</p>
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