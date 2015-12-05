<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="java.util.Date, java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>VPBank | Administration console | Adding a new client</title>
	<style>
		body {
			font-family: arial, sans-serif;
			font-size: 14px;
		}
	</style>
</head>
<body>
	<h1 style="text-decoration: underline;">VPBank administration console</h1>
	<h1>Add a new client</h1>
	
	<form method="POST" action="new_client">
		Login: <!-- Allow digits, simple letters (without diakritics) Min 6 max 20  -->
		<input name="user_ID" type="text" pattern="[A-Za-z0-9]{6,20}"></input> 
		<br /><br />
		Password: <!-- Allow digits, simple letters (without diakritics), symbols ".-_" Min 8  -->
		<input name="user_PWD" type="text" pattern="[A-Za-z0-9\Q._-\E]{8,}">
		 gfgfgnf
		<br /><br />
		First name: <!-- Allow digits, simple letters (without diakritics), symbols ".-_" Min 8  -->
		<input name="first_name" type="text"></input>
		<br /><br />
		Last name:
		<input name="last_name" type="text"></input>
		<br /><br />
		Identity card (passport) number:
		<input name="pass_num" type="text"></input>
		<br /><br />
		Date of birth:
		<% 
			Date now = new Date(); // current date 
			SimpleDateFormat dateFormated = new SimpleDateFormat("yyyy-MM-dd");
		%>
		<input type="date" name="dob" value="<%= dateFormated.format(now) %>" max="<%= dateFormated.format(now) %>"></input>
		<br /><br />
		Gender:
		<select name="gender">
			<option value="MALE">Male</option>
			<option value="FEMALE">Female</option>
		</select>
		<br /><br />
		Marital status:
		<select name="marital_status">
			<option value="SINGLE">Single</option>
			<option value="MARRIED">Married</option>
			<option value="DIVORCED">Divorced</option>
			<option value="WIDOWED">Widowed</option>
			<option value="COHABITING">Cohabiting</option>
			<option value="CIVIL_UNION">Civil union</option>
			<option value="DOMESTIC_PARTNERSHIP">Domestic partnership</option>
			<option value="UNMARRIED_PARTNERS">Unmarried partners</option>
		</select>
		<hr />
		<button name="find_stream" type="submit" value="confirm_only">Confirm</button>
		<button name="find_stream" type="submit" value="confirm_and_continue">Confirm and add an account</button>
		<hr />
	</form>
</body>
</html>