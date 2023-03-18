package com.example.storeapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.storeapp.Models.Item;
import com.example.storeapp.Models.ItemCategory;
import com.example.storeapp.R;
import com.example.storeapp.ViewModels.ImageItemDetailAdapter;
import com.example.storeapp.databinding.ItemDetailLayoutBinding;

import java.util.ArrayList;
import java.util.List;

public class ItemDetailActivity extends AppCompatActivity{
    public static final int CREATE_ORDER = 931;
    public static final int ADD_TO_SHOPPING_CART = 932;
    private ItemDetailLayoutBinding binding = null;
    private ArrayList<ItemCategory> categories = null;
    private final int MIN_QUANTITY = 5;
    private int quantity = 5;
    private int totalQuantity = 0;
    private int totalPrice = 0;
    private Item item = null;
    private Bundle mBundle = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ItemDetailLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Chi tiết sản phẩm");
        // 1.
        mBundle = getIntent().getExtras();
        item = mBundle.getParcelable(MainActivity.SEND_ITEM);
        categories = item.getItemCategories();
        quantity = (categories.isEmpty()) ? 0 : categories.get(0).getQuantity();
        totalQuantity = (categories.isEmpty()) ? 0 : categories.get(0).getQuantity();
        totalPrice = item.getPrice(totalQuantity);
        binding.quantityText.setText(Integer.toString(quantity));
        binding.totalQuantityText.setText(Integer.toString(totalQuantity));
        binding.totalPrice.setText(Item.parceInt(totalPrice) + "VND");

        ArrayList<String> mImageList = new ArrayList<>();
        ArrayList<String> mCategoriesList = new ArrayList<>();

        for(int i = 0; i < categories.size(); i++){
            mCategoriesList.add(categories.get(i).getName());
            mImageList.add(categories.get(i).getImage_url());
        }

        binding.imageRecyclerView.setAdapter(new ImageItemDetailAdapter(
                mImageList, this
        ));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        linearLayoutManager.setSmoothScrollbarEnabled(false);
        binding.imageRecyclerView.setLayoutManager(linearLayoutManager);

        // 2. add spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, R.layout.v1_spinner_item, mCategoriesList);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.v1_spinner_drop_item);
        // attaching data adapter to spinner
        binding.spinner.setAdapter(dataAdapter);
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                changeQuantity(-totalQuantity + item.getItemCategories().get(i).getQuantity());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
        // 2.1 set event action when increase ord decrease quantity
        binding.decreaseQuantity.setOnClickListener(v -> {
            if(quantity > MIN_QUANTITY){
                changeQuantity(-1);
            }
        });
        binding.increaseQuantity.setOnClickListener(v -> {
            changeQuantity(1);
        });

        // 2.3 fill item inform
        binding.itemName.setText(item.getName());
        binding.itemPrice.setText(item.getPriceAsString());
        binding.itemDesciption.setText(item.getDescription());

        // 3. set event to create order and add item
        binding.addToCart.setOnClickListener(v -> {
            // 3.1 add item and back to home list
            Intent data = getIntent();
            mBundle.putParcelable(MainActivity.SEND_ITEM, item);
            mBundle.putInt(MainActivity.SEND_QUANTITY, quantity);
            data.putExtras(mBundle);
            setResult(ADD_TO_SHOPPING_CART, data);
            finish();
        });
        binding.createOrder.setOnClickListener(v -> {
            // 3.2 create new order and back to home list
            Intent data = getIntent();
            mBundle.putParcelable(MainActivity.SEND_ITEM, item);
            mBundle.putInt(MainActivity.SEND_QUANTITY, quantity);
            data.putExtras(mBundle);
            setResult(CREATE_ORDER, data);
            finish();
        });
    }

    void changeQuantity(int num){
        quantity += num;
        totalQuantity += num;
        totalPrice += num * item.getPrice();

        binding.quantityText.setText(Integer.toString(quantity));
        binding.totalQuantityText.setText(Integer.toString(totalQuantity));
        binding.totalPrice.setText(Item.parceInt(totalPrice) + " VND");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.order_cart_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                finish();
                return true;
            case R.id.shopping_cart:
                Intent intentShopping = new Intent(this, ShoppingCartActivity.class);
                intentShopping.putExtras(mBundle);
                startActivityForResult(intentShopping, MainActivity.CHOOSE_ITEM);
                return true;
            default:break;
        }

        return super.onOptionsItemSelected(item);
    }
}
