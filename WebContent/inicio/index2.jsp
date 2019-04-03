<%@page import="java.util.List"%>
<%@page import="java.sql.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="entidades.Dieta"%>
<%@page import="entidades.Rol"%>
<%@page import="servicios.RolServicio"%>
<%@page import="servicios.UsuarioServicio"%>
<%@page import="servicios.DietaServicio"%>
<%@page import="entidades.Usuario"%>
<%@ page import="biblio.BiblioUtilidades"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
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
	 	// Si el usuario no tiene una sesión iniciada
		if (session.getAttribute("usuario-nombre") == null || session.getAttribute("usuario-rol") == null) {
			
			response.sendRedirect("../"); // Lo mandamos a la pantalla de inicio
			
		} else { // Si el usuario tiene una sesión iniciada
		    
			UsuarioServicio usuarioServicio = new UsuarioServicio();
			RolServicio rolServicio = new RolServicio();
			
			Usuario usuario = usuarioServicio.buscaPorNombre(session.getAttribute("usuario-nombre").toString());
			Rol rol = rolServicio.buscaPorRol(session.getAttribute("usuario-rol").toString());
			
			if (rol.getRol().equals("usuario")) {
				
				DietaServicio dietaServicio = new DietaServicio();
				
				out.print("<h2>" + usuario + " <a href=\"../logout/\">Cerrar sesión</a></h2>");
					    
				if (!BiblioUtilidades.compruebaFecha(usuario.getExpiraDieta())) { // Si la deita ha expirado la actualizamos
					
					dietaServicio.actualiza(usuario.getId());
				}
					    
				out.print("<h1 id=\"expiraDieta\">La dieta expira el: " + usuario.getExpiraDieta() + "</h1>");
					
				out.print("<table>");
				out.print("<thead>");
				out.print("<tr><th>Lunes</th><th>Martes</th><th>Miércoles</th><th>Jueves</th>" +
					"<th>Viernes</th><th>Sábado</th><th>Domingo</th></tr>");
				out.print("</thead>\n<tbody>");
						
				Dieta[][] tabla = dietaServicio.generaTablaDietaUsuario(usuario.getId()); // Generamos la tabla de la dieta del usuario
						
				boolean stripped = false;
						
				for (int fila = 0; fila < 4; fila++) { // Pintamos la tabla
						    
					if (stripped) { // Decoración en zebra de la tabla
						        
						out.print("<tr" + " class=\"stripped\"" + ">");
					    stripped = false;
						        
					} else {
						        
						out.print("<tr>");
					    stripped = true;
					}
						    
					for (int columna = 0; columna < 7; columna++) { // Pintamos las columnas
					        
						Dieta dieta = tabla[fila][columna];
						out.print("<td><a class=\"enlaceTd\" href=\"receta/?id=" + dieta.getReceta().getId() + "\">" +
							dieta.getReceta().getNombre() + "</a></td>");
					}
						    
					out.print("</tr>");
				}
						
				out.print("</tbody>");
				out.print("</table>");
				
			} else if (rol.getRol().equals("admin")) {
				
				// FORMULARIO AÑADIR USUARIO ---------------------------
		    	out.print("<div id=\"formAnyadir\">");
		    	out.print("<form action=\"insertar/\" method=\"POST\">");
		    	out.print("<input id=\"inputNombre\" name=\"nombre\" type=\"text\" placeholder=\"Nombre\" maxlength=\"20\" required>");
		    	out.print("<input name=\"contrasena\" type=\"password\" placeholder=\"Contraseña\" maxlength=\"30\" required>");
		    	
		    	// Proceso de obtención de la fecha por defecto del input
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
			    
			    // Le añadimos 7 días a la fecha
			    cal.add(Calendar.DATE, 7);
				
			    Date fechaSql = new Date(cal.getTimeInMillis()); // Formateamos la fecha
			    
		    	out.print("<input id=\"inputED\" name=\"expiraDieta\" type=\"date\" value=\"" + fechaSql + "\"required>");
		    	out.print("<select id=\"privilegio\" name=\"idPrivilegio\" required>");
		    	
		    	List<Rol> roles = rolServicio.buscaTodos();
		    	
		    	for (Rol rolEle : roles) {

		    	    String rolNombre = rolEle.getRol().substring(0, 1).toUpperCase() + rolEle.getRol().substring(1);
		    		
					if (rolEle.getRol().equals("usuario")) {
		    	        
		    	        out.print("<option value=\"" + rolEle.getId() + "\" selected>" + rolNombre + "</option>");
		    	        
		    	    } else {
		    	        
		    	        out.print("<option value=\"" + rolEle.getId() + "\">" + rolNombre + "</option>");
		    	    }
		    	}
		    	
		    	out.print("</select>");
		    	out.print("<button type=\"submit\" class=\"any\">Añadir</button>");
		    	out.print("</form>");
		    	out.print("</div>");

				// GENERACIÓN TABLA DE USUARIOS ---------------------------
				out.print("<table>");
				out.print("<thead>");
				out.print("<tr class=\"stripped\"><th>Nombre</th><th>Fecha Expiración Dieta</th>" +
					"<th>Tipo Usuario</th><th></th><th></th></tr>");
				out.print("</thead>\n<tbody>");
				
				boolean stripped = false;
				
				for (Rol rolEle : roles) {
					
					if (!rolEle.getRol().equals("admin")) {
						
			    	    String rolNombre = rolEle.getRol().substring(0, 1).toUpperCase() + rolEle.getRol().substring(1);
			    		List<Usuario> usuarios = usuarioServicio.buscaPorRol(rolEle);
			    		
			    		for (Usuario usuarioEle : usuarios) {
			    			
							if (stripped) { // Decoración en zebra de la tabla
						        
						        out.print("<tr" + " class=\"stripped\"" + ">");
						        stripped = false;
						        
						    } else {
						        
						        out.print("<tr>");
						        stripped = true;
						    }
						    
						    out.print("<td>" + usuarioEle.getNombre() + "</td>");
						    out.print("<td>" + usuarioEle.getExpiraDieta() + "</td>");
						    out.print("<td>" + rolNombre + "</td>");
							out.println("<td><a href='modificar/?id=" + usuarioEle.getId() +
							        "'><button type='submit' class='mod'>" + "Modificar</button></td></a>");
							out.println("<td><button onclick=\"eliminar(this);\" type='button' class='eli'>Eliminar</button></td>");
						    out.print("</tr>");
			    		}
					}
		    	}
			}
		}
	%>
</body>
</html>