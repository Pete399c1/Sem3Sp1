package app.daos;

import app.entities.Director;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class DirectorDAO implements IDAO<Director>{
    private final EntityManagerFactory emf;

    @Override
    public Director create(Director director){
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(director);
            em.getTransaction().commit();
        }
        return director;
    }

    @Override
    public List<Director> getAll() {
        try(EntityManager em = emf.createEntityManager()){
            TypedQuery<Director> query = em.createQuery("select d from Director d", Director.class);
            return query.getResultList();
        }
    }
}
