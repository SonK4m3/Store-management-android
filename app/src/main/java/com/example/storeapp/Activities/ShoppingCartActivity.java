package com.example.storeapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.fragment.app.FragmentTransaction;

import com.example.storeapp.Fragments.ShoppingCartListFragment;
import com.example.storeapp.Models.Item;
import com.example.storeapp.Models.Order;
import com.example.storeapp.Models.ShoppingCart;
import com.example.storeapp.databinding.ShoppingCartLayoutBinding;

import java.util.ArrayList;

public class ShoppingCartActivity extends AppCompatActivity implements ShoppingCartListFragment.OnDataTransferListener {
    private ShoppingCartLayoutBinding binding = null;
    private boolean isChoosingItem = false;
    private ShoppingCart shoppingCart = null;
    private Order order = null;
    private Bundle shoppingCartData = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ShoppingCartLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // 1. app bar setting
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Giỏ Hàng");

        // 2. get intent
        shoppingCartData = getIntent().getExtras();
        isChoosingItem = shoppingCartData.getBoolean(MainActivity.ITEM_CHOOSING);
        shoppingCart = shoppingCartData.getParcelable(MainActivity.SEND_SHOPPING_CART);

        if(shoppingCart != null)
            Log.d("AAA", "onCreate shopping cart: " + Integer.toString(shoppingCart.getCustomer().getShoppingCart().getItemList().size()));

        // 3. get item list recyclerview fragment
        ShoppingCartListFragment childrenFragment = new ShoppingCartListFragment();
        childrenFragment.setArguments(shoppingCartData);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(binding.fragmentContainer.getId(), childrenFragment).commitNow();
        // 4. Show 3. if has chose items
        setViewList(isChoosingItem);
        // 4.1 if did not chose item back to main activity
        binding.notificationChooseItem.setOnClickListener(v -> {
            sendDataToMain();
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                sendDataToMain();
                return true;
            default: break;
        }
        return super.onOptionsItemSelected(item);
    }
    /*
    make list shopping carts visible and notification gone and gainsay them
     */
    public void setViewList(boolean ok) {
        if (ok) {
            binding.notification.setVisibility(View.GONE);
            binding.fragmentContainer.setVisibility(View.VISIBLE);
        } else {
            binding.notification.setVisibility(View.VISIBLE);
            binding.fragmentContainer.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClearShoppingCart() {
        //1. when remove all item cart, change isChoosingItem to false -> update view to notification visible
        Log.d("AAA", "Clear all item");
        shoppingCart.clear();
        isChoosingItem = false;
        setViewList(false);
    }

    @Override
    public void onRemoveShoppingCart(ArrayList<Pair<Item, Integer>> cartItemList) {
        //1. when remove item cart, re update cart item list
        Log.d("AAA", "remove item, size list contain: " + Integer.toString(cartItemList.size()));
        //1.1 new list is remove removed-item, re-assign to old list
        this.shoppingCart.setItemList(cartItemList);
        //1.2 if remove last item -> set to is not chose item
        if(cartItemList.isEmpty()) isChoosingItem = false;
        setViewList(isChoosingItem);
    }

    @Override
    public void onCreateOrder() {
        // 1. create new order then clear all shoping cart item
        Order nOrder = new Order(shoppingCart.copy());
        ShoppingCart spc = nOrder.getShoppingCart();
        order = nOrder;

        Log.d("AAA", "create new order, owner: " + order.toString());
        shoppingCart.clear();
        isChoosingItem = false;
        sendDataToMain();
    }

    void sendDataToMain(){
        Intent data = getIntent();
        shoppingCartData.putParcelable(MainActivity.SEND_SHOPPING_CART, shoppingCart);
        shoppingCartData.putBoolean(MainActivity.ITEM_CHOOSING, isChoosingItem);
        shoppingCartData.putParcelable(MainActivity.SEND_CREATE_ORDER, order);
        data.putExtras(shoppingCartData);
        setResult(MainActivity.SHOPPING_CART, data);
        finish();
    }
}
