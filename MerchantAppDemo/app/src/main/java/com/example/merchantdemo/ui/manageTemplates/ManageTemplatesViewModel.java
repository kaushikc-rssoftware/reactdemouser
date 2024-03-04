package com.example.merchantdemo.ui.manageTemplates;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.merchantdemo.dataClass.ManageTemplatesData;

import java.util.ArrayList;
import java.util.List;

public class ManageTemplatesViewModel extends ViewModel {
    private MutableLiveData<List<ManageTemplatesData>> templateList = new MutableLiveData<>();
    List<ManageTemplatesData> currentList = new ArrayList<>();

    public LiveData<List<ManageTemplatesData>> getTemplateList() {
//        add any previous static data
        return templateList;
    }

    public void addTemplate(ManageTemplatesData template) {
//        List<ManageTemplatesData> currentList = templateList.getValue();
//        if (currentList == null) {
//            currentList = new ArrayList<>();
//        }
        currentList.add(template);
        templateList.setValue(currentList);
    }

    public void deleteTemplate(int position) {
        if (position >= 0 && position < currentList.size()) {
            currentList.remove(position);
            templateList.setValue(currentList);
        }
    }

    public void updateTemplate(int position, ManageTemplatesData updateTemplate) {
//        List<ItemData> currentItems = itemList.getValue();
//        if (currentItems != null && position >= 0 && position < currentItems.size()) {
        // Update the item's data at the specified position
        currentList.set(position, updateTemplate);
        // Notify observers of the LiveData
        templateList.setValue(currentList);

    }
}
