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
	
		Class.forName("com.mysql.jdbc.Driver");
		Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/autodieta","admin", "nimda12_34$");
		Statement s = conexion.createStatement();
		String usuario = "";
		String usuarioHash = "";
		String contrasena = "";
		boolean accesoLegal = true;
	
		if (session.getAttribute("usuario") == null) { // Si el usuario no tiene una sesión iniciada
		
			if (request.getParameter("usuario") == null || request.getParameter("contrasena") == null) { // Si se intenta acceder por URL
		    	
			    response.sendRedirect("../"); // Lo mandamos a la pantalla de inicio
				accesoLegal = false;
		    	
	    	} else { // Si el acceso es legítimo

	    	    usuario = request.getParameter("usuario"); // Obtenemos datos del formulario
	    	    usuarioHash = DigestUtils.sha256Hex(usuario);
	    	    contrasena = DigestUtils.sha256Hex(request.getParameter("contrasena"));
	    	}
			
		} else { // Si el usuario tiene una sesión iniciada
		    
			if (request.getParameter("usuario") != null) { // Si se inicia una nueva sesión
		    	
			    usuario = request.getParameter("usuario"); // Obtenemos datos del formulario
			    usuarioHash = DigestUtils.sha256Hex(usuario);
			    contrasena = DigestUtils.sha256Hex(request.getParameter("contrasena"));
		    	
	    	} else { // Si no se está iniciando una nueva sesión
				
				Object usuUsObj = session.getAttribute("usuario"); // Obtenemos datos de la sesión
				usuario = usuUsObj.toString();
		    	
		    	ResultSet Listadocontrasena = s.executeQuery("SELECT contrasena FROM usuario WHERE nombre = '" + usuario + "'");
		    	
		    	while (Listadocontrasena.next()) { // Obtenemos la contraseña con una consulta
		    	    
		    	    contrasena = Listadocontrasena.getString("contrasena");
		    	}
	    	}
		}
		
		if (accesoLegal) {
		    
		    out.print("<h2>" + usuario + " <a href=\"../cerrarSesion/\">Cerrar sesión</a></h2>");
		    
		    String usuarioId = "";
			String usuarioPrivilegio = "";
			ResultSet listado = s.executeQuery("SELECT * FROM usuario WHERE (nombre = '" + usuario +
				"' OR nombre = '" + usuarioHash + "') AND contrasena = '" + contrasena + "'");

			while (listado.next()) { // Obtenemos el Id e IdPrivilegio del usuario (y la fecha de expiración de la dieta)
			    
			    usuarioId = listado.getString("Id");
			    usuarioPrivilegio = listado.getString("IdPrivilegio");
			}
			
			if (usuarioId.equals("")) { // Si el Id del usuario no existe
			    
			    response.sendRedirect("../?error=true");
			    
			} else { // Si el Id del usuario existe
				
				ResultSet listadoPrivi = s.executeQuery("SELECT privilegio FROM privilegio WHERE id = '" + usuarioPrivilegio + "'");
				String privilegio = "";
				
				while (listadoPrivi.next()) { // Obtenemos el privilegio de la cuenta
				    
				    privilegio = listadoPrivi.getString("privilegio");
				}
				
				if (privilegio.equals("admin")) { // Si tiene privilegio de administrador le mandamos a su página
				    
				    session.setAttribute("adLog", "true");
				    response.sendRedirect("../admin/");
				    
				} else if (privilegio.equals("usuario")) { // Si tiene privilegio de usuario recogemos su dieta y la pintamos

					ResultSet listadoExpiraDieta = s.executeQuery("SELECT ExpiraDieta FROM usuario WHERE Id = " + usuarioId);
					String expiraDieta = "";
					
					while (listadoExpiraDieta.next()) { // Consultamos la fecha en la que expira la dieta
					    
					    expiraDieta = listadoExpiraDieta.getString("ExpiraDieta");
					}
				    
				    if (!BiblioUtilidades.compruebaFecha(expiraDieta)) { // Si la deita ha expirado la actualizamos
				        
				    	Dieta.actualizaDietaUsuario(usuarioId);
				    
				    	listadoExpiraDieta = s.executeQuery("SELECT ExpiraDieta FROM usuario WHERE Id = " + usuarioId);
						
						while (listadoExpiraDieta.next()) { // Consultamos la fecha en la que expira la dieta
						    
						    expiraDieta = listadoExpiraDieta.getString("ExpiraDieta");
						}
				    }
				    
				    out.print("<h1 id=\"expiraDieta\">La dieta expira el: " + expiraDieta + "</h1>");
					
				    session.setAttribute("usuario", usuario); // Guardamos los datos de la sesión
				
					out.print("<table>");
					out.print("<thead>");
					out.print("<tr><th>Lunes</th><th>Martes</th><th>Miércoles</th><th>Jueves</th>" +
						"<th>Viernes</th><th>Sábado</th><th>Domingo</th></tr>");
					out.print("</thead>\n<tbody>");
					
					Dieta[][] tabla = Dieta.generaTablaUsuarioDieta(usuarioId); // Generamos la tabla de la dieta del usuario
					
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
					        out.print("<td><a class=\"enlaceTd\" href=\"receta/?id=" + dieta.getIdReceta() + "\">" +
					        	dieta.getNombreReceta() + "</a></td>");
					    }
					    
					    out.print("</tr>");
					}
					
					out.print("</tbody>");
					out.print("</table>");
				}
			}
			
			conexion.close();
		}
	%>
</body>
</html>