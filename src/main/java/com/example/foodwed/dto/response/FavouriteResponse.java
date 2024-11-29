package com.example.foodwed.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
    public class FavouriteResponse {
        private String recipeId;
        private String recipeName;
        private String recipeImage;


    }

