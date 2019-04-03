<%@page import="entidades.Rol"%>
<%@page import="servicios.RolServicio"%>
<%@page import="entidades.Usuario"%>
<%@page import="servicios.UsuarioServicio"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<%@ page import="biblio.BiblioUtilidades"%>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="org.apache.commons.codec.digest.DigestUtils" %>
<%@ page import="dieta.Dieta" %>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<link rel="stylesheet" href="../static/css/inicio.css">
	<link rel="icon" href="../static/img/favicon.png">
	<link href="https://fonts.googleapis.com/css?family=Poppins" rel="stylesheet">
	<title>Autodieta</title>
</head>
<body>
	<%
		session.removeAttribute("adLog");

		String usuario = "";
		String contrasena = "";
		boolean accesoLegal = true;
	
		if (session.getAttribute("usuario") == null) { // Si el usuario no tiene una sesión iniciada
		
			if (request.getParameter("usuario") == null || request.getParameter("contrasena") == null) { // Si se intenta acceder por URL
		    	
			    response.sendRedirect("../"); // Lo mandamos a la pantalla de inicio
				accesoLegal = false;
		    	
	    	} else { // Si el acceso es legítimo

	    	    usuario = request.getParameter("usuario"); // Obtenemos datos del formulario
	    	    contrasena = DigestUtils.sha256Hex(request.getParameter("contrasena"));
	    	}
			
		} else { // Si el usuario tiene una sesión iniciada
		    
			if (request.getParameter("usuario") != null) { // Si se inicia una nueva sesión
		    	
			    usuario = request.getParameter("usuario"); // Obtenemos datos del formulario
			    contrasena = DigestUtils.sha256Hex(request.getParameter("contrasena"));
		    	
	    	} else { // Si no se está iniciando una nueva sesión

		    	UsuarioServicio usuarioServicio = new UsuarioServicio();
				
				usuario = session.getAttribute("usuario").toString(); // Obtenemos datos de la sesión
				contrasena = usuarioServicio.buscaPorNombre(usuario).getContrasena();
	    	}
		}
		
		if (accesoLegal) {
		    
		    UsuarioServicio usuarioServicio = new UsuarioServicio();
		    RolServicio rolServicio = new RolServicio();
		    
		    Usuario usuario1 = usuarioServicio.buscaPorNombre(usuario);
		    Rol rol = rolServicio.buscaPorId(usuario1.getRol().getId());
			
			if (usuario1.equals(null)) { // Si el usuario no existe
			    
			    response.sendRedirect("./?error=true");
			    
			} else { // Si el Id del usuario existe
				
				session.setAttribute("usuario-nombre", usuario1.getNombre());
				session.setAttribute("usuario-rol", usuario1.getRol().getRol());
			    response.sendRedirect("../inicio/");
			}
		}
	%>
</body>
</html>