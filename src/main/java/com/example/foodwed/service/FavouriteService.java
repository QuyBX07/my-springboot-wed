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
        // Tạo một đối tượng FavouriteId với fvid tự động tạo (ở đây ta sử dụng UUID để tạo fvid)
        String fvid = UUID.randomUUID().toString(); // Tạo fvid tự động bằng UUID
        FavouriteId favouriteId = new FavouriteId(fvid, userId, recipeId);

        // Kiểm tra xem Favourite đã tồn tại chưa
        Optional<Favourite> existingFavourite = favouriteReponsitory.findById(favouriteId);
        if (existingFavourite.isPresent()) {
            throw new IllegalStateException("This recipe is already in the favourites list");
        }

        // Tạo đối tượng Favourite mới
        Favourite favourite = new Favourite();
        favourite.setId(favouriteId);  // Thiết lập khóa chính tổng hợp

        // Tùy chọn: lấy đối tượng User và Recipe từ database (nếu cần ánh xạ đầy đủ)
        User user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("User not found with ID: " + userId));
        Recipe recipe = recipeReponsitory.findById(recipeId).orElseThrow(() ->
                new IllegalArgumentException("Recipe not found with ID: " + recipeId));

        favourite.setUser(user);      // Thiết lập quan hệ với User
        favourite.setRecipe(recipe);  // Thiết lập quan hệ với Recipe

        // Lưu Favourite vào database
        return favouriteReponsitory.save(favourite);
    }

    public Favourite deleteRecipeFromFavourites(FavouriteId favouriteId) {
        // Tìm đối tượng Favourite trước khi xóa
        Favourite favourite = favouriteReponsitory.findById(favouriteId)
                .orElseThrow(() -> new RuntimeException("Favourite with ID " + favouriteId + " not found"));

        // Xóa đối tượng Favourite
        favouriteReponsitory.deleteById(favouriteId);

        // Trả về đối tượng đã xóa
        return favourite;
    }

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
