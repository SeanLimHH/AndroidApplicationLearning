package com.example.gymapplication;

public class SetModel {
    Integer id;
    String setValue;
    String weightValue;
    public SetModel(Integer id, String setValue, String weightValue) {
        this.id = id;
        this.setValue = setValue;
        this.weightValue = weightValue;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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



}
