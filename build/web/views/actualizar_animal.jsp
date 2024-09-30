<%-- 
    Document   : actualizar_animal
    Created on : 26/09/2024, 11:45:14 AM
    Author     : BRASHAN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Connection, java.sql.DriverManager, java.sql.PreparedStatement, java.sql.ResultSet, configuration.ConnectionBD" %> 

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ACTUALIZAR ANIMAL</title>
    </head>
    <body>
        <h2>ACTUALIZA TU ANIMAL</h2>

        <%
            String id = request.getParameter("id");
            String color = "";
            String especie = "";
            String animal = "";
            String alimento = "";
            double peso = 0;
            String habitad = "";
            String altura = "";

            ConnectionBD conexion = new ConnectionBD();
            Connection connection = conexion.getConnectionBD();
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            try {

                // Consulta para obtener los datos del usuario por ID 
                String sql = "SELECT altura, color, especie, habitad, peso, tipo_alimento, tipo_animal FROM animales WHERE id LIKE ?";
                statement = connection.prepareStatement(sql);
                statement.setString(1, id);
                resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    color = resultSet.getString("color");
                    especie = resultSet.getString("especie");
                    animal = resultSet.getString("tipo_animal");
                    alimento = resultSet.getString("tipo_alimento");
                    habitad = resultSet.getString("habitad");
                    altura = resultSet.getString("altura");
                    peso = resultSet.getDouble("peso");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (resultSet != null) {
                        resultSet.close();
                    }
                    if (statement != null) {
                        statement.close();
                    }
                    if (connection != null) {
                        connection.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        %> 

        <form id="formActualizarAnimal">
            <input type="hidden" id="txt_id" value="<%= id%>">
            <p>ID recibido: <%= id%></p>
            COLOR <br>
            <input type="text" id="txt_color" max="50" value="<%= color%>"><br>
            ESPECIE <br>
            <input type="text" id="txt_especie" max="100" value="<%= especie%>"><br>
            TIPO DE ANIMAL <br>
            <input type="text" id="txt_animal" max="50" value="<%= animal%>"><br>
            TIPO DE ALIMENTO <br>
            <input type="text" id="txt_alimento" max="50" value="<%= alimento%>"><br>
            PESO <br>
            <input type="number" id="txt_peso" value="<%= peso%>"><br>
            HABITAD <br>
            <input type="text" id="txt_habitad" max="100" value="<%= habitad%>"><br>
            ALTURA <br>
            <input type="text" id="txt_altura" max="50" value="<%= altura%>"><br>
            <input type="button" value="Actualizar" onclick="actualizarAnimal()">
        </form>

        <div id="resultado"></div> 


        <script>
            function actualizarAnimal() {
                const id = document.getElementById("txt_id").value;
                const color = document.getElementById("txt_color").value.trim();
                const especie = document.getElementById("txt_especie").value.trim();
                const animal = document.getElementById("txt_animal").value.trim();
                const alimento = document.getElementById("txt_alimento").value.trim();
                const peso = document.getElementById("txt_peso").value.trim();
                const habitad = document.getElementById("txt_habitad").value.trim();
                const altura = document.getElementById("txt_altura").value.trim();




                const datos = {
                    id: id,
                    color: color,
                    especie: especie,
                    tipo_animal: animal,
                    tipo_alimento: alimento,
                    peso: peso,
                    habitad: habitad,
                    altura: altura
                };

                console.log("Datosaaaa URLSearchParams:");
                console.log(datos.toString());


                console.log("Iniciando fetch...");
                console.log("Datos:", JSON.stringify(datos));
                fetch(`${pageContext.request.contextPath}/animales?id=` + id, {
                    method: "PUT",
                    body: JSON.stringify(datos),
                    headers: {
                        'Content-Type': 'application/json; charset=UTF-8'
                    }
                })
                        .then(response => {
                            if (response.ok) {
                                return response.text();
                            } else {
                                throw new Error('Error en la actualización');
                            }
                        })
                        .then(data => {
                            console.log(data);
                            document.getElementById("resultado").innerText = "Animal actualizado exitosamente";
                        })
                        .catch(error => {
                            document.getElementById("resultado").innerText = "Error al actualizar su animal.";
                            console.error('Error:', error);
                        });

            }


        </script> 
    </body>
</html>
