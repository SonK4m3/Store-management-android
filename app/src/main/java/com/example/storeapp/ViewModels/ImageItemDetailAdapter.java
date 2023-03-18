package com.example.storeapp.ViewModels;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.storeapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageItemDetailAdapter extends RecyclerView.Adapter<ImageItemDetailAdapter.ImageViewHolder> {

    private ArrayList<String> mImageList;
    private Context mContext;

    public ImageItemDetailAdapter(ArrayList<String> mImageList, Context mContext){
        this.mContext = mContext;
        this.mImageList = mImageList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.image_item_detail_layout, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        if(!mImageList.get(position).equals(""))
            Picasso.get().load(mImageList.get(position)).into(holder.image);
        else
            holder.image.setImageAlpha(R.drawable.ic_launcher_background);
    }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        private View viewitem;
        public ImageView image;
        public ImageViewHolder(View viewItem){
            super(viewItem);
            viewitem = viewItem;
            image = viewItem.findViewById(R.id.imageView);
        }
    }
}
