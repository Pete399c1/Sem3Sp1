package app.Services;

import app.daos.MovieDAO;
import app.dtos.MovieDTO;
import app.entities.Movie;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


import javax.swing.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.*;

public class MovieService {
    private final MovieDAO movieDAO;
    private static final String URL_DISCOVER = "https://api.themoviedb.org/3/discover/movie";
    private static final String API_KEY = System.getenv("api_key");


    public MovieService(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    public void getAndSaveMoviesInTheLast2Years() throws IOException, InterruptedException{
        // starting on page 1
        int currentPage = 1;
        // we do not know how many pages will be so setting it to one and so the while loop will run at least once
        int totalPages = 1;

        //saving the date of today
        LocalDate nowToday = LocalDate.now();
        //saving the date 2 years back from today
        LocalDate twoYearsAgo = nowToday.minusYears(2);

        // looping through all the pages
        while(currentPage <= totalPages){
            /*
            building kode where is the movie from it is danish and from the country Denmark
            from/to release date gte/from and lte/to
            witch pages do we get
             */
            String encodedURL = String.format("%s?api_key=%s&language=da&with_original_language=da" +
                    "&primary_release_date.gte=%s&primary_release_date.lte=%s&page=%d",
                    URL_DISCOVER, API_KEY, twoYearsAgo,nowToday, currentPage);



            HttpClient client= HttpClient.newHttpClient();

            // the get request
            HttpRequest request = HttpRequest.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .uri(URI.create(encodedURL))
                    .GET()
                    .build();

            // send request and wait for answer
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            //check did we get the correct answer status code from the api
            if(response.statusCode() == HttpURLConnection.HTTP_OK){
                // converting the JSON answer to the tree JSONNode
                JsonNode root = objectMapper.readTree(response.body());

                // read the total_pages so that we know how many pages that exist
                totalPages  = root.get("total_pages").asInt();

                // get the result Array from JSON that has the movies on the page
                JsonNode results = root.get("results");

                // map JSON Array to the dto class (MovieDTO[])
                MovieDTO[] pageMovies = objectMapper.readValue(results.toString(),MovieDTO[].class);

                // for each movie on this page
                for(MovieDTO movieDTO: pageMovies){

                    // is the movie existing already
                    boolean exist = movieDAO.getByTitle(movieDTO.getTitle())
                            .stream()
                            // avoid duplicates by checking the matches with tmdbId
                            .anyMatch(m -> m.getTmdbId() == movieDTO.getId());

                    // if it dont exist create and save movie
                    if(!exist){
                        Movie movie = new Movie(movieDTO);
                        movieDAO.create(movie);
                    }
                }

            }else{
                throw  new IOException("It failed tying to get the data from the api");
            }
            // going through the pages
            currentPage++;
        }
    }

    public double getAverageRating() {
        List<Movie> movies = movieDAO.getAll();
        return movies.stream()
                //translating the Movie object to getRating
                .mapToDouble(Movie::getRating)
                .average()
                .orElse(0.0);
    }

    public List<Movie> getTopTenHighestRated() {
        return movieDAO.getAll().stream()
                //From the highest rated to the lowest rated displayed
                .sorted(Comparator.comparingDouble(Movie::getRating).reversed())
                //take the first 10
                .limit(10)
                .toList();
    }

    public List<Movie> getTopTenLowestRated() {
        return movieDAO.getAll().stream()
                //From lowe to high rated
                .sorted(Comparator.comparingDouble(Movie::getRating))
                .limit(10)
                .toList();
    }

    public List<Movie> getTheTenMostPopularMovies() {
        return movieDAO.getAll().stream()
                //From the most popular first to the least last displayed
                .sorted(Comparator.comparingDouble(Movie::getPopularity).reversed())
                .limit(10)
                .toList();

    }
}

