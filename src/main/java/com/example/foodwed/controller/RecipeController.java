package com.example.foodwed.controller;

import com.example.foodwed.dto.Request.RecipeCreateRequest;
import com.example.foodwed.dto.Request.RecipeUpdateRequest;
import com.example.foodwed.dto.response.ApiRespone;
import com.example.foodwed.dto.response.RecipeEditResponse;
import com.example.foodwed.entity.Recipe;
import com.example.foodwed.exception.Appexception;
import com.example.foodwed.exception.ErrorCode;
import com.example.foodwed.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/recipe")
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    @GetMapping
    public ResponseEntity<Page<Recipe>> getAllRecipe(@RequestParam(defaultValue = "0") int page){
        Page<Recipe> recipes = recipeService.getAllRecipe(page);
        return ResponseEntity.ok(recipes);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createRecipe(@RequestBody RecipeCreateRequest recipe){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiRespone<RecipeEditResponse>("success","200","Recipe created successfully",
                        recipeService.create(recipe)));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateRecipe(@RequestBody RecipeUpdateRequest recipe){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiRespone<RecipeEditResponse>("success","200","Recipe updated successfully",
                        recipeService.update(recipe)));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable String id){
        if (id == null || id.trim().isEmpty()) {
            throw new Appexception(ErrorCode.PARAM_ERROR);
        }
        recipeService.delete(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiRespone<String>("success", "200", "Recipe deleted successfully", "delete success"));
    }
}
