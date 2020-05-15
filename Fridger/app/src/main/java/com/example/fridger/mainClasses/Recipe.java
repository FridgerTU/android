package com.example.fridger.mainClasses;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Recipe implements Parcelable {
    private ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
    private String name;
    private String cookRecipeText;
    private String timeToCook;
    private String imageLink;

    public Recipe(ArrayList<Ingredient> ingredients, String cookRecipeText, String timeToCook, String name, String imageLink) {
        this.ingredients = ingredients;
        this.cookRecipeText = cookRecipeText;
        this.timeToCook = timeToCook;
        this.name = name;
        this.imageLink = imageLink;
    }

    public Recipe() {

    }

    protected Recipe(Parcel in) {
        name = in.readString();
        cookRecipeText = in.readString();
        timeToCook = in.readString();
        imageLink = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public void setCookRecipeText(String cookRecipeText) {
        this.cookRecipeText = cookRecipeText;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public void setTimeToCook(String timeToCook) {
        this.timeToCook = timeToCook;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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

    public String getImageLink() {
        return imageLink;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(cookRecipeText);
        dest.writeString(timeToCook);
        dest.writeString(imageLink);
    }
}
