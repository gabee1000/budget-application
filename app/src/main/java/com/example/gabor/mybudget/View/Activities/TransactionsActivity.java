package com.example.gabor.mybudget.View.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.gabor.mybudget.Model.Constants.Constants;
import com.example.gabor.mybudget.Model.Entities.Transaction;
import com.example.gabor.mybudget.Presenter.Adapters.TransactionRecyclerViewAdapter;
import com.example.gabor.mybudget.Presenter.Callbacks.ResultListener;
import com.example.gabor.mybudget.Presenter.Utils.SignedInAppCompatActivity;
import com.example.gabor.mybudget.R;
import com.example.gabor.mybudget.View.Dialogs.TransactionDialog;
import com.example.gabor.mybudget.View.Dialogs.YearMonthPickerDialog;
import com.example.gabor.mybudget.View.Dialogs.YearPickerDialog;

/**
 * Created by Gabor on 2017. 05. 28..
 */

public class TransactionsActivity extends SignedInAppCompatActivity implements DatePickerDialog.OnDateSetListener, ResultListener {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private TransactionRecyclerViewAdapter mTransactionRecyclerViewAdapter;
    protected static int SELECTED_YEAR = 0;
    protected static int SELECTED_MONTH = 0;

    @Override
    protected void init() {
        setContentView(R.layout.transactions_layout);
        initCardView();
    }

    private void initCardView() {
        toolbar.setTitle(R.string.transactions);
        mRecyclerView = (RecyclerView) findViewById(R.id.transaction_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mTransactionRecyclerViewAdapter = new TransactionRecyclerViewAdapter(System.currentTimeMillis());
        mRecyclerView.setAdapter(mTransactionRecyclerViewAdapter);
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
                Toast.makeText(this, SELECTED_YEAR + " " + SELECTED_MONTH, Toast.LENGTH_SHORT).show();
                startNewTransactionDialog();
                break;
            case R.id.pick_date:
                YearMonthPickerDialog yearMonthPickerDialog = new YearMonthPickerDialog();
                yearMonthPickerDialog.setListener(this);
                yearMonthPickerDialog.setSelectedYear(SELECTED_YEAR);
                yearMonthPickerDialog.setSelectedMonth(SELECTED_MONTH);
                yearMonthPickerDialog.show(getFragmentManager(), "YearMonthPickerDialog");
                break;
            case R.id.pick_year:
                YearPickerDialog yearPickerDialog = new YearPickerDialog();
                yearPickerDialog.setListener(this);
                yearPickerDialog.setSelectedYear(SELECTED_YEAR);
                yearPickerDialog.show(getFragmentManager(), "YearPickerDialog");
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
        mTransactionRecyclerViewAdapter.loadDataSet(0, year, month);
    }

    private void startNewTransactionDialog() {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.Extra.LAYOUT_RES, R.layout.transaction_dialog_layout);
        bundle.putInt(Constants.Extra.SELECT_YEAR, SELECTED_YEAR);
        bundle.putInt(Constants.Extra.SELECT_MONTH, SELECTED_MONTH);
        TransactionDialog transactionDialog = new TransactionDialog();
        transactionDialog.setArguments(bundle);
        transactionDialog.show(getFragmentManager(), "transaction_dialog");
    }

    @Override
    public void onResult(int resultCode, @Nullable Intent data) {
        switch (resultCode) {
            case Constants.ResultCodes.TRANSACTION_ADDED:
                if (data != null && data.hasExtra(Constants.Extra.TRANSACTION)) {
                    Transaction newTransaction = data.getExtras().getParcelable(Constants.Extra.TRANSACTION);
                    mTransactionRecyclerViewAdapter.updateDataSet(mTransactionRecyclerViewAdapter.getItemCount(), newTransaction);
                }
                break;
            case Constants.ResultCodes.EMPTY_EDIT_TEXTS:
                showErrorDialog(getString(R.string.some_fields_were_missing_transaction));
                break;
        }
    }
}
