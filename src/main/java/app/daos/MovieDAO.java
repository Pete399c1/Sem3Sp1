package app.daos;

import app.entities.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class MovieDAO implements IDAO<Movie> {
    private final EntityManagerFactory emf;

    @Override
    public Movie create(Movie movie){
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(movie);
            em.getTransaction().commit();
        }
        return movie;
    }

    public Movie update(Movie movie){
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            Movie update = em.merge(movie);
            em.getTransaction().commit();
            return update;
        }
    }

    public boolean delete(Integer id){
        try(EntityManager em = emf.createEntityManager()){
            Movie movie = em.find(Movie.class, id);
            if(movie != null){
                em.getTransaction().begin();
                em.remove(movie);
                em.getTransaction().commit();
                return true;
            }
            return false;
        }
    }


    public List<Movie> getByTitle(String title){
        try(EntityManager em = emf.createEntityManager()) {
            //Using lower making sure that the title is case-insensitive
            return em.createQuery("select m from Movie m where lower(m.title) = lower(:title)", Movie.class)
                    .setParameter("title", title)
                    .getResultList();
        }
    }

    @Override
    public List<Movie> getAll(){
        try(EntityManager em = emf.createEntityManager()){
            TypedQuery<Movie> query = em.createQuery("select m from Movie m", Movie.class);
            return query.getResultList();
        }
    }
}
