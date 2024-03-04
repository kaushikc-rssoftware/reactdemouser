package com.example.merchantdemo.ui.createInvoice;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.merchantdemo.dataClass.ItemData;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CreateInvoiceViewModel extends ViewModel {

//    private final MutableLiveData<String> mText;
//
//    public CreateInvoiceViewModel() {
//        mText = new MutableLiveData<>();
//        mText.setValue("CREATE INVOICE");
//    }

//    public LiveData<String> getText() {
//        return mText;
//    }

    private MutableLiveData<List<ItemData>> itemList;

    public LiveData<List<ItemData>> getItemList() {
        if (itemList == null) {
            itemList = new MutableLiveData<>();
//            loadItems(); // Load initial data
        }
        return itemList;
    }

//    private void loadItems() {
//        // In a real application, you might fetch data from a repository or API.
//        // For simplicity, let's add some dummy data here.
//        List<ItemData> items = new ArrayList<>();
//        items.add(new ItemData("Item 1","","",""));
//        itemList.setValue(items);
//    }

    // Method to add a new item to the list
    public void addItem(ItemData item) {
        List<ItemData> currentItems = itemList.getValue();
        if (currentItems == null) {
            currentItems = new ArrayList<>();
        }
        currentItems.add(item);
        itemList.setValue(currentItems);
    }

    public void updateItem(int position, ItemData updatedItem) {
        List<ItemData> currentItems = itemList.getValue();
        if (currentItems != null && position >= 0 && position < currentItems.size()) {
            // Update the item's data at the specified position
            currentItems.set(position, updatedItem);
            // Notify observers of the LiveData
            itemList.setValue(currentItems);
        }
    }



    private MutableLiveData<BigDecimal> discountResult = new MutableLiveData<>();
    private MutableLiveData<BigDecimal> subTotalBeforeDiscount = new MutableLiveData<>();
    private MutableLiveData<BigDecimal> subTotalAfterDiscount = new MutableLiveData<>();
    private MutableLiveData<BigDecimal> taxResult = new MutableLiveData<>();
    private MutableLiveData<BigDecimal> totalAmountResult = new MutableLiveData<>();

    public LiveData<BigDecimal> getTaxResult() {
        return taxResult;
    }

    public LiveData<BigDecimal> getSubTotalBeforeDiscount() {
        return subTotalBeforeDiscount;
    }
    public LiveData<BigDecimal> getDiscountResult() {
        return discountResult;
    }

    public LiveData<BigDecimal> getTotalAmount() {
        return totalAmountResult;
    }

    public void calculateTax(List<ItemData> items,BigDecimal discount,BigDecimal tax) {
        if (items!=null) {
            List<BigDecimal> subtotalPrice = new ArrayList<>();
            for (int i = 0; i < items.size(); i++) {
                subtotalPrice.add(new BigDecimal(items.get(i).getItemAmountPrice()));
            }
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//                taxResult.setValue((subtotalPrice.stream().reduce(BigDecimal.ZERO, BigDecimal::subtract).multiply(BigDecimal.valueOf(discount))).divide(BigDecimal.valueOf(100)));
////                taxResult.setValue((subtotalPrice.stream().reduce(BigDecimal.ZERO, BigDecimal::add).multiply(BigDecimal.valueOf(tax))).divide(BigDecimal.valueOf(100)));
//                totalAmountResult.setValue(subtotalPrice.stream().reduce(BigDecimal.ZERO, BigDecimal::add).add(taxResult.getValue()));
//            }

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                Log.e("TAG", "calculateTax:dbcjkb "+subtotalPrice.stream().reduce(BigDecimal.ZERO, BigDecimal::add) );
                subTotalBeforeDiscount.setValue(subtotalPrice.stream().reduce(BigDecimal.ZERO, BigDecimal::add));
                discountResult.setValue((subtotalPrice.stream().reduce(BigDecimal.ZERO, BigDecimal::add).multiply(discount)).divide(BigDecimal.valueOf(100)));
                subTotalAfterDiscount.setValue(subtotalPrice.stream()
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        .subtract(discountResult.getValue()));
                taxResult.setValue(subTotalAfterDiscount.getValue().multiply(tax)
                        .divide(BigDecimal.valueOf(100)));
                totalAmountResult.setValue(subTotalAfterDiscount.getValue().add(taxResult.getValue()));
            }

        }
    }


    private MutableLiveData<String> imagePath = new MutableLiveData<>();

    public LiveData<String> getImagePath() {
        return imagePath;
    }

    public void setImagePath(String path) {
        imagePath.setValue(path);
    }

}