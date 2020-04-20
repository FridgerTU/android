package com.example.fridger.customAdapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fridger.AsyncResponse;
import com.example.fridger.R;
import com.example.fridger.mainClasses.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipesListViewAdapter extends ArrayAdapter<Recipe> {

    private Context context;
    private int resource;


    public RecipesListViewAdapter(Context context, int resource, List<Recipe> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String recipeName = getItem(position).getName();
        String imgLink = getItem(position).getImageLink();

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        TextView tvName =  (TextView) convertView.findViewById(R.id.recipeName);
        ImageView ivRecipeImage = (ImageView) convertView.findViewById(R.id.recipeImage);

        tvName.setText(recipeName);
        Picasso.get().load(imgLink).into(ivRecipeImage);

        return convertView;
    }
}
