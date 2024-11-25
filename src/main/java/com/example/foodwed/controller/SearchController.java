package com.example.foodwed.controller;

import com.example.foodwed.entity.Recipe;
import com.example.foodwed.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping
    public ResponseEntity<?> searchRecipe(@RequestParam String name) {
        try {
            List<Recipe> recipeSearch = searchService.getSearch(name);

            if (recipeSearch.isEmpty()) {
                return ResponseEntity.ok("No recipes found!");
            }

            return ResponseEntity.ok(recipeSearch);
        } catch (IllegalArgumentException ex) {
            // Return a 400 Bad Request response with the exception message
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
