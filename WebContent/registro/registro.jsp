<%@page import="org.apache.commons.codec.digest.DigestUtils"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.sql.Date" %>
<%@ page import="dieta.Dieta" %>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="icon" href="../img/favicon.png">
    <link rel="stylesheet" href="../css/espera.css">
    <title>Autodieta</title>
</head>
<body>
	<h1>Espere mientras procesamos su acción...</h1>
	<%
		session.removeAttribute("adLog");
	
		if (request.getParameter("usuario") == null || request.getParameter("contrasena") == null) { // Si se intenta acceder por URL
		    	
			response.sendRedirect("../"); // Lo mandamos a la pantalla de inicio
		    	
	    } else { // Si el acceso es legítimo
	        
	        Class.forName("com.mysql.jdbc.Driver");
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/autodieta","admin", "nimda12_34$");
			Statement s = conexion.createStatement();
			
			String usuario = request.getParameter("usuario");
			int numeroUsuarios = 0;

	        ResultSet listaUsuario = s.executeQuery("SELECT COUNT(Id) FROM usuario WHERE Nombre = '" + usuario + "' OR " +
	        	"Nombre = '" + DigestUtils.sha256Hex(usuario) + "'");
			
	        while (listaUsuario.next()) { // Consultamos cuántos usuarios hay con ese nombre
	            
	            numeroUsuarios = Integer.parseInt(listaUsuario.getString("COUNT(Id)"));
	        }
	        
	        if (numeroUsuarios > 0) { // Si existe algún usuario con ese mismo nombre, se devuelve al registro
	            
	            response.sendRedirect("./?error=true");
	            
	        } else { // Si el nombre de usuario está disponible lo creamos
	            
	            String contrasena = request.getParameter("contrasena");
		        contrasena = DigestUtils.sha256Hex(contrasena);
		        ResultSet listadoIds = s.executeQuery("SELECT Id FROM usuario");
		        
		        int idUsuario = 1;
		        int idListado;
		        
		        /*
		        	Consultamos las Ids almacenadas para averiguar cuál es la última y así configurarle
		        	una nueva al nuevo usuario
		        */
		        while (listadoIds.next()) { // Consultamos las Ids de los usuarios almacenados
		            
		            idListado = Integer.parseInt(listadoIds.getString("ID"));
		            
		            if (idUsuario == idListado) { // Vamos incrementando la Id para el nuevo usuario
		                
		                idUsuario++;
		            }
		        }
		        
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
		        
				String insertar = "INSERT INTO usuario (Id, IdPrivilegio, Nombre, Contrasena, ExpiraDieta) VALUES ('";
				insertar += idUsuario + "', 2, '" + usuario + "', '" + contrasena + "', '" + fechaSql + "')";
				
				s.execute(insertar);
				
				// GENERACIÓN DE LA DIETA
				
				ResultSet listaIdRecetas = s.executeQuery("SELECT Id FROM receta");
				ArrayList<String> idRecetas = new ArrayList<String>();
			
		        while (listaIdRecetas.next()) { // Volcamos todas las recetas disponibles en la lista
		            
		            idRecetas.add(listaIdRecetas.getString("Id"));
		        }
		        
		        ArrayList<Dieta> dietaUsuario = Dieta.generaDietaUsuario(idRecetas); // Generamos la dieta del usuario
		        String dietaUsuarioInsert = "INSERT INTO dieta (IdUsuario, IdReceta, IdHora, Dia) VALUES ";
		        
		        for (int i = 0; i < 28; i++) { // Creamos la variable para insertar los datos en la BBDD
		            
		            if (i == 0) {
		                
		                dietaUsuarioInsert += "('" + idUsuario + "', '" + dietaUsuario.get(i).getIdReceta() + "', '"
		                	+ dietaUsuario.get(i).getIdHora() + "', '" + dietaUsuario.get(i).getDia() + "')";
		                
		            } else {
		                
		                dietaUsuarioInsert += ", ('" + idUsuario + "', '" + dietaUsuario.get(i).getIdReceta() + "', '"
			                	+ dietaUsuario.get(i).getIdHora() + "', '" + dietaUsuario.get(i).getDia() + "')";
		            }
		        }
		        
		        s.execute(dietaUsuarioInsert); // Realizamos la inserción
		        
		        conexion.close();
				
				response.sendRedirect("../");
	        }
	    }
	%>
</body>
</html>