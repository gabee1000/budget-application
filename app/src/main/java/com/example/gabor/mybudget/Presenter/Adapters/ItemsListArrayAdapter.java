package com.example.gabor.mybudget.Presenter.Adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.gabor.mybudget.Model.Database.ItemDatabaseHandler;
import com.example.gabor.mybudget.Model.Entities.Item;
import com.example.gabor.mybudget.Presenter.Utils.BudgetAppArrayAdapter;
import com.example.gabor.mybudget.Presenter.Utils.SignedInAppCompatActivity;

import java.util.List;

public class ItemsListArrayAdapter extends BudgetAppArrayAdapter {
    public List<Item> mItemList;
    private final ItemDatabaseHandler mItemDBHandler;

    public ItemsListArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Item> list) {
        super(context, resource, list);
        this.mItemList = list;
        this.mItemDBHandler = SignedInAppCompatActivity.itemDBHandler;
    }

    private void loadItemList() {
        mItemList.addAll(mItemDBHandler.getAllItems());
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return mItemList.get(position).getName();
    }

    @Override
    public void notifyDBChanged() {
        mItemList.clear();
        loadItemList();
        notifyDataSetChanged();
    }
}
