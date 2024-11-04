package com.example.foodwed.service;

import com.example.foodwed.dto.response.SuggestionResponse;
import com.example.foodwed.entity.Recipe;
import com.example.foodwed.exception.Appexception;
import com.example.foodwed.exception.ErrorCode;
import com.example.foodwed.repository.RecipeReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RandomRecipeService {
    @Autowired
    private RecipeReponsitory recipeReponsitory;

    public List<SuggestionResponse> getSuggestion(String id, String categoryId) {
        List<Recipe> recipes = recipeReponsitory.findRandomByIdNotAndCategoryId(id, categoryId);
        if (recipes.isEmpty()){
            throw new Appexception(ErrorCode.SUGGESTION_ERROR);
        }

        // Lấy ngẫu nhiên 2 bản ghi
        Collections.shuffle(recipes);
        List<Recipe> randomRecipes = recipes.stream().limit(8).collect(Collectors.toList());

        return randomRecipes.stream()
                .map(recipe -> new SuggestionResponse(recipe.getId(), recipe.getName(), recipe.getImage()))
                .collect(Collectors.toList());
    }

}
