package app.daos;

import app.entities.Actor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import java.util.List;

@AllArgsConstructor
public class ActorDAO implements IDAO<Actor> {
    private final EntityManagerFactory emf;

    @Override
    public Actor create(Actor actor){
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(actor);
            em.getTransaction().commit();
        }
        return actor;
    }

    @Override
    public List<Actor> getAll() {
        try(EntityManager em = emf.createEntityManager()){
            TypedQuery<Actor> query = em.createQuery("select a from Actor a", Actor.class);
            return query.getResultList();
        }
    }
}
