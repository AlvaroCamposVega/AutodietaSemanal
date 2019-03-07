<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.Connection" %>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<link rel="stylesheet" href="../../css/receta.css">
	<link rel="icon" href="../../img/favicon.png">
	<link href="https://fonts.googleapis.com/css?family=Poppins" rel="stylesheet">
	<title>Autodieta</title>
</head>
<body>
	<a href="../">Atrás</a>
	<div id="wrapper">
	<%
		session.removeAttribute("adLog");
	
		if (request.getParameter("id") == null || session.getAttribute("usuario") == null) { // Si se intenta acceder por URL
		        
		    response.sendRedirect("../"); // Lo mandamos a su dieta
		
		} else { // Si el acceso es legítimo
		        
		    Class.forName("com.mysql.jdbc.Driver");
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/autodieta","admin", "nimda12_34$");
			Statement s = conexion.createStatement();
				
			String idReceta = request.getParameter("id");
			ResultSet listadoReceta = s.executeQuery("SELECT Nombre, Ingredientes, Descripcion FROM receta WHERE Id = " + idReceta);
				
			while(listadoReceta.next()) {
				    
				out.print("<h1>" + listadoReceta.getString("Nombre").toUpperCase() + "</h1>");
				out.print("<div id=\"cajaIngredientes\">");
				out.print("<h2 class=\"ingredientes\">Ingredientes - " + listadoReceta.getString("Ingredientes") + "</h2>");
				out.print("</div>");
				out.print("<div id=\"cajaDescripcion\">");
				out.print("<h2 class=\"cabecera\">Descripción</h2>");
				out.print("<h3>" + listadoReceta.getString("Descripcion") + "</h3>");
				out.print("</div>");
			}
				
			conexion.close();
		}
	%>
	</div>
</body>
</html>