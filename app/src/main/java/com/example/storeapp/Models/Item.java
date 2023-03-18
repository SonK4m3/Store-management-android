package com.example.storeapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Random;

public class Item implements Parcelable {
    static int number = 0;   // thuộc tính dùng để test
    @SerializedName("id") private String id = "MDH";
    @SerializedName("name") private String name = "Tên sản phẩm";
    @SerializedName("price") private int unitPrice = 0;
    @SerializedName("url_image") private String imageUrl = "";
    @SerializedName("description") private String description = "không có mô tả sản phẩm";
    @SerializedName("categories") private ArrayList<ItemCategory> itemCategories = null;
    public Item(){
        number += 1;
        createItem();
    }
    public Item(String id, String name, int unitPrice){
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
        itemCategories = new ArrayList<>();
    }

    protected Item(Parcel in) {
        id = in.readString();
        name = in.readString();
        unitPrice = in.readInt();
        imageUrl = in.readString();
        description = in.readString();
        itemCategories = in.readArrayList(ItemCategory.class.getClassLoader());
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
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
        parcel.writeInt(unitPrice);
        parcel.writeString(imageUrl);
        parcel.writeString(description);
        parcel.writeList(itemCategories);
    }

    void createItem(){
        this.itemCategories = new ArrayList<>();
        this.name = this.name + " " + Integer.toString(number);
        this.unitPrice = new Random().nextInt(1000000);
        this.itemCategories = ItemCategory.createList(6);
    }
    /*
    convert number to price format
     */
    public static String parceInt(int x){
        if(x == 0) return "0";
        String res = "";
        String signal = "";
        if(x < 0) {
            signal = "-";
            x = -x;
        }

        int i = 0;
        while(x > 0){
            int y = x % 10;
            i += 1;
            if(i == 3 && x > 9){
                res = "," + Integer.toString(y) + res;
                i = 0;
            } else {
                res = Integer.toString(y) + res;
            }
            x /= 10;
        }
        return signal + res;
    }

    public String getName(){
        return this.name;
    }

    public String getPriceAsString(){
        return parceInt(this.unitPrice) + " VND";
    }

    public int getPrice(){
        return this.unitPrice;
    }

    public String getPriceAsString(int quantity){
        return parceInt(this.unitPrice * quantity) + " VND";
    }

    public int getPrice(int quantity){
        return this.unitPrice * quantity;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public static int getNumber() {
        return number;
    }

    public static void setNumber(int number) {
        Item.number = number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public ArrayList<ItemCategory> getItemCategories() {
        return itemCategories;
    }

    public void setItemCategories(ArrayList<ItemCategory> itemCategories) {
        this.itemCategories = itemCategories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;

        Item item = (Item) o;

        if (unitPrice != item.unitPrice) return false;
        if (getId() != null ? !getId().equals(item.getId()) : item.getId() != null) return false;
        if (getName() != null ? !getName().equals(item.getName()) : item.getName() != null)
            return false;
        if (getImageUrl() != null ? !getImageUrl().equals(item.getImageUrl()) : item.getImageUrl() != null)
            return false;
        if (getDescription() != null ? !getDescription().equals(item.getDescription()) : item.getDescription() != null)
            return false;
        return itemCategories != null ? itemCategories.equals(item.itemCategories) : item.itemCategories == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + unitPrice;
        result = 31 * result + (getImageUrl() != null ? getImageUrl().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (itemCategories != null ? itemCategories.hashCode() : 0);
        return result;
    }

    public static ArrayList<Item> createListItem(int number){
        ArrayList<Item> ls = new ArrayList<>();
        for(int i = 0; i < number; i++){
            ls.add(new Item());
        }
        return ls;
    }
}
