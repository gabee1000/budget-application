package com.example.gabor.mybudget.View.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.gabor.mybudget.Model.Constants.Constants;
import com.example.gabor.mybudget.Presenter.Adapters.MainMenuGridAdapter;
import com.example.gabor.mybudget.Presenter.Callbacks.ResultListener;
import com.example.gabor.mybudget.Presenter.Utils.SignedInAppCompatActivity;
import com.example.gabor.mybudget.R;
import com.example.gabor.mybudget.View.Dialogs.TransactionDialog;

/**
 * Created by Gabor on 2017. 05. 20..
 */

public class MainMenuActivity extends SignedInAppCompatActivity implements ResultListener {
    private GridView mMenuGridView;
    private MainMenuGridAdapter mMainMenuGridAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        actions();
    }

    @Override
    protected void init() {
        setContentView(R.layout.main_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle(R.string.my_budget);
        initGridView();
    }

    private void initGridView() {
        mMenuGridView = (GridView) findViewById(R.id.gridview);
        mMainMenuGridAdapter = new MainMenuGridAdapter(this);
        mMenuGridView.setAdapter(mMainMenuGridAdapter);
    }

    @Override
    protected void actions() {
        mMenuGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                processItemClick(parent, v, position, id);
            }
        });
    }

    private void processItemClick(AdapterView<?> parent, View v, int position, long id) {
        String selectedItem = mMainMenuGridAdapter.mGridItemResources.mDescriptions[position];
        final String newTransaction = getString(R.string.new_transaction);
        final String items = getString(R.string.items);
        final String showTransactions = getString(R.string.show_transactions);
        final String statistics = getString(R.string.statistics);
        if (selectedItem.equals(newTransaction)) {
            startNewTransactionDialog();
        } else if (selectedItem.equals(items)) {
            startItemsActivity();
        } else if (selectedItem.equals(showTransactions)) {
            startTransactionsActivity();
        } else if (selectedItem.equals(statistics)) {
            startStatisticsActivity();
        }
    }

    private void startNewTransactionDialog() {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.Extra.LAYOUT_RES, R.layout.transaction_dialog_layout);
        TransactionDialog transactionDialog = new TransactionDialog();
        transactionDialog.setArguments(bundle);
        transactionDialog.show(getFragmentManager(), "transaction_dialog");
    }

    private void startTransactionsActivity() {
        Intent intent = new Intent(MainMenuActivity.this, TransactionsActivity.class);
        startActivity(intent);
    }

    private void startStatisticsActivity() {
        Intent intent = new Intent(MainMenuActivity.this, StatisticsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResult(int resultCode, Intent data) {
        switch (resultCode) {
            case Constants.ResultCodes.SHOW_AGAIN_TRANSACTION:
                startNewTransactionDialog();
                break;
            case Constants.ResultCodes.EMPTY_EDIT_TEXTS:
                showErrorDialog(getString(R.string.some_fields_were_missing_transaction));
                break;
        }
    }

    private void startItemsActivity() {
        Intent intent = new Intent(MainMenuActivity.this, ItemsActivity.class);
        startActivity(intent);
    }
}
