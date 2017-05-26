package com.example.gabor.mybudget.Presenter.Utils;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.example.gabor.mybudget.Model.Database.CategoryDatabaseHandler;
import com.example.gabor.mybudget.Model.Database.ItemDatabaseHandler;

/**
 * Created by Gabor on 2017. 05. 21..
 */

// TODO Navigation drawer
public abstract class SignedInAppCompatActivity extends BudgetAppCompatActivity {
    protected ItemDatabaseHandler mItemDBHandler;
    protected CategoryDatabaseHandler mCategoryDBHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mItemDBHandler = new ItemDatabaseHandler(this);
        mCategoryDBHandler = new CategoryDatabaseHandler(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
    }
}
