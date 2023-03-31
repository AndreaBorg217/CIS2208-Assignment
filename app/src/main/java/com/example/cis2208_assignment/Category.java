package com.example.cis2208_assignment;

public class Category {
    public int categoryId;
    public String categoryName;
    public String categoryIcon;

    public String categoryScore;

    // A constructor used for the category selection buttons
    public Category(int id, String name, String icon){
        this.categoryId = id;
        this.categoryName = name;
        this.categoryIcon = icon;
    }

    // A constructor used for the category score cards
    public Category(int id, String name, String icon, String score){
        this.categoryId = id;
        this.categoryName = name;
        this.categoryIcon = icon;
        this.categoryScore = score;
    }
}

