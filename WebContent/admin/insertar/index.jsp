<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="biblio.BiblioUtilidades" %>
<%@ page import="dieta.Dieta" %>
<%@page import="org.apache.commons.codec.digest.DigestUtils"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
	<link rel="stylesheet" href="../../css/espera.css">
    <link href="https://fonts.googleapis.com/css?family=Poppins" rel="stylesheet">
    <link rel="icon" href="../../img/favicon.png">
    <title>Autodieta</title>
</head>
<body>
	<h1>Espere mientras procesamos su acción...</h1>
	<%
		if (request.getParameter("nombre") == null || request.getParameter("contrasena") == null ||
			request.getParameter("idPrivilegio") == null || request.getParameter("expiraDieta") == null ||
			session.getAttribute("adLog") == null) { // Si se intenta acceder por URL o no es administrador
		   	
			response.sendRedirect("../"); // Lo mandamos a la pantalla de admin
		    	
	    } else { // Si el acceso es legítimo
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/autodieta","admin", "nimda12_34$");
			Statement s = conexion.createStatement();

			String nombre = request.getParameter("nombre");
			String nombreHash = DigestUtils.sha256Hex(nombre);
			String contrasena = DigestUtils.sha256Hex(request.getParameter("contrasena"));
			String idPrivilegio = request.getParameter("idPrivilegio");
			String expiraDieta = request.getParameter("expiraDieta");
			
			// Comprobamos si existe ya algún usuario con ese nombre en el sistema
			ResultSet listadoCuentaUsuarios = s.executeQuery("SELECT COUNT(Id) FROM usuario WHERE Nombre = '" +
				nombre + "' OR Nombre = '" + nombreHash + "'");
			
			int existeUsuario = 0;
			
			while (listadoCuentaUsuarios.next()) {
			    
			    existeUsuario = Integer.parseInt(listadoCuentaUsuarios.getString("COUNT(Id)"));
			}
			
			boolean fechaValida = BiblioUtilidades.compruebaFecha(expiraDieta);
			
			if (existeUsuario > 0 && !fechaValida) { // Si los dos están mal error 3
			    
			    response.sendRedirect("../?error=3");
			    
			} else if (existeUsuario > 0) { // Si el usuario está mal error 1
			    
			    response.sendRedirect("../?error=1");
			    
			} else if (!fechaValida) { // Si la fecha está mal error 2
			    
			    response.sendRedirect("../?error=2");
			    
			} else { // Si todo está bien realizamos el insert
			    
			    String insertUsuario = "INSERT INTO usuario VALUES(";
			    int id = BiblioUtilidades.obtenerNuevaIdUsuario();
			    boolean generaDieta = true;
			    insertUsuario += id + ", " + idPrivilegio + ", '";
			    
			    if (idPrivilegio.equals("1")) { // Si el privilegio es administrador el nombre va cifrado
			        
			        insertUsuario += nombreHash + "', '" + contrasena + "', '" + expiraDieta + "'";
			    	generaDieta = false;
			        
			    } else {
			        
			        insertUsuario += nombre + "', '" + contrasena + "', '" + expiraDieta + "')";
			    }
			    
			    s.execute(insertUsuario); // Añadimos el usuario al sistema
			    
			    if (generaDieta) {
			    
				    // GENERACIÓN DIETA PARA EL NUEVO USUARIO
				    
				    ResultSet listaIdRecetas = s.executeQuery("SELECT Id FROM receta");
					ArrayList<String> idRecetas = new ArrayList<String>();
				
			        while (listaIdRecetas.next()) { // Volcamos todas las recetas disponibles en la lista
			            
			            idRecetas.add(listaIdRecetas.getString("Id"));
			        }
			        
			        ArrayList<Dieta> dietaUsuario = Dieta.generaDietaUsuario(idRecetas); // Generamos la dieta del usuario
			        String dietaUsuarioInsert = "INSERT INTO dieta (IdUsuario, IdReceta, IdHora, Dia) VALUES ";
			        
			        for (int i = 0; i < 28; i++) { // Creamos la variable para insertar los datos en la BBDD
			            
			            if (i == 0) {
			                
			                dietaUsuarioInsert += "('" + id + "', '" + dietaUsuario.get(i).getIdReceta() + "', '"
			                	+ dietaUsuario.get(i).getIdHora() + "', '" + dietaUsuario.get(i).getDia() + "')";
			                
			            } else {
			                
			                dietaUsuarioInsert += ", ('" + id + "', '" + dietaUsuario.get(i).getIdReceta() + "', '"
				                	+ dietaUsuario.get(i).getIdHora() + "', '" + dietaUsuario.get(i).getDia() + "')";
			            }
			        }
			        
			        s.execute(dietaUsuarioInsert); // Realizamos la inserción
			    }

				conexion.close();
				
				response.sendRedirect("../");
			}
	    }
	%>
</body>
</html>