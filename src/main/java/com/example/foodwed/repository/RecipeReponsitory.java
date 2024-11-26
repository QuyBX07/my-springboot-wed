package com.example.foodwed.repository;

import com.example.foodwed.entity.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RecipeReponsitory extends JpaRepository<Recipe, String> {
    @Query("SELECT r FROM Recipe r WHERE r.id <> :id ORDER BY function('RAND')")
    List<Recipe> findRandomByIdNotAndCategoryId(@Param("id") String id);

    @Query("SELECT r FROM Recipe r WHERE r.name = :name")
    List<Recipe> findByName(@Param("name") String name);

    @Query("SELECT r FROM Recipe r")
    Page<Recipe> findAllRecipes(Pageable pageable);
}
