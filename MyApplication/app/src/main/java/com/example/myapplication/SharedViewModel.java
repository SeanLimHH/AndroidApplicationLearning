package com.example.myapplication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<String> sharedText = new MutableLiveData<>();

    public void setSharedText(String text) {
        sharedText.setValue(text);
    }

    public LiveData<String> getSharedText() {
        return sharedText;
    }

}
