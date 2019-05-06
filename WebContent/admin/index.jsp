<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.sql.Date" %>
<!DOCTYPE html>
<html lang="es">
<head>
	<%
		String formatoPagina = "\"../static/css/adminError3.css\"";
		String htmlError = "<div id=\"capaError\"><h1 id=\"textoError\">Nombre de usuario no disponible<br>La fecha debe ser superior a la de hoy" +
		        "</h1><button class=\"can\" onclick=\"ocultar('oscurecer', 'capaError')\">Cerrar</button</div>";
	
		if (request.getParameter("error") == null) {
		    
		    formatoPagina = "\"../static/css/admin.css\"";
		    htmlError = "";
		    
		} else if (Integer.parseInt(request.getParameter("error")) == 1) {
		    
		    formatoPagina = "\"../static/css/adminError1.css\"";
		    htmlError = "<div id=\"capaError\"><h1 id=\"textoError\">Nombre de usuario no disponible</h1>" +
				"</h1><button class=\"can\" onclick=\"ocultar('oscurecer', 'capaError')\">Cerrar</button</div>";;
		    
		} else if (Integer.parseInt(request.getParameter("error")) == 2) {
		    
		    formatoPagina = "\"../static/css/adminError2.css\"";
		    htmlError = "<div id=\"capaError\"><h1 id=\"textoError\">La fecha debe ser superior a la de hoy</h1>" +
				"</h1><button class=\"can\" onclick=\"ocultar('oscurecer', 'capaError')\">Cerrar</button</div>";
		}
	%>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
	<link rel="stylesheet" href=<%= formatoPagina %>>
    <link href="https://fonts.googleapis.com/css?family=Poppins" rel="stylesheet">
    <link rel="icon" href="../static/img/favicon.png">
    <script src="../static/js/script.js" type="text/javascript"></script>
    <title>Autodieta</title>
</head>
<body>
	<%
		if (session.getAttribute("adLog") == null) { // Si se intenta acceder por URL (comprobando la sesión)
		    	
			response.sendRedirect("../"); // Lo mandamos a la pantalla de inicio
		    	
	    } else { // Si el acceso es legítimo
	        
	    	session.removeAttribute("usuario");

			Class.forName("com.mysql.jdbc.Driver");
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/autodieta","admin", "nimda12_34$");
			Statement s = conexion.createStatement();
			ResultSet listadoPrivilegios = s.executeQuery("SELECT * FROM Privilegio");
			
			String usuario = session.getAttribute("adLog").toString();
			
			// Pintamos la barra de navegación
	    	out.print("<nav><ul><li><a id=\"activo\" href=\"#\">Inicio</a></li>");
	    	out.print("<li><a href=\"../logout/\">Cerrar Sesión</a></li></ul>");
	    	out.print("<div><span>" + usuario + "<img id=\"imgFresa2\" src=\"../static/img/fresa.png\"></span></div></nav>");
			
			// FORMULARIO AÑADIR USUARIO ---------------------------
			out.print("<div id=\"wrapper\">");
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
	    	
	    	while (listadoPrivilegios.next()) { // Generamos las etiquetas option con los privilegios
	    	    
	    	    String idPrivilegio = listadoPrivilegios.getString("Id");
	    	    String privilegio = listadoPrivilegios.getString("Privilegio").substring(0, 1).toUpperCase() +
	    	            listadoPrivilegios.getString("Privilegio").substring(1);
	    	    
	    	    if (privilegio.equals("Usuario")) {
	    	        
	    	        out.print("<option value=\"" + idPrivilegio + "\" selected>" + privilegio + "</option>");
	    	        
	    	    } else {
	    	        
	    	        out.print("<option value=\"" + idPrivilegio + "\">" + privilegio + "</option>");
	    	    }
	    	}
	    	
	    	out.print("</select>");
	    	out.print("<button type=\"submit\" class=\"any\">Añadir</button>");
	    	out.print("</form>");
	    	out.print("</div>");
			
	    	// GENERACIÓN TABLA DE USUARIOS ---------------------------
			Statement s1 = conexion.createStatement();
			ResultSet listadoPrivilegioUsuario = s1.executeQuery("SELECT Id, Privilegio FROM privilegio WHERE Privilegio != 'admin'");
			
			out.print("<table>");
			out.print("<thead>");
			out.print("<tr class=\"stripped\"><th>Nombre</th><th>Fecha Expiración Dieta</th>" +
				"<th>Tipo Usuario</th><th></th><th></th></tr>");
			out.print("</thead>\n<tbody>");
			
			boolean stripped = false;
			
			while (listadoPrivilegioUsuario.next()) {
			    
			    String idPrivilegio = listadoPrivilegioUsuario.getString("Id");
			    String privilegio = listadoPrivilegioUsuario.getString("Privilegio").substring(0, 1).toUpperCase() +
	    	            listadoPrivilegioUsuario.getString("Privilegio").substring(1);
			    ResultSet listadoUsuarios = s.executeQuery("SELECT Id, Nombre, ExpiraDieta FROM usuario WHERE IdPrivilegio = "
						+ idPrivilegio);
				
				while (listadoUsuarios.next()) {
				    
				    if (stripped) { // Decoración en zebra de la tabla
				        
				        out.print("<tr" + " class=\"stripped\"" + ">");
				        stripped = false;
				        
				    } else {
				        
				        out.print("<tr>");
				        stripped = true;
				    }
				    
				    out.print("<td>" + listadoUsuarios.getString("Nombre") + "</td>");
				    out.print("<td>" + listadoUsuarios.getString("ExpiraDieta") + "</td>");
				    out.print("<td>" + privilegio + "</td>");
					out.println("<td><a href='modificar/?id=" + listadoUsuarios.getString("Id") +
					        "'><button type='submit' class='mod'>" + "Modificar</button></td></a>");
					out.println("<td><button onclick=\"eliminar(this);\" type='button' class='eli'>Eliminar</button></td>");
				    out.print("</tr>");
				}
			}
			
			out.print("</tbody>");
			out.print("</table>");
			
			conexion.close();
	    }
	%>
    </div>
    <div id="oscurecer"></div>
    <div id="formulario">
    	<h1>Se eliminará al usuario</h1>
    	<h2></h2>
		<form method="POST" action="eliminar/">
			<input name="id" id="idInput" type="text" readonly="readonly"><br>
			<input id="aceptar" class="inputButton" type="submit" value="Aceptar">
			<input onclick="ocultar('oscurecer', 'formulario');" id="cancelar" class="inputButton" type="button" value="Cancelar">
		</form>
	</div>
	<%= htmlError %>
</body>
</html>