package com.example.gabor.mybudget.Presenter.Callbacks;

import android.content.Intent;
import android.support.annotation.Nullable;

public interface ResultListener {
    /**
     * <p>Called when a caller needs to pass information to this method.</p>
     * @param resultCode of the information
     * @param data of the information
     */
    void onResult(int resultCode,@Nullable Intent data);
}
