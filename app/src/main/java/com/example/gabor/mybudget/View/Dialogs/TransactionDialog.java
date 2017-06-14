package com.example.gabor.mybudget.View.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gabor.mybudget.Model.Constants.Constants;
import com.example.gabor.mybudget.Model.Database.CategoryDatabaseHandler;
import com.example.gabor.mybudget.Model.Database.ItemDatabaseHandler;
import com.example.gabor.mybudget.Model.Database.TransactionDatabaseHandler;
import com.example.gabor.mybudget.Model.Database.UserDatabaseHandler;
import com.example.gabor.mybudget.Model.Entities.Item;
import com.example.gabor.mybudget.Model.Entities.Transaction;
import com.example.gabor.mybudget.Presenter.Callbacks.ResultListener;
import com.example.gabor.mybudget.Presenter.Listeners.ItemLoader;
import com.example.gabor.mybudget.Presenter.Listeners.DismissDialogClickListener;
import com.example.gabor.mybudget.Presenter.Utils.CustomLayoutDialog;
import com.example.gabor.mybudget.Presenter.Utils.SignedInAppCompatActivity;
import com.example.gabor.mybudget.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TransactionDialog extends CustomLayoutDialog implements DialogInterface.OnClickListener {

    private AutoCompleteTextView mItemAutoCompleteTV;
    private AutoCompleteTextView mCategoryNameAutoCompleteTV;
    private EditText mValueET;
    private CheckBox mIsIncome;
    private AlertDialog mDialog;
    private ResultListener mResultListener;
    private ArrayAdapter mItemsAdapter;
    private ArrayAdapter mCategoryAdapter;

    private ItemDatabaseHandler itemDBHandler = SignedInAppCompatActivity.itemDBHandler;
    private CategoryDatabaseHandler categoryDBHandler = SignedInAppCompatActivity.categoryDBHandler;
    private TransactionDatabaseHandler transactionDBHandler = SignedInAppCompatActivity.transactionDBHandler;
    private UserDatabaseHandler userDBHandler = SignedInAppCompatActivity.userDatabaseHandler;
    private int mSelectedYear = 0;
    private int mSelectMonth = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(Constants.Extra.SELECT_YEAR) && bundle.containsKey(Constants.Extra.SELECT_MONTH)) {
            this.mSelectedYear = bundle.getInt(Constants.Extra.SELECT_YEAR);
            this.mSelectMonth = bundle.getInt(Constants.Extra.SELECT_MONTH);
        }
    }

    @Override
    protected void initViews() {
        mItemAutoCompleteTV = (AutoCompleteTextView) dialogView.findViewById(R.id.item_autocomplete);
        mCategoryNameAutoCompleteTV = (AutoCompleteTextView) dialogView.findViewById(R.id.category_name_edit_text);
        mValueET = (EditText) dialogView.findViewById(R.id.value_edit_text);
        mIsIncome = (CheckBox) dialogView.findViewById(R.id.is_income_checkbox);
        mResultListener = (ResultListener) getActivity();
        initItemAutoCompleteTV();
        initCategoryAutoCompleteTV();
    }

    private void initItemAutoCompleteTV() {
        List<Item> list = SignedInAppCompatActivity.itemDBHandler.getAllItems();
        mItemsAdapter = new ArrayAdapter<Item>(getActivity(), android.R.layout.simple_list_item_1, list);
        mItemAutoCompleteTV.setAdapter(mItemsAdapter);
    }

    private void initCategoryAutoCompleteTV() {
        List<String> list = SignedInAppCompatActivity.categoryDBHandler.getAllCategories();
        mCategoryAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list);
        mCategoryNameAutoCompleteTV.setAdapter(mCategoryAdapter);
    }

    @Override
    protected void actions() {
        mItemAutoCompleteTV.addTextChangedListener(new ItemLoader(mCategoryNameAutoCompleteTV, mValueET, mIsIncome));
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDialog = (AlertDialog) super.onCreateDialog(savedInstanceState);
        setButtons();
        setTitle();
        return mDialog;
    }

    private void setButtons() {
        DismissDialogClickListener dismissListener = new DismissDialogClickListener(this);
        mDialog.setButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE, getString(R.string.ok), this);
        mDialog.setButton(android.support.v7.app.AlertDialog.BUTTON_NEGATIVE, getString(R.string.cancel), dismissListener);
    }

    private void setTitle() {
        mDialog.setTitle(getString(R.string.new_transaction));
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        boolean empty = areEditTextsEmpty();
        if (empty) {
            mResultListener.onResult(Constants.ResultCodes.EMPTY_EDIT_TEXTS, null);
            return;
        }
        String itemName = mItemAutoCompleteTV.getText().toString();
        String categoryName = mCategoryNameAutoCompleteTV.getText().toString();
        long value = Long.parseLong(mValueET.getText().toString());
        boolean isIncome = mIsIncome.isChecked();

        long categoryResult = categoryDBHandler.addCategory(categoryName);
        long categoryId = categoryDBHandler.getCategoryId(categoryName);
        long itemResult = itemDBHandler.addItem(new Item(0, itemName, categoryId, value, isIncome));
        if (itemResult == -1) {
            long itemId = itemDBHandler.getItem(itemName).getId();
            Item newItem = new Item(itemId, itemName, categoryId, value, isIncome);
            itemDBHandler.updateItemById(newItem, itemId);
        }

        long userID = SignedInAppCompatActivity.loggedInUserId;
        long itemID = itemDBHandler.getItem(itemName).getId();

        long createdTime;
        if (mSelectedYear <= 0 && mSelectMonth <= 0) {
            createdTime = System.currentTimeMillis();
        } else {
            createdTime = getGivenDateInMillis();
        }
        Transaction transaction = new Transaction(0, userID, itemID, value, createdTime, isIncome);
        transactionDBHandler.addTransaction(transaction);

        Intent data = new Intent();
        data.putExtra(Constants.Extra.TRANSACTION, transaction);
        mResultListener.onResult(Constants.ResultCodes.TRANSACTION_ADDED, data);
    }

    private boolean areEditTextsEmpty() {
        List<EditText> etList = new ArrayList<>();
        etList.add(mItemAutoCompleteTV);
        etList.add(mCategoryNameAutoCompleteTV);
        etList.add(mValueET);
        return !validateEditTexts(etList);
    }

    private long getGivenDateInMillis() {
        Calendar c = Calendar.getInstance();
        c.set(mSelectedYear, mSelectMonth - 1, 1);
        return c.getTimeInMillis();
    }


}
