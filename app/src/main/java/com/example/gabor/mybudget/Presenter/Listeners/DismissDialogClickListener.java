package com.example.gabor.mybudget.Presenter.Listeners;

import android.app.DialogFragment;
import android.content.DialogInterface;

import com.example.gabor.mybudget.Presenter.Utils.BudgetAppDialog;
import com.example.gabor.mybudget.View.Dialogs.ApproveDialog;

public class DismissDialogClickListener implements DialogInterface.OnClickListener {
    private final DialogFragment dialog;

    public DismissDialogClickListener(DialogFragment dialog) {
        this.dialog = dialog;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
    }
}
