package com.example.gabor.mybudget.Presenter.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gabor.mybudget.Model.Database.ItemDatabaseHandler;
import com.example.gabor.mybudget.Model.Database.TransactionDatabaseHandler;
import com.example.gabor.mybudget.Model.Entities.Transaction;
import com.example.gabor.mybudget.Presenter.Utils.SignedInAppCompatActivity;
import com.example.gabor.mybudget.R;

import java.sql.Date;
import java.text.DateFormat;
import java.util.List;

public class TransactionRecyclerViewAdapter extends RecyclerView.Adapter<TransactionRecyclerViewAdapter.ViewHolder> {
    protected final TransactionDatabaseHandler mTransactionDBHandler;
    protected final ItemDatabaseHandler mItemDBHandler;
    protected List<Transaction> mTransactionDataSet;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {

        protected final TextView nameTextView;
        protected final TextView valueTextView;
        private final TextView createdTimeTextView;
        protected final LinearLayout isIncomeMarker;

        public ViewHolder(View v) {
            super(v);
            isIncomeMarker = (LinearLayout) v.findViewById(R.id.is_income_marker);
            nameTextView = (TextView) v.findViewById(R.id.name);
            valueTextView = (TextView) v.findViewById(R.id.value);
            createdTimeTextView = (TextView) v.findViewById(R.id.created_time);
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TransactionRecyclerViewAdapter(long time) {
        this.mTransactionDBHandler = SignedInAppCompatActivity.transactionDBHandler;
        this.mItemDBHandler = SignedInAppCompatActivity.itemDBHandler;
        loadDataSet(time, 0, 0);
    }

    public void loadDataSet(long time, int year, int month) {
        mTransactionDataSet = mTransactionDBHandler.getAllTransactionByDate(time, year, month);
        notifyDataSetChanged();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TransactionRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
        Transaction transactionItem = mTransactionDataSet.get(position);
        String itemName = mItemDBHandler.getItem(transactionItem.getItemId()).getName();
        String value = String.valueOf(transactionItem.getValue());
        String createdTime = DateFormat.getDateTimeInstance().format(new Date(transactionItem.getCreatedTime()));
        boolean isIncome = transactionItem.isIncome();

        if (isIncome) {
            holder.isIncomeMarker.setBackgroundResource(R.color.green);
            holder.valueTextView.setText(String.format("+" + value));
        } else {
            holder.isIncomeMarker.setBackgroundResource(R.color.red);
            holder.valueTextView.setText(String.format("-" + value));
        }

        holder.nameTextView.setText(itemName);
        holder.createdTimeTextView.setText(createdTime);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mTransactionDataSet.size();
    }

    public void updateDataSet(int itemInsertedPosition, Transaction newTransaction) {
        mTransactionDataSet.add(newTransaction);
        notifyItemInserted(itemInsertedPosition);
    }
}
