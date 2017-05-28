package com.example.gabor.mybudget.Presenter.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gabor.mybudget.Model.Database.ItemDatabaseHandler;
import com.example.gabor.mybudget.Model.Database.TransactionDatabaseHandler;
import com.example.gabor.mybudget.Model.Entities.Transaction;
import com.example.gabor.mybudget.Presenter.Utils.SignedInAppCompatActivity;
import com.example.gabor.mybudget.R;

import java.sql.Date;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Gabor on 2017. 05. 28..
 */

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {
    private final TransactionDatabaseHandler mTransactionDBHandler;
    private final ItemDatabaseHandler mItemDBHandler;
    private List<Transaction> mTransactionDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView itemTextView;
        private final TextView valueTextView;
        private final TextView createdTimeTextView;

        public ViewHolder(View v) {
            super(v);
            itemTextView = (TextView) v.findViewById(R.id.item);
            valueTextView = (TextView) v.findViewById(R.id.value);
            createdTimeTextView = (TextView) v.findViewById(R.id.created_time);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TransactionAdapter(long time) {
        this.mTransactionDBHandler = SignedInAppCompatActivity.transactionDBHandler;
        this.mItemDBHandler = SignedInAppCompatActivity.itemDBHandler;
        loadDataSet(time, 0, 0);
    }

    public void loadDataSet(long time, int year, int month) {
        mTransactionDataset = mTransactionDBHandler.getAllTransactionByDate(time, year, month);
        notifyDataSetChanged();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TransactionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_transaction_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        // ...
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Transaction transactionItem = mTransactionDataset.get(position);
        String itemName = mItemDBHandler.getItem(transactionItem.getItemId()).getName();
        String value = String.valueOf(transactionItem.getValue());
        String createdTime = DateFormat.getDateTimeInstance().format(new Date(transactionItem.getCreatedTime()));

        holder.itemTextView.setText(itemName);
        holder.valueTextView.setText(value);
        holder.createdTimeTextView.setText(createdTime);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mTransactionDataset.size();
    }
}
