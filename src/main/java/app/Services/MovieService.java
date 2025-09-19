package app.Services;

import app.daos.MovieDAO;
import app.entities.Movie;

import java.util.Comparator;
import java.util.List;

public class MovieService {
    private final MovieDAO movieDAO;

    public MovieService(MovieDAO movieDAO){
        this.movieDAO = movieDAO;
    }

    public double getAverageRating(){
        List<Movie> movies = movieDAO.getAll();
        return movies.stream()
                //translating the Movie object to getRating
                .mapToDouble(Movie::getRating)
                .average()
                .orElse(0.0);
    }

    public List<Movie> getTopTenHighestRated(){
        return movieDAO.getAll().stream()
                //From the highest rated to the lowest rated displayed
                .sorted(Comparator.comparingDouble(Movie::getRating).reversed())
                //take the first 10
                .limit(10)
                .toList();
    }

    public List<Movie> getTopTenLowestRated(){
        return movieDAO.getAll().stream()
                //From lowe to high rated
                .sorted(Comparator.comparingDouble(Movie::getRating))
                .limit(10)
                .toList();
    }

    public List<Movie> getTheTenMostPopularMovies(){
        return movieDAO.getAll().stream()
                //From the most popular first to the least last displayed
                .sorted(Comparator.comparingDouble(Movie::getPopularity).reversed())
                .limit(10)
                .toList();

    }
}
