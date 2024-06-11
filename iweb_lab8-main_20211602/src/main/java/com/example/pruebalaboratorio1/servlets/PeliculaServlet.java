package com.example.pruebalaboratorio1.servlets;

import com.example.pruebalaboratorio1.beans.genero;
import com.example.pruebalaboratorio1.beans.pelicula;
import com.example.pruebalaboratorio1.beans.streaming;
import com.example.pruebalaboratorio1.daos.peliculaDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "pelicula-servlet", value = {"/listaPeliculas", "/crearPelicula"})
public class PeliculaServlet extends HttpServlet {

    private peliculaDao peliculaDao = new peliculaDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "listar";
        }

        switch (action) {
            case "listar":
                listarPeliculas(request, response);
                break;
            case "filtrar":
                filtrarPeliculas(request, response);
                break;
            case "crear":
                mostrarFormularioCreacion(request, response);
                break;
            case "borrar":
                borrarPelicula(request, response);
                break;
            default:
                listarPeliculas(request, response);
                break;
        }
    }

    private void listarPeliculas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<genero> generos = peliculaDao.listarGeneros();
        request.setAttribute("generos", generos);

        ArrayList<pelicula> peliculas = peliculaDao.listarPeliculas();
        request.setAttribute("listaPeliculas", peliculas);

        RequestDispatcher view = request.getRequestDispatcher("listaPeliculas.jsp");
        view.forward(request, response);
    }

    private void filtrarPeliculas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<genero> generos = peliculaDao.listarGeneros();
        request.setAttribute("generos", generos);

        String selectedGenero = request.getParameter("selectedGenero");
        ArrayList<pelicula> peliculas;
        if (selectedGenero != null && !selectedGenero.isEmpty()) {
            peliculas = peliculaDao.listarPeliculasPorGenero(selectedGenero);
        } else {
            peliculas = peliculaDao.listarPeliculas();
        }
        request.setAttribute("listaPeliculas", peliculas);

        RequestDispatcher view = request.getRequestDispatcher("listaPeliculas.jsp");
        view.forward(request, response);
    }

    private void mostrarFormularioCreacion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<genero> generos = peliculaDao.listarGeneros();
        ArrayList<streaming> streamings = peliculaDao.listarStreamings();
        request.setAttribute("generos", generos);
        request.setAttribute("streamings", streamings);
        RequestDispatcher view = request.getRequestDispatcher("crearPelicula.jsp");
        view.forward(request, response);
    }

    private void borrarPelicula(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idPelicula = Integer.parseInt(request.getParameter("idPelicula"));
        peliculaDao.borrarPelicula(idPelicula);
        response.sendRedirect(request.getContextPath() + "/listaPeliculas?action=listar");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "listar";
        }

        switch (action) {
            case "crear":
                crearPelicula(request, response);
                break;
            default:
                listarPeliculas(request, response);
                break;
        }
    }

    private void crearPelicula(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String titulo = request.getParameter("titulo");
        String director = request.getParameter("director");
        int anoPublicacion = Integer.parseInt(request.getParameter("anoPublicacion"));
        double rating = Double.parseDouble(request.getParameter("rating"));
        double boxOffice = Double.parseDouble(request.getParameter("boxOffice"));
        String duracion = request.getParameter("duracion");
        int generoId = Integer.parseInt(request.getParameter("generoId"));
        int streamingId = Integer.parseInt(request.getParameter("streamingId"));
        boolean premioOscar = Boolean.parseBoolean(request.getParameter("premioOscar"));

        pelicula nuevaPelicula = new pelicula();
        nuevaPelicula.setTitulo(titulo);
        nuevaPelicula.setDirector(director);
        nuevaPelicula.setAnoPublicacion(anoPublicacion);
        nuevaPelicula.setRating(rating);
        nuevaPelicula.setBoxOffice(boxOffice);
        nuevaPelicula.setDuracion(duracion);

        genero generoObj = new genero();
        generoObj.setIdGenero(generoId);
        nuevaPelicula.setGenero(generoObj);

        streaming streamingObj = new streaming();
        streamingObj.setId(streamingId);
        nuevaPelicula.setStreaming(streamingObj);

        nuevaPelicula.setPremioOscar(premioOscar);

        peliculaDao.crearPelicula(nuevaPelicula);
        response.sendRedirect(request.getContextPath() + "/listaPeliculas?action=listar");
    }
}



