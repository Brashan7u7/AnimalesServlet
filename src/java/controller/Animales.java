package controller;

import configuration.ConnectionBD;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.AnimalesModel;


@WebServlet("/animales")
public class Animales extends HttpServlet {

    ConnectionBD conexion = new ConnectionBD();
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Animales</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Animales at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Se ejecuta el doGet");
        List<AnimalesModel> listaAniamales = new ArrayList<>();
        String sql = "Select id, color, especie, tipo_animal, tipo_alimento, peso, habitad, altura from animales";
        try {
            conn = conexion.getConnectionBD();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            //Itera sobre los resultados y crea objetos AnimalesModel
            while (rs.next()) {
                AnimalesModel animales = new AnimalesModel();
                animales.setId(rs.getString("id"));
                animales.setColor(rs.getString("color"));
                animales.setEspecie(rs.getString("especie"));
                animales.setAnimal(rs.getString("tipo_animal"));
                animales.setAlimento(rs.getString("tipo_alimento"));
                animales.setPeso(rs.getDouble("peso"));
                animales.setHabitad(rs.getString("habitad"));
                animales.setAltura(rs.getString("altura"));
                listaAniamales.add(animales);

            }
            //Pasa la lista de animales al JSP
            request.setAttribute("animales", listaAniamales);
            request.getRequestDispatcher("/views/mostrar_animales.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al obtener los usuarios");
        }
    }

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Se ejecuta el doPost");
        //Obtener los datos del formulario 
        String color = request.getParameter("txt_color");
        String especie = request.getParameter("txt_especie");
        String animal = request.getParameter("txt_animal");
        String alimento = request.getParameter("txt_alimento");
        String peso = request.getParameter("txt_peso");
        String habitad = request.getParameter("txt_habitad");
        String altura = request.getParameter("txt_altura");

        double pesoFinal = Double.parseDouble(peso);
        try {
            //Creamos una consulta sql
            String sql = "Insert into animales(color, especie,tipo_animal, tipo_alimento, peso, habitad, altura) VALUES (?, ?, ?, ?, ?, ?, ?)";
            conn = conexion.getConnectionBD();
            ps = conn.prepareStatement(sql);
            ps.setString(1, color);
            ps.setString(2, especie);
            ps.setString(3, animal);
            ps.setString(4, alimento);
            ps.setDouble(5, pesoFinal);
            ps.setString(6, habitad);
            ps.setString(7, altura);

            //Ejecución de la consulta
            int filasInsertadas = ps.executeUpdate();
            if (filasInsertadas > 0) {
                //Si se insertó correctamente, redirigir al usuario a una pagina de exito
                request.setAttribute("mensaje", "Usuario registrado con éxito!!");
                request.getRequestDispatcher("/views/resultado.jsp").forward(request, response);
            } else {
                //Si falló, redirigir a una pagina de error
                request.setAttribute("mensaje", "Error al registrar usuario D:!");
                request.getRequestDispatcher("/views/resultado.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Ocurrió un error: !" + e.getMessage());
            request.getRequestDispatcher("/views/resultado.jsp").forward(request, response);
        } finally {
            //Cerrar los recursos 
            try {
                if (ps != null) {
                    ps.close();
                }
                if (ps != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("SE ESTA EJECUTANDO EL DELETE");
        ConnectionBD conexion = new ConnectionBD();
        String id = request.getParameter("id");

        if (id == null && id.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String sql = "DELETE FROM animales WHERE id like ?";
        try {
            conn = conexion.getConnectionBD();
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    System.out.println("Se ejecuta el doPut para actualizar");
    
    //Leer JSON
    StringBuilder sb = new StringBuilder();
    BufferedReader reader = request.getReader();
    String line;
    while ((line = reader.readLine()) != null) {
        sb.append(line);
    }
    String requestBody = sb.toString();

    // Eliminar llaves y comillas del JSOn
    requestBody = requestBody.replaceAll("[{}\"]", ""); 
    String[] pairs = requestBody.split(",");

    String id = null, color = null, especie = null, tipoAnimal = null, tipoAlimento = null, pesoStr = null, habitad = null, altura = null;

    for (String pair : pairs) {
        String[] keyValue = pair.split(":");
        String key = keyValue[0].trim();
        String value = keyValue[1].trim();

        switch (key) {
            case "id":
                id = value;
                break;
            case "color":
                color = value;
                break;
            case "especie":
                especie = value;
                break;
            case "tipo_animal":
                tipoAnimal = value;
                break;
            case "tipo_alimento":
                tipoAlimento = value;
                break;
            case "peso":
                pesoStr = value;
                break;
            case "habitad":
                habitad = value;
                break;
            case "altura":
                altura = value;
                break;
        }
    }


    if (id == null || id.isEmpty()) {
        System.out.println("El ID es requerido");
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El ID es requerido");
        return;
    }
    
    if (especie == null || especie.isEmpty()) {
        System.out.println("La especie es requerida");
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El campo 'especie' es requerido.");
        return;
    }

    if (tipoAnimal == null || tipoAnimal.isEmpty()) {
        System.out.println("El tipo de animal es requerido");
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El campo 'tipoAnimal' es requerido.");
        return;
    }

    if (tipoAlimento == null || tipoAlimento.isEmpty()) {
        System.out.println("El tipo de alimento es requerido");
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El campo 'tipoAlimento' es requerido.");
        return;
    }

    if (pesoStr == null || pesoStr.isEmpty()) {
        System.out.println("El peso es requerido");
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El campo 'peso' es requerido.");
        return;
    }

    if (habitad == null || habitad.isEmpty()) {
        System.out.println("El hábitat es requerido");
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El campo 'habitad' es requerido.");
        return;
    }

    if (altura == null || altura.isEmpty()) {
        System.out.println("La altura es requerida");
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El campo 'altura' es requerido.");
        return;
    }

    if (color == null || color.isEmpty()) {
        System.out.println("El color es requerido");
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El campo 'color' es requerido.");
        return;
    }


    double peso = 0;
    try {
        peso = Double.parseDouble(pesoStr);
    } catch (NumberFormatException e) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El formato del peso es inválido.");
        return;
    }


    String sql = "UPDATE animales SET color = ?, especie = ?, tipo_animal = ?, tipo_alimento = ?, peso = ?, habitad = ?, altura = ? WHERE id = ?";

    try {
        conn = conexion.getConnectionBD();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, color);
        ps.setString(2, especie);
        ps.setString(3, tipoAnimal);
        ps.setString(4, tipoAlimento);
        ps.setDouble(5, peso);
        ps.setString(6, habitad);
        ps.setString(7, altura);
        ps.setString(8, id); 

        int rowsUpdated = ps.executeUpdate();

        if (rowsUpdated > 0) {
            System.out.println("El animal con ID " + id + " ha sido actualizado exitosamente.");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Animal actualizado exitosamente.");
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "No se encontró el animal con ID: " + id);
        }

    } catch (Exception e) {
        e.printStackTrace();
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al actualizar el animal.");
    }
}


    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
