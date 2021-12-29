package com.fifa.restaurant.modele;

/**
 * TODO: doublon avec classe Model (supprimer une des 2 classes)
 *
 * Cette classe fonctionne avec l'ancienne lisView
 */



public class DataModel {

    private boolean isSelected;
    private String meal;
    private Integer price;

    public DataModel() {
    }

    public DataModel(boolean isSelected, String meal, Integer price) {
        this.isSelected = isSelected;
        this.meal = meal;
        this.price = price;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
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
}
