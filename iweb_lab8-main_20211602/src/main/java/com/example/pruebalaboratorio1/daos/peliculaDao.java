package com.example.pruebalaboratorio1.daos;

import com.example.pruebalaboratorio1.beans.pelicula;
import com.example.pruebalaboratorio1.beans.genero;
import com.example.pruebalaboratorio1.beans.streaming;

import java.sql.*;
import java.util.ArrayList;

public class peliculaDao extends baseDao {

    public ArrayList<pelicula> listarPeliculas() {
        ArrayList<pelicula> listaPeliculas = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            String sql = "SELECT P.*, G.nombre AS nombreGenero, S.nombreservicio FROM pelicula P " +
                    "JOIN genero G ON P.idGenero = G.idGenero " +
                    "JOIN streaming S ON P.idStreaming = S.idStreaming " +
                    "ORDER BY P.rating DESC, P.boxOffice DESC";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                listaPeliculas.add(extractPeliculaFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaPeliculas;
    }
    public void crearPelicula(pelicula movie) {
        String sql = "INSERT INTO pelicula (titulo, director, anoPublicacion, rating, boxOffice, duracion, idGenero, idStreaming, premioOscar) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, movie.getTitulo());
            stmt.setString(2, movie.getDirector());
            stmt.setInt(3, movie.getAnoPublicacion());
            stmt.setDouble(4, movie.getRating());
            stmt.setDouble(5, movie.getBoxOffice());
            stmt.setString(6, movie.getDuracion());
            stmt.setInt(7, movie.getGenero().getIdGenero());
            stmt.setInt(8, movie.getStreaming().getId());
            stmt.setBoolean(9, movie.isPremioOscar());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private pelicula extractPeliculaFromResultSet(ResultSet rs) throws SQLException {
        pelicula movie = new pelicula();
        movie.setIdPelicula(rs.getInt("idPelicula"));
        movie.setTitulo(rs.getString("titulo"));
        movie.setDirector(rs.getString("director"));
        movie.setAnoPublicacion(rs.getInt("anoPublicacion"));
        movie.setRating(rs.getDouble("rating"));
        movie.setBoxOffice(rs.getDouble("boxOffice"));
        movie.setDuracion(rs.getString("duracion"));
        movie.setPremioOscar(rs.getBoolean("premioOscar"));

        genero generoObj = new genero();
        generoObj.setNombre(rs.getString("nombreGenero"));
        movie.setGenero(generoObj);

        streaming streamingObj = new streaming();
        streamingObj.setNombreServicio(rs.getString("nombreservicio"));
        movie.setStreaming(streamingObj);

        return movie;
    }

    public ArrayList<genero> listarGeneros() {
        ArrayList<genero> generos = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM genero ORDER BY nombre");
            while (rs.next()) {
                genero gen = new genero();
                gen.setIdGenero(rs.getInt("idGenero"));
                gen.setNombre(rs.getString("nombre"));
                generos.add(gen);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return generos;
    }
    public ArrayList<pelicula> listarPeliculasPorGenero(String generoNombre) {
        ArrayList<pelicula> listaPeliculas = new ArrayList<>();
        String sql = "SELECT P.*, G.nombre AS nombreGenero, S.nombreservicio FROM pelicula P " +
                "JOIN genero G ON P.idGenero = G.idGenero " +
                "JOIN streaming S ON P.idStreaming = S.idStreaming " +
                "WHERE G.nombre = ? ORDER BY P.rating DESC, P.boxOffice DESC";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, generoNombre);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                listaPeliculas.add(extractPeliculaFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaPeliculas;
    }
    public ArrayList<streaming> listarStreamings() {
        ArrayList<streaming> streamings = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM streaming ORDER BY nombreServicio");
            while (rs.next()) {
                streaming str = new streaming();
                str.setId(rs.getInt("idStreaming"));
                str.setNombreServicio(rs.getString("nombreServicio"));
                streamings.add(str);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return streamings;
    }
    public void borrarPelicula(int idPelicula) {
        String sql = "DELETE FROM pelicula WHERE idPelicula = ? AND duracion > 180 AND premioOscar = false";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPelicula);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
