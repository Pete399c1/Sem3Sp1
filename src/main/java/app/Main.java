package app;

import app.Services.MovieService;
import app.config.HibernateConfig;
import app.daos.MovieDAO;
import jakarta.persistence.EntityManagerFactory;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        MovieDAO movieDAO = new MovieDAO(emf);
        MovieService movieService = new MovieService(movieDAO);

        movieService.getAndSaveMoviesInTheLast2Years();

       movieService.getTopTenHighestRated().forEach(System.out::println);

        emf.close();
    }
}