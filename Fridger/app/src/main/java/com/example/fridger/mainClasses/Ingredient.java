package com.example.fridger.mainClasses;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable {
    String name;
    String quantity;

    public Ingredient(String name,  String quantity) {
        this.name = name;
        this.quantity = quantity;
    }


    protected Ingredient(Parcel in) {
        name = in.readString();
        quantity = in.readString();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public String getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(quantity);
    }
}
