package com.example.storeapp.Models;

import java.util.List;

public interface OnCalledApi {
    void onGetItemsSuccess(List<Item> items);
    void onGetCustomerSuccess(List<Customer> customers);
}
