package com.example.storeapp.Activities;

import com.example.storeapp.Models.Order;

import java.util.ArrayList;

public interface OnCreateOrderListener {
    void onOrderCreated(ArrayList<Order> orders);
}
