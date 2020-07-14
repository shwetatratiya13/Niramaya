package com.technoplanet.p360.Models;

/**
 * Created by BHARGAV on 09/10/2018.
 */

public class ProductDetPojo {

    private String pId, productName,productDesc,amount,orgAmount,quantity,catname,subcatname,productImages ;

    public ProductDetPojo(String pId, String productName, String productDesc, String amount, String orgAmount, String quantity, String catname, String subcatname, String productImages) {
        this.pId = pId;
        this.productName = productName;
        this.productDesc = productDesc;
        this.amount = amount;
        this.orgAmount = orgAmount;
        this.quantity = quantity;
        this.catname = catname;
        this.subcatname = subcatname;
        this.productImages = productImages;
     }

    public String getpId() { return pId; }

    public String getProductName() { return productName; }

    public String getProductDesc() { return productDesc; }

    public String getAmount() { return amount; }

    public String getOrgAmount() { return orgAmount; }

    public String getQuantity() { return quantity; }

    public String getCatname() { return catname; }

    public String getSubcatname() { return subcatname; }

    public String getProductImages() { return productImages; }
}
