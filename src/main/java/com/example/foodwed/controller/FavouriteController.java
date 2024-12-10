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
    @DeleteMapping("/delete/{userId}/{recipeId}")
    public ResponseEntity<Favourite> deleteFavourite(@PathVariable String userId, @PathVariable String recipeId) {
        // Tạo fvid tự động (ví dụ: UUID)
        String fvid = UUID.randomUUID().toString();  // Tạo fvid tự động

        // Tạo FavouriteId từ fvid, userId và recipeId
        FavouriteId favouriteId = new FavouriteId(fvid, userId, recipeId);

        // Gọi phương thức xóa trong service
        Favourite favourite = favouriteService.deleteRecipeFromFavourites(favouriteId);

        return ResponseEntity.ok(favourite);
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

