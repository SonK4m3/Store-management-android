package com.example.storeapp.ViewModels;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStateManagerControl;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.storeapp.Activities.MainActivity;
import com.example.storeapp.Fragments.OrderFragment;
import com.example.storeapp.Fragments.CustomerFragment;
import com.example.storeapp.Fragments.HomePageFragment;

import java.util.List;

public class MainAdapter extends FragmentStateAdapter {
    private List<Fragment> fragmentList;

    public MainAdapter(List<Fragment> fragmentList, FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        this.fragmentList = fragmentList;

    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }
}
