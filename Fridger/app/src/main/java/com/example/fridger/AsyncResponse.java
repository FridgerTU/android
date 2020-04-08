package com.example.fridger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public interface AsyncResponse {
    void processFinish(JSONArray obj) throws JSONException;
}
