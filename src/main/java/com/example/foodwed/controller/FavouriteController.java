package com.example.foodwed.controller;

import com.example.foodwed.dto.response.ApiRespone;
import com.example.foodwed.dto.response.FavouriteResponse;
import com.example.foodwed.entity.Category;
import com.example.foodwed.entity.Favourite;
import com.example.foodwed.entity.FavouriteId;
import com.example.foodwed.service.FavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/favourites")
public class FavouriteController {

    @Autowired
    private FavouriteService favouriteService;

    @PostMapping("/add")
    public ResponseEntity<Favourite> addFavourite(@RequestParam String recipeId, @RequestParam String userId) {
        Favourite favourite = favouriteService.addRecipeToFavourites(recipeId, userId);
        return ResponseEntity.ok(favourite);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteFavourite(@RequestParam String userId, @RequestParam String recipeId) {
        // Gọi phương thức xóa trong service
        favouriteService.deleteRecipeFromFavourites(userId, recipeId);

        // Trả về phản hồi thành công
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiRespone<String>("success", "200", "Recipe deleted successfully", "delete success"));
    }

    /**
     * API lấy danh sách món ăn yêu thích của một người dùng.
     *
     * @param userId ID của người dùng.
     * @return Danh sách FavouriteResponse.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getFavouriteRecipes(@PathVariable String userId) {
        List<FavouriteResponse> favouriteRecipes = favouriteService.getFavouriteRecipesByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiRespone<List<FavouriteResponse>>("success", "200", "successfully",favouriteRecipes));
    }
}

