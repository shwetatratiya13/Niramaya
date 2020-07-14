package com.technoplanet.p360.Models;

/**
 * Created by BHARGAV on 09/10/2018.
 */

public class SubcatagoryPojo {

    private String categoryName, categoryId, categoryThumb, categoryImage;

    public SubcatagoryPojo(String categoryName, String categoryId, String categoryThumb, String categoryImage) {
        this.categoryName = categoryName;
        this.categoryId = categoryId;
        this.categoryThumb = categoryThumb;
        this.categoryImage = categoryImage;
    }

    public String getCategoryName() { return categoryName;}

    public String getCategoryId() { return categoryId; }

    public String getCategoryThumb() { return categoryThumb; }

    public String getCategoryImage() { return categoryImage; }
}
