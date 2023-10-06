package com.example.myapplication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<String> sharedText = new MutableLiveData<>();

    private final MutableLiveData<ArrayList<String>> sharedItem = new MutableLiveData<>();

    public void setSharedText(String text) {
        sharedText.setValue(text);
    }

    public LiveData<String> getSharedText() {
        return sharedText;
    }


    public void setSharedItem(String text) {
        ArrayList<String> currentItems = sharedItem.getValue();

        // If the list is null, create a new one
        if (currentItems == null) {
            currentItems = new ArrayList<>();
        }

        // Add the new text to the list
        currentItems.add(text);

        // Set the updated list to the LiveData
        sharedItem.setValue(currentItems);
    }

    public LiveData<ArrayList<String>> getSharedItem() {
        return sharedItem;
    }


}
