package app.entities;

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
    private String overview;
    private LocalDate releaseDate;
    private double rating;
    private double popularity;

    //movie owns the relation
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Actor> actors = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "director_id")
    private Director director;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
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
