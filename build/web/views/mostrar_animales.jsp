<%@page import="java.util.ArrayList"%>
<%@page import="model.AnimalesModel"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Lista de Animales</title>
        <style>
            table {
                width: 80%;
                border-collapse: collapse;
            }
            table, th, td {
                border: 1px solid black;
            }
            th, td {
                padding: 10px;
                text-align: left;
            }
            th {
                background-color: #f2f2f2;
            }
        </style>
        <script>
           function eliminarAnimal(id){
               if (confirm("Estas seguro del eliminar al animal?")) {
                   fetch(`animales?id=`+id,{
                       method: 'DELETE'
                   }).then(response =>{
                       if(response.ok){
                           alert('Animal eliminado exitosamente');
                           location.reload();
                       }else{
                           alert('Error al eliminar el animal');
                       }
                   }).catch(error => console.error('Error', error));
                }
           }
        </script>

    </head>
    <body>
        <h2>Lista de Animales</h2>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Color</th>
                    <th>Especie</th>
                    <th>Tipo de animal</th>
                    <th>Tipo de alimento</th>
                    <th>Peso</th>
                    <th>Habitad</th>
                    <th>Altura</th>
                </tr>
            </thead>
            <tbody>
                <%
                    ArrayList<AnimalesModel> listaAnimales = (ArrayList<AnimalesModel>) request.getAttribute("animales");

                    if (listaAnimales != null && !listaAnimales.isEmpty()) {
                        for (AnimalesModel animal : listaAnimales) {
                %>
                <tr>
                    <td><%= animal.getId()%></td>
                    <td><%= animal.getColor()%></td>
                    <td><%= animal.getEspecie()%></td>
                    <td><%= animal.getAnimal()%></td>
                    <td><%= animal.getAlimento()%></td>
                    <td><%= animal.getPeso()%></td>
                    <td><%= animal.getHabitad()%></td>
                    <td><%= animal.getAltura()%></td>
                    <td> 
                        <button onclick="eliminarAnimal(<%= animal.getId() %>)">Eliminar</button> 
                        
                    </td>

                    <td>
                        <!-- Botón para actualizar, que redirige a actualizarAnimal.jsp con el nombre del animal --> 
                        <form action="${pageContext.request.contextPath}/views/actualizar_animal.jsp" method="GET"> 
                            <input type="hidden" name="id" value="<%= animal.getId()%>"> 
                            <input type="submit" value="Actualizar"> 
                        </form> 
                    </td>

                </tr>
                <%
                    }
                } else {
                %>
                <tr>
                    <td colspan="7">No hay animales registrados.</td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </body>
</html>
