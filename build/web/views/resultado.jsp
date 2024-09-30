<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Resultado del registro</title>
    </head>
    <body>
        <h2>Resultado</h2>
        <p><%= request.getAttribute("mensaje")%></p>
        <a href="/views/registro_animales.jsp">Regresar al formulario</a>
    </body>
</html>
