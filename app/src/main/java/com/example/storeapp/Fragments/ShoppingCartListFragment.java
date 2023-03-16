package com.example.storeapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.storeapp.Activities.MainActivity;
import com.example.storeapp.Activities.ShoppingCartActivity;
import com.example.storeapp.Models.Customer;
import com.example.storeapp.Models.Item;
import com.example.storeapp.Models.Order;
import com.example.storeapp.Models.ShoppingCart;
import com.example.storeapp.ViewModels.CartItemAdapter;
import com.example.storeapp.databinding.ShoppingCartItemListLayoutBinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ShoppingCartListFragment extends Fragment {
    private ShoppingCartItemListLayoutBinding binding = null;
    private CartItemAdapter adapter = null;
    private ShoppingCart shoppingCart = null;
    private boolean isChoosingItem = false;
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

        binding.totalQuantityText.setText(Integer.toString(shoppingCart.getTotalQuantity()));
        binding.totalPriceText.setText(shoppingCart.getTotalPriceFormat());

        ArrayList<Pair<Item, Integer>> cartItemList = shoppingCart.getItemList();

        adapter = new CartItemAdapter(cartItemList, getContext());
        adapter.setOnClickCartItem(new CartItemAdapter.OnClickCartItem() {
            @Override
            public void onRemoveItemCart() {
                shoppingCart.setItemList(cartItemList);
                binding.totalQuantityText.setText(Integer.toString(shoppingCart.getTotalQuantity()));
                binding.totalPriceText.setText(shoppingCart.getTotalPriceFormat());
                if(mDataTransferListener != null){
                    mDataTransferListener.onRemoveShoppingCart(cartItemList);
                }
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(linearLayoutManager);

        binding.removeAll.setOnClickListener(v -> {
            clearCartItems();
        });

        binding.createOrder.setOnClickListener(v -> {
            // send list item to order
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

    void sendBooleanToActivity(boolean data){
        if(mDataTransferListener != null){
            mDataTransferListener.onBooleanTransfer(data);
        }
    }

    void createOrder(){
        if(mDataTransferListener != null){
            mDataTransferListener.onCreateOrder();
        }
    }
    public interface OnDataTransferListener {
        void onBooleanTransfer(boolean data);
        void onClearShoppingCart();
        void onRemoveShoppingCart(ArrayList<Pair<Item, Integer>> cartItemList);
        void onCreateOrder();
    }
}
