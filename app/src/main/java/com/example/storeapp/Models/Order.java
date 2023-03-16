package com.example.storeapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Order implements Parcelable {
    public enum State {
        NEW, OLD
    }
    private String idOrder = "DH10012021/16:26:49";
    private String date = "01/10/2021 16:26:49";
    private State state = State.NEW;
    private String name = "TEN DON HANG";
    private ShoppingCart shoppingCart;

    protected Order(Parcel in) {
        idOrder = in.readString();
        date = in.readString();
        state = State.valueOf(in.readString());
        name = in.readString();
        shoppingCart = in.readParcelable(ShoppingCart.class.getClassLoader());
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(idOrder);
        parcel.writeString(date);
        parcel.writeString(state.name());
        parcel.writeString(name);
        parcel.writeParcelable(shoppingCart, i);
    }
    public Order(ShoppingCart shoppingCart){
        this.shoppingCart = shoppingCart;
        createOrderDate();
    }

    public Order(){
        createOrderDate();
    }

    void createOrderDate(){
//        SimpleDateFormat idFormatter = new SimpleDateFormat("yyyyMMdd/HHmmss");
//        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//        Date date = new Date();
//        this.idOrder = "DH" + idFormatter.format(date);
//        this.date = dateFormatter.format(date);
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public String getStateStr(){
        return (this.state == State.NEW) ? "Đơn hàng mới" : "Đơn hàng đã giao";
    }

    public int getTotalQuantity(){
        return (this.shoppingCart != null) ? shoppingCart.getTotalQuantity() : 0;
    }

    public String getTotalPriceFormat(){
        return (this.shoppingCart != null) ? shoppingCart.getTotalPriceFormat() : "0 VND";
    }

    public void remove(){

    }

    @Override
    public String toString() {
        return "Order{" +
                "idOrder='" + idOrder + '\'' +
                ", date='" + date + '\'' +
                ", state=" + state +
                ", name='" + name + '\'' +
                ", shoppingCart=" + shoppingCart +
                '}';
    }
}
