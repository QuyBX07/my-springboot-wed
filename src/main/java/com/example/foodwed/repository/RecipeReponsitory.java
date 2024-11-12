package com.example.foodwed.repository;

import com.example.foodwed.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
@Repository
public interface RecipeReponsitory extends JpaRepository<Recipe, String> {
//        @Query("SELECT r FROM Recipe r WHERE r.id <> :id AND r.categoryid = :categoryId ORDER BY function('RAND')")
//    List<Recipe> findRandomByIdNotAndCategoryId(@Param("id") String id, @Param("categoryId") String categoryId);
    @Query("SELECT r FROM Recipe r WHERE r.id = :recipeId")
    Optional    <Recipe> findByRecipeId(@Param("recipeId") String recipeId);


}
