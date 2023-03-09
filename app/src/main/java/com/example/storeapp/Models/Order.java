package com.example.storeapp.Models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Order {
    String idOrder = "DH10012021/16:26:49";
    String date = "01/10/2021 16:26:49";
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
        return parceInt(this.total) + " VND";
    }

    public String getQuantity(){
        return Float.toString(this.quantity);
    }

    public String getState(){
        return (this.state == State.NEW) ? "Đơn hàng mới" : "Đơn hàng cũ";
    }

    String parceInt(int x){
        String res = "";
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
        return res;
    }
}
