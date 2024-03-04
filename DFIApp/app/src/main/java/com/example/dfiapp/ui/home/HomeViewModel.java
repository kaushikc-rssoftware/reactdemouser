package com.example.dfiapp.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dfiapp.dataClass.InvoiceItemData;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("INVOICE PRESENTMENT");
    }

    public LiveData<String> getText() {
        return mText;
    }



    private MutableLiveData<List<InvoiceItemData>> itemList;
    private List<InvoiceItemData> fullItemData;

    public MutableLiveData<List<InvoiceItemData>> getItemList() {
        if (itemList == null) {
            itemList = new MutableLiveData<>();
            fullItemData = generateInvoiceItemList(); // Populate with sample data
            itemList.setValue(fullItemData);
        }
        return itemList;
    }


    private List<InvoiceItemData> generateInvoiceItemList() {
        // In a real application, you might fetch data from a repository or API.
        // For simplicity, let's add some dummy data here.
        List<InvoiceItemData> items = new ArrayList<>();
        items.add(new InvoiceItemData("Chair","4","2000.00","200.00","2200.00"));
        items.add(new InvoiceItemData("Chair","4","2000.00","200.00","2200.00"));
        items.add(new InvoiceItemData("Chair","4","2000.00","200.00","2200.00"));
        items.add(new InvoiceItemData("Diner Set","2","3600.00","360.00","3960.00"));
        items.add(new InvoiceItemData("Diner Set","2","3600.00","360.00","3960.00"));
        items.add(new InvoiceItemData("Diner Set","2","3600.00","360.00","3960.00"));
        items.add(new InvoiceItemData("Diner Set","2","3600.00","360.00","3960.00"));
        items.add(new InvoiceItemData("Diner Set","2","3600.00","360.00","3960.00"));
        items.add(new InvoiceItemData("Folding sofa cum bed ","1","12000.00","1200.00","13200.00"));
        items.add(new InvoiceItemData("Folding sofa cum bed ","1","12000.00","1200.00","13200.00"));
        items.add(new InvoiceItemData("Folding sofa cum bed ","1","12000.00","1200.00","13200.00"));
        items.add(new InvoiceItemData("Folding sofa cum bed ","1","12000.00","1200.00","13200.00"));
        items.add(new InvoiceItemData("Folding sofa cum bed ","1","12000.00","1200.00","13200.00"));
        items.add(new InvoiceItemData("Folding sofa cum bed ","1","12000.00","1200.00","13200.00"));
        items.add(new InvoiceItemData("Other Item ","3","120000.00","12000.00","132000.00"));
        items.add(new InvoiceItemData("Other Item ","3","120000.00","12000.00","132000.00"));
            return items;
    }


}