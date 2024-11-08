package com.example.foodwed.service;

import com.example.foodwed.dto.response.CategoryResponse;
import com.example.foodwed.entity.Category;
import com.example.foodwed.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(CategoryResponse::new) // Chuyển đổi từng Category thành CategoryResponse
                .collect(Collectors.toList());
    }
}
