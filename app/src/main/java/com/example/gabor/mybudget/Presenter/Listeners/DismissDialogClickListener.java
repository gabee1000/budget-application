package com.example.gabor.mybudget.Presenter.Listeners;

import android.content.DialogInterface;

import com.example.gabor.mybudget.Presenter.Utils.BudgetAppDialog;
import com.example.gabor.mybudget.View.Dialogs.ApproveDialog;

/**
 * Created by Gabor on 2017. 05. 25..
 */

public class DismissDialogClickListener implements DialogInterface.OnClickListener {
    private final BudgetAppDialog dialog;

    public DismissDialogClickListener(BudgetAppDialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
    }
}
