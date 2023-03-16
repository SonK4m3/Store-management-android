package com.example.storeapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.storeapp.Activities.MainActivity;
import com.example.storeapp.Models.Customer;
import com.example.storeapp.databinding.CustomerLayoutBinding;

import java.util.ArrayList;


public class CustomerFragment extends Fragment {
    public interface OnSelectCustomerListener {
        void onSendCurrentCustomer(Customer currCustomer);
    }
    private boolean isChooseCustomer = false;
    private CustomerLayoutBinding binding = null;
    private ArrayList<Customer> customers = null;
    private Customer currentCustomer = null;
    private OnSelectCustomerListener mlistener = null;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mlistener = (OnSelectCustomerListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnDataTransferListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CustomerLayoutBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // create Customer
        Customer newCustomer = Customer.createSample();

        binding.backHomeBtn.setOnClickListener(v -> {
            // 1 check is choose customer or not
            if(!isChooseCustomer){
                // 1.2 visiability item list
                isChooseCustomer = true;
            }
            currentCustomer = newCustomer;
            sendCurrentCustomerToActivity();
            // --> back to home list fragment
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    boolean getIsChooseCustomer(){
        return isChooseCustomer;
    }

    void sendCurrentCustomerToActivity(){
        if(mlistener != null){
            mlistener.onSendCurrentCustomer(currentCustomer);
        }
    }
}
