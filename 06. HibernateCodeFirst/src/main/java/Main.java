import entities.shampoo.BasicIngredient;
import entities.shampoo.BasicLabel;
import entities.shampoo.BasicShampoo;
import entities.shampoo.ProductionBatch;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory("PU_Name");
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();

        ProductionBatch batch = new ProductionBatch(LocalDate.now());
        BasicLabel label = new BasicLabel("blue");
        BasicShampoo shampoo = new BasicShampoo("shower", label, batch);

        BasicIngredient ingredient = new BasicIngredient(100, "B12");
        BasicIngredient ingredient2 = new BasicIngredient(2, "Violet");

        shampoo.addIngredient(ingredient);
        shampoo.addIngredient(ingredient2);

        List<String> names = Arrays.asList("pavel", "erol", "stilqn");
        shampoo.setNames(names);

        manager.persist(ingredient);
        manager.persist(ingredient2);

        manager.persist(batch);
        manager.persist(label);
        manager.persist(shampoo);

        manager.getTransaction().commit();
        manager.close();
    }
}
