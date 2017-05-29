package com.example.gabor.mybudget.Model.Entities;

import android.os.Parcel;

/**
 * Created by Gabor on 2017. 05. 30..
 */

public class CategoryAndValue extends Category {
    private long value;
    private boolean isIncome;

    public CategoryAndValue(long id, String name, long value, boolean isIncome) {
        super(id, name);
        this.value = value;
        this.isIncome = isIncome;
    }

    public long getValue() {
        return value;
    }

    public boolean isIncome() {
        return isIncome;
    }

    protected CategoryAndValue(Parcel in) {
        super(in);
    }
}
