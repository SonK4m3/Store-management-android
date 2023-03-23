package com.example.storeapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class Customer implements Parcelable {

    @SerializedName("id") private String id;
    @SerializedName("name") private String name;
    @SerializedName("address") private String address;
    private ShoppingCart shoppingCart;
    public Customer(){
        this.shoppingCart = new ShoppingCart(this);
    }

    public Customer(String id, String name, String address){
        this.id = id;
        this.name = name;
        this.address = address;
        this.shoppingCart = new ShoppingCart(this);
    }

    protected Customer(Parcel in) {
        id = in.readString();
        name = in.readString();
        address = in.readString();
    }

    public static final Creator<Customer> CREATOR = new Creator<Customer>() {
        @Override
        public Customer createFromParcel(Parcel in) {
            return new Customer(in);
        }

        @Override
        public Customer[] newArray(int size) {
            return new Customer[size];
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
        parcel.writeString(address);
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
        this.shoppingCart.setCustomer(this);
    }

    public Customer copy(){
        Customer newCustomer = new Customer();

        newCustomer.setId(this.id);
        newCustomer.setName(this.name);
        newCustomer.setAddress(this.address);
        return newCustomer;
    }

    /*
    @param m : string need find
    @param ls: customer list that find customer who had name contain m
    return list of customer whose have name contain m
     */
    public static ArrayList<Customer> findCustomer(String m, ArrayList<Customer> ls){
        ArrayList<Customer> newLs = new ArrayList<>();
        for(Customer c : ls){
            if(c.getName().contains(m)){
                newLs.add(c);
            }
        }
        return newLs;
    }

    public static Customer createSample(){
        return new Customer("CSID", "NGO BA KHA", "BAC NINH 99");
    }
}
