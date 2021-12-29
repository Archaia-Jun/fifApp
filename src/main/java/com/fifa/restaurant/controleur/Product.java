package com.fifa.restaurant.controleur;

public class Product {

    private String productName = "";
    private String description = "";
    private Double price = 0.00;
    private int quantity = 0;
    private Double ext = 0.00;

    public Product(String productName, String description, Double price) {
        super();
        this.productName = productName;
        this.description = description;
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public Double getExt() {
        return ext;
    }
    public void setExt(Double ext) {
        this.ext = ext;
    }

}
