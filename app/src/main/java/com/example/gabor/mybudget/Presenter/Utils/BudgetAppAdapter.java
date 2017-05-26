package com.example.gabor.mybudget.Presenter.Utils;

import android.content.Context;
import android.widget.BaseAdapter;

/**
 * Created by Gabor on 2017. 05. 26..
 */

public abstract class BudgetAppAdapter extends BaseAdapter {
    protected Context mContext;

    public BudgetAppAdapter(Context context) {
        this.mContext = context;
    }

    public abstract void notifyDBChanged();
}
