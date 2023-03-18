package com.example.storeapp.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.example.storeapp.Activities.MainActivity;
import com.example.storeapp.Models.Customer;
import com.example.storeapp.databinding.HomeLayoutBinding;

public class HomePageFragment extends Fragment {
    private HomeLayoutBinding homeLayoutBinding = null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        homeLayoutBinding = HomeLayoutBinding.inflate(inflater, container, false);
        return homeLayoutBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 1. create item list fragment
        HomeListFragment childFragment = new HomeListFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(homeLayoutBinding.fragmentContainer.getId(), childFragment).commit();

        // 2. set action go to customer if does not choose customer
        homeLayoutBinding.changeToCustomer.setOnClickListener(v -> {
            // 2.1 go to customer fragment
            ViewPager2 viewPager2 = ((MainActivity) getActivity()).getViewPager();
            viewPager2.setCurrentItem(MainActivity.CUSTOMER_PAGE_ID, false);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        homeLayoutBinding = null;
    }

    public void showItemList(){
        homeLayoutBinding.notificationSelectCustomer.setVisibility(View.GONE);
        homeLayoutBinding.fragmentContainer.setVisibility(View.VISIBLE);
    }
}
