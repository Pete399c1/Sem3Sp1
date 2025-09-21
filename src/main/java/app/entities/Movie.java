package app.entities;

import app.dtos.MovieDTO;
import jakarta.persistence.*;
import lombok.*;

import java.security.DrbgParameters;
import java.security.PrivateKey;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private int id;

    private int tmdbId;
    private String title;

    @Column(columnDefinition = "TEXT")
    private String overview;
    private LocalDate releaseDate;
    private double rating;
    private double popularity;

    // mapping dto to entity by constructor
    public Movie(MovieDTO movieDTO) {
        this.tmdbId = movieDTO.getId();
        this.title = movieDTO.getTitle();
        this.overview = movieDTO.getOverview();
        this.releaseDate = movieDTO.getReleaseDate();
        this.rating = movieDTO.getRating();
        this.popularity = movieDTO.getPopularity();
    }



    //movie owns the relation
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @ToString.Exclude
    private Set<Actor> actors = new HashSet<>();

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "director_id")
    private Director director;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @ToString.Exclude
    private Set<Genre> genres = new HashSet<>();



    // update both sides
    public void addActor(Actor actor){
        this.actors.add(actor);
        if (actor != null){
            actor.getMovies().add(this);
        }
    }


    public void addGenre(Genre genre){
        this.genres.add(genre);
        if(genre != null){
            genre.getMovies().add(this);
        }
    }

    // bi-directional link
    public void setDirector(Director director){
        this.director = director;
        if (director != null){
            director.getMovies().add(this);
        }
    }
}
