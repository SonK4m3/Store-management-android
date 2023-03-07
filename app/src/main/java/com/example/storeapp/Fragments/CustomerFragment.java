package com.example.storeapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.storeapp.databinding.CustomerLayoutBinding;


public class CustomerFragment extends Fragment {
    private CustomerLayoutBinding binding = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CustomerLayoutBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
