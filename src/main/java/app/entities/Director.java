package app.entities;

import jakarta.persistence.*;
import lombok.*;

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
public class Director {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "director_id")
    private int id;

    private int tmdbId;

    @Column(name = "director_name")
    private String name;
    private LocalDate birthDate;

    @OneToMany(mappedBy = "director", cascade = CascadeType.ALL)
    private Set<Movie> movies = new HashSet<>();


}
