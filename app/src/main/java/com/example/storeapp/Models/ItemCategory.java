package com.example.storeapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ItemCategory implements Parcelable {
    @SerializedName("id") private String id = "MDH";
    @SerializedName("name") private String name;
    @SerializedName("quantity") private int quantity = 5;
    @SerializedName("image_url") private String image_url = "";
    public ItemCategory(){

    }
    
    public ItemCategory(String id, String name, int quantity){
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    protected ItemCategory(Parcel in) {
        id = in.readString();
        name = in.readString();
        quantity = in.readInt();
        image_url = in.readString();
    }

    public static final Creator<ItemCategory> CREATOR = new Creator<ItemCategory>() {
        @Override
        public ItemCategory createFromParcel(Parcel in) {
            return new ItemCategory(in);
        }

        @Override
        public ItemCategory[] newArray(int size) {
            return new ItemCategory[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeInt(quantity);
        parcel.writeString(image_url);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public static ArrayList<ItemCategory> createList(int num){
        ArrayList<ItemCategory> ls = new ArrayList<ItemCategory>();

        for(int i = 0; i < num; i++){
            ls.add(new ItemCategory("ID", "COLOR " + i, 5));
        }
        return ls;
    }
}
