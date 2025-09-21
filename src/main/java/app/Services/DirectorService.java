package app.Services;


import app.daos.DirectorDAO;
import app.daos.MovieDAO;
import app.entities.Director;
import app.entities.Movie;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
public class DirectorService {
 private final DirectorDAO directorDAO;
 private final MovieDAO movieDAO;
 private static final String API_KEY = System.getenv("api_key");

// object mapper to convert JSON from the api to java objects
    private static  final ObjectMapper objectMapper = new ObjectMapper()
        .registerModule( new JavaTimeModule());
}

