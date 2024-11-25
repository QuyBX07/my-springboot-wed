package com.example.foodwed.dto.response;

import com.example.foodwed.entity.Category;
import com.example.foodwed.entity.Recipe;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class RecipeDetailResponse {
    private Recipe recipe;
    private List<Category> categories;
}