package com.example.storeapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.storeapp.Activities.ItemDetailActivity;
import com.example.storeapp.Activities.MainActivity;
import com.example.storeapp.Models.Item;
import com.example.storeapp.R;
import com.example.storeapp.ViewModels.ItemAdapter;
import com.example.storeapp.ViewModels.ItemOffsetDecoration;
import com.example.storeapp.databinding.ItemListLayoutBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeListFragment extends Fragment {
    private ItemListLayoutBinding binding = null;
    private ItemAdapter adapter;
    private List<Item> itemList = null;
    private int LAYOUT_COLUMN = 2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ItemListLayoutBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 1. get list item
        itemList = Item.createListItem(10);
        // 1.1 create item adapter, item offset, layout
        adapter = new ItemAdapter(itemList, getActivity());
        adapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // 1. click item to show more detail
                // 1.1 create item detail activity
                Intent intent = new Intent(getActivity(), ItemDetailActivity.class);
                intent.putExtra(MainActivity.ITEM_CHOOSING, ((MainActivity) getActivity()).getIsChooseItem());

                Bundle bundle = new Bundle();
                bundle.putParcelable(MainActivity.SEND_ITEM, itemList.get(position));
                intent.putExtras(bundle);
                // 1.2 listen result when add, remove or order item
                getActivity().startActivityForResult(intent, MainActivity.ITEM_DETAIL);
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), LAYOUT_COLUMN);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getContext(), R.dimen.item_spacing);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(gridLayoutManager);
        binding.recyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}