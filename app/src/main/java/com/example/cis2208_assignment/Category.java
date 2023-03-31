package com.example.cis2208_assignment;

public class Category {
    public int categoryId;
    public String categoryName;
    public String categoryIcon;

    public String categoryScore;

    public Category(int id, String name, String icon){
        this.categoryId = id;
        this.categoryName = name;
        this.categoryIcon = icon;
    }

    public Category(int id, String name, String icon, String score){
        this.categoryId = id;
        this.categoryName = name;
        this.categoryIcon = icon;
        this.categoryScore = score;
    }
}

