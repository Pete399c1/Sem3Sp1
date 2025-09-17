package app.daos;

import app.entities.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;

public class MovieDAO {
    private EntityManagerFactory emf;

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
            em.merge(movie);
            em.getTransaction().begin();
        }
        return movie;
    }

    public boolean delete(Integer id){
        try(EntityManager em = emf.createEntityManager()){
            Movie movie = em.find(Movie.class, id);
            if(movie != null){
                em.getTransaction().begin();
                em.remove(movie);
                em.getTransaction().commit();
                return true;
            }else{
                return false;
            }
        }catch (PersistenceException e){
            return false;
        }
    }

    public Movie getByTitle(String title){
        try(EntityManager em = emf.createEntityManager()){
           return em.createQuery("select m from Movie m where m.title =:title", Movie.class)
                   .setParameter("title",title)
                   .getSingleResult();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
