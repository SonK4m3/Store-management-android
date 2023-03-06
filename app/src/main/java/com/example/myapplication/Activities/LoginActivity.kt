package com.example.myapplication.Activities

import android.content.Intent
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity

import com.example.myapplication.databinding.LoginLayoutBinding

class LoginActivity: AppCompatActivity() {

    private lateinit var binding: LoginLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Check inform user then to login
        binding.loginBtn.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            val bundle = Bundle()

            startActivity(intent)
        }
    }
}