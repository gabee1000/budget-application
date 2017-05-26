package com.example.gabor.mybudget.Presenter.Utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.gabor.mybudget.Model.Constants.Constants;
import com.example.gabor.mybudget.R;

/**
 * Created by Gabor on 2017. 05. 26..
 */

public abstract class CustomLayoutDialog extends DialogFragment {
    protected View dialogView;
    private int mResId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        this.mResId = bundle.getInt(Constants.Extra.LAYOUT_RES);
        if (mResId == 0) {
            mResId = R.layout.add_new_item_layout;
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = inflateCustomLayout();
        initViews();
        actions();
        return dialog;
    }

    private Dialog inflateCustomLayout() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        dialogView = inflater.inflate(mResId, null);
        dialogBuilder.setView(dialogView);
        return dialogBuilder.create();
    }

    /**
     * Called before actions(). Initialize Alert Dialog layout elements here.
     */
    protected abstract void initViews();

    /**
     * Called after initViews(). Set actions here.
     */
    protected abstract void actions();
}
