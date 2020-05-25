package com.example.fridger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.MenuItemCompat;
import androidx.core.view.ViewCompat;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Parcelable;
import android.transition.TransitionInflater;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.fridger.customAdapters.RecipesListViewAdapter;
import com.example.fridger.mainClasses.Recipe;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.kogitune.activity_transition.ActivityTransitionLauncher;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SearchView searchBar;
    ObjectAnimator animator;
    ListView recipesListView;
    Button doneButton;
    int deviceHeight;
    int deviceWidth;
    ArrayList<String> ingredients = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ChipGroup chipGroup = (ChipGroup) findViewById(R.id.ingredientsChipGroup);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        deviceWidth = size.x;
        deviceHeight = size.y;

        recipesListView = (ListView) findViewById(R.id.recipiesListView);
        searchBar = (SearchView) findViewById(R.id.searchBar);
        doneButton = (Button) findViewById(R.id.doneButton);

        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                horizontalUpMove(v, -(deviceHeight / 2 - searchBar.getHeight()));
                doneButton.setVisibility(View.VISIBLE);

            }
        });

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ingredients.add(query);
                Chip newChip = new Chip(MainActivity.this);
                newChip.setText(query);
                newChip.setCloseIconVisible(true);
                chipGroup.addView(newChip);
                doneButton.setEnabled(true);
                searchBar.setQuery("", false);
                searchBar.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                JsonResponseWrapper.getRecipesList(ingredients, new AsyncResponse<List<Recipe>>() {
                    @Override
                    public void processFinish(List<Recipe> result) {
                        RecipesListViewAdapter adapter = new RecipesListViewAdapter(MainActivity.this,
                                R.layout.recipes_listview_layout,
                                result);
                        recipesListView.setVisibility(View.VISIBLE);
                        recipesListView.setAdapter(adapter);
                        doneButton.setVisibility(View.GONE);
                        chipGroup.setVisibility(View.GONE);
                    }
                });
            }
        });
        recipesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                TextView recipeNameView =  (TextView) view.findViewById(R.id.recipeName);
                String recipeName = recipeNameView.getText().toString();

                JsonResponseWrapper.getRecipeInfo(recipeName, new AsyncResponse<Recipe>() {
                    @Override
                    public void processFinish(Recipe result) {
                        Intent intent = new Intent(MainActivity.this, RecipeInfoActivity.class);
                        intent.putExtra("recipe", result);
                        intent.putParcelableArrayListExtra("ingrList", result.getIngredients());
                        ActivityTransitionLauncher.with(MainActivity.this).from(view).launch(intent);
                    }
                });
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


}
