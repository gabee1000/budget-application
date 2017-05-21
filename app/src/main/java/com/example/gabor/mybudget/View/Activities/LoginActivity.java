package com.example.gabor.mybudget.View.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.gabor.mybudget.Model.Constants.Constants;
import com.example.gabor.mybudget.Model.Database.UserDatabaseHandler;
import com.example.gabor.mybudget.Presenter.Utils.LauncherAppCompatActivity;
import com.example.gabor.mybudget.R;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends LauncherAppCompatActivity {

    private Button mLoginButton;
    private Button mRegisterButton;
    private UserDatabaseHandler mUserDBHandler;
    private EditText mPassword;
    private EditText mUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        actions();
    }


    @Override
    protected void init() {
        super.setContentView(R.layout.login_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle(R.string.login);
        mLoginButton = (Button) findViewById(R.id.login_button);
        mRegisterButton = (Button) findViewById(R.id.register_button);
        mUserName = (EditText) findViewById(R.id.user_name_et);
        mPassword = (EditText) findViewById(R.id.password_et);
        mUserDBHandler = new UserDatabaseHandler(LoginActivity.this);
    }

    @Override
    protected void actions() {
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateEditTexts()) {
                    return;
                }
                boolean userNameExists = mUserDBHandler.userNameExists(mUserName.getText().toString());
                if (!userNameExists) {
                    askIfWantToRegister();
                } else if (userNameExists) {
                    authenticateUser();
                }
            }


        });
    }

    /**
     * <p>Ask with a popup AlertDialog if the not existing username should be registered in the db.</p>
     */
    private void askIfWantToRegister() {
        final AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this, R.style.AlertDialogTheme).create();
        alertDialog.setTitle(getString(R.string.error));
        alertDialog.setMessage(getString(R.string.username_does_not_exist));
        Drawable drawable = getDrawable(android.R.drawable.ic_dialog_alert);
        assert drawable != null;
        drawable.setTint(Color.argb(255, 255, 80, 80));
        alertDialog.setIcon(drawable);
        alertDialog.setButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE, getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                String name = mUserName.getText().toString();
                intent.putExtra(Constants.Extra.REGISTER_NAME, name);
                startActivity(intent);
            }
        });
        alertDialog.setButton(android.support.v7.app.AlertDialog.BUTTON_NEGATIVE, getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private boolean validateEditTexts() {
        List<EditText> editTextList = new ArrayList<>();
        editTextList.add(mUserName);
        editTextList.add(mPassword);
        if (!super.validateEditTexts(editTextList)) {
            return false;
        }
        return true;
    }

    /**
     *
     * @return
     */
    private boolean authenticateUser() {
        String name = mUserName.getText().toString();
        String password = mPassword.getText().toString();
        if (mUserDBHandler.authenticate(name, password)) {
            Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
            startActivity(intent);
            finish();
        }
        return false;
    }

}
