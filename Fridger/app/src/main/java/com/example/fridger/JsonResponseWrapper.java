package com.example.fridger;

import com.example.fridger.mainClasses.Ingredient;
import com.example.fridger.mainClasses.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonResponseWrapper {

    public List<Recipe> getRecipesList(JSONArray jsonArray) throws JSONException {
        ArrayList<Recipe> recipes = new ArrayList<Recipe>();

        for(int i = 0; i < jsonArray.length(); i++) {
            recipes.get(i).setName(jsonArray.getString(0));
            recipes.get(i).setImageLink(jsonArray.getString(1));
        }

        return recipes;
    }

    public Recipe getRecipeInfo(JSONObject jsonObject) throws JSONException{
        Recipe recipe = new Recipe();

        recipe.setName(jsonObject.getString("recipeName"));
        JSONArray ingrArr = jsonObject.getJSONArray("ingredients");

        for(int i = 0; i < ingrArr.length(); i++) {
            Ingredient ingredient = new Ingredient(ingrArr.getString(0), ingrArr.getString(1), ingrArr.getString(2));
            recipe.addIngredient(ingredient);
        }
    }
}
