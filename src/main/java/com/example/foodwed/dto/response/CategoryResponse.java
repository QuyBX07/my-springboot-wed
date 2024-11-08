package com.example.foodwed.dto.response;

import com.example.foodwed.entity.Category;
import lombok.Data;

@Data
public class CategoryResponse {
    private String categoryId;
    private String name;

    public CategoryResponse(Category category) {
        this.categoryId = category.getCategoryid();
        this.name = category.getName();
    }
}
