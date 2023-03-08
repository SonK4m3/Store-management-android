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
import com.example.storeapp.R;
import com.example.storeapp.databinding.HomeLayoutBinding;
import com.example.storeapp.databinding.ItemListLayoutBinding;
import com.example.storeapp.databinding.OrderListLayoutBinding;

public class HomePageFragment extends Fragment {
    private static final int CUSTOMER_PAGE_ID = 2;
    private boolean CHOOSE_CUSTOMER = false;
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
            viewPager2.setCurrentItem(CUSTOMER_PAGE_ID, false);
            // 2.2 check is choose customer or not
            CHOOSE_CUSTOMER = true;
            // 2.3 visiability item list
            homeLayoutBinding.notificationSelectCustomer.setVisibility(View.GONE);
            homeLayoutBinding.fragmentContainer.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        homeLayoutBinding = null;
    }
}
