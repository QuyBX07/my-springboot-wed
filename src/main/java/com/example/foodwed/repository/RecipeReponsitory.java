package com.example.foodwed.repository;

import com.example.foodwed.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RecipeReponsitory extends JpaRepository<Recipe, String> {
    @Query("SELECT r FROM Recipe r WHERE r.id <> :id ORDER BY function('RAND')")
    List<Recipe> findRandomByIdNotAndCategoryId(@Param("id") String id);


}
