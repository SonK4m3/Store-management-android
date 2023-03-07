package com.example.storeapp.ViewModels;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.storeapp.Models.Order;
import com.example.storeapp.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List orderList;
    private Context mContext;

    public OrderAdapter(List orderList, Context mContext){
        this.orderList = orderList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View orderView = inflater.inflate(R.layout.order_layout, parent, false);
        return new OrderViewHolder(orderView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = (Order) orderList.get(position);

        holder.orderId.setText(order.getIdOrder());
        holder.orderDay.setText(order.getDate());
        holder.customerState.setText(order.getState());
        holder.customerName.setText(order.getName());
        holder.quantity.setText(order.getQuantity());
        holder.total.setText(order.getTotal());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }


    public class OrderViewHolder extends RecyclerView.ViewHolder {
        private View itemview;
        public AppCompatTextView orderId;
        public AppCompatTextView orderDay;
        public AppCompatTextView customerState;
        public AppCompatTextView customerName;
        public AppCompatTextView quantity;
        public AppCompatTextView total;
        public AppCompatButton cancelBtn;

        public OrderViewHolder(View itemView){
            super(itemView);
            itemview = itemView;
            orderId = itemView.findViewById(R.id.order_id);
            orderDay = itemView.findViewById(R.id.day_created_content);
            customerState = itemView.findViewById(R.id.state_order_content);
            customerName = itemView.findViewById(R.id.customer_name_content);
            quantity = itemView.findViewById(R.id.quantity_content);
            total = itemView.findViewById(R.id.total_content);
            cancelBtn = itemView.findViewById(R.id.cancel_btn);
            
            cancelBtn.setOnClickListener(v -> {
                Toast.makeText(v.getContext(), "HUY", Toast.LENGTH_SHORT).show();
            });

        }
    }
}
