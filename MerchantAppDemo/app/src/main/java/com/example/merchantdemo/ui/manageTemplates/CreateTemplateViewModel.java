package com.example.merchantdemo.ui.manageTemplates;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CreateTemplateViewModel extends ViewModel {

//    private final MutableLiveData<String> mText;
////    private final MutableLiveData<ManageTemplatesData[]> manageTemplatesData;
//
//    public CreateTemplateViewModel() {
//        mText = new MutableLiveData<>();
//        mText.setValue("CREATE NEW TEMPLATE");
//    }
//
//    public LiveData<String> getText() {
//        return mText;
//    }

    private MutableLiveData<String> imagePath = new MutableLiveData<>();

    public LiveData<String> getImagePath() {
        return imagePath;
    }

    public void setImagePath(String path) {
        imagePath.setValue(path);
    }
}
