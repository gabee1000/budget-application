package com.example.gabor.mybudget.Presenter.Listeners;

import android.content.Context;
import android.content.DialogInterface;

import com.example.gabor.mybudget.Model.Constants.Constants;
import com.example.gabor.mybudget.Presenter.Callbacks.ResultListener;
import com.example.gabor.mybudget.Presenter.Utils.SignedInAppCompatActivity;

/**
 * Created by Gabor on 2017. 06. 15..
 */

public class DeleteItemListener implements DialogInterface.OnClickListener {
    private Context context;
    private ResultListener resultListener;
    private String itemName;

    public DeleteItemListener(Context context, String itemName) {
        this.context = context;
        this.resultListener = (ResultListener) context;
        this.itemName = itemName;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        SignedInAppCompatActivity.itemDBHandler.removeItem(itemName);
        resultListener.onResult(Constants.ResultCodes.NOTIFY_ADAPTER_DATASET_CHANGED, null);
    }
}
