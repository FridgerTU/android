package com.example.fridger;

import com.example.fridger.mainClasses.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public interface AsyncResponse<T> {
    void processFinish(T result);
}
