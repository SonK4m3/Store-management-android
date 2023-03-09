package com.example.storeapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.storeapp.Models.Item;
import com.example.storeapp.R;
import com.example.storeapp.ViewModels.ItemAdapter;
import com.example.storeapp.ViewModels.ItemOffsetDecoration;
import com.example.storeapp.databinding.ItemListLayoutBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeListFragment extends Fragment {

    private ItemListLayoutBinding binding = null;
    private List itemList = null;
    private int COL = 2;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ItemListLayoutBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        itemList = new ArrayList<Item>();

        for(int i = 0; i < 5; i++){
            itemList.add(new Item());
        }

        ItemAdapter adapter = new ItemAdapter(itemList, getContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), COL);
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
