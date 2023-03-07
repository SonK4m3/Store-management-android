package com.example.storeapp.Activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.storeapp.Fragments.HomePageFragment;
import com.example.storeapp.R;
import com.example.storeapp.ViewModels.MainAdapter;
import com.example.storeapp.databinding.MainLayoutBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class MainActivity extends AppCompatActivity {
    private MainLayoutBinding binding = null;

    private String title = "Trang chủ";
    private int[] icon_pager = {
            R.drawable.ic_home_page,
            R.drawable.ic_menu,
            R.drawable.ic_customer
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MainLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 1. Create page view Adapter
        MainAdapter adapter = new MainAdapter(getSupportFragmentManager(), getLifecycle());
        // 2. update adapter to viewpager
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        title = "Trang chủ";
                        break;
                    case 1:
                        title = "Đơn hàng";
                        break;
                    case 2:
                        title = "Khách hàng";
                        break;
                }
                setTitle(title);
            }
        });
        // 3. update icon and content to tab layout
        new TabLayoutMediator(binding.tabLayout, binding.viewPager,
                ((tab, position) -> {
                    switch (position){
                        case 0:
                            title = "Trang chủ";
                            break;
                        case 1:
                            title = "Đơn hàng";
                            break;
                        case 2:
                            title = "Khách hàng";
                            break;
                    }
                    tab.setText(title);
                    tab.setIcon(icon_pager[position]);
                })).attach();
    }

    public ViewPager2 getViewPager(){
        return binding.viewPager;
    }
}
