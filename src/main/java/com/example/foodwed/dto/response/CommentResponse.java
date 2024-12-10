package com.example.foodwed.dto.response;

import com.example.foodwed.entity.Comment;
import lombok.Data;

import java.time.LocalDateTime; // Sửa từ LocalDate thành LocalDateTime
import java.util.List;

@Data
public class CommentResponse {
    private String cmtid;
    private String fullname;    // Fullname của người dùng
    private String content;
    private LocalDateTime date;
    private List<CommentResponse> replies;

    public CommentResponse(Comment comment, List<CommentResponse> replies) {
        this.cmtid = comment.getCmtid();
        this.fullname = comment.getFullname(); // Lấy fullname từ entity Comment
        this.content = comment.getContent();
        this.date = comment.getDate();
        this.replies = replies;
    }
}

