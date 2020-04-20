package com.example.fridger;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.fridger.customAdapters.RecipesListViewAdapter;
import com.example.fridger.mainClasses.Recipe;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    SearchView searchBar;
    ObjectAnimator animator;
    ListView recipesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchBar = (SearchView) findViewById(R.id.searchBar);

        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                horizontalUpMove(v, -300);
            }
        });

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                JsonResponseWrapper.getRecipesList(new AsyncResponse<List<Recipe>>() {
                    @Override
                    public void processFinish(List<Recipe> result) {
                        recipesListView = (ListView) findViewById(R.id.recipiesListView);
                        RecipesListViewAdapter adapter = new RecipesListViewAdapter(MainActivity.this, R.layout.recipes_listview_layout, result);
                        recipesListView.setAdapter(adapter);
                    }
                });

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });


    }

    private void horizontalUpMove(View view, float value) {
        searchBar.setIconified(false);
        animator = ObjectAnimator.ofFloat(view, "translationY", value);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(2000);
        animator.start();
    }

//    @Override
//    public void processFinish(JSONArray arr) throws JSONException {
//        jsonString.setText(arr.getString(0));
//        jsonString.setVisibility(View.VISIBLE);
//    }
}
