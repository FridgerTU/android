package com.example.fridger.mainClasses;

public class Ingredient {
    String name;
    String  quantityType;
    float quantity;

    public Ingredient(String name, String quantityType, float quantity) {
        this.name = name;
        this.quantityType = quantityType;
        this.quantity = quantity;
    }
    public float getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    public String getQuantityType() {
        return quantityType;
    }
}
