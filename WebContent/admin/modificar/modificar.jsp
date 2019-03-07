<%@page import="java.text.DateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.sql.Statement"%>
<%@ page import="java.sql.DriverManager"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.ResultSet"%>
<%@page import="org.apache.commons.codec.digest.DigestUtils"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.sql.Date"%>
<%@ page import="dieta.Dieta"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<link rel="stylesheet" href="../../css/espera.css">
<link href="https://fonts.googleapis.com/css?family=Poppins"
	rel="stylesheet">
<link rel="icon" href="../../img/favicon.png">
<title>Autodieta</title>
</head>
<body>
	<h1>Espere mientras procesamos su acción...</h1>
	<%
		if (request.getParameter("id") == null || request.getParameter("idPrivilegio") == null ||
			request.getParameter("expiraDieta") == null || request.getParameter("nombre")== null ||
			session.getAttribute("adLog") == null) { // Si se intenta acceder por URL
	
			response.sendRedirect("../"); // Lo mandamos a la pantalla de inicio
			
		} else { // Si el acceso es legítimo
		    
		    Class.forName("com.mysql.jdbc.Driver");
		    Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/autodieta", "admin",
		            "nimda12_34$");
		    Statement s = conexion.createStatement();

		    // Recogemos los siguientes datos del formulario modificar
		    String id = request.getParameter("id");
		    String idPrivilegio = request.getParameter("idPrivilegio");
		    String expiraDieta = request.getParameter("expiraDieta");
		    String nombre = request.getParameter("nombre");

		    String nombreHash = DigestUtils.sha256Hex(nombre); // Nombre cifrado

		    // Obtenemos el nombre antes de cambiarlo
		    ResultSet listadoNombreOriginal = s.executeQuery("SELECT Nombre FROM usuario WHERE Id = " + id);
		    String nombreOriginal = "";

		    while (listadoNombreOriginal.next()) {

		        nombreOriginal = listadoNombreOriginal.getString("Nombre");
		    }

		    // Obtenemos la fecha de hoy
		    Calendar cal = Calendar.getInstance();

		    int anyo = cal.get(Calendar.YEAR);
		    int mes = cal.get(Calendar.MONTH);
		    int dia = cal.get(Calendar.DAY_OF_MONTH);

		    cal.clear();

		    cal.set(Calendar.YEAR, anyo);
		    cal.set(Calendar.MONTH, mes);
		    cal.set(Calendar.DATE, dia);

		    // Formateamos la fecha para que tenga el mismo formato que en sql
		    Date hoy = new Date(cal.getTimeInMillis());
		    // Comparamos las fechas
		    int comparaFecha = hoy.compareTo(Date.valueOf(expiraDieta));

		    int usuarios = 0;

		    // Si el nombre original no es igual al que se ha introducido comprobamos si ya existe
		    if (!(nombreOriginal.equals(nombre) || nombreOriginal.equals(nombreHash))) {

		        // Obtenemos cuántos usuarios hay con ese nombre (no puede haber 2 usuarios con el mismo nombre)
		        ResultSet listadoCuentaUsuarios = s.executeQuery("SELECT COUNT(Id) FROM usuario WHERE Nombre = '"
		                + nombre + "' OR " + "Nombre = '" + nombreHash + "'");

		        while (listadoCuentaUsuarios.next()) {

		            usuarios = Integer.parseInt(listadoCuentaUsuarios.getString("COUNT(Id)"));
		        }
		    }

		    if ((comparaFecha != (-1)) && (usuarios > 0)) { // Si los dos están mal enviamos el error 3

		        response.sendRedirect("./?id=" + id + "&error=3");

		    } else if (comparaFecha != (-1)) { // Si la fecha es igual o menor a la de hoy no le dejamos actualizarla

		        response.sendRedirect("./?id=" + id + "&error=2");

		    } else if (usuarios > 0) { // Si ya existe este usuario reenviamos al formulario con un error 1

		        response.sendRedirect("./?id=" + id + "&error=1");

		    } else { // Podemos continuar con la actualización del usuario

		        String updateUsuario = "UPDATE usuario SET ";

		        String contrasena = request.getParameter("contrasena");

		        // Si el privilegio es administrador el nombre que actualizamos va cifrado y debemos eliminarle la dieta
		        if (idPrivilegio.equals("1")) {
					
		            Dieta.eliminaDietaUsuario(id, s);
		            updateUsuario += "Nombre = '" + nombreHash + "', ";

		        } else { // Si no lo actualizamos plano

		            updateUsuario += "Nombre = '" + nombre + "', ";
		        }

		        if (!(contrasena.equals("") || contrasena == null)) { // Si la contraseña no está vacía la actualizamos

		            updateUsuario += "Contrasena = '" + DigestUtils.sha256Hex(contrasena) + "', ";
		        }

		        updateUsuario += "ExpiraDieta = '" + expiraDieta + "', IdPrivilegio = " + idPrivilegio + " WHERE Id = "
		                + id;

		        s.execute(updateUsuario);

		        conexion.close();

		        response.sendRedirect("../");
		    }
		}
	%>
</body>
</html>