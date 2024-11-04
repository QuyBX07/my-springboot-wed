package com.example.foodwed.repository;

import com.example.foodwed.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeReponsitory extends JpaRepository<Recipe, String> {
    List<Recipe> findByIdNotAndCategoryId(String id , String categoryId);
}
