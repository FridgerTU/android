package com.example.fridger;

import com.example.fridger.mainClasses.Ingredient;
import com.example.fridger.mainClasses.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class JsonResponseWrapper {
    private static final String baseUrl = "http://fridger-server.cfapps.io/api/v1";
    ArrayList<String> parameters = new ArrayList<String>();

    static void getRecipesList(ArrayList<String> params, final AsyncResponse<List<Recipe>> response) {
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
                               String id = jsonArray.getJSONObject(i).getString("recipeId");

                               Recipe recipe = new Recipe();
                               recipe.setName(name);
                               recipe.setImageLink(tumbnailUrl);
                               recipe.setRecipeId(id);

                               recipes.add(recipe);
                           }

                           response.processFinish(recipes);
                       } catch (JSONException e) {
                           e.printStackTrace();
                       }

                   }
               });
    }

      static void getRecipeInfo(String id, final AsyncResponse<Recipe> response) {
        String query =  QueryBuilder.getRecipeInfoQuery(id, baseUrl);

        ClientServerCommunication.execute(query, new AsyncResponse<String>() {
            @Override
            public void processFinish(String result) {
                Recipe recipe = new Recipe();
                try {
                    JSONObject obj = new JSONObject(result);
                    recipe.setName(obj.getString("recipeName"));
                    recipe.setImageLink(obj.getString("thumbnail"));
                    recipe.setCookRecipeText(obj.getString("instructions"));
                    JSONArray arr =  obj.getJSONArray("ingredients");
                    for(int i = 0; i < arr.length(); i++) {
                        Ingredient ingr = new Ingredient(arr.getJSONObject(i).getString("name"),
                                arr.getJSONObject(i).getString("quantity"));
                        recipe.addIngredient(ingr);
                    }
                    response.processFinish(recipe);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
      }
}
