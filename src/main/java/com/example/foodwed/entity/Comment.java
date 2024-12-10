package com.example.foodwed.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime; // Import LocalDateTime

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String cmtid;
    private String userid;      // ID của người dùng
    private String fullname;    // Thêm fullname của người dùng
    private String recipeid;
    private String content;
    private String parentCmtid;
    private LocalDateTime date = LocalDateTime.now();

    // Getters và Setters
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getCmtid() {
        return cmtid;
    }

    public void setCmtid(String cmtid) {
        this.cmtid = cmtid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getRecipeid() {
        return recipeid;
    }

    public void setRecipeid(String recipeid) {
        this.recipeid = recipeid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getParentCmtid() {
        return parentCmtid;
    }

    public void setParentCmtid(String parentCmtid) {
        this.parentCmtid = parentCmtid;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
