package com.example.fridger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.MenuItemCompat;
import androidx.core.view.ViewCompat;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.transition.TransitionInflater;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.fridger.customAdapters.RecipesListViewAdapter;
import com.example.fridger.mainClasses.Recipe;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.kogitune.activity_transition.ActivityTransitionLauncher;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;
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
    List<Recipe> recipes;
    Button fabAddButton;
    ImageButton closeFabExtensionButton;
    Boolean isOpenedFirstTime = false;
    EditText ingrName;
    Button doneFabExtensionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);

        final FlexboxLayout chipsGroup = (FlexboxLayout) findViewById(R.id.chipsLayoutId);
        final FlexboxLayout addedChipsGroup = (FlexboxLayout) findViewById(R.id.fabChipsLayoutId);

        View chipLayout = chipsGroup.findViewById(R.id.customChipId);

        final SpeedDialView dial = findViewById(R.id.speedDial);
        dial.addActionItem(new SpeedDialActionItem.Builder(R.id.fab_add_ingr, R.drawable.ic_add_circle_outline_black_24dp)
                .setFabBackgroundColor(getResources().getColor(R.color.md_light_green_300))
                .setLabel("Add Ingredient")
                .create());
        dial.addActionItem(new SpeedDialActionItem.Builder(R.id.fab_new_search, R.drawable.ic_search_black_24dp)
                .setFabBackgroundColor(getResources().getColor(R.color.md_brown_300))
                .setLabel("New Search")
                .create());

        final LinearLayout fabExtendedView = (LinearLayout) findViewById(R.id.addIngrLayoutId);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        deviceWidth = size.x;
        deviceHeight = size.y;

        recipesListView = (ListView) findViewById(R.id.recipiesListView);
        searchBar = (SearchView) findViewById(R.id.searchBar);
        doneButton = (Button) findViewById(R.id.doneButton);
        fabAddButton = (Button) findViewById(R.id.fabAddButtonId);
        closeFabExtensionButton = (ImageButton) findViewById(R.id.fabCloseButtonId);
        doneFabExtensionButton = (Button) findViewById(R.id.fabMakeRequestDoneId);
        ingrName = (EditText) findViewById(R.id.fabEditTextId);

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
                View newChip = inflater.inflate(R.layout.custom_chip_layout, chipsGroup, false);
                TextView ingrName = (TextView) newChip.findViewById(R.id.chipTextId);
                ingrName.setText(query);
                chipsGroup.addView(newChip);
                doneButton.setEnabled(true);
                searchBar.setQuery("", false);
                searchBar.clearFocus();

                newChip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView ingrName = (TextView) v.findViewById(R.id.chipTextId);
                        for(int i = 0; i < chipsGroup.getChildCount(); i++) {
                            if(ingrName.getText().equals(ingredients.get(i))) {
                                ingredients.remove(i);
                                break;
                            }
                        }
                        ((ViewGroup) v.getParent()).removeView(v);
                    }
                });
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
                        recipes = result;
                        RecipesListViewAdapter adapter = new RecipesListViewAdapter(MainActivity.this,
                                R.layout.recipes_listview_layout,
                                result);
                        recipesListView.setVisibility(View.VISIBLE);
                        recipesListView.setAdapter(adapter);
                        doneButton.setVisibility(View.GONE);
                        chipsGroup.setVisibility(View.GONE);
                        dial.setVisibility(View.VISIBLE);
                    }
                });
            }
        });


        recipesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

                String chosenRecipeId = recipes.get(position).getRecipeId();
                JsonResponseWrapper.getRecipeInfo(chosenRecipeId, new AsyncResponse<Recipe>() {
                    @Override
                    public void processFinish(Recipe result) {
                        Intent intent = new Intent(MainActivity.this, RecipeInfoActivity.class);
                        intent.putExtra("recipe", result);
                        intent.putParcelableArrayListExtra("ingrList", result.getIngredients());
                        intent.putExtra("searchIngr", ingredients);
                        ActivityTransitionLauncher.with(MainActivity.this).from(view).launch(intent);
                    }
                });
            }
        });

        dial.setOnActionSelectedListener(new SpeedDialView.OnActionSelectedListener() {
            @Override
            public boolean onActionSelected(SpeedDialActionItem speedDialActionItem) {
                switch (speedDialActionItem.getId()) {
                    case R.id.fab_add_ingr:
                        fabExtendedView.setVisibility(View.VISIBLE);
                        if(isOpenedFirstTime == false) {
                            for(int i = 0; i < chipsGroup.getChildCount(); i++) {
                                View newChip = inflater.inflate(R.layout.custom_chip_layout, addedChipsGroup, false);
                                TextView ingrName = (TextView) newChip.findViewById(R.id.chipTextId);
                                ingrName.setText(ingredients.get(i));
                                addedChipsGroup.addView(newChip);
                                isOpenedFirstTime = true;
                            }
                        }
                        return false; // true to keep the Speed Dial open
                    case R.id.fab_new_search:

                    default:
                        return false;
                }
            }
        });

        fabAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    View newChip = inflater.inflate(R.layout.custom_chip_layout, addedChipsGroup, false);
                    TextView name = (TextView) newChip.findViewById(R.id.chipTextId);
                    name.setText(ingrName.getText());
                    addedChipsGroup.addView(newChip);
                    ingredients.add(ingrName.getText().toString());
                    ingrName.getText().clear();

                newChip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView ingrName = (TextView) v.findViewById(R.id.chipTextId);
                        for(int i = 0; i < chipsGroup.getChildCount(); i++) {
                            if(ingrName.getText().equals(ingredients.get(i))) {
                                ingredients.remove(i);
                                break;
                            }
                        }
                        ((ViewGroup) v.getParent()).removeView(v);
                    }
                });

            }
        });

        closeFabExtensionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabExtendedView.setVisibility(View.GONE);
                hideKeyboardFrom(MainActivity.this, ingrName);
            }
        });

        doneFabExtensionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonResponseWrapper.getRecipesList(ingredients, new AsyncResponse<List<Recipe>>() {
                    @Override
                    public void processFinish(List<Recipe> result) {
                        recipes = result;
                        RecipesListViewAdapter adapter = new RecipesListViewAdapter(MainActivity.this,
                                R.layout.recipes_listview_layout,
                                result);
                        recipesListView.setVisibility(View.VISIBLE);
                        recipesListView.setAdapter(adapter);
                        fabExtendedView.setVisibility(View.GONE);
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

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
