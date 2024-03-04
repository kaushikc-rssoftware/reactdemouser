package com.example.dfiapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dfiapp.dataClass.InvoiceItemData;
import com.example.dfiapp.databinding.FragmentHomeBinding;
import com.example.dfiapp.helperClass.AdapterInvoiceItems;

import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    AdapterInvoiceItems adapterInvoiceItems;
    HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        binding.itemRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterInvoiceItems = new AdapterInvoiceItems(homeViewModel.getItemList().getValue(),getContext());
        binding.itemRecyclerView.setAdapter(adapterInvoiceItems);


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}