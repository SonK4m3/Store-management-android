package com.example.storeapp.Models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Order {
    String idOrder = "DH";
    String date = "00:00:00";
    enum State {
        NEW, OLD
    }
    State state = State.NEW;
    String name = "hai vinh";
    float quantity = 0.0f;
    int total = 1000;

    public Order(){
        createOrder();
    }

    void createOrder(){
        Random ran = new Random();
//        SimpleDateFormat idFormatter = new SimpleDateFormat("ddMMyyyy/HHmmss");
//        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//        Date date = new Date();
//        this.idOrder = "DH" + idFormatter.format(date);
//        this.date = dateFormatter.format(date);
        this.quantity = ran.nextFloat() * 1000;
        this.total = ran.nextInt(2000000);
    }

    public String getIdOrder(){
        return this.idOrder;
    }

    public String getDate(){
        return this.date;
    }

    public String getName(){
        return this.name;
    }

    public String getTotal(){
        String result = "24,700,000 VND";
        return result;
    }

    public String getQuantity(){
        return Float.toString(this.quantity);
    }

    public String getState(){
        return (this.state == State.NEW) ? "Đơn hàng mới" : "Đơn hàng cũ";
    }
}
