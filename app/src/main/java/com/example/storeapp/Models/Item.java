package com.example.storeapp.Models;

import java.util.Random;

public class Item {

    static int number = 0;

    String name = "Tên sản phẩm";
    int price = 0;

    public Item(){
        number += 1;
        createItem();
    }

    void createItem(){
        this.name = this.name + " " + Integer.toString(number);
        this.price = new Random().nextInt(1000000);
    }

    public String getName(){
        return this.name;
    }

    public String getPriceAsString(){
        return parceInt(this.price) + " VND";
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
