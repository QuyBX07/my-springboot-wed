package com.example.foodwed.repository;

import com.example.foodwed.entity.Category;
import com.example.foodwed.entity.Favourite;
import com.example.foodwed.entity.FavouriteId;
import com.example.foodwed.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

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
}
