package com.example.storeapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.storeapp.Activities.MainActivity;
import com.example.storeapp.Models.OnCreateOrderListener;
import com.example.storeapp.Models.Order;
import com.example.storeapp.ViewModels.OrderAdapter;
import com.example.storeapp.databinding.OrderListLayoutBinding;

import java.util.ArrayList;

public class OrderFragment extends Fragment implements OnCreateOrderListener {
    private OrderListLayoutBinding binding = null;
    private ArrayList<Order> orderList = new ArrayList<Order>();
    private OrderAdapter adapter = null;
    private OnOrderClickListener mDataTransferListener = null;

//    public static OrderFragment getInstance(MainActivity activity){
//        OrderFragment orderFragment = new OrderFragment();
//        activity.setOnOrderFragmentCreated(orderFragment);
//        return orderFragment;
//    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            // 1. assign from parent activity
            mDataTransferListener = (OnOrderClickListener) context;
            ((MainActivity) getActivity()).setOnOrderFragmentCreated(this);

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
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

        // 1. create order adapter
        adapter = new OrderAdapter(orderList, getContext());
        // 1.1 when click remove order, callback to order list in main activity to remove it
        adapter.setOnClickCartItem(new OrderAdapter.OnClickOder() {
            @Override
            public void onRemoveOrder(int position) {
                if(mDataTransferListener != null){
                    mDataTransferListener.onRemoveOrder(position);
                }
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
    }

    /*
    -> update order list when add more order to list
    -> notify to adapter re-update recycler view
     */
    @Override
    public void onOrderCreated(ArrayList<Order> orders) {
        orderList.clear();
        orderList.addAll(orders);
        adapter.notifyDataSetChanged();

        Log.d("AAA", "Update order recyclerView " + Integer.toString(orders.size()));
    }

    /*
    create callback to main activity when click to order
     */
    public interface OnOrderClickListener {
        void onRemoveOrder(int position);
    }
}
