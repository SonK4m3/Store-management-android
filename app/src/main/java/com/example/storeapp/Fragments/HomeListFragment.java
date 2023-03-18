package com.example.storeapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.storeapp.Activities.ItemDetailActivity;
import com.example.storeapp.Activities.MainActivity;
import com.example.storeapp.Models.Item;
import com.example.storeapp.Models.OnCalledApi;
import com.example.storeapp.R;
import com.example.storeapp.ViewModels.ItemAdapter;
import com.example.storeapp.ViewModels.ItemOffsetDecoration;
import com.example.storeapp.databinding.ItemListLayoutBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeListFragment extends Fragment implements OnCalledApi {
    private ItemListLayoutBinding binding = null;
    private ItemAdapter adapter = null;
    private ArrayList<Item> itemList = null;
    private final int LAYOUT_COLUMN = 2;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            // 1. assign from parent activity
            ((MainActivity) getActivity()).setOnCalledApi(this);
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }

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
//        itemList = Item.createListItem(10);
        itemList = new ArrayList<>();
        // 1.1 create item adapter, item offset, layout
        adapter = new ItemAdapter(itemList, getActivity());
        // 2. click item to show more detail
        adapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // 2.1 create item detail activity
                Intent intent = new Intent(getActivity(), ItemDetailActivity.class);
                Bundle mBundle = ((MainActivity) getActivity()).sendShoppingCartList();
                mBundle.putParcelable(MainActivity.SEND_ITEM, itemList.get(position));
                intent.putExtras(mBundle);
                // 2.2 listen result when add, remove or order item
                getActivity().startActivityForResult(intent, MainActivity.ITEM_DETAIL);
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), LAYOUT_COLUMN);
        // 3. create space between item
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

    @Override
    public void onGetItemsSuccess(List<Item> items) {
        itemList.clear();
        itemList.addAll(items);
        adapter.notifyDataSetChanged();
        if(!itemList.isEmpty()){
            binding.loadingItem.setVisibility(View.GONE);
            binding.recyclerView.setVisibility(View.VISIBLE);
        }
    }
}