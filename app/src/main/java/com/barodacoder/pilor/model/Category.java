package com.barodacoder.pilor.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Category implements Serializable {
    private String categoryId = "";
    private String categoryName = "";
    private String categoryImage = "";
    private String parentId = "";

    private ArrayList<Category> alSubCategory;

    public Category() {
        alSubCategory = new ArrayList<>();
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public ArrayList<Category> getAlSubCategory() {
        return alSubCategory;
    }

    public void setAlSubCategory(ArrayList<Category> alSubCategory) {
        this.alSubCategory = alSubCategory;
    }
}
