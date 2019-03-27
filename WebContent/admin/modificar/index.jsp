<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.ResultSet" %>
<!DOCTYPE html>
<html lang="es">
<head>
	<%
		String formatoPagina = "\"../../css/modificarError3.css\"";
		String textoError = "<h1 id=\"textoError\">Nombre de usuario no disponible<br>La fecha debe ser superior a la de hoy</h1>";
	
		if (request.getParameter("error") == null) {
		    
		    formatoPagina = "\"../../css/modificar.css\"";
		    textoError = "";
		    
		} else if (Integer.parseInt(request.getParameter("error")) == 1) {
		    
		    formatoPagina = "\"../../css/modificarError1.css\"";
		    textoError = "<h1 id=\"textoError\">Nombre de usuario no disponible</h1>";
		    
		} else if (Integer.parseInt(request.getParameter("error")) == 2) {
		    
		    formatoPagina = "\"../../css/modificarError2.css\"";
		    textoError = "<h1 id=\"textoError\">La fecha debe ser superior a la de hoy</h1>";
		}
	%>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
	<link rel="stylesheet" href=<%= formatoPagina %>>
    <link href="https://fonts.googleapis.com/css?family=Poppins" rel="stylesheet">
    <link rel="icon" href="../../img/favicon.png">
    <title>Autodieta</title>
</head>
<body>
	<div id="wrapper">
		<form action="modificar.jsp" method="POST">
	<%
		if (request.getParameter("id") == null || session.getAttribute("adLog") == null) { // Si se intenta acceder por URL
		        
		    response.sendRedirect("../"); // Lo mandamos a la página de administrador
		
		} else { // Si el acceso es legítimo
		        
			Class.forName("com.mysql.jdbc.Driver");
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/autodieta","admin", "nimda12_34$");
			Statement s = conexion.createStatement();
			Statement s1 = conexion.createStatement();
				
			String idUsuario = request.getParameter("id");
				
			ResultSet listadoAtributosUsuario = s.executeQuery("SELECT IdPrivilegio, Nombre, Contrasena, ExpiraDieta " +
				"FROM usuario WHERE Id = " + idUsuario);
				
			ResultSet listadoPrivilegios = s1.executeQuery("SELECT * FROM privilegio");
				
			String idPrivilegio = "";
	        String nombre = "";
	        String contrasena = "";
	        String expiraDieta = "";
				
			while (listadoAtributosUsuario.next()) {
				    
				idPrivilegio = listadoAtributosUsuario.getString("IdPrivilegio");
	        	nombre = listadoAtributosUsuario.getString("Nombre");
	        	contrasena = listadoAtributosUsuario.getString("Contrasena");
	        	expiraDieta = listadoAtributosUsuario.getString("ExpiraDieta");
	                
	            out.print("<input name=\"id\" style=\"display:none\" type=\"number\" value=\"" + idUsuario
	            	+ "\" readonly=\"readonly\">");
	                
	            out.print("<table>");
		        	out.print("<tr>");
			        	out.print("<td>Nombre</td>");
			            out.print("<td>Contraseña</td>");
			            out.print("<td>Fecha Expiración Dieta</td>");
			            out.print("<td>Tipo Usuario</td>");
		            out.print("</tr>");
		                
		        out.print("<tr>");
	            out.print("<td><input id=\"inputNombre\" name=\"nombre\" type=\"text\" placeholder=\"Nombre\" maxlength=\"20\" value=\"" + nombre + "\" required></td>");
	            out.print("<td><input name=\"contrasena\" type=\"password\" placeholder=\"Contraseña\" maxlength=\"30\"></td>");
	            out.print("<td><input id=\"inputED\" name=\"expiraDieta\" type=\"date\" value=\"" + expiraDieta + "\"></td>");
	            out.print("<td><select name=\"idPrivilegio\" required>");
	                
	            while (listadoPrivilegios.next()) {
	                    
	            	String idPrivi = listadoPrivilegios.getString("Id");
	                String privi = listadoPrivilegios.getString("Privilegio").substring(0, 1).toUpperCase() +
	                        listadoPrivilegios.getString("Privilegio").substring(1);
	                    
	                if (Integer.parseInt(idPrivi) == Integer.parseInt(idPrivilegio)) {
	                        
	                	out.print("<option value=\"" + idPrivi + "\" selected>" + privi + "</option>");
	                        
	                } else {
	                        
	                	out.print("<option value=\"" + idPrivi + "\">" + privi + "</option>");
	                }
	            }
	                
	            out.print("</select></td><br>");
	            out.print("</tr>");
	            out.print("</table>");
	            out.print("<input id=\"aceptar\" class=\"inputButton\" type=\"submit\" value=\"Aceptar\">");
	            out.print("<a href=\"../\"><input id=\"cancelar\" class=\"inputButton\" type=\"button\" value=\"Cancelar\"></a>");
			}
				
			conexion.close();
		}
	%>
		</form>
		<%= textoError %>
	</div>
</body>
</html>