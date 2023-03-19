package com.example.storeapp.ViewModels;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.example.storeapp.Models.Item;
import com.example.storeapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ItemViewHolder>{
    public interface OnClickCartItem {
        void onRemoveItemCart();
    }
    private ArrayList<Pair<Item, Integer>> mItemList;
    private Context mContext;
    private OnClickCartItem mListener;

    public CartItemAdapter(ArrayList<Pair<Item, Integer>> itemList, Context context){
        mItemList = itemList;
        mContext = context;
    }

    public void setOnClickCartItem(OnClickCartItem listener){
        mListener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.item_cart_layout, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Pair<Item, Integer> pItem = (Pair<Item, Integer>) mItemList.get(position);
        if(!pItem.first.getImageUrl().equals(""))
            Picasso.get().load(pItem.first.getImageUrl()).into(holder.itemImage);
        holder.itemName.setText(pItem.first.getName());
        holder.quantity.setText(pItem.second.toString());
        holder.total.setText(pItem.first.getPriceAsString(pItem.second));

        holder.removeBtn.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "REMOVE " + position, Toast.LENGTH_SHORT).show();
            // 1. check if user tab is really fast, to handle user tab event
                if(position < mItemList.size() && mItemList.contains(mItemList.get(position))){
                    mItemList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, mItemList.size());

                    if(mListener != null){
                        mListener.onRemoveItemCart();
                    }
                }
        });
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        private View itemview;
        public ImageView itemImage;
        public TextView itemName;
        public TextView quantity;
        public TextView total;
        public ImageButton removeBtn;

        public ItemViewHolder(View itemView){
            super(itemView);
            itemview = itemView;
            itemImage = itemView.findViewById(R.id.itemImage);
            itemName = itemView.findViewById(R.id.itemName);
            quantity = itemView.findViewById(R.id.quantity_content);
            total = itemView.findViewById(R.id.total_content);
            removeBtn = itemView.findViewById(R.id.remove_btn);
        }
    }
}
