package com.example.storeapp.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ItemsResponse {
    @SerializedName("items") public ArrayList<Item> itemList = new ArrayList<>();
    @SerializedName("orders") public ArrayList<Order> orderList = new ArrayList<>();
}
