package com.example.storeapp.Models;

import com.example.storeapp.Models.Order;

import java.util.ArrayList;

public interface OnCreateOrderListener {
    void onOrderCreated(ArrayList<Order> orders);
}
