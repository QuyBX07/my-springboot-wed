package com.example.foodwed.controller;

import com.example.foodwed.dto.Request.CategoryCreateRequest;
import com.example.foodwed.dto.Request.CategoryUpdateRequest;
import com.example.foodwed.dto.response.ApiRespone;
import com.example.foodwed.entity.Category;
import com.example.foodwed.exception.Appexception;
import com.example.foodwed.exception.ErrorCode;
import com.example.foodwed.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    // Tạo category
    @PostMapping("/create")
    public ResponseEntity<?> createCategory(@RequestBody CategoryCreateRequest category) {
        if (category.getName() == null || category.getName().trim().isEmpty()) {
            throw new Appexception(ErrorCode.PARAM_ERROR);
        }
        Category newCategory = categoryService.create(category.getName());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiRespone<Category>("success", "200", "Category created successfully", newCategory));
    }

    // Xóa category
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new Appexception(ErrorCode.PARAM_ERROR);
        }
        categoryService.delete(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiRespone<String>("success", "200", "Category deleted successfully", "delete success"));
    }

    // Cập nhật category
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable String id, @RequestBody CategoryUpdateRequest category) {
        if (id == null || id.trim().isEmpty() || category.getName() == null || category.getName().trim().isEmpty()) {
            throw new Appexception(ErrorCode.PARAM_ERROR);
        }
        Category updatedCategory = categoryService.update(id, category.getName());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiRespone<Category>("success", "200", "Category updated successfully", updatedCategory));
    }

}
