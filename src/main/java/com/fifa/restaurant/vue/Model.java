package com.fifa.restaurant.vue;

public class Model {

    private boolean isSelected;
    private String meal;
    private String price;

    public Model() {
    }

    public Model(boolean isSelected, String meal, String price) {
        this.isSelected = isSelected;
        this.meal = meal;
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public boolean getSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setPrice(boolean b) {
    }
}
