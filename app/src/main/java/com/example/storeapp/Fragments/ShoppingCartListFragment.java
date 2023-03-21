package com.example.storeapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.storeapp.Activities.MainActivity;
import com.example.storeapp.Models.Item;
import com.example.storeapp.Models.ShoppingCart;
import com.example.storeapp.ViewModels.CartItemAdapter;
import com.example.storeapp.databinding.ShoppingCartItemListLayoutBinding;

import java.util.ArrayList;

public class ShoppingCartListFragment extends Fragment {
    private ShoppingCartItemListLayoutBinding binding = null;
    private CartItemAdapter adapter = null;
    private ShoppingCart shoppingCart = null;
    private Bundle mBundle = null;
    private  OnDataTransferListener mDataTransferListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mDataTransferListener = (OnDataTransferListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnDataTransferListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = getArguments();
        shoppingCart = mBundle.getParcelable(MainActivity.SEND_SHOPPING_CART);
        shoppingCart = (shoppingCart == null) ? new ShoppingCart() : shoppingCart;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ShoppingCartItemListLayoutBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 1. initial text view of quantity and price with contain shopping carts
        binding.totalQuantityText.setText(Integer.toString(shoppingCart.getTotalQuantity()));
        binding.totalPriceText.setText(shoppingCart.getTotalPriceFormat());
        // 2. create view adapter
        ArrayList<Pair<Item, Integer>> cartItemList = shoppingCart.getItemList();
        adapter = new CartItemAdapter(cartItemList, getContext());
        // 3. set cart item click listener
        adapter.setOnClickCartItem(new CartItemAdapter.OnClickCartItem() {
            @Override
            public void onRemoveItemCart() {
                // 3.1 update new list to shopping cart then re-update text view of quantity and price
                shoppingCart.setItemList(cartItemList);
                binding.totalQuantityText.setText(Integer.toString(shoppingCart.getTotalQuantity()));
                binding.totalPriceText.setText(shoppingCart.getTotalPriceFormat());
                // 3.2 callback to shopping cart activity to update state view and shopping cart list
                if(mDataTransferListener != null){
                    mDataTransferListener.onRemoveShoppingCart(cartItemList);
                }
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(linearLayoutManager);

        binding.removeAll.setOnClickListener(v -> {
            // 4. clear all cart items
            clearCartItems();
        });

        binding.createOrder.setOnClickListener(v -> {
            // 5. send list item to order
            createOrder();
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    void clearCartItems(){
        if(mDataTransferListener != null){
            shoppingCart.clear();
            adapter.notifyDataSetChanged();
            mDataTransferListener.onClearShoppingCart();
        }
    }

    void createOrder(){
        if(mDataTransferListener != null){
            mDataTransferListener.onCreateOrder();
        }
    }
    public interface OnDataTransferListener {
        void onClearShoppingCart();
        void onRemoveShoppingCart(ArrayList<Pair<Item, Integer>> cartItemList);
        void onCreateOrder();
    }
}
