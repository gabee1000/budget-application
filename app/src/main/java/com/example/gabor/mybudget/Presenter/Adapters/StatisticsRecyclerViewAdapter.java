package com.example.gabor.mybudget.Presenter.Adapters;

import com.example.gabor.mybudget.Model.Database.CategoryDatabaseHandler;
import com.example.gabor.mybudget.Model.Entities.CategoryAndValue;
import com.example.gabor.mybudget.Presenter.Utils.SignedInAppCompatActivity;
import com.example.gabor.mybudget.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Gabor on 2017. 05. 29..
 */

public class StatisticsRecyclerViewAdapter extends TransactionRecyclerViewAdapter {
    private int mMonth;
    private List<CategoryAndValue> mCategoryAndValueDataSet;

    /**
     * <p>Transactions adapter for displaying costs yearly/monthly grouped by category.</p>
     */
    public StatisticsRecyclerViewAdapter(long time, int month) {
        super(time);
        this.mMonth = month;
        mCategoryAndValueDataSet = new ArrayList<>();
        loadCategoryDataSet(time);
    }

    private void loadCategoryDataSet(long time) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        int year = c.get(Calendar.YEAR);
        int month = mMonth;
        mCategoryAndValueDataSet.clear();
        mCategoryAndValueDataSet.addAll(mTransactionDBHandler.getTransactionsForStatistics(year, month, false));
        mCategoryAndValueDataSet.addAll(mTransactionDBHandler.getTransactionsForStatistics(year, month, true));
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nameTextView.setText(mCategoryAndValueDataSet.get(position).getName());
        if (mCategoryAndValueDataSet.get(position).isIncome()) {
            holder.isIncomeMarker.setBackgroundResource(R.color.green);
            holder.valueTextView.setText(String.format("+" + String.valueOf(mCategoryAndValueDataSet.get(position).getValue())));
        } else {
            holder.isIncomeMarker.setBackgroundResource(R.color.red);
            holder.valueTextView.setText(String.format("-" + String.valueOf(mCategoryAndValueDataSet.get(position).getValue())));
        }
    }

    @Override
    public int getItemCount() {
        return mCategoryAndValueDataSet.size();
    }
}
