package com.example.foodwed.controller;

import com.example.foodwed.dto.response.ApiRespone;
import com.example.foodwed.dto.response.CategoryResponse;
import com.example.foodwed.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // API lấy danh sách tất cả các categories
    @GetMapping
    public ResponseEntity<ApiRespone<List<CategoryResponse>>> getAllCategories() {
        List<CategoryResponse> categories = categoryService.getAllCategories();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiRespone<>("success", "200", "Categories retrieved successfully", categories));
    }
}
