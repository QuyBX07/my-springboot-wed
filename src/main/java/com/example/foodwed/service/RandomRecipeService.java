package com.example.foodwed.service;

import com.example.foodwed.entity.Recipe;
import com.example.foodwed.repository.RecipeReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RandomRecipeService {
    @Autowired
    private RecipeReponsitory recipeReponsitory;

    public List<Recipe> suggestion(String id, String categoryId){
        return recipeReponsitory.findByIdNotAndCategoryId(id,categoryId);
    }
}
