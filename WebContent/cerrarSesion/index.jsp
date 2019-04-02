<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<link rel="stylesheet" href="../static/css/espera.css">
	<link rel="icon" href="../static/img/favicon.png">
	<link href="https://fonts.googleapis.com/css?family=Poppins" rel="stylesheet">
	<title>Autodieta</title>
</head>
<body>
	<h1>Espere mientras procesamos su acción...</h1>
	<%
		session.removeAttribute("adLog");
	
		if (session.getAttribute("usuario") != null) {
		    
		    session.removeAttribute("usuario");
			response.sendRedirect("../");
			
		} else {
		    
		    response.sendRedirect("../");
		}
	%>
</body>
</html>