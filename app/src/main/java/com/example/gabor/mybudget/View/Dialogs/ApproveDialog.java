package com.example.gabor.mybudget.View.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.content.res.ResourcesCompat;

import com.example.gabor.mybudget.Model.Constants.Constants;
import com.example.gabor.mybudget.Presenter.Listeners.DismissDialogClickListener;
import com.example.gabor.mybudget.Presenter.Utils.BudgetAppDialog;
import com.example.gabor.mybudget.R;
import com.example.gabor.mybudget.View.Activities.LoginActivity;
import com.example.gabor.mybudget.View.Activities.RegisterActivity;

/**
 * Created by Gabor on 2017. 05. 25..
 */

/**
 * Should always call setPositiveClickListener and setNegativeClickListener or the application becomes unstable!
 */
public class ApproveDialog extends BudgetAppDialog {
    private DialogInterface.OnClickListener mNegativeClickListener;

    @Override
    protected void initDrawable() {
        setDialogDrawable(R.drawable.ic_help_black_48dp);
        mDrawable.setTint(ResourcesCompat.getColor(getResources(), R.color.colorQuestionDrawable, null));
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog alertDialog = (AlertDialog) super.onCreateDialog(savedInstanceState);
        if (!isNegativeClickListenerAdded()) {
            mNegativeClickListener = new DismissDialogClickListener(this);
        }
        mDrawable.setTint(ResourcesCompat.getColor(getResources(), R.color.colorQuestionDrawable, null));
        alertDialog.setButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE, getString(R.string.yes), mPositiveClickListener);
        alertDialog.setButton(android.support.v7.app.AlertDialog.BUTTON_NEGATIVE, getString(R.string.no), mNegativeClickListener);
        return alertDialog;
    }

    private boolean isNegativeClickListenerAdded() {
        return mNegativeClickListener != null;
    }

    public ApproveDialog setNegativeClickListener(DialogInterface.OnClickListener mNegativeClickListener) {
        this.mNegativeClickListener = mNegativeClickListener;
        return this;
    }


}
