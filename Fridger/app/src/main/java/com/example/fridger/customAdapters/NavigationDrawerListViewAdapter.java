package com.example.fridger.customAdapters;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.fridger.AsyncResponse;
import com.example.fridger.MainActivity;
import com.example.fridger.R;
import com.example.fridger.mainClasses.Ingredient;
import com.example.fridger.mainClasses.Recipe;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class NavigationDrawerListViewAdapter extends ArrayAdapter<Ingredient> {

    private Context context;
    private int resource;
    private ArrayList<String> seacrhIngr;

    public NavigationDrawerListViewAdapter(Context context, int resource, List<Ingredient> objects, ArrayList<String> searchIngr) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.seacrhIngr = searchIngr;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String ingredientName = getItem(position).getName();
        String quantity = getItem(position).getQuantity();

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        TextView tvIngrName =  (TextView) convertView.findViewById(R.id.recipeNameNavDrawer);
        TextView tvQuantity = (TextView) convertView.findViewById(R.id.recipeQuantityNavDrawer);
        for(int i = 0; i < seacrhIngr.size(); i++) {
            if(ingredientName.toLowerCase().equals(seacrhIngr.get(i))){
                tvIngrName.setText(ingredientName.toLowerCase());
                tvIngrName.setTextColor(Color.parseColor("#66b266"));
                tvQuantity.setText(quantity.toLowerCase());
                tvQuantity.setTextColor(Color.parseColor("#66b266"));
            }
            else {
                tvIngrName.setText(ingredientName.toLowerCase());
                tvQuantity.setText(quantity.toLowerCase());
            }
        }
        return convertView;
    }
}