package _04;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class _04Main {
    public static void main(String[] args) {

        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory("PU_Name");
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();


        manager.getTransaction().commit();
        manager.close();
    }
}
