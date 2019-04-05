<%@page import="servicios.DietaServicio"%>
<%@page import="entidades.Rol"%>
<%@page import="servicios.RolServicio"%>
<%@page import="biblio.BiblioUtilidades"%>
<%@page import="entidades.Usuario"%>
<%@page import="servicios.UsuarioServicio"%>
<%@page import="org.apache.commons.codec.digest.DigestUtils"%>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.sql.Date" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="icon" href="../static/img/favicon.png">
    <link rel="stylesheet" href="../static/css/espera.css">
    <title>Autodieta</title>
</head>
<body>
	<h1>Espere mientras procesamos su acción...</h1>
	<%
		session.removeAttribute("adLog");
	
		if (request.getParameter("usuario") == null || request.getParameter("contrasena") == null) { // Si se intenta acceder por URL
		    	
			response.sendRedirect("../"); // Lo mandamos a la pantalla de inicio
		    	
	    } else { // Si el acceso es legítimo
			
			UsuarioServicio usuarioServicio = new UsuarioServicio();
			
			Usuario usuario = null;
			String nombreUsuario = request.getParameter("usuario");
			Usuario existeUsuario = usuarioServicio.buscaPorNombre(nombreUsuario);
	        
	        if (existeUsuario != null) { // Si existe algún usuario con ese mismo nombre, se devuelve al registro
	            
	            response.sendRedirect("./?error=true");
	            
	        } else { // Si el nombre de usuario está disponible lo creamos
	            
	        	RolServicio rolServicio = new RolServicio();
	        	
	            String contrasena = DigestUtils.sha256Hex(request.getParameter("contrasena"));
		        int idUsuario = BiblioUtilidades.obtenerNuevaIdUsuario(); // Obtenemos una nueva id para el usuario
		        Rol rol = rolServicio.buscaPorRol("usuario");
		        
		        // Proceso de obtención de la fecha en la que expira la dieta del nuevo usuario
				Calendar cal = Calendar.getInstance();
			    
		        // Obtenemos la fecha de hoy
				int anyo = cal.get(Calendar.YEAR);
			    int mes = cal.get(Calendar.MONTH);
			    int dia = cal.get(Calendar.DAY_OF_MONTH);

			    cal.clear();
				
			    // Actualizamos el calendario con los datos de arriba
			    cal.set(Calendar.YEAR, anyo);
			    cal.set(Calendar.MONTH, mes);
			    cal.set(Calendar.DATE, dia);
			    
			    // Le añadimos 7 días a la fecha ya que la dieta se renueva cada semana
			    cal.add(Calendar.DATE, 7);
				
			    Date fechaSql = new Date(cal.getTimeInMillis()); // Formateamos la fecha para que se pueda almacenar en sql
			    
			    usuario = new Usuario(idUsuario, rol, nombreUsuario, contrasena, fechaSql.toString());
		        
				usuarioServicio.salva(usuario);
				
				// GENERACIÓN DE LA DIETA
				
				DietaServicio dietaServicio = new DietaServicio();
				
				dietaServicio.salva(dietaServicio.generaDieta(idUsuario));
				
				response.sendRedirect("../");
	        }
	    }
	%>
</body>
</html>