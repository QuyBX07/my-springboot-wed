package com.example.foodwed.service;

import com.example.foodwed.dto.response.FavouriteResponse;
import com.example.foodwed.entity.Favourite;
import com.example.foodwed.entity.FavouriteId;
import com.example.foodwed.entity.Recipe;
import com.example.foodwed.entity.User;
import com.example.foodwed.repository.FavouriteReponsitory;
import com.example.foodwed.repository.RecipeReponsitory;
import com.example.foodwed.repository.UserReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FavouriteService {

    @Autowired
    private FavouriteReponsitory favouriteReponsitory;

    @Autowired
    private UserReponsitory userRepository;

    @Autowired
    private RecipeReponsitory recipeReponsitory;

    public Favourite addRecipeToFavourites(String recipeId, String userId) {
        // Kiểm tra xem User có tồn tại không
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        // Kiểm tra xem Recipe có tồn tại không
        Recipe recipe = recipeReponsitory.findById(recipeId)
                .orElseThrow(() -> new IllegalArgumentException("Recipe not found with ID: " + recipeId));

        // Kiểm tra xem Favourite đã tồn tại chưa
        boolean isFavouriteExists = favouriteReponsitory.existsByUserAndRecipe(userId, recipeId);
        if (isFavouriteExists) {
            throw new IllegalStateException("This recipe is already in the favourites list");
        }

        String fvid = UUID.randomUUID().toString(); // Tạo fvid tự động bằng UUID
        FavouriteId favouriteId = new FavouriteId(fvid, userId, recipeId);
        Favourite favourite = new Favourite();
        favourite.setId(favouriteId);
        favourite.setUser(user);
        favourite.setRecipe(recipe);

        // Lưu Favourite vào database
        return favouriteReponsitory.save(favourite);
    }
    public boolean isExits(String recipeId, String userId){
        boolean isFavouriteExists = favouriteReponsitory.existsByUserAndRecipe(userId, recipeId);
        return isFavouriteExists;
    }

    public void deleteRecipeFromFavourites(String userid, String recipeid) {

        favouriteReponsitory.deleteByUserUseridAndRecipeRecipeId(userid, recipeid);
    };

    /**
     * Lấy danh sách các món ăn yêu thích của một người dùng.
     *
     * @param userId ID của người dùng.
     * @return Danh sách FavouriteResponse.
     */
    public List<FavouriteResponse> getFavouriteRecipesByUserId(String userId) {
        List<Favourite> favourites = favouriteReponsitory.findByUserUserid(userId);

        // Chuyển từ entity Favourite sang DTO FavouriteResponse
        return favourites.stream()
                .map(favourite -> new FavouriteResponse(
                        favourite.getRecipe().getId(),
                        favourite.getRecipe().getName(),
                        favourite.getRecipe().getImage()
                ))
                .collect(Collectors.toList());
    }
}