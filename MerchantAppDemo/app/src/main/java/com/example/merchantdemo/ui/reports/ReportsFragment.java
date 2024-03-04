package com.example.merchantdemo.ui.reports;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.merchantdemo.R;
import com.example.merchantdemo.dataClass.ReportData;
import com.example.merchantdemo.databinding.FragmentReportsBinding;
import com.example.merchantdemo.helperClass.AdapterCreateInvoiceCustomerItems;
import com.example.merchantdemo.helperClass.AdapterImportTemplate;
import com.example.merchantdemo.helperClass.AdapterManageTemplates;
import com.example.merchantdemo.helperClass.AdapterReport;
import com.example.merchantdemo.ui.createInvoice.CreateInvoiceFragment;
import com.example.merchantdemo.util.Utility;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ReportsFragment extends Fragment {

    private FragmentReportsBinding binding;
    private NavController navController;
    private AdapterReport adapterReport;
    ReportsViewModel reportsViewModel;
    MaterialDatePicker<Pair<Long, Long>> picker;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy", Locale.getDefault());


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        reportsViewModel = new ViewModelProvider(this).get(ReportsViewModel.class);

        binding = FragmentReportsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.reportRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapterReport = new AdapterReport();
        binding.reportRecyclerView.setAdapter(adapterReport);
        adapterReport.setItemList(reportsViewModel.getItemList().getValue(),getContext());

        adapterReport.setOnItemClickListener(new AdapterReport.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                Log.e("TAG", "onItemClick16737: "+position );
//                Toast.makeText(getContext(), "position ", Toast.LENGTH_LONG).show();
//                if (position==1){
                    Bundle bundle = new Bundle();
                    bundle.putString("viewInvoice", "withPayment");
                    bundle.putInt("position", position);
                    navController.navigate(R.id.action_navReports_to_navViewInvoice, bundle);
//                }
            }
        });


        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    reportsViewModel.resetList();
                } else {
                    reportsViewModel.performSearch(newText);
                }
                return true;
            }
        });

        reportsViewModel.getItemList().observe(this, items -> {
            adapterReport.setItemList(reportsViewModel.getItemList().getValue(),getContext());
            binding.reportRecyclerView.setAdapter(adapterReport);
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
            binding.dateFilterTv.setText( dateFormat.format(selection.first)+ " - "+dateFormat.format(selection.second));
        });

        Pair<Long, Long> selection = picker.getSelection();
        if (selection != null) {
            binding.dateFilterTv.setText( dateFormat.format(selection.first)+ " - "+dateFormat.format(selection.second));
}


        // You can show the picker later when an event occurs, for example, when a button is clicked
        // Show the Material Date Range Picker when a button is clicked or any other event
        binding.reportCalender.setOnClickListener(view -> {
            picker.show(getChildFragmentManager(), picker.toString());
        });




        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}