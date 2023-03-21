package com.example.storeapp.ViewModels;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.storeapp.Models.Customer;
import com.example.storeapp.R;

import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {
    /*
    callback to customerfragment when click into customer item view component
     */
    public interface OnClickCustomerListener{
        void onSelectCustomer(int position);

        void onSelectNameCustomer(int position);
    }

    private List mCustomerList;
    private Context mContext;
    private OnClickCustomerListener mCustomerListener;

    public CustomerAdapter(List customerList, Context context){
        this.mCustomerList = customerList;
        this.mContext = context;
    }

    public void setOnClickCustomerListener(OnClickCustomerListener onClickCustomerListener){
        this.mCustomerListener = onClickCustomerListener;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View customerView = inflater.inflate(R.layout.customer_item_layout, parent, false);
        return new CustomerViewHolder(customerView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        Customer customer = (Customer) mCustomerList.get(position);
        holder.customerName.setText(customer.getName());
        holder.customerAddress.setText(customer.getAddress());

        holder.customerName.setOnClickListener(v -> {
            if(mCustomerListener != null)
                mCustomerListener.onSelectNameCustomer(position);
        });

        holder.selectCustomerBtn.setOnClickListener(v -> {
            if(mCustomerListener != null)
                mCustomerListener.onSelectCustomer(position);
        });
    }

    @Override
    public int getItemCount() {
        return mCustomerList.size();
    }

    class CustomerViewHolder extends RecyclerView.ViewHolder {
        public TextView customerName;
        public TextView customerAddress;
        public AppCompatButton selectCustomerBtn;

        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            customerName = itemView.findViewById(R.id.customer_name);
            customerAddress = itemView.findViewById(R.id.customer_address);
            selectCustomerBtn = itemView.findViewById(R.id.select_customer_btn);
        }
    }
}
