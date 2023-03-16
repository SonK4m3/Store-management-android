package com.example.storeapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;

public class ShoppingCart implements Parcelable{
    private Customer customer;
    private ArrayList<Pair<Item, Integer>> itemList;
    private int totalQuantity = 0;
    private int totalPrice = 0;

    public ShoppingCart(){

    }

    public ShoppingCart(Customer customer){
        this.customer = customer;
        this.itemList = new ArrayList<>();
        this.totalPrice = 0;
        this.totalQuantity = 0;
    }

    public ShoppingCart(Customer customer, ArrayList<Pair<Item, Integer>> itemList){
        this.customer = customer;
        this.itemList = itemList;
    }

    protected ShoppingCart(Parcel in) {
        totalQuantity = in.readInt();
        totalPrice = in.readInt();
        itemList = convertToPairList(in.createTypedArrayList(MyPair.CREATOR));
        customer = in.readParcelable(Customer.class.getClassLoader());
        customer.setShoppingCart(this);
    }

    public static final Creator<ShoppingCart> CREATOR = new Creator<ShoppingCart>() {
        @Override
        public ShoppingCart createFromParcel(Parcel in) {
            return new ShoppingCart(in);
        }

        @Override
        public ShoppingCart[] newArray(int size) {
            return new ShoppingCart[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(totalQuantity);
        parcel.writeInt(totalPrice);
        parcel.writeTypedList(convertToMyPairList(itemList));
        parcel.writeParcelable(customer, i);
    }

    public void addItem(Item item, int quantity){
        for(Pair<Item, Integer> p : itemList){
            if(p.first.equals(item)){
                p = new Pair<>(p.first, p.second + quantity);
                this.totalQuantity += quantity;
                this.totalPrice += p.first.getPrice(quantity);
                return;
            }
        }
        itemList.add(new Pair<>(item, quantity));
        this.totalQuantity += quantity;
        this.totalPrice += item.getPrice(quantity);
    }

    public void removeItem(Item item){
        for(Pair<Item, Integer> p : itemList){
            if(p.first.equals(item)){
                itemList.remove(p);
                this.totalQuantity -= p.second;
                this.totalPrice += p.first.getPrice(p.second);
                return;
            }
        }
    }

    public void clear(){
        this.itemList.clear();
        this.totalQuantity = 0;
        this.totalPrice = 0;
    }

    public ArrayList<Pair<Item, Integer>> getItemList(){
        return this.itemList;
    }

    int getTotalInt(){
        int total = 0;
        for(Pair<Item, Integer> p : itemList){
            total += p.first.getPrice(p.second);
        }
        return total;
    }

    void updateQuantity(){
        this.totalQuantity = 0;
        for(Pair<Item, Integer> p : itemList){
            this.totalQuantity += p.second;
        }
    }

    void updatePrice(){
        this.totalPrice = 0;
        for(Pair<Item, Integer> p : itemList){
            this.totalPrice += p.first.getPrice(p.second);
        }
    }

    public int getTotalQuantity(){
        updateQuantity();
        return this.totalQuantity;
    }

    public int getTotalPrice(){
        updatePrice();
        return this.totalPrice;
    }

    public String getTotalPriceFormat(){
        return Item.parceInt(getTotalPrice()) + " VND";
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setItemList(ArrayList<Pair<Item, Integer>> itemList) {
        this.itemList = itemList;
    }

    private ArrayList<MyPair> convertToMyPairList(ArrayList<Pair<Item, Integer>> list) {
        ArrayList<MyPair> myPairs = new ArrayList<>();
        for (Pair<Item, Integer> pair : list) {
            MyPair myPair = new MyPair();
            myPair.setItem(pair.first);
            myPair.setCount(pair.second);
            myPairs.add(myPair);
        }
        return myPairs;
    }

    private ArrayList<Pair<Item, Integer>> convertToPairList(ArrayList<MyPair> list) {
        ArrayList<Pair<Item, Integer>> pairs = new ArrayList<>();
        for (MyPair myPair : list) {
            pairs.add(new Pair<>(myPair.getItem(), myPair.getCount()));
        }
        return pairs;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "customer=" + customer +
                ", itemList=" + itemList +
                ", totalQuantity=" + totalQuantity +
                ", totalPrice=" + totalPrice +
                '}';
    }

    public ShoppingCart copy() {
        ShoppingCart newShoppingCart = new ShoppingCart();

        // copy customer
        newShoppingCart.setCustomer(this.getCustomer().copy());

        // copy itemList
        ArrayList<Pair<Item, Integer>> newItemList = new ArrayList<Pair<Item, Integer>>();
        for (Pair<Item, Integer> item : this.getItemList()) {
            Pair<Item, Integer> newItem = new Pair<Item, Integer>(item.first, item.second);
            newItemList.add(newItem);
        }
        newShoppingCart.setItemList(newItemList);


        return newShoppingCart;
    }
}
class MyPair implements Parcelable {
    private Item item;
    private int count;

    // Constructor, getters and setters
    public MyPair(){

    }
    // Implement Parcelable methods
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(item, i);
        parcel.writeInt(count);
    }

    public static final Parcelable.Creator<MyPair> CREATOR = new Parcelable.Creator<MyPair>() {
        @Override
        public MyPair createFromParcel(Parcel parcel) {
            return new MyPair(parcel);
        }

        @Override
        public MyPair[] newArray(int size) {
            return new MyPair[size];
        }
    };

    private MyPair(Parcel parcel) {
        item = parcel.readParcelable(Item.class.getClassLoader());
        count = parcel.readInt();
    }

    public Item getItem() {
        return this.item;
    }

    public int getCount(){
        return this.count;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
