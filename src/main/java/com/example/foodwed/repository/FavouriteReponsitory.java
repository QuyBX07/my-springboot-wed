package com.example.foodwed.repository;

import com.example.foodwed.entity.Category;
import com.example.foodwed.entity.Favourite;
import com.example.foodwed.entity.FavouriteId;
import com.example.foodwed.entity.Recipe;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FavouriteReponsitory extends JpaRepository<Favourite, FavouriteId> {
    /**
     * Tìm danh sách Favourite theo userId.
     *
     * @param userId ID của người dùng.
     * @return Danh sách Favourite.
     */
    List<Favourite> findByUserUserid(String userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Favourite f WHERE f.user.userid = :userId AND f.recipe.id = :recipeId")
    void deleteByUserUseridAndRecipeRecipeId(@Param("userId") String userid,
                                             @Param("recipeId") String id);

}
