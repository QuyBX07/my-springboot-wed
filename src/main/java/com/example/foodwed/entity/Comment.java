package com.example.foodwed.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String cmtid;
    private String userid;
    private String recipeid;
    private String content;
    private String parent_cmtid;
    private LocalDate date;

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

    public String getParent_cmtid() {
        return parent_cmtid;
    }

    public void setParent_cmtid(String parent_cmtid) {
        this.parent_cmtid = parent_cmtid;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
