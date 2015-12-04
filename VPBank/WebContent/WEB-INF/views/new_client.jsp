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
		Login:
		<input name="user_ID" type="text"></input>
		<br /><br />
		Password:
		<input name="user_PWD" type="text"></input>
		<br /><br />
		First name:
		<input name="first_name" type="text"></input>
		<br /><br />
		Last name:
		<input name="last_name" type="text"></input>
		<br /><br />
		Identity card (passport) number:
		<input name="pass_num" type="text"></input>
		<br /><br />
		Date of birth:
		<input name="dob" type="text"></input>
		<br /><br />
		Gender:
		<select name="gender">
			<option value="FEMALE">Female</option>
			<option value="MALE">Male</option>
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
		<button type="submit">Add client</button>
		<hr />
	</form>
</body>
</html>