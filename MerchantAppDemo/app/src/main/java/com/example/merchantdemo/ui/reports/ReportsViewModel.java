package com.example.merchantdemo.ui.reports;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.merchantdemo.Dashbord;
import com.example.merchantdemo.R;
import com.example.merchantdemo.dataClass.ReportData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReportsViewModel extends ViewModel {

//    private final MutableLiveData<String> mText;
//
//    public ReportsViewModel() {
//        mText = new MutableLiveData<>();
//        mText.setValue("REPORT");
//    }
//
//    public LiveData<String> getText() {
//        return mText;
//    }


    private MutableLiveData<List<ReportData>> itemList;
    private List<ReportData> fullReportData;

    public MutableLiveData<List<ReportData>> getItemList() {
        if (itemList == null) {
            itemList = new MutableLiveData<>();
            fullReportData = generateReportList(); // Populate with sample data
            itemList.setValue(fullReportData);
        }
        return itemList;
    }


    private List<ReportData> generateReportList() {
        // In a real application, you might fetch data from a repository or API.
        // For simplicity, let's add some dummy data here.
        List<Integer> statuses = Arrays.asList(R.string.report_status_done, R.string.report_status_pending,R.string.report_status_dispatch_pending, R.string.report_status_partial_pending);

        List<ReportData> items = new ArrayList<>();
        if (!Dashbord.viewInvoiceDataSaved.isEmpty()) {
            for (int i = 0; i < Dashbord.viewInvoiceDataSaved.size(); i++) {
//                Log.e("tag","edhb2eb8778"+Dashbord.viewInvoiceDataSaved);
//                Log.e("tag","edhb2eb8778999"+Dashbord.viewInvoiceDataSaved.get(i).getCustomerName());
                int statusIndex = i % 4;
                items.add(new ReportData(Dashbord.viewInvoiceDataSaved.get(i).getInvoiceNo(), Dashbord.viewInvoiceDataSaved.get(i).getCustomerName(), Dashbord.viewInvoiceDataSaved.get(i).getTotalValue(), statuses.get(statusIndex)));
                Log.e("tag","edhb2eb8778999"+statuses.get(statusIndex));
            }
//        items.add(new ReportData("INV-5", "Liam Brown", "3567", R.string.report_status_pending));
//        items.add(new ReportData("INV-11", "Leo Martin", "324", R.string.report_status_done));
//        items.add(new ReportData("INV-9", "Olivia Wilson", "1099", R.string.report_status_dispatch_pending));
//        items.add(new ReportData("INV-15", "Amelia Gagnon", "440", R.string.report_status_partial_pending));

                return items;
        }else {
            return null;
        }
    }

    // Method to perform the search
    public void performSearch(String query) {
        // Simulate searching logic
        List<ReportData> filteredList = new ArrayList<>();
        for (ReportData item : fullReportData) {
            if (item.getCustomerName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(item);
            }
        }
        itemList.setValue(filteredList);

    }

    public void resetList() {
        itemList.setValue(fullReportData);
    }

}