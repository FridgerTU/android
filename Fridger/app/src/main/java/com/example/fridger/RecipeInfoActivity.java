package com.example.fridger;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fridger.customAdapters.NavigationDrawerListViewAdapter;
import com.example.fridger.customAdapters.RecipesListViewAdapter;
import com.example.fridger.mainClasses.Ingredient;
import com.example.fridger.mainClasses.Recipe;
import com.google.android.material.navigation.NavigationView;
import com.kogitune.activity_transition.ActivityTransition;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_info);

        ListView navDrawer = (ListView) findViewById(R.id.listViewNavId);
        ImageView image = (ImageView) findViewById(R.id.imageId);

        Recipe recipe = (Recipe) getIntent().getExtras().getParcelable("recipe");
        List<Ingredient> ingrList = getIntent().getParcelableArrayListExtra("ingrList");

        Glide.with(this).load(recipe.getImageLink()).centerCrop().into(image);
        ActivityTransition.with(getIntent()).to(findViewById(R.id.imageId)).start(savedInstanceState);

        NavigationDrawerListViewAdapter adapter = new NavigationDrawerListViewAdapter(RecipeInfoActivity.this,
                R.layout.navigation_drawer_layout,
                ingrList);

        navDrawer.setAdapter(adapter);


    }
}
