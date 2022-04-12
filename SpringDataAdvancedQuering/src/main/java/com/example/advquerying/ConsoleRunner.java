package com.example.advquerying;

import com.example.advquerying.entities.Size;
import com.example.advquerying.services.IngredientService;
import com.example.advquerying.services.ShampooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;


@Component
public class ConsoleRunner implements CommandLineRunner {

    private final ShampooService shampooService;
    private final IngredientService ingredientService;

    @Autowired
    public ConsoleRunner(ShampooService shampooService, IngredientService ingredientService) {
        this.shampooService = shampooService;
        this.ingredientService = ingredientService;
    }

    @Override
    public void run(String... args) throws Exception {
    }

//          1  shampooService.selectShampoosBySize(Size.MEDIUM)
//                .forEach(System.out::println);
//          2  shampooService.selectShampoosBySizeOrLabelId(Size.MEDIUM, 10L)
//                .forEach(System.out::println);
//          3  shampooService.selectShampoosByPriceHigherThan(BigDecimal.valueOf(5))
//                .forEach(System.out::println);
//          4  ingredientService.selectIngredientsByNameStartWith("M")
//                .forEach(System.out::println);
//          5  ingredientService.selectIngredientsByNames(
//                List.of("Lavender", "Herbs", "Apple")
//                 ).forEach(System.out::println);
//          6  int count = shampooService.countShampoosByPriceLowerThan(BigDecimal.valueOf(8.50));
//                 System.out.println(count);
//          7  shampooService.selectShampoosByIngredients(List.of("Berry", "Mineral-Collagen"))
//                .forEach(System.out::println);
//          8  shampooService.selectShampoosByIngredientsCount(2)
//                .forEach(System.out::println);
//          10  ingredientService.updateIngredientsPriceByPercent(BigDecimal.valueOf(0.10));
}
