package com.example.myapplication.Activities

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R
import com.example.myapplication.ViewModels.MainAdapter
import com.example.myapplication.databinding.MainLayoutBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity: AppCompatActivity() {

    private lateinit var binding: MainLayoutBinding

    private lateinit var title: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // 1. create adapter
        val adapter = MainAdapter(supportFragmentManager, lifecycle)
        // 2. update adapter to viewpager
        binding.viewPager.adapter = adapter
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when(position) {
                    0 -> title = "Trang chủ"
                    1 -> title = "Đơn hàng"
                    2 -> title = "Khách hàng"
                    else -> {title = "Trang chủ"}
                }
                setTitle(title)
            }
        })
        // 3. update icon and content to tab layout
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            run{
                when(position) {
                    0 -> {
                        title = "Trang chủ"
                    }
                    1 -> {
                        title = "Đơn hàng"
                    }
                    2 -> {
                        title = "Khách hàng"
                    }
                    else -> {
                        title = "Trang chủ"
                    }
                }
                tab.text = title
            }
        }.attach()
    }
}