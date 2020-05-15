package com.example.fridger;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.fridger.mainClasses.Recipe;

public class RecipeInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_info);

        Recipe recipe = (Recipe) getIntent().getExtras().getParcelable("recipe");
        TextView recipeName = (TextView) findViewById(R.id.recipeNameId);
        recipeName.setText(recipe.getName());
    }

}
