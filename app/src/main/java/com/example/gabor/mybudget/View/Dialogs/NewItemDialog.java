package com.example.gabor.mybudget.View.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.gabor.mybudget.Model.Constants.Constants;
import com.example.gabor.mybudget.Model.Entities.Item;
import com.example.gabor.mybudget.Presenter.Callbacks.ResultListener;
import com.example.gabor.mybudget.Presenter.Listeners.DismissDialogClickListener;
import com.example.gabor.mybudget.Presenter.Utils.CustomLayoutDialog;
import com.example.gabor.mybudget.Presenter.Utils.SignedInAppCompatActivity;
import com.example.gabor.mybudget.R;

// TODO Need to fix item editing! When just a simple thing is modified other than the name, e.g. 'Is Income' checkbox, the item already exists dialog appears instead of modifying just that property.

// TODO Change items showing layout to something like 'android.R.layout.simple_list_item_1' for better universal list showing.

public class NewItemDialog extends CustomLayoutDialog implements DialogInterface.OnClickListener {
    private EditText mItemNameET;
    private EditText mCategoryNameET;
    private EditText mValueET;
    private CheckBox mIsIncome;
    private ResultListener mResultListener;
    private AlertDialog mDialog;

    private long mCategoryId;
    private long mItemId;

    @Override
    protected void initViews() {
        mItemNameET = (EditText) dialogView.findViewById(R.id.item_name_edit_text);
        mCategoryNameET = (EditText) dialogView.findViewById(R.id.category_name_edit_text);
        mValueET = (EditText) dialogView.findViewById(R.id.value_edit_text);
        mIsIncome = (CheckBox) dialogView.findViewById(R.id.is_income_checkbox);
        mResultListener = (ResultListener) getActivity();
        fillEditTextsIfHasArguments();
    }

    private void fillEditTextsIfHasArguments() {
        if (getArguments().containsKey(Constants.Extra.EDIT_ITEM)) {
            Item item = getArguments().getParcelable(Constants.Extra.ITEM);
            if (item != null) {
                mItemNameET.setText(item.getName());
                String categoryName = SignedInAppCompatActivity.categoryDBHandler.getCategory(item.getCategoryId());
                mCategoryId = item.getCategoryId();
                mItemId = item.getId();
                if (categoryName != null) {
                    mCategoryNameET.setText(categoryName);
                }
                mValueET.setText(String.valueOf(item.getLastValue()));
                mIsIncome.setChecked(item.isIncome());
            }
        }
    }

    @Override
    protected void actions() {

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
        if (getArguments().containsKey(Constants.Extra.EDIT_ITEM)) {
            mDialog.setTitle(getString(R.string.edit_item));
        } else {
            mDialog.setTitle(getString(R.string.add_new_item));
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        boolean emptyEditText = isEmpty(mItemNameET) || isEmpty(mCategoryNameET) || isEmpty(mValueET);
        if (emptyEditText) {
            mResultListener.onResult(Constants.ResultCodes.EMPTY_EDIT_TEXTS, null);
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(Constants.Extra.ITEM_NAME, mItemNameET.getText().toString());
        intent.putExtra(Constants.Extra.CATEGORY_NAME, mCategoryNameET.getText().toString());
        intent.putExtra(Constants.Extra.VALUE, Long.parseLong(mValueET.getText().toString()));
        intent.putExtra(Constants.Extra.IS_INCOME, mIsIncome.isChecked());
        int resultCode;
        if (getArguments().containsKey(Constants.Extra.EDIT_ITEM)) {
            resultCode = Constants.ResultCodes.EDIT_ITEM_REQUEST;
            intent.putExtra(Constants.Extra.CATEGORY_ID, mCategoryId);
            intent.putExtra(Constants.Extra.ITEM_ID, mItemId);
        } else {
            resultCode = Constants.ResultCodes.NEW_ITEM_REQUEST;
        }
        mResultListener.onResult(resultCode, intent);
    }

    private boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }
}
