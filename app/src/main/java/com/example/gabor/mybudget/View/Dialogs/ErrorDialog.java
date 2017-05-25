package com.example.gabor.mybudget.View.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;

import com.example.gabor.mybudget.Presenter.Utils.BudgetAppDialog;
import com.example.gabor.mybudget.R;

/**
 * Always call the setPositiveClickListener or the application becomes unstable!
 */
public class ErrorDialog extends InfoDialog {

    @Override
    protected void initDrawable() {
        setDialogDrawable(android.R.drawable.ic_dialog_alert);
        mDrawable.setTint(ResourcesCompat.getColor(getResources(), R.color.colorErrorDialog, null));
    }
}