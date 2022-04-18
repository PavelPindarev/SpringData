package com.example.advquerying.services;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import com.example.advquerying.repositories.ShampooRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Service
public class ShampooServiceImpl implements ShampooService {

    private final ShampooRepository shampooRepository;

    @Autowired
    public ShampooServiceImpl(ShampooRepository shampooRepository) {
        this.shampooRepository = shampooRepository;
    }

    @Override
    public List<Shampoo> selectShampoosBySize(Size size) {
        return shampooRepository.findShampooBySize(size);
    }

    @Override
    public List<Shampoo> selectShampoosBySizeOrLabelId(Size size, Long labelId) {
        return shampooRepository.findShampooBySizeOrLabelId(size, labelId);
    }

    @Override
    public List<Shampoo> selectShampoosByPriceHigherThan(BigDecimal price) {
        return  shampooRepository.findShampooByPriceGreaterThanOrderByPriceDesc(price);

    }

    @Override
    public int countShampoosByPriceLowerThan(BigDecimal price) {
        return shampooRepository.countShampooByPriceLessThan(price);
    }

    @Override
    public Set<String> selectShampoosByIngredients(List<String> ingredients) {
        return shampooRepository.findShampoosByIngredients(ingredients);
    }

    @Override
    public List<Shampoo> selectShampoosByIngredientsCount(int ingredientsCount) {
        return shampooRepository.findShampoosByIngredientsCountLessThan(ingredientsCount);
    }
}
