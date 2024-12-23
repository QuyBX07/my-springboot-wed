package com.example.foodwed.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Favourite {

    @EmbeddedId
    private FavouriteId id;

    @ManyToOne
    @MapsId("userid")  // Ánh xạ tới khóa ngoại "userid" trong FavouriteId
    @JoinColumn(name = "userid")
    private User user;

    @ManyToOne
    @MapsId("recipeid")  // Ánh xạ tới khóa ngoại "recipeid" trong FavouriteId
    @JoinColumn(name = "recipeid")
    private Recipe recipe;

    // Getter và setter cho id
    public FavouriteId getId() {
        return id;
    }

    public void setId(FavouriteId id) {
        this.id = id;
    }

    // Getter và setter cho user
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Getter và setter cho recipe
    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
