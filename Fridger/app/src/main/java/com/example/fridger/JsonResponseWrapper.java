package com.example.fridger;

import com.example.fridger.mainClasses.Recipe;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

class JsonResponseWrapper {
    private static final String baseUrl = "http://fridger-server.cfapps.io/api/v1";
    ArrayList<String> parameters = new ArrayList<String>();

    static void getRecipesList(String params, final AsyncResponse<List<Recipe>> response) {
        //method of class QueryBuilder, to get ready URL, pass parameters
        String query = QueryBuilder.getRecipeListQuery(params, baseUrl);

        ClientServerCommunication
               .execute(query, new AsyncResponse<String>() {
                   @Override
                   public void processFinish(String result) {
                       ArrayList<Recipe> recipes = new ArrayList<Recipe>();
                       try {
                           JSONArray jsonArray = new JSONArray(result);
                           for(int i = 0; i < jsonArray.length(); i++) {
                               String name = jsonArray.getJSONObject(i).getString("recipeName");
                               String tumbnailUrl = jsonArray.getJSONObject(i).getString("thumbnail");

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
