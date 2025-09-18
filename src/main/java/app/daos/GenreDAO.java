package app.daos;

import app.entities.Genre;
import app.entities.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;

import java.util.List;


@AllArgsConstructor
public class GenreDAO implements IDAO<Genre> {
    private final EntityManagerFactory emf;

    @Override
    public Genre create(Genre genre){
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(genre);
            em.getTransaction().commit();
        }
        return genre;
    }

    @Override
    public List<Genre> getAll(){
        try(EntityManager em = emf.createEntityManager()){
            TypedQuery<Genre> query = em.createQuery("select g from Genre g", Genre.class);
            return query.getResultList();
        }
    }

    public List<Movie> getMoviesByGenre(int genreId){
        try(EntityManager em = emf.createEntityManager()){
            TypedQuery<Movie> query = em.createQuery("select m from Movie m join m.genres g where g.id =:genreId", Movie.class);
            query.setParameter("genreId",genreId);
            return query.getResultList();
        }
    }
}
