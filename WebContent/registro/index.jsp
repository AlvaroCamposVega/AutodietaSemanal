<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
	<%
		session.removeAttribute("adLog");
	
		String mensajeError = "<h3>usuario no disponible</h3>";
		String formatoPagina = "\"../css/registroError.css\"";
		
		if (request.getParameter("error") == null) {
		    
		    mensajeError = "";
		    formatoPagina = "\"../css/registro.css\"";
		}
		
		if (session.getAttribute("usuario") != null) { // Si hay una sesión iniciada no se puede acceder
		    
		    response.sendRedirect("../inicio");
		}
	%>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link href="https://fonts.googleapis.com/css?family=Poppins" rel="stylesheet">
    <link rel="icon" href="../img/favicon.png">
    <link rel="stylesheet" href=<%= formatoPagina %>>
    <title>Autodieta</title>
</head>
<body>
	<header></header>
    <nav>
        <ul>
            <li>
                <a href="#">Inicio</a>
            </li>
            <li>
                <a href="../">Iniciar Sesión</a>
            </li>
            <li>
                <a id="activo" href="#">Registrarse</a>
            </li>
        </ul>
    </nav>
    <section id="s1">
        <h1 class="tituloCentrado">Registrarse</h1>
        <form method="POST" action="registro.jsp">
        	<%= mensajeError %>
            <input id="usuario" name="usuario" type="text" placeholder="Usuario" maxlength="20" required><br>
            <input name="contrasena" type="password" placeholder="Contraseña" maxlength="30" required><br>
            <button type="submit">Enviar</button>
        </form>
    </section>
    <footer>
        <div>
            <a>GitHub</a><br>
            <a>LinkedIn</a><br>
            <a>acvbusiness@gmail.com</a>
        </div>
    </footer>
</body>
</html>