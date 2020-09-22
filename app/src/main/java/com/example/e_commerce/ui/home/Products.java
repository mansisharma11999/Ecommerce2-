package com.example.e_commerce.ui.home;

public class Products {
    private String productName;
    private int productImageId;
    private boolean addedTocart = false;

    public  Products(String productName,int productImageId)
    {
        this.productName=productName;
        this.productImageId=productImageId;
    }

    public boolean isAddedTocart() {
        return addedTocart;
    }

    public void setAddedTocart(boolean addedTocart) {
        this.addedTocart = addedTocart;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductImageId() {
        return productImageId;
    }
}
