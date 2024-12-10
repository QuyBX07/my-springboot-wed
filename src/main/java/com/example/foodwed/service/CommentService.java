package com.example.foodwed.service;

import com.example.foodwed.dto.Request.CommentRequest;
import com.example.foodwed.dto.response.CommentResponse;
import com.example.foodwed.entity.Comment;
import com.example.foodwed.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserService userService; // Thêm UserService để lấy fullname từ userid

    // Thêm comment hoặc reply
    public CommentResponse addComment(CommentRequest request) {
        Comment comment = new Comment();
        comment.setUserid(request.getUserid());
        comment.setRecipeid(request.getRecipeId());
        comment.setContent(request.getContent());
        comment.setParentCmtid(request.getParentCmtId()); // nếu là reply, cần parent comment ID

        // Lấy fullname từ UserService
        String fullname = userService.getFullnameByUserId(request.getUserid());
        comment.setFullname(fullname);

        Comment savedComment = commentRepository.save(comment);
        return new CommentResponse(savedComment, Collections.emptyList());
    }

    // Lấy danh sách comment và reply theo recipe ID, đã sắp xếp theo ngày
    public List<CommentResponse> getCommentsByRecipe(String recipeId) {
        // Lấy danh sách comment gốc, đã sắp xếp theo ngày
        List<Comment> comments = commentRepository.findByRecipeidAndParentCmtidIsNullOrderByDateDesc(recipeId);
        return comments.stream()
                .map(comment -> {
                    // Cập nhật fullname nếu chưa có
                    if (comment.getFullname() == null || comment.getFullname().isEmpty()) {
                        String fullname = userService.getFullnameByUserId(comment.getUserid());
                        comment.setFullname(fullname);
                        commentRepository.save(comment); // Lưu lại fullname vào DB
                    }
                    return new CommentResponse(comment, getReplies(comment.getCmtid()));
                })
                .collect(Collectors.toList());
    }

    // Lấy danh sách replies của một comment, đã sắp xếp theo ngày
    private List<CommentResponse> getReplies(String parentCmtId) {
        List<Comment> replies = commentRepository.findByParentCmtidOrderByDateDesc(parentCmtId);
        return replies.stream()
                .map(reply -> {
                    // Cập nhật fullname nếu chưa có
                    if (reply.getFullname() == null || reply.getFullname().isEmpty()) {
                        String fullname = userService.getFullnameByUserId(reply.getUserid());
                        reply.setFullname(fullname);
                        commentRepository.save(reply); // Lưu lại fullname vào DB
                    }
                    return new CommentResponse(reply, Collections.emptyList());
                })
                .collect(Collectors.toList());
    }
}
