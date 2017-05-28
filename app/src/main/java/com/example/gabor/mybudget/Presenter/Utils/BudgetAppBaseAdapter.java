package com.example.gabor.mybudget.Presenter.Utils;

import android.content.Context;
import android.widget.BaseAdapter;

/**
 * Created by Gabor on 2017. 05. 26..
 */

public abstract class BudgetAppBaseAdapter extends BaseAdapter {
    protected Context mContext;

    public BudgetAppBaseAdapter(Context context) {
        this.mContext = context;
    }

    public abstract void notifyDBChanged();
}
