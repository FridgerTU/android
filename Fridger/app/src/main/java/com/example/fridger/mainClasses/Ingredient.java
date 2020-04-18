package com.example.fridger.mainClasses;

public class Ingredient {
    String name;
    String  quantityType;
    String quantity;

    public Ingredient(String name,  String quantity, String quantityType) {
        this.name = name;
        this.quantityType = quantityType;
        this.quantity = quantity;
    }


    public String getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    public String getQuantityType() {
        return quantityType;
    }
}
