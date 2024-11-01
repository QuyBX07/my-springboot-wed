package com.example.foodwed.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Favourite {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String fvid;
    private String userid;
    private String recipeid;

    public String getFvid() {
        return fvid;
    }

    public void setFvid(String fvid) {
        this.fvid = fvid;
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
}
