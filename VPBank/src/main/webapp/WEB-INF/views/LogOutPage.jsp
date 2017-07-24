<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
		<h1>Session was finished</h1>
		<p style="text-align: center; font-size: 14px; color: #19792d;">
		Thanks for using VPBank Internet-Banking!
		</p>
		<br />
		<p style="text-align: center;"><img src="resources/images/smile.png" width="100px" height="100px" alt="smile" /></p>
		<form action="logout" method="post">
			<input type="submit" value="Restore session" id="submitBtn" />
		</form>
	</div>
</body>
</html>