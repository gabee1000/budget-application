package com.example.gabor.mybudget.View.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.gabor.mybudget.Model.Constants.Constants;
import com.example.gabor.mybudget.Presenter.Adapters.TransactionAdapter;
import com.example.gabor.mybudget.Presenter.Utils.SignedInAppCompatActivity;
import com.example.gabor.mybudget.R;
import com.example.gabor.mybudget.View.Dialogs.YearMonthPickerDialog;

/**
 * Created by Gabor on 2017. 05. 28..
 */

public class TransactionsActivity extends SignedInAppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private TransactionAdapter mTransactionAdapter;
    private static int SELECTED_YEAR = 0;
    private static int SELECTED_MONTH = 0;

    @Override
    protected void init() {
        setContentView(R.layout.transactions_layout);
        initCardView();
    }

    private void initCardView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.transaction_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mTransactionAdapter = new TransactionAdapter(System.currentTimeMillis());
        mRecyclerView.setAdapter(mTransactionAdapter);
    }

    @Override
    protected void actions() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_transaction, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_new_transaction:
                break;
            case R.id.pick_date:
                YearMonthPickerDialog pd = new YearMonthPickerDialog();
                pd.setListener(this);
                pd.setSelectedYear(SELECTED_YEAR);
                pd.setSelectedMonth(SELECTED_MONTH);
                pd.show(getFragmentManager(), "YearMonthPickerDialog");
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        SELECTED_YEAR = year;
        SELECTED_MONTH = month;
        showCardViewByDate(year, month);
    }

    private void showCardViewByDate(int year, int month) {
        mTransactionAdapter.loadDataSet(0, year, month);
    }
}
