package com.example.storeapp.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.storeapp.databinding.LoginLayoutBinding;

public class LoginActivity extends AppCompatActivity {

    private LoginLayoutBinding binding = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LoginLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 1. Check inform user then to login
        binding.loginBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
//            Bundle bundle = new Bundle();

            // 1.1 if login successful, go to main activity
            startActivity(intent);
        });
    }
}
