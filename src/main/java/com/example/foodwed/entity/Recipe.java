package com.example.foodwed.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
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






}
