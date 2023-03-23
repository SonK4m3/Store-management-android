package com.example.storeapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.storeapp.Activities.MainActivity;
import com.example.storeapp.Models.Customer;
import com.example.storeapp.Models.Item;
import com.example.storeapp.Models.OnCalledApi;
import com.example.storeapp.ViewModels.CustomerAdapter;
import com.example.storeapp.databinding.CustomerLayoutBinding;

import java.util.ArrayList;
import java.util.List;


public class CustomerFragment extends Fragment implements CustomerAdapter.OnClickCustomerListener, AddNewCustomerDialogFragment.OnActionCustomerListener, OnCalledApi {
    public interface OnSelectCustomerListener {
        void onSendCurrentCustomer(Customer currCustomer, String action);
    }
    public static final int ACTION_CUSTOMER = 1;
    private boolean isChooseCustomer = false;
    private CustomerLayoutBinding binding = null;
    private ArrayList<Customer> customerList = null;
    private ArrayList<Customer> previousCustomerList = null;
    private Customer currentCustomer = null;
    private OnSelectCustomerListener onSelectCustomerListener = null;
    private CustomerAdapter adapter = null;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            onSelectCustomerListener = (OnSelectCustomerListener) context;
            ((MainActivity) getActivity()).setOnCalledApi(this, 1);
        } catch (ClassCastException e) {
            throw new ClassCastException(context + " must implement OnDataTransferListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customerList = new ArrayList<>();
        previousCustomerList = new ArrayList<>();
        Log.d("AAA", "create customer list");
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

        // 1.
        // 2.
        setCurrentCustomerView();

        adapter = new CustomerAdapter(customerList, getContext());
        adapter.setOnClickCustomerListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.customerRecyclerview.setAdapter(adapter);
        binding.customerRecyclerview.setLayoutManager(linearLayoutManager);

        binding.searchBtn.setOnClickListener(v -> {
            String searchText = binding.searchingText.getText().toString();
            ArrayList<Customer> filter = Customer.findCustomer(searchText, previousCustomerList);
            customerList.clear();
            customerList.addAll(filter);
            adapter.notifyDataSetChanged();
            Log.d("AAA", searchText + " " + Integer.toString(customerList.size()));

        });

        binding.addCustomerBtn.setOnClickListener(v -> {
            Customer nCustomer = new Customer();
            showAddCustomerDialog(nCustomer, AddNewCustomerDialogFragment.ADD_CUSTOMER, -1);
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    void showCustomerList(boolean isShow){
        if(isShow){
            binding.loadingCustomer.setVisibility(View.GONE);
            binding.customerRecyclerview.setVisibility(View.VISIBLE);
        }else{
            binding.loadingCustomer.setVisibility(View.VISIBLE);
            binding.customerRecyclerview.setVisibility(View.GONE);
        }
    }

    void setCurrentCustomerView(){
        if(currentCustomer != null){
            binding.notificationCurrentCustomer.setVisibility(View.GONE);
            binding.currentCustomerName.setVisibility(View.VISIBLE);
            binding.currentCustomerAddress.setVisibility(View.VISIBLE);
            binding.currentCustomerName.setText(currentCustomer.getName());
            binding.currentCustomerAddress.setText(currentCustomer.getAddress());
        } else {
            binding.notificationCurrentCustomer.setVisibility(View.VISIBLE);
            binding.currentCustomerName.setVisibility(View.GONE);
            binding.currentCustomerAddress.setVisibility(View.GONE);
        }
    }

    void sendCurrentCustomerToActivity(String action){
        if(onSelectCustomerListener != null){
            onSelectCustomerListener.onSendCurrentCustomer(currentCustomer, action);
        }
    }

    void showAddCustomerDialog(Customer customer, String action, int position){
        AddNewCustomerDialogFragment addNewCustomerDialogFragment = AddNewCustomerDialogFragment.getInstance(customer, action, position);
        addNewCustomerDialogFragment.show(getFragmentManager(), AddNewCustomerDialogFragment.class.toString());
        addNewCustomerDialogFragment.setTargetFragment(CustomerFragment.this, ACTION_CUSTOMER);
    }

    @Override
    public void onSelectCustomer(int position) {
        currentCustomer = customerList.get(position);
        setCurrentCustomerView();
            //1.1 check is choose customer or not
            if(!isChooseCustomer){
                // 1.2 visibility item list
                isChooseCustomer = true;
            }
            sendCurrentCustomerToActivity(MainActivity.BACK_HOME_LIST_VIEW);
    }

    @Override
    public void onSelectNameCustomer(int position) {
        showAddCustomerDialog(customerList.get(position), AddNewCustomerDialogFragment.UPDATE_CUSTOMER, position);
    }

    @Override
    public void onAddCustomer(Customer customer) {
        // 1. if this id customer is exist
        for(Customer c : previousCustomerList){
            if(c.getId().equals(customer.getId())){
                Toast.makeText(getContext(), "This customer is exist", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        // 2. else add new customer
        previousCustomerList.add(customer);
        customerList.clear();
        customerList.addAll(previousCustomerList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onUpdateCustomer(Customer customer, int position) {
        // 1. check customer in list or not
        if(-1 < position && position < customerList.size()) {
            String old_id = customerList.get(position).getId();
            // 1.1 check new id is exist or not
            for(Customer c : previousCustomerList){
                if(c.getId().equals(customer.getId()) && !old_id.equals(customer.getId())){
                    Toast.makeText(getContext(), "This id customer is exist", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            // 2 else update customer
            // 2.1 if this customer is current customer, update view and send notify to main activity
            if(customerList.get(position).equals(currentCustomer)){
                currentCustomer = customer;
                setCurrentCustomerView();
                sendCurrentCustomerToActivity(MainActivity.STAY_CUSTOMER_VIEW);
            }
            // 2.2 remove in previous list
            for(int i = 0; i < previousCustomerList.size(); i++){
                if(previousCustomerList.get(i).getId().equals(old_id)){
                    previousCustomerList.remove(i);
                    previousCustomerList.add(i, customer);
                    break;
                }
            }
            // 2.3 update in list is shown in filter view
            customerList.remove(position);
            customerList.add(position, customer);
            adapter.notifyItemRangeChanged(position, customerList.size());
        }
    }

    @Override
    public void onRemoveCustomer(int position) {
        // 1. check customer is in list or not
        if(-1 < position && position < customerList.size()) {
            // 1.1 reomve customer in previous list
            String old_id = customerList.get(position).getId();
            for(int i = 0; i < previousCustomerList.size(); i++){
                if(previousCustomerList.get(i).getId().equals(old_id)){
                    previousCustomerList.remove(i);
                }
            }
            // 1.2 if customer is current customer, set current customer is null and notify to main activity
            if(customerList.get(position).equals(currentCustomer)){
                currentCustomer = null;
                setCurrentCustomerView();
                sendCurrentCustomerToActivity(MainActivity.STAY_CUSTOMER_VIEW);
            }
            customerList.remove(position);
            adapter.notifyItemRemoved(position);
            adapter.notifyItemRangeChanged(position, customerList.size());
        }
    }

    @Override
    public void onGetItemsSuccess(List<Item> items) {
        return;
    }

    @Override
    public void onGetCustomerSuccess(List<Customer> customers) {
        Log.d("AAA", "from customer: " + Integer.toString(customers.size()));
        // 1. customer list is list filter to show in recyclerview
        // 2. previous list is list contain all customer
        previousCustomerList.clear();
        previousCustomerList.addAll(customers);
        customerList.clear();
        customerList.addAll(previousCustomerList);
        adapter.notifyDataSetChanged();
        showCustomerList(!customerList.isEmpty());
    }
}
