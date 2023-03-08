package com.example.storeapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.storeapp.Activities.MainActivity;
import com.example.storeapp.databinding.HomeLayoutBinding;
import com.example.storeapp.databinding.OrderListLayoutBinding;

public class HomePageFragment extends Fragment {
    private static final int CUSTOMER_PAGE_ID = 2;
    private boolean CHOOSE_CUSTOMER = false;
    private HomeLayoutBinding homeLayoutBinding = null;
    private OrderListLayoutBinding orderListLayoutBinding = null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        homeLayoutBinding = HomeLayoutBinding.inflate(inflater, container, false);
//        orderListLayoutBinding = OrderListLayoutBinding.inflate(inflater, container, false);
//        if(CHOOSE_CUSTOMER == false)
            return homeLayoutBinding.getRoot();
//        return orderListLayoutBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        homeLayoutBinding.changeToCustomer.setOnClickListener(v -> {
            ViewPager2 viewPager2 = ((MainActivity) getActivity()).getViewPager();
                CHOOSE_CUSTOMER = true;
//            binding.notificationSelectCustomer.setVisibility(View.INVISIBLE);
            viewPager2.setCurrentItem(CUSTOMER_PAGE_ID, false);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        homeLayoutBinding = null;
    }
}
