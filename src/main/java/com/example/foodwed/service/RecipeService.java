package com.example.foodwed.service;

import com.example.foodwed.dto.Request.RecipeCreateRequest;
import com.example.foodwed.dto.Request.RecipeUpdateRequest;
import com.example.foodwed.dto.response.RecipeEditResponse;
import com.example.foodwed.entity.Category;
import com.example.foodwed.entity.Recipe;
import com.example.foodwed.entity.RecipeCategoryId;
import com.example.foodwed.entity.Recipe_Category;
import com.example.foodwed.exception.Appexception;
import com.example.foodwed.exception.ErrorCode;
import com.example.foodwed.repository.CategoryReponsitory;
import com.example.foodwed.repository.RecipeCategoryRepository;
import com.example.foodwed.repository.RecipeReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RecipeService {
    @Autowired
    private RecipeReponsitory recipeReponsitory;
    @Autowired
    private CategoryReponsitory categoryReponsitory;
    @Autowired
    private RecipeCategoryRepository recipeCategoryRepository;

    public Page<Recipe> getAllRecipe(int page) {
        Pageable pageable = PageRequest.of(page, 9);
        return recipeReponsitory.findAllRecipes(pageable);
    }

    public RecipeEditResponse create(RecipeCreateRequest recipeRequest) {
        // 1. Kiểm tra tất cả categoryId có tồn tại trong database không
        for (String categoryId : recipeRequest.getCategoryids()) {
            if (!categoryReponsitory.existsById(categoryId)) {
                throw new Appexception(ErrorCode.CATEGORY_NOT_EXITED);
            }
        }

        // 2. Nếu tất cả categoryId đều hợp lệ, tiến hành tạo và lưu Recipe
        Recipe recipe = new Recipe();
        recipe.setName(recipeRequest.getName());
        recipe.setDescription(recipeRequest.getDescription());
        recipe.setIngredien(recipeRequest.getIngredien());
        recipe.setImage(recipeRequest.getImage());
        recipe.setTime(recipeRequest.getTime());
        recipe.setServes(recipeRequest.getServes());
        recipe.setStep(recipeRequest.getStep());

        Recipe savedRecipe = recipeReponsitory.save(recipe);

        // 3. Tạo các bản ghi Recipe_Category
        for (String categoryId : recipeRequest.getCategoryids()) {
            Category category = categoryReponsitory.findById(categoryId).get(); // Vì đã check tồn tại ở trên
            Recipe_Category recipeCategory = new Recipe_Category();
            recipeCategory.setId(new RecipeCategoryId(savedRecipe.getId(), categoryId));
            recipeCategory.setRecipe(savedRecipe);
            recipeCategory.setCategory(category);
            recipeCategoryRepository.save(recipeCategory);
        }

        // 4. Trả về phản hồi
        return new RecipeEditResponse(savedRecipe, recipeRequest.getCategoryids());
    }


    @Transactional
    public void delete(String recipeId) {
        // Kiểm tra xem Recipe có tồn tại không
        if (!recipeReponsitory.existsById(recipeId)) {
            throw new Appexception(ErrorCode.RECIPE_NOT_FOUND);
        }

        // Xóa tất cả các bản ghi trong bảng recipe_category có liên quan đến recipeId
        recipeCategoryRepository.deleteByRecipeId(recipeId);

        // Xóa bản ghi trong bảng recipe
        recipeReponsitory.deleteById(recipeId);
    }


    public RecipeEditResponse update(RecipeUpdateRequest recipeRequest) {
        // 1. Kiểm tra xem Recipe có tồn tại không
        Recipe recipe = recipeReponsitory.findById(recipeRequest.getRecipe().getId())
                .orElseThrow(() -> new Appexception(ErrorCode.RECIPE_NOT_FOUND));

        // 2. Kiểm tra tất cả categoryId có tồn tại trong database không
        for (String categoryId : recipeRequest.getCategoryids()) {
            if (!categoryReponsitory.existsById(categoryId)) {
                throw new Appexception(ErrorCode.CATEGORY_NOT_EXITED);
            }
        }

        // 3. Cập nhật các thuộc tính của Recipe
        recipe.setName(recipeRequest.getRecipe().getName());
        recipe.setDescription(recipeRequest.getRecipe().getDescription());
        recipe.setIngredien(recipeRequest.getRecipe().getIngredien());
        recipe.setImage(recipeRequest.getRecipe().getImage());
        recipe.setTime(recipeRequest.getRecipe().getTime());
        recipe.setServes(recipeRequest.getRecipe().getServes());
        recipe.setStep(recipeRequest.getRecipe().getStep());

        Recipe updatedRecipe = recipeReponsitory.save(recipe);

        // 4. Xóa các bản ghi Recipe_Category cũ liên quan đến Recipe này
        recipeCategoryRepository.deleteByRecipeId(recipeRequest.getRecipe().getId());

        // 5. Thêm các bản ghi Recipe_Category mới
        for (String categoryId : recipeRequest.getCategoryids()) {
            Category category = categoryReponsitory.findById(categoryId).get(); // Chắc chắn tồn tại do đã kiểm tra ở trên
            Recipe_Category recipeCategory = new Recipe_Category();
            recipeCategory.setId(new RecipeCategoryId(updatedRecipe.getId(), categoryId));
            recipeCategory.setRecipe(updatedRecipe);
            recipeCategory.setCategory(category);
            recipeCategoryRepository.save(recipeCategory);
        }

        // 6. Trả về phản hồi
        return new RecipeEditResponse(updatedRecipe, recipeRequest.getCategoryids());
    }

}
