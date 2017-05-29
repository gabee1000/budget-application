package com.example.gabor.mybudget.View.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gabor.mybudget.Model.Database.TransactionDatabaseHandler;
import com.example.gabor.mybudget.Model.Entities.Transaction;
import com.example.gabor.mybudget.Presenter.Utils.SignedInAppCompatActivity;
import com.example.gabor.mybudget.R;

import java.util.List;

public class SummaryFragment extends Fragment {
    private int mMonth;
    private TextView mIncomeTV;
    private TextView mExpenseTV;
    private TextView mGrossProfitTV;
    private RecyclerView mStatisticsRecyclerView;
    private int mSelectedYear;
    private TransactionDatabaseHandler mTransactionDBHandler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.summary_fragment, container, false);
        this.mIncomeTV = (TextView) view.findViewById(R.id.total_income_tv);
        this.mExpenseTV = (TextView) view.findViewById(R.id.total_expense_tv);
        this.mGrossProfitTV = (TextView) view.findViewById(R.id.gross_profit_tv);
        //TODO RecyclerView
        this.mStatisticsRecyclerView = (RecyclerView) view.findViewById(R.id.statistics_recycler_view);
        this.mTransactionDBHandler = SignedInAppCompatActivity.transactionDBHandler;
        if (mMonth == 0) {
            showYearSummary();
        } else {
            showMonthSummary();
        }
        return view;
    }

    public void showSummaryAtPositionByDate(int position, int selectedYear) {
        this.mMonth = position;
        this.mSelectedYear = selectedYear;
    }

    private void showYearSummary() {
        List<Transaction> list = mTransactionDBHandler.getAllTransactionByYear(mSelectedYear);
        computeFinance(list);
    }

    private void showMonthSummary() {
        List<Transaction> list = mTransactionDBHandler.getAllTransactionByDate(null, mSelectedYear, mMonth);
        computeFinance(list);
    }

    private void computeFinance(List<Transaction> list) {
        long sumIncome = 0;
        long sumExpense = 0;
        long sumGrossProfit;
        for (Transaction t : list) {
            if (t.isIncome()) {
                sumIncome += t.getValue();
            } else {
                sumExpense += t.getValue();
            }
        }
        sumGrossProfit = (sumIncome - sumExpense);
        setIncomeTextView(String.valueOf(sumIncome));
        setExpenseTextView(String.valueOf(sumExpense));
        setGrossProfitTextView(String.valueOf(sumGrossProfit));
    }

    private void setIncomeTextView(String text) {
        mIncomeTV.setText(text);
    }

    private void setExpenseTextView(String text) {
        mExpenseTV.setText(text);
    }

    private void setGrossProfitTextView(String text) {
        mGrossProfitTV.setText(text);
    }
}
