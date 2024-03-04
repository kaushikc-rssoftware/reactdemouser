package com.example.merchantdemo.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.merchantdemo.R;
import com.example.merchantdemo.databinding.FragmentHomeBinding;
import com.example.merchantdemo.databinding.FragmentViewInvoiceBinding;
import com.example.merchantdemo.util.Utility;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    private NavController navController;
    MaterialDatePicker<Pair<Long, Long>> picker;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy", Locale.getDefault());

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home, container, false);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_dashbord);


        binding.createInvoiceCv.setOnClickListener(view -> {
            navController.navigate(R.id.nav_create_invoice);
        });

        binding.manageTemplateCv.setOnClickListener(view -> {
            navController.navigate(R.id.nav_manage_templates);
        });

        binding.viewReportsCv.setOnClickListener(view -> {
            navController.navigate(R.id.nav_reports);
        });

        // Get the current date
        Calendar calendar = Calendar.getInstance();
        Long today = calendar.getTimeInMillis();

        // Create a Material Date Range Picker with default selection as today's date
        picker = MaterialDatePicker.Builder.dateRangePicker()
                .setTheme(R.style.CustomThemeOverlay_MaterialCalendar_Fullscreen)
                .setTitleText("SELECT A DATE RANGE")
                .setSelection(Pair.create(today, today)) // Set default selection to today's date
                .setCalendarConstraints(Utility.buildCalendarConstraints())
                .build();

        // Set listener for when the user selects a date range
        picker.addOnPositiveButtonClickListener(selection -> {
            binding.dateFilterHomeTv.setText( dateFormat.format(selection.first)+ " - "+dateFormat.format(selection.second));
        });

        Pair<Long, Long> selection = picker.getSelection();
        if (selection != null) {
            binding.dateFilterHomeTv.setText( dateFormat.format(selection.first)+ " - "+dateFormat.format(selection.second));
        }


        // You can show the picker later when an event occurs, for example, when a button is clicked
        // Show the Material Date Range Picker when a button is clicked or any other event
        binding.homeCalender.setOnClickListener(view -> {
            picker.show(getChildFragmentManager(), picker.toString());
        });




        return root;
    }

}