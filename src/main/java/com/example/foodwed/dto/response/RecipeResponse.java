package com.example.foodwed.dto.response;

public class RecipeResponse {
    private String id;
    private String name;
    private String image;
    private int serves;
    private int time;

    public RecipeResponse(String id, String name, String image, int serves, int time) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.serves = serves;
        this.time  = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String recipeid) {
        this.id = recipeid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getServes() {
        return serves;
    }

    public void setServes(int serves) {
        this.serves = serves;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
