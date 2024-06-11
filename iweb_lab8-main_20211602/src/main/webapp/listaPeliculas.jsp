

<%@page import="java.util.ArrayList"%>
<%@page import="com.example.pruebalaboratorio1.beans.pelicula"%>
<%@page import="com.example.pruebalaboratorio1.beans.genero"%>
<%@page import="java.text.NumberFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    ArrayList<pelicula> lista = (ArrayList<pelicula>) request.getAttribute("listaPeliculas");
    ArrayList<genero> generos = (ArrayList<genero>) request.getAttribute("generos");
    String selectedGenero = request.getParameter("selectedGenero");
    NumberFormat formatter = NumberFormat.getCurrencyInstance();
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Lista de Películas</title>
</head>
<body>

<h1>Lista de Películas</h1>

<form action="listaPeliculas" method="get">
    <input type="hidden" name="action" value="filtrar">
    <label for="selectedGenero">Selecciona un género:</label>
    <select name="selectedGenero" id="selectedGenero" onchange="this.form.submit()">
        <option value="">Selecciona una opción</option>
        <% for (genero gen : generos) { %>
        <option value="<%= gen.getNombre() %>" <%= (selectedGenero != null && selectedGenero.equals(gen.getNombre())) ? "selected" : "" %>><%= gen.getNombre() %></option>
        <% } %>
    </select>
</form>

<table border="1">
    <tr>
        <th>Título</th>
        <th>Director</th>
        <th>Año de Publicación</th>
        <th>Rating</th>
        <th>Box Office</th>
        <th>Género</th>
        <th>Duración</th>
        <th>Streaming</th>
        <th>Premios Oscar</th>
        <th>Actores</th>
        <th>Borrar</th>
    </tr>
    <%
        for (pelicula movie : lista) {
    %>
    <tr>
        <td><a href="detallePelicula.jsp?idPelicula=<%= movie.getIdPelicula() %>"><%= movie.getTitulo() %></a></td>
        <td><%= movie.getDirector() %></td>
        <td><%= movie.getAnoPublicacion() %></td>
        <td><%= movie.getRating() %>/10</td>
        <td><%= formatter.format(movie.getBoxOffice()) %></td>
        <td><%= movie.getGenero().getNombre() %></td>
        <td><%= movie.getDuracion() %></td>
        <td><%= movie.getStreaming().getNombreServicio() %></td>
        <td><%= movie.isPremioOscar() ? "Sí" : "No" %></td>
        <td><a href="actores.jsp?idPelicula=<%= movie.getIdPelicula() %>">Ver Actores</a></td>
        <td><a href="listaPeliculas?action=borrar&idPelicula=<%= movie.getIdPelicula() %>" onclick="return confirm('¿Está seguro de querer borrar esta película?');">Borrar</a></td>
    </tr>
    <%
        }
    %>
</table>

<a href="crearPelicula?action=crear" style="display: inline-block; padding: 10px 20px; background-color: #007bff; color: #fff; text-decoration: none; border-radius: 5px;">Crear Película</a>

</body>
</html>
