package com.example.storeapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.example.storeapp.R;
import com.example.storeapp.ViewModels.MainAdapter;
import com.example.storeapp.databinding.MainLayoutBinding;
import com.google.android.material.tabs.TabLayoutMediator;


public class MainActivity extends AppCompatActivity {

    private int CHOOSE_TIEM = 0;
    private static final int HOME_PAGE_ID = 0;
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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                Intent intentLogin = new Intent(this, LoginActivity.class);
                startActivity(intentLogin);
                finish();
                return true;
            case R.id.shopping_cart:
                Intent intentShopping = new Intent(this, ShoppingCartActivity.class);

                startActivityForResult(intentShopping, CHOOSE_TIEM);

                Toast.makeText(this, "Shopping cart", Toast.LENGTH_SHORT).show();
                return true;
            default:break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CHOOSE_TIEM){
            if(resultCode == Activity.RESULT_OK){
                binding.viewPager.setCurrentItem(HOME_PAGE_ID, false);
            }
        }
    }
}
