package com.example.storeapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.storeapp.databinding.ShoppingCartLayoutBinding;

public class ShoppingCartActivity extends AppCompatActivity {
    private ShoppingCartLayoutBinding binding = null;

    private boolean CHOOSE_ITEM = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ShoppingCartLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // 1. app bar setting
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Giỏ Hàng");

        binding.notificationChooseItem.setOnClickListener(v -> {
            final Intent data = getIntent();
            setResult(Activity.RESULT_OK, data);
            finish();
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default: break;
        }
        return super.onOptionsItemSelected(item);
    }
}
