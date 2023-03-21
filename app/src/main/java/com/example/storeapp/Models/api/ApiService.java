package com.example.storeapp.Models.api;

import com.example.storeapp.Models.Item;
import com.example.storeapp.Models.ItemsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("v3/fd6c0312-9763-4474-9678-9219e418bdb1")
    Call<List<Item>> getItems();

//    @GET("v3/18fdd193-0271-44b3-9f50-e243b76f31b8")
//    Call<ItemsResponse> getOrdersAndItems();
    @GET("v3/d873d150-fd5c-4dcf-aed1-8b38390957f7")
    Call<ItemsResponse> getOrdersAndCustomers();
}
