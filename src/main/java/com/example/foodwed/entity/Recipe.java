package com.example.foodwed.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Recipe {
    @Id

    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String ingredien;
    private String description;
    private String name;
    private String step;
    private String image;
    private int time;
    private int serves;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }





    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIngredien() {
        return ingredien;
    }

    public void setIngredien(String ingredien) {
        this.ingredien = ingredien;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getServes() {
        return serves;
    }

    public void setServes(int serves) {
        this.serves = serves;
    }
}
