<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="java.util.Date, java.text.SimpleDateFormat, com.vpbank.models.Gender, com.vpbank.models.MaritalStatus"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="../resources/images/favicon.ico"
	type="image/x-icon">
<link rel="stylesheet" type="text/css"
	href="../resources/css/admin_style.css" />
<link href='http://fonts.googleapis.com/css?family=Montserrat:400,700'
	rel='stylesheet' type='text/css'>
<title>VPBank | Administration console | Adding a new client</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
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
				<h2>Adding a new client</h2>
				<form class="form" method="POST" action="new_client">					
					<div class="input_field">
						<label for="login_username">Login:</label>
						<!-- Allow digits, simple letters (without diakritics) Min 6 max 20  -->
						<input name="login_username"
							value="<%=request.getAttribute("loginUsername")%>" type="text"
							pattern="[A-Za-z0-9]{6,20}" required></input> <small>
							*min 6 characters: only English letters or digits</small>
					    <%
					        if ((Boolean) request.getAttribute("username_exists")) {
                        %>
                            <br><strong>This username is already exists.</strong>
                        <%
                            }
                        %>
					</div>
					<div class="input_field">
						<label for="login_password">Password:</label>
						<!-- Allow digits, simple letters (without diakritics), symbols ".-_" Min 8  -->
						<input name="login_password" type="text"
							value="<%=request.getAttribute("loginPassword")%>"
							pattern="[A-Za-z0-9\.\_\-]{8,}" required></input> <small>
							*min 8 characters: only English letters, digits, '.', '-' or '_'</small>
					</div>
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
					<div class="input_field">
						<label for="pass_num">Identity card (passport) number:</label> 
						<input name="pass_num" type="text" 
						  value="<%=request.getAttribute("passNum")%>"
						  pattern="[[A-Za-z0-9]{6,20}" required></input>
						<small> *min 6 characters: only English letters or digits</small>
						<%
                            if ((Boolean)request.getAttribute("client_exists")) {
                        %>
                            <br><strong>Client with such passport number is already exists.</strong>
                        <%
                            }
                        %>
					</div>
					<div class="input_field">
						<label for="dob">Date of birth:</label>
						<%
						    Date now = new Date(); // current date 
						    SimpleDateFormat dateFormated = new SimpleDateFormat("yyyy-MM-dd");
						%>
						<input name="dob" type="date" max="<%=dateFormated.format(now)%>"
						    value="<%=request.getAttribute("dobStr")%>" required></input> 
						<small> *date in format dd.mm.yyyy</small>
						<%
					        if ((Boolean)request.getAttribute("incorrect_date")) {
                        %>
                                <br><strong>Incorrect date format.</strong>
                        <%
                            } else {
						    if ((Boolean)request.getAttribute("under_age")) {
                        %>
                                <br><strong>This person is under-age (less then 18 years old).</strong>
                        <%
                                }
                            }
                        %>
					</div>
					<div class="input_field">
						<label for="gender">Gender:</label> <select name="gender">
							<option value="MALE" <%if ((Gender)request.getAttribute("gender") == Gender.MALE) {%>selected<%} %>>Male</option>
							<option value="FEMALE" <%if ((Gender)request.getAttribute("gender") == Gender.FEMALE) {%>selected<%} %>>Female</option>
						</select>
					</div>
					<div class="input_field">
						<label for="marital_status">Marital status:</label> <select
							name="marital_status">
							<option value="SINGLE" <%if ((MaritalStatus)request.getAttribute("maritalStatus") == MaritalStatus.SINGLE) {%>selected<%} %>>Single</option>
							<option value="MARRIED" <%if ((MaritalStatus)request.getAttribute("maritalStatus") == MaritalStatus.MARRIED) {%>selected<%} %>>Married</option>
							<option value="DIVORCED" <%if ((MaritalStatus)request.getAttribute("maritalStatus") == MaritalStatus.DIVORCED) {%>selected<%} %>>Divorced</option>
							<option value="WIDOWED" <%if ((MaritalStatus)request.getAttribute("maritalStatus") == MaritalStatus.WIDOWED) {%>selected<%} %>>Widowed</option>
							<option value="COHABITING" <%if ((MaritalStatus)request.getAttribute("maritalStatus") == MaritalStatus.COHABITING) {%>selected<%} %>>Cohabiting</option>
							<option value="CIVIL_UNION" <%if ((MaritalStatus)request.getAttribute("maritalStatus") == MaritalStatus.CIVIL_UNION) {%>selected<%} %>>Civil union</option>
							<option value="DOMESTIC_PARTNERSHIP" <%if ((MaritalStatus)request.getAttribute("maritalStatus") == MaritalStatus.DOMESTIC_PARTNERSHIP) {%>selected<%} %>>Domestic partnership</option>
							<option value="UNMARRIED_PARTNERS" <%if ((MaritalStatus)request.getAttribute("maritalStatus") == MaritalStatus.UNMARRIED_PARTNERS) {%>selected<%} %>>Unmarried partners</option>
						</select>
					</div>
					<br /> <br />
					<hr />
					<button name="find_stream" type="submit" value="confirm_only">Confirm</button>
					<button name="find_stream" type="submit"
						value="confirm_and_continue">Confirm and add an account</button>
					<hr />
				</form>
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