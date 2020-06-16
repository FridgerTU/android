package com.example.fridger;

import java.util.ArrayList;
import java.util.List;

public class QueryBuilder {
    public static String getRecipeListQuery(ArrayList<String> params, String url) {
        String finalizedQuery;

        finalizedQuery = url + "/recipes?ingredients=" + params.get(0).replaceAll("\\s+", "_");
        for(int i = 1; i < params.size(); i++) {
            finalizedQuery += ',';
            finalizedQuery += params.get(i).replaceAll("\\s+", "_");
        }
        return finalizedQuery;
    }

    public static String getRecipeInfoQuery(String id, String url) {
        String finalizedQuery;
        finalizedQuery = url + "/recipe/"+ id;
        return finalizedQuery;
    }
}
