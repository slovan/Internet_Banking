<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="com.vpbank.models.*"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<link rel="shortcut icon" href="resources/images/favicon.ico" type="image/x-icon">
	<link rel="stylesheet" type="text/css" href="resources/css/main_style.css" />
	<link href='http://fonts.googleapis.com/css?family=Montserrat:400,700' rel='stylesheet' type='text/css'>
	<title>VPBank | online banking</title>
</head>
<body>
	<div id="wrap">

		<div id="header">	
		<img src="resources/images/logo.png" alt="VPBank - " />
			<h1>Your live Internet-Bank</h1>
		</div>

		<div id="menu">
			<ul>
				<li><a href="#">Review</a></li>
				<li><a href="#">Accounts</a></li>
				<li><a href="#">Cards</a></li>
				<li><a href="#">Payments</a></li>
				
			</ul>
			<ul class="logout">
				<li><a href="#">logout</a></li>
			</ul>
		</div>
		<%
		ClientAccessDetails cad = (ClientAccessDetails)request.getAttribute("login_info");
		String firstName = (String)cad.getClient().getFirstName();
		String lastName = (String)cad.getClient().getLastName();
		%>
		<div id="contentwrap"> 

			<div id="content">

				<h2>Wellcome to VPBank online services,</h2>
				<h2><%="Mr. " + firstName + " " + lastName + "!"%></h2>
				
				<div style="float: left; padding: 10px 0 10px 0;"><img src="business.png" alt="image" /></div>

<p>Non facilius quaesita uno originem. Conflantur me mo ob scripturas in divisibile attendenti deprehendi. Corporea ac perpauca innumera ad collecta contumax. Humanas accipio facilem et colores ut ut. Infinitum veritates dubitabam re ex. Vigilantes substantia dubitandum de ha frequenter cavillandi in ex. Ac putantur occurrit judicium profecto ut.</p>

<p>Excludere im sapientia evidenter et delusisse. Externarum vi requiratur in judicarent an cavillandi. Agi praecise similium sequatur existant vel sed. Visa rem unam idea nia omne esse. An ad ea adeo otii heri vero homo. Essem paulo rem prava meo fas firma nomen.</p>

<p>Quo locis utens timet nobis miror sua. Aeque fecto ii im nulli ut forte mo. Ha ac creatorem conservat perfectae exhibetur. Fas rea gallice cui exhibet probant ponamus per. Occurreret ab facultates reducantur to ei si sufficiunt. Venturum ex dubitare to curantes meditari eo opinione explicui. Sui eos vix quietem sentire dicamne antehac remotam figuram age. Nam devenimus obdormiam usu dum eminenter hac. Plus vi amen ad quis se fiat meam ii.</p>

<h2>Quo locis utens timet nobis miror sua</h2>

<div style="float: left; padding: 30px 10px 10px 10px;"><img src="business1.png" alt="image" /></div>

<p>Fusce ullamcorper libero nec turpis semper et rutrum est hendrerit. Etiam sit amet nibh in tortor ultrices tempus. Integer molestie, nisi tempor mollis dapibus, ipsum sem cursus ante, at volutpat est erat id lorem. Nulla mattis porta lectus, nec iaculis est posuere vitae. Suspendisse eu nulla mi, in fermentum quam. In vulputate rutrum mauris ut luctus. Cras vehicula ante vitae nisi consequat consectetur.</p>

<div style="clear: both;"> </div>
</div>

<div id="sidebar">
<h3><%="Mr. " + firstName + " " + lastName + "!"%></h3>
<ul>
<li><a href="#">Proin at</a></li>
<li><a href="#">Class aptent taciti</a></li>
<li><a href="#">Morbi in dolor</a></li>
<li><a href="#">Praesent ultricies</a></li>
<li><a href="#">Aenean euismod</a></li>
<li><a href="#">Donec sempe</a></li>
<li><a href="#">Suspendisse potenti</a></li>
</ul>

<h3>Useful Resources</h3>
<ul>
<li><a href="#">Praesent ultricies</a></li>
<li><a href="#">Aenean euismod</a></li>
<li><a href="#">Donec sempe</a></li>
<li><a href="#">Suspendisse potenti</a></li>
<li><a href="#">Proin at</a></li>
<li><a href="#">Class aptent taciti</a></li>
</ul>

</div>

<div style="clear: both;"> </div>

</div>

<div id="footer">
<p>&copy; Copyright 2011 <a href="#">You</a> | Template by <a href="http://armandoboni.com">Dyqan Online</a> | <a href="http://armandoboni.com">Web Design</a></p>
</div>

</div>

</body>
</html>