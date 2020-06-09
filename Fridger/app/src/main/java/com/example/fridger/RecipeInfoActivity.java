package com.example.fridger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.animation.ObjectAnimator;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fridger.customAdapters.NavigationDrawerListViewAdapter;
import com.example.fridger.customAdapters.RecipeInfoPageAdapter;
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

        final AnimationSet anim = new AnimationSet(false);

        final ListView navDrawer = (ListView) findViewById(R.id.listViewNavId);
        ImageView image = (ImageView) findViewById(R.id.imageId);
        final ImageButton arrow = (ImageButton) findViewById(R.id.arrowViewId);
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //ViewPager viewPager = (ViewPager) findViewById(R.id.viewPagerId);

        final int location[] = new int[2];
        arrow.getLocationOnScreen(location);

        arrow.bringToFront();
        Recipe recipe = (Recipe) getIntent().getExtras().getParcelable("recipe");
        List<Ingredient> ingrList = getIntent().getParcelableArrayListExtra("ingrList");

        Glide.with(this).load(recipe.getImageLink()).centerCrop().into(image);
        ActivityTransition.with(getIntent()).to(findViewById(R.id.imageId)).start(savedInstanceState);

        //viewPager.setAdapter(new RecipeInfoPageAdapter(getSupportFragmentManager()));

        NavigationDrawerListViewAdapter adapter = new NavigationDrawerListViewAdapter(RecipeInfoActivity.this,
                R.layout.navigation_drawer_layout,
                ingrList);

        navDrawer.setAdapter(adapter);

        arrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(drawerLayout.isDrawerOpen(GravityCompat.END)) {
                    arrow.setX(arrow.getX() + navigationView.getMeasuredWidth());
                    arrow.setBackgroundResource(0);
                    arrow.setImageResource(R.drawable.ic_keyboard_arrow_left_white_24dp);
                    fadeInAnimation(arrow, anim);
                    drawerLayout.closeDrawer(Gravity.RIGHT);

                } else {
                    arrow.setX(arrow.getX() - navigationView.getMeasuredWidth());
                    arrow.setBackgroundResource(0);
                    arrow.setImageResource(R.drawable.ic_keyboard_arrow_right_white_24dp);
                    fadeInAnimation(arrow, anim);
                    drawerLayout.openDrawer(Gravity.RIGHT);
                }
            }
        });
    }

    private void fadeInAnimation(View view, AnimationSet anim) {
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(1000);

        anim.addAnimation(fadeIn);
        view.startAnimation(anim);
    }

//    private void passDataToFragment(Fragment fragment, String data) {
//        Bundle bundle = new Bundle();
//        bundle.putString("data", data);
//        fragment.setArguments(bundle);
//    }
}
