package com.example.gabor.mybudget.Presenter.Utils;

import android.widget.EditText;

import com.example.gabor.mybudget.R;

import java.util.List;

/**
 * Created by Gabor on 2017. 05. 20..
 */

public abstract class LauncherAppCompatActivity extends BudgetAppCompatActivity {

    /**
     * <p>Validate EditTexts.</p>
     *
     * @return returns true if characters were written to EditTexts, false otherwise (some EditTexts are empty).
     */
    protected boolean validateEditTexts(List<EditText> editTextList) {
        int errorCount = 0;
        int numberOfEditTexts = editTextList.size();
        for (EditText editText : editTextList) {
            boolean isEmpty = editText.getText().toString().isEmpty();
            if (isEmpty) {
                editText.setError(getString(R.string.required_field));
                errorCount++;
            }
        }
        if (errorCount >= numberOfEditTexts) {
            return false;
        }
        return true;
    }

}
