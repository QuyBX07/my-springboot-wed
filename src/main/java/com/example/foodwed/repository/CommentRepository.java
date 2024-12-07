package com.example.foodwed.repository;

import com.example.foodwed.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {
    // Lấy tất cả comment gốc theo recipeId, sắp xếp theo ngày giảm dần
    @Query("SELECT c FROM Comment c WHERE c.recipeid = ?1 AND c.parentCmtid IS NULL ORDER BY c.date DESC")
    List<Comment> findByRecipeidAndParentCmtidIsNullOrderByDateDesc(String recipeId);

    // Lấy tất cả reply của một comment, sắp xếp theo ngày giảm dần
    @Query("SELECT c FROM Comment c WHERE c.parentCmtid = ?1 ORDER BY c.date DESC")
    List<Comment> findByParentCmtidOrderByDateDesc(String parentCmtId);
}
