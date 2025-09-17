package app;

import app.config.HibernateConfig;
import jakarta.persistence.EntityManagerFactory;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();



        emf.close();
    }
}