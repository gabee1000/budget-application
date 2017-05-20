package com.example.gabor.mybudget.View.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gabor.mybudget.Model.Database.UserDatabaseHandler;
import com.example.gabor.mybudget.Model.Entities.User;
import com.example.gabor.mybudget.Presenter.Utils.BudgetAppCompatActivity;
import com.example.gabor.mybudget.Presenter.Utils.LauncherAppCompatActivity;
import com.example.gabor.mybudget.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gabor on 2017. 05. 20..
 */

public class RegisterActivity extends LauncherAppCompatActivity {
    private Button mRegisterToDbButton;
    private UserDatabaseHandler mUserDBHandler;
    private EditText mNewUserNameEditText;
    private EditText mNewPasswordEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        actions();
    }

    @Override
    protected void init() {
        super.setContentView(R.layout.register_activity);
        getSupportActionBar().setTitle(R.string.register);
        mRegisterToDbButton = (Button) findViewById(R.id.register_to_db_button);
        mNewUserNameEditText = (EditText) findViewById(R.id.new_user_name_et);
        mNewPasswordEditText = (EditText) findViewById(R.id.new_password_et);
        mUserDBHandler = new UserDatabaseHandler(RegisterActivity.this);
    }

    @Override
    protected void actions() {
        mRegisterToDbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<EditText> editTextList = new ArrayList<>();
                editTextList.add(mNewUserNameEditText);
                editTextList.add(mNewPasswordEditText);
                boolean isValid = validateEditTexts(editTextList);
                if (isValid) {
                    insertUserToDb();
                }
            }
        });
    }

    private void insertUserToDb() {
        String newUser = mNewUserNameEditText.getText().toString();
        String password = mNewPasswordEditText.getText().toString();
        User user = new User(newUser, password);
        if (mUserDBHandler.addUser(user)) {
            Toast.makeText(RegisterActivity.this, "[" + newUser + "] created!", Toast.LENGTH_SHORT).show();
            finish();
        }
        Toast.makeText(RegisterActivity.this, "[" + newUser + "] already exists!", Toast.LENGTH_SHORT).show();
    }
}
