package com.example.storeapp.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.storeapp.Activities.MainActivity;
import com.example.storeapp.Models.Customer;
import com.example.storeapp.R;
import com.example.storeapp.databinding.CustomerInforLayoutBinding;

public class AddNewCustomerDialogFragment extends DialogFragment {
    public interface OnActionCustomerListener {
        void onAddCustomer(Customer customer);
        void onUpdateCustomer(Customer customer, int position);
        void onRemoveCustomer(int position);
    }
    public static final String ADD_CUSTOMER = "add customer";
    public static final String UPDATE_CUSTOMER = "update customer";
    private String action = ADD_CUSTOMER;
    private CustomerInforLayoutBinding binding = null;
    private OnActionCustomerListener onActionCustomerListener = null;
    public static AddNewCustomerDialogFragment getInstance(Customer customer, String action, int position){
        AddNewCustomerDialogFragment addNewCustomerDialogFragment = new AddNewCustomerDialogFragment();
        Bundle args = new Bundle();
        args.putInt(MainActivity.SEND_CUSTOMER_POSITION, position);
        args.putParcelable(MainActivity.SEND_CUSTOMER, customer);
        args.putString(MainActivity.SEND_ACTION_CUSTOMER, action);
        addNewCustomerDialogFragment.setArguments(args);
        return addNewCustomerDialogFragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        try{
            onActionCustomerListener = (OnActionCustomerListener) getTargetFragment();
        }catch (ClassCastException e){
            Log.e("AAA", "onAttach: ClassCastException: " + e.getMessage());
        }
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        action = getArguments().getString(MainActivity.SEND_ACTION_CUSTOMER);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CustomerInforLayoutBinding.inflate(inflater, container, false);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        if(action.equals(UPDATE_CUSTOMER)){
            binding.updateCustomerBtn.setVisibility(View.VISIBLE);
            binding.removeCustomerBtn.setVisibility(View.VISIBLE);
            binding.addCustomerBtn.setVisibility(View.GONE);
        }

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Customer customer = getArguments().getParcelable(MainActivity.SEND_CUSTOMER);
        int position = getArguments().getInt(MainActivity.SEND_CUSTOMER_POSITION);
        binding.customerIdText.setText(customer.getId(), TextView.BufferType.EDITABLE);
        binding.customerNameText.setText(customer.getName(), TextView.BufferType.EDITABLE);
        binding.customerPhonenumberText.setText("079411111", TextView.BufferType.EDITABLE);
        binding.customerAddressText.setText(customer.getAddress(), TextView.BufferType.EDITABLE);
        binding.customerEmailText.setText("email", TextView.BufferType.EDITABLE);
        binding.customerDescriptionText.setText("description", TextView.BufferType.EDITABLE);

        binding.backBtn.setOnClickListener(v -> {
            dismiss();
        });

        binding.updateCustomerBtn.setOnClickListener(v -> {
            if(inputValidate()){
                if(onActionCustomerListener != null)
                    onActionCustomerListener.onUpdateCustomer(getCustomerInfor(), position);
                dismiss();
            }
            Toast.makeText(getContext(), "Update", Toast.LENGTH_SHORT).show();
        });
        binding.removeCustomerBtn.setOnClickListener(v -> {
            if(inputValidate()){
                if(onActionCustomerListener != null)
                    onActionCustomerListener.onRemoveCustomer(position);
                dismiss();
            }
            Toast.makeText(getContext(), "Remove", Toast.LENGTH_SHORT).show();
        });
        binding.addCustomerBtn.setOnClickListener(v -> {
            if(inputValidate()){
                if(onActionCustomerListener != null)
                    onActionCustomerListener.onAddCustomer(getCustomerInfor());
                dismiss();
            }
            Toast.makeText(getContext(), "Add", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        if(getDialog() != null){
            Window window = getDialog().getWindow();
            if(window != null){
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setDimAmount(0.5f);
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    boolean inputValidate(){
        String id = binding.customerIdText.getText().toString();
        String name  = binding.customerNameText.getText().toString();
        String number = binding.customerPhonenumberText.getText().toString();
        String address = binding.customerAddressText.getText().toString();
        if(id.equals("") || name.equals("") || number.equals("") || address.equals("")){
            Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin bắt buộc(*)", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    } 

    Customer getCustomerInfor(){
        String id = binding.customerIdText.getText().toString();
        String name  = binding.customerNameText.getText().toString();
        int number = Integer.parseInt(binding.customerPhonenumberText.getText().toString());
        String address = binding.customerAddressText.getText().toString();
        String email = binding.customerEmailText.getText().toString();
        String descrition = binding.customerDescriptionText.getText().toString();
        return new Customer(id, name, address);
    }
}
