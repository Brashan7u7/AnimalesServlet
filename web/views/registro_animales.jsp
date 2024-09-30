<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Registro de animales</h1>
         <form action="${pageContext.request.contextPath}/animales" method="POST">
            COLOR <br>
            <input type="text" name="txt_color" max="50"><br>
            ESPECIE <br>
            <input type="text" name="txt_especie" max="100"><br>
            TIPO DE ANIMAL <br>
            <input type="text" name="txt_animal" max="50"><br>
            TIPO DE ALIMENTO <br>
            <input type="text" name="txt_alimento" max="50"><br>
            PESO <br>
            <input type="number" name="txt_peso"><br>
            HABITAD <br>
            <input type="text" name="txt_habitad" max="100"><br>
            ALTURA <br>
            <input type="text" name="txt_altura" max="50"><br>
            <input type="submit" name="accion" value="Agregar">
        </form>
            <a href="${pageContext.request.contextPath}/animales">Cargar Animales</a>
    </body>
</html>
