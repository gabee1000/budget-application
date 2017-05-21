package com.example.gabor.mybudget.View.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gabor.mybudget.Model.Constants.Constants;
import com.example.gabor.mybudget.Model.Database.UserDatabaseHandler;
import com.example.gabor.mybudget.Model.Entities.User;
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
    private EditText mNewPasswordAgainEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        checkForExtras();
        actions();
    }

    private void checkForExtras() {
        Intent intent = getIntent();
        if (intent.hasExtra(Constants.Extra.REGISTER_NAME)) {
            Bundle bundle = intent.getExtras();
            String newName = bundle.getString(Constants.Extra.REGISTER_NAME);
            mNewUserNameEditText.setText(newName);
        }
    }

    @Override
    protected void init() {
        super.setContentView(R.layout.register_activity);
        getSupportActionBar().setTitle(R.string.register);
        mRegisterToDbButton = (Button) findViewById(R.id.register_to_db_button);
        mNewUserNameEditText = (EditText) findViewById(R.id.new_user_name_et);
        mNewPasswordEditText = (EditText) findViewById(R.id.new_password_et);
        mNewPasswordAgainEditText = (EditText) findViewById(R.id.new_password_again_et);
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
                editTextList.add(mNewPasswordAgainEditText);
                boolean isValid = validateEditTexts(editTextList);
                if (isValid) {
                    insertUserToDb();
                }
            }
        });
    }

    /**
     * <p>Validate the two required password fields, if they are matching.</p>
     * @param editTextList
     * @return True if the password fields are matching, false otherwise.
     */
    @Override
    protected boolean validateEditTexts(List<EditText> editTextList) {
        if (!super.validateEditTexts(editTextList)) {
            return false;
        }
        boolean isEqual = mNewPasswordEditText.getText().toString().equals(mNewPasswordAgainEditText.getText().toString());
        if (!isEqual) {
            mNewPasswordAgainEditText.setError(getString(R.string.passwords_are_not_matching));
            return false;
        }
        return true;
    }

    private void insertUserToDb() {
        String newUser = mNewUserNameEditText.getText().toString();
        String password = mNewPasswordEditText.getText().toString();
        User user = new User(newUser, password);
        if (mUserDBHandler.addUser(user)) {
            Toast.makeText(RegisterActivity.this, "[" + newUser + "] created!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        Toast.makeText(RegisterActivity.this, "[" + newUser + "] already exists!", Toast.LENGTH_SHORT).show();
    }
}
