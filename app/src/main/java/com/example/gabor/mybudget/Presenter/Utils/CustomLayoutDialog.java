package com.example.gabor.mybudget.Presenter.Utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.gabor.mybudget.Model.Constants.Constants;
import com.example.gabor.mybudget.R;

import java.util.List;

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

    /**
     * <p>Validate EditTexts.</p>
     *
     * @return returns true if characters were written to EditTexts, false otherwise (some EditTexts are empty).
     */
    protected boolean validateEditTexts(List<EditText> editTextList) {
        int errorCount = 0;
        for (EditText editText : editTextList) {
            boolean isEmpty = editText.getText().toString().isEmpty();
            if (isEmpty) {
                editText.setError(getString(R.string.required_field));
                errorCount++;
            }
        }
        if (errorCount > 0) {
            return false;
        }
        return true;
    }
}
