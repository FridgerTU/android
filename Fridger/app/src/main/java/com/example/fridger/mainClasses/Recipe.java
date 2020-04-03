package com.example.fridger.mainClasses;

import java.util.ArrayList;

public class Recipe {
    private ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
    private String cookRecipeText;
    private String timeToCook;

    public Recipe(ArrayList<Ingredient> ingredients, String cookRecipeText, String timeToCook) {
        this.ingredients = ingredients;
        this.cookRecipeText = cookRecipeText;
        this.timeToCook = timeToCook;
    }

    public String getCookRecipeText() {
        return cookRecipeText;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public String getTimeToCook() {
        return timeToCook;
    }
}
