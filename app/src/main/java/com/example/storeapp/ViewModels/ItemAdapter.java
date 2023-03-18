package com.example.storeapp.ViewModels;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.storeapp.Activities.ItemDetailActivity;
import com.example.storeapp.Activities.MainActivity;
import com.example.storeapp.Models.Item;
import com.example.storeapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    private List itemList;
    private Context mContext;
    private Activity mActivity;
    private OnItemClickListener mListener;

    public ItemAdapter(List itemList, Activity activity){
        this.itemList = itemList;
        this.mContext = activity.getBaseContext();
        this.mActivity = activity;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.item_layout, parent, false);
        return new ItemViewHolder(itemView, mActivity);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = (Item) itemList.get(position);
        if(!item.getImageUrl().equals(""))
            Picasso.get().load(item.getImageUrl()).into(holder.itemImage);
        holder.itemName.setText(item.getName());
        holder.itemPrice.setText(item.getPriceAsString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener != null){
                    mListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

     class ItemViewHolder extends RecyclerView.ViewHolder {
        private View itemview;
        private Context mContext;
        private Activity mActivity;
        public TextView itemName;
        public TextView itemPrice;
        public ImageView itemImage;
        public ItemViewHolder(View itemView, Activity activity){
            super(itemView);
            itemview = itemView;
            mContext = activity.getBaseContext();
            mActivity = activity;
            itemName = itemView.findViewById(R.id.itemName);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            itemImage = itemView.findViewById(R.id.itemImage);
        }
    }
}
