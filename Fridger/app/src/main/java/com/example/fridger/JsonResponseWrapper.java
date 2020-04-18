package com.example.fridger;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.fridger.mainClasses.Ingredient;
import com.example.fridger.mainClasses.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class JsonResponseWrapper {
    private static final String url1  = "https://my-json-server.typicode.com/ivanganchev/FakeOnlineJson/recipes";

    static void getRecipesList(final AsyncResponse<List<Recipe>> response) {

        ClientServerCommunication
               .execute(url1, new AsyncResponse<String>() {
                   @Override
                   public void processFinish(String result) {
                       ArrayList<Recipe> recipes = new ArrayList<Recipe>();
                       try {
                           JSONArray jsonArray = new JSONArray(result);
                           for(int i = 0; i < jsonArray.length(); i++) {
                               String name = jsonArray.getJSONObject(i).getString("recipeName");
                               String tumbnailUrl = jsonArray.getJSONObject(i).getString("tumbnailUrl");

                               Recipe recipe = new Recipe();
                               recipe.setName(name);
                               recipe.setImageLink(tumbnailUrl);

                               recipes.add(recipe);
                           }

                           response.processFinish(recipes);
                       } catch (JSONException e) {
                           e.printStackTrace();
                       }

                   }
               });
    }

//    public void getRecipeInfo(final AsyncResponse response) {
//        this.recipeResponse = response;
//        clientServerCommunication.execute("https://my-json-server.typicode.com/ivanganchev/FakeOnlineJson/recipe");
//        Recipe recipe = new Recipe();
//
//        recipe.setName(jsonObject.getString("recipeName"));
//        JSONArray ingrArr = jsonObject.getJSONArray("ingredients");
//
//        for(int i = 0; i < ingrArr.length(); i++) {
//            Ingredient ingredient = new Ingredient(ingrArr.getString(0), ingrArr.getString(1), ingrArr.getString(2));
//            recipe.addIngredient(ingredient);
//        }
//
//    }
}
