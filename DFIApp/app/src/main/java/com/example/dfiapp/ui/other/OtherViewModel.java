package com.example.dfiapp.ui.other;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OtherViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public OtherViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Other fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}