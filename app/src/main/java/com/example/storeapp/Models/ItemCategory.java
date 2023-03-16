package com.example.storeapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class ItemCategory implements Parcelable {
    private String id = "MDH";
    private String name;
    private int quantity = 5;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemCategory)) return false;

        ItemCategory that = (ItemCategory) o;

        if (getQuantity() != that.getQuantity()) return false;
        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        return getName() != null ? getName().equals(that.getName()) : that.getName() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + getQuantity();
        return result;
    }

    public static ArrayList<ItemCategory> createList(int num){
        ArrayList<ItemCategory> ls = new ArrayList<ItemCategory>();

        for(int i = 0; i < num; i++){
            ls.add(new ItemCategory("ID", "COLOR " + i, 5));
        }
        return ls;
    }
}
