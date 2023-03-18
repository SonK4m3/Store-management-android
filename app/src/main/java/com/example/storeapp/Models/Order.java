package com.example.storeapp.Models;

import android.icu.text.SimpleDateFormat;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Order implements Parcelable {
    public enum State {
        NEW, OLD
    }
    @SerializedName("id") private String idOrder = "DH01012023/000000";
    @SerializedName("date") private String date = "01/01/2023 00:00:00";
    @SerializedName("state") private State state = State.NEW;
    @SerializedName("customer_name") private String name = "TEN KHACH HANG";
    @SerializedName("total_quantity") private int totalQuantity = 0;
    @SerializedName("total_price") private int totalPrice = 0;
    private ShoppingCart shoppingCart;

    protected Order(Parcel in) {
        idOrder = in.readString();
        date = in.readString();
        state = State.valueOf(in.readString());
        name = in.readString();
        totalQuantity = in.readInt();
        totalPrice = in.readInt();
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
        parcel.writeInt(totalQuantity);
        parcel.writeInt(totalPrice);
        parcel.writeParcelable(shoppingCart, i);
    }
    public Order(ShoppingCart shoppingCart){
        this.shoppingCart = shoppingCart;
        this.name = shoppingCart.getCustomer().getName();
        createOrderDate();

        this.totalQuantity = (this.shoppingCart != null) ? shoppingCart.getTotalQuantity() : 0;
        this.totalPrice = (this.shoppingCart != null) ? shoppingCart.getTotalPrice() : 0;
    }

    public Order(){
        createOrderDate();
    }

    void createOrderDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        this.idOrder = "DH" + sdf.format(new Date()) + "/";
        this.date = dateFormatter.format(new Date());
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
        this.totalQuantity = (this.shoppingCart != null) ? shoppingCart.getTotalQuantity() : 0;
        this.totalPrice = (this.shoppingCart != null) ? shoppingCart.getTotalPrice() : 0;
    }

    public String getStateStr(){
        return (this.state == State.NEW) ? "Đơn hàng mới" : "Đơn hàng đã xác nhận";
    }

    public int getTotalQuantity(){
        return this.totalQuantity;
    }

    public int getTotalPrice() {
        return this.totalPrice;
    }

    public String getTotalPriceFormat(){
        return Item.parceInt(totalPrice) + " VND";
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
