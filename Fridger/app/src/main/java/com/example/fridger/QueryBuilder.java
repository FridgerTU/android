package com.example.fridger;

import java.util.ArrayList;
import java.util.List;

public class QueryBuilder {
    public static String getRecipeListQuery(ArrayList<String> params, String url) {
        String finalizedQuery;
        finalizedQuery = url + "/recipes?ingredients=";
        for(int i = 0; i < params.size(); i++){
            if(i - 1 < params.size()) {
                finalizedQuery += params.get(i);
            } else {
                finalizedQuery = finalizedQuery  + params.get(i) + ',';
            }
        }


        return finalizedQuery;
    }

    public static String getRecipeInfoQuery(String name, String url) {
        String finalizedQuery;
        name = name.replaceAll("\\s+","%20");
        finalizedQuery = url + "/recipe?name="+ name;
        return finalizedQuery;
    }
}
