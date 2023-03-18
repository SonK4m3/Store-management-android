package com.example.storeapp.Models.api;

import com.example.storeapp.Models.Item;
import com.example.storeapp.Models.ItemsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("v3/0d91bcb2-ce00-4605-8698-50b80b09f2bc")
    Call<List<Item>> getItems();

    @GET("v3/18fdd193-0271-44b3-9f50-e243b76f31b8")
    Call<ItemsResponse> getOrdersAndItems();
}
