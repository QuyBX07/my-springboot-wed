package com.example.foodwed.service;

import com.example.foodwed.entity.Recipe;
import com.example.foodwed.repository.RecipeReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {
    @Autowired
    private RecipeReponsitory recipeRepository;

    public List<Recipe> getSearch(String name) {
        if (name != null && !name.trim().isEmpty()) {
            return recipeRepository.findByName(name);
        }

        throw new IllegalArgumentException("Invalid search name");  //
    }

}
