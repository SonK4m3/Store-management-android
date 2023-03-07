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

public class HomePageFragment extends Fragment {
    private static final int CUSTOMER_PAGE_ID = 2;

    private HomeLayoutBinding binding = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = HomeLayoutBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.changeToCustomer.setOnClickListener(v -> {
            ViewPager2 viewPager2 = ((MainActivity) getActivity()).getViewPager();
            viewPager2.setCurrentItem(CUSTOMER_PAGE_ID, true);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
