package com.example.gabor.mybudget.View.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.gabor.mybudget.Model.Constants.Constants;
import com.example.gabor.mybudget.Presenter.Callbacks.ResultListener;
import com.example.gabor.mybudget.Presenter.Listeners.DismissDialogClickListener;
import com.example.gabor.mybudget.Presenter.Utils.CustomLayoutDialog;
import com.example.gabor.mybudget.R;

/**
 * Created by Gabor on 2017. 05. 26..
 */

public class NewItemDialog extends CustomLayoutDialog implements DialogInterface.OnClickListener {
    private EditText mItemNameET;
    private EditText mCategoryNameET;
    private EditText mValueET;
    private CheckBox mIsIncome;
    private ResultListener mResultListener;
    private AlertDialog mDialog;

    @Override
    protected void initViews() {
        mItemNameET = (EditText) dialogView.findViewById(R.id.item_name_edit_text);
        mCategoryNameET = (EditText) dialogView.findViewById(R.id.category_name_edit_text);
        mValueET = (EditText) dialogView.findViewById(R.id.value_edit_text);
        mIsIncome = (CheckBox) dialogView.findViewById(R.id.is_income_checkbox);
        mResultListener = (ResultListener) getActivity();
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
        mDialog.setTitle(getString(R.string.add_new_item));
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        boolean emptyEditText = isEmpty(mItemNameET) || isEmpty(mCategoryNameET) || isEmpty(mValueET);
        if (emptyEditText) {
            mResultListener.onResult(Constants.RequestCodes.EMPTY_EDIT_TEXTS, null);
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(Constants.Extra.ITEM_NAME, mItemNameET.getText().toString());
        intent.putExtra(Constants.Extra.CATEGORY_NAME, mCategoryNameET.getText().toString());
        intent.putExtra(Constants.Extra.VALUE, Long.parseLong(mValueET.getText().toString()));
        intent.putExtra(Constants.Extra.IS_INCOME, mIsIncome.isChecked());
        mResultListener.onResult(Constants.RequestCodes.NEW_ITEM_REQUEST, intent);
    }

    private boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }
}
