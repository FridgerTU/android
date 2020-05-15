package com.example.fridger.mainClasses;

public class Ingredient {
    String name;
    String quantity;

    public Ingredient(String name,  String quantity) {
        this.name = name;
        this.quantity = quantity;
    }


    public String getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

}
