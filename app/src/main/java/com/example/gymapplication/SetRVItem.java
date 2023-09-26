package com.example.gymapplication;

import android.util.Log;

public class SetRVItem {
    String setValue;
    String weightValue;

    boolean isClicked;


    public SetRVItem(String setValue, String weightValue) {
        this.setValue = setValue;
        this.weightValue = weightValue;
        this.isClicked = false;
    }

    public String getSetValue() {
        return setValue;
    }

    public void setSetValue(String setValue) {
        this.setValue = setValue;
    }

    public String getWeightValue() {
        return weightValue;
    }

    public void setWeightValue(String weightValue) {
        this.weightValue = weightValue;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;

        Log.v("Your Filter", "CLICKED");
    }

}
