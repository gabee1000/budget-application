package com.example.gabor.mybudget.Presenter.Utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.example.gabor.mybudget.Presenter.Listeners.DismissDialogClickListener;
import com.example.gabor.mybudget.R;

/**
 * Always set the Click Listeners according to the used Dialog types.
 */
public abstract class BudgetAppDialog extends DialogFragment {
    protected String mTitle = "not set";
    protected String mMessage = "not set";
    protected Drawable mDrawable;
    protected DialogInterface.OnClickListener mPositiveClickListener = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDrawable();
    }

    protected abstract void initDrawable();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme).create();
        if (!isPositiveClickListenerAdded()) {
            mPositiveClickListener = new DismissDialogClickListener(this);
        }
        alertDialog.setTitle(mTitle);
        alertDialog.setMessage(mMessage);
        alertDialog.setIcon(mDrawable);
        return alertDialog;
    }

    protected boolean isPositiveClickListenerAdded() {
        return mPositiveClickListener != null;
    }

    public BudgetAppDialog setTitle(String mTitle) {
        this.mTitle = mTitle;
        return this;
    }

    public BudgetAppDialog setMessage(String mMessage) {
        this.mMessage = mMessage;
        return this;
    }

    public BudgetAppDialog setPositiveClickListener(DialogInterface.OnClickListener clickListener) {
        this.mPositiveClickListener = clickListener;
        return this;
    }

    public BudgetAppDialog setDialogDrawable(int ResID) {
        this.mDrawable = getActivity().getDrawable(ResID);
        return this;
    }
}
