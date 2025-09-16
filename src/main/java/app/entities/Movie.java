package app.entities;

import jakarta.persistence.*;
import lombok.*;

import java.security.PrivateKey;
import java.time.LocalDate;
import java.util.List;

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
    private int id;

    private String title;
    private String overview;
    private LocalDate releaseDate;
    private double rating;
    private double popularity;

    //movie owns the relation
    @ManyToMany(mappedBy = "movies")
    private List<Actor> actors;

    @ManyToOne
    private Director director;

    @ManyToMany(mappedBy = "movies")
    private List<Genre> genres;

}
