<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="biblio.BiblioUtilidades" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
	<link rel="stylesheet" href="../../static/css/espera.css">
    <link href="https://fonts.googleapis.com/css?family=Poppins" rel="stylesheet">
    <link rel="icon" href="../../static/img/favicon.png">
    <title>Autodieta</title>
</head>
<body>
	<h1>Espere mientras procesamos su acción...</h1>
	<%
		if (request.getParameter("id") == null || session.getAttribute("adLog") == null) { // Si se intenta acceder por URL y no es administrador
		    	
			response.sendRedirect("../"); // Lo mandamos a la pantalla de admin
		    	
	    } else { // Si el acceso es legítimo

			Class.forName("com.mysql.jdbc.Driver");
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/autodieta","admin", "nimda12_34$");
			Statement s = conexion.createStatement();
			// Eliminamos el usuario reordenando las Ids para que no queden "huecos"
			BiblioUtilidades.eliminaUsuarioCompruebaId(request.getParameter("id"), conexion);

			conexion.close();
			
			response.sendRedirect("../");
	    }
	%>
</body>
</html>