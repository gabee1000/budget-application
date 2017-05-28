package com.example.gabor.mybudget.Presenter.Utils;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by Gabor on 2017. 05. 27..
 */

public abstract class BudgetAppArrayAdapter extends ArrayAdapter {

    public BudgetAppArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects) {
        super(context, resource, objects);
    }

    public abstract void notifyDBChanged();
}
