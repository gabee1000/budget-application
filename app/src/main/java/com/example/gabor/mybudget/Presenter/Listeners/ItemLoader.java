package com.example.gabor.mybudget.Presenter.Listeners;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.gabor.mybudget.Model.Database.CategoryDatabaseHandler;
import com.example.gabor.mybudget.Model.Database.ItemDatabaseHandler;
import com.example.gabor.mybudget.Model.Entities.Item;
import com.example.gabor.mybudget.Presenter.Utils.SignedInAppCompatActivity;

/**
 * Created by Gabor on 2017. 05. 28..
 */

public class ItemLoader implements TextWatcher {

    private final EditText categoryET;
    private final EditText valueEt;
    private final CheckBox isIncomeCB;
    private final ItemDatabaseHandler itemDBHandler;
    private final CategoryDatabaseHandler categoryDBHandler;

    public ItemLoader(EditText categoryET, EditText valueET, CheckBox isIncomeCB) {
        this.categoryET = categoryET;
        this.valueEt = valueET;
        this.isIncomeCB = isIncomeCB;
        this.itemDBHandler = SignedInAppCompatActivity.itemDBHandler;
        this.categoryDBHandler = SignedInAppCompatActivity.categoryDBHandler;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        Item item = itemDBHandler.getItem(s.toString());
        if (item != null) {
            String category = categoryDBHandler.getCategory(item.getCategoryId());
            categoryET.setText(category);
            categoryET.setEnabled(false);
            String value = String.valueOf(item.getLastValue());
            valueEt.setText(value);
            isIncomeCB.setChecked(item.isIncome());
        } else {
            categoryET.setEnabled(true);
        }
    }

}
