package com.example.fridger;

import java.util.ArrayList;
import java.util.List;

public class QueryBuilder {
    public static String getRecipeListQuery(String params, String url) {
        String finalizedQuery;
        finalizedQuery = url + "/recipes?ingredients=";

        finalizedQuery += params;

        return finalizedQuery;
    }
}
