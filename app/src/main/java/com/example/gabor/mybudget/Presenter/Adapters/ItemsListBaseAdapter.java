package com.example.gabor.mybudget.Presenter.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.gabor.mybudget.Model.Database.ItemDatabaseHandler;
import com.example.gabor.mybudget.Model.Entities.Item;
import com.example.gabor.mybudget.Presenter.Utils.BudgetAppBaseAdapter;
import com.example.gabor.mybudget.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gabor on 2017. 05. 26..
 */

// TODO Replaced with an ArrayAdapter! Can be deleted if its no longer needed.
public class ItemsListBaseAdapter extends BudgetAppBaseAdapter{
    private ItemDatabaseHandler mItemDBHandler;
    private List<Item> mItemList;

    public ItemsListBaseAdapter(Context context, ItemDatabaseHandler itemDBHandler) {
        super(context);
        this.mItemDBHandler = itemDBHandler;
        this.mItemList = new ArrayList<>();
        loadItemList();
    }

    private void loadItemList() {
        mItemList.addAll(mItemDBHandler.getAllItems());
    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public Item getItem(int position) {
        return mItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.single_textview_item, parent, false);
        TextView textView = (TextView) view.findViewById(R.id.item_text_view);
        textView.setText(getItem(position).getName());
        return view;
    }

    @Override
    public void notifyDBChanged() {
        mItemList.clear();
        mItemList = mItemDBHandler.getAllItems();
        notifyDataSetChanged();
    }
}
