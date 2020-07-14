package com.technoplanet.p360.Models;

/**
 * Created by BHARGAV on 09/10/2018.
 */

public class ProductsPojo {

    private String id, productName,amount,categoryName,average_rating,image ;

    public ProductsPojo(String id, String productName, String amount, String categoryName, String average_rating, String image) {
        this.id = id;
        this.productName = productName;
        this.amount = amount;
        this.categoryName = categoryName;
        this.average_rating = average_rating;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public String getAmount() {
        return amount;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getAverage_rating() {
        return average_rating;
    }

    public String getImage() {
        return image;
    }
}
