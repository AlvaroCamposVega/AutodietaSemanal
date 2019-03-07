<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
	<%
		session.removeAttribute("adLog");
	
		String mensajeError = "<h3>usuario o contraseña no válidos</h3>";
		String formatoPagina = "\"css/indexError.css\"";
		
		if (session.getAttribute("usuario") != null) { // Si hay una sesión iniciada
		    
		    response.sendRedirect("inicio/");
		}
		
		if (request.getParameter("error") == null) { // Si no hay fallo de autenticación
		    
		    mensajeError = "";
		    formatoPagina = "\"css/index.css\"";
		}
	%>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link href="https://fonts.googleapis.com/css?family=Poppins" rel="stylesheet">
    <link rel="icon" href="img/favicon.png">
    <link rel="stylesheet" href=<%= formatoPagina %>>
    <title>Autodieta Semanal</title>
</head>
<body>
    <div id="wrapper">
        <h1>Iniciar Sesión</h1>
        <form method="POST" action="inicio/">
        	<%= mensajeError %>
            <input name="usuario" type="text" placeholder="Usuario" maxlength="20" required><br>
            <input name="contrasena" type="password" placeholder="Contraseña" maxlength="30" required><br>
            <button type="submit">Entrar</button>
        </form>
        <h2>¿No tienes cuenta? <a href="registro/">¡Registrate!</a></h2>
    </div>
</body>
</html>