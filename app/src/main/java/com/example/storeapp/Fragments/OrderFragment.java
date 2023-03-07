package com.example.storeapp.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.storeapp.Models.Order;
import com.example.storeapp.ViewModels.OrderAdapter;
import com.example.storeapp.databinding.OrderListLayoutBinding;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment {
    private OrderListLayoutBinding binding = null;
    private List orderList = null;
    private OrderAdapter adapter = null;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = OrderListLayoutBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        orderList = new ArrayList();
        for(int i = 1; i <= 5; i ++){
            orderList.add(new Order());
        }


        Log.d("AAA", String.valueOf(orderList.size()));

        adapter = new OrderAdapter(orderList, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
    }
}
