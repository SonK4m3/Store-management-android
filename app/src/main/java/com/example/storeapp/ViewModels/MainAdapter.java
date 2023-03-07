package com.example.storeapp.ViewModels;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.storeapp.Fragments.OrderFragment;
import com.example.storeapp.Fragments.CustomerFragment;
import com.example.storeapp.Fragments.HomePageFragment;

public class MainAdapter extends FragmentStateAdapter {

    private static final int FRAGMENT_NUMBER = 3;

    public MainAdapter(FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new HomePageFragment();
            case 1:
                return new OrderFragment();
            case 2:
                return new CustomerFragment();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return FRAGMENT_NUMBER;
    }
}
