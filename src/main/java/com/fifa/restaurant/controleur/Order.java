package com.fifa.restaurant.controleur;

public class Order {

    public String products;
    public String price;
    public String phone;
    public String orderFinalList;

    public Order() {
        super();
    }

    public String getOrderFinalList() {
        return orderFinalList;
    }

    public void setOrderFinalList(String orderFinalList) {
        this.orderFinalList = orderFinalList;
    }

    public Order(String products, String price, String phone, String orderFinalList) {
        this.products = products;
        this.price = price;
        this.phone = phone;
        this.orderFinalList = orderFinalList;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}




// TODO: classe utilisée avec lancienne listview, à supprimer

/*
public class Order {

    public  String name;
    public String description;
    public String price;
    public  String type;

    public Order(boolean isSelected, String email, String phone) {
    }


    public Order(String name, String description, String price, String type) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.type = type;
    }

    public Order(boolean isSelected, String meal, int price) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}*/
