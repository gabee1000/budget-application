package com.example.gabor.mybudget.Presenter.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.gabor.mybudget.Model.Database.ItemDatabaseHandler;
import com.example.gabor.mybudget.Model.Entities.Item;
import com.example.gabor.mybudget.Presenter.Utils.BudgetAppAdapter;
import com.example.gabor.mybudget.R;
import com.example.gabor.mybudget.View.Activities.ItemsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gabor on 2017. 05. 26..
 */

public class ItemsListAdapter extends BudgetAppAdapter {
    private ItemDatabaseHandler mItemDBHandler;
    private List<Item> mItemList;

    public ItemsListAdapter(Context context, ItemDatabaseHandler itemDBHandler) {
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
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.single_textview_item, parent, false);
            TextView textView = (TextView) convertView.findViewById(R.id.item_text_view);
            textView.setText(getItem(position).getName());
        }
        return convertView;
    }

    @Override
    public void notifyDBChanged() {
        mItemList = mItemDBHandler.getAllItems();
        notifyDataSetChanged();
    }
}
