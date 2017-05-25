package com.example.gabor.mybudget.View.Activities;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.gabor.mybudget.Model.Database.UserDatabaseHandler;
import com.example.gabor.mybudget.Presenter.Listeners.DismissDialogClickListener;
import com.example.gabor.mybudget.Presenter.Listeners.ShouldCreateUserClickListener;
import com.example.gabor.mybudget.Presenter.Utils.LauncherAppCompatActivity;
import com.example.gabor.mybudget.R;
import com.example.gabor.mybudget.View.Dialogs.ApproveDialog;
import com.example.gabor.mybudget.View.Dialogs.ErrorDialog;
import com.example.gabor.mybudget.View.Dialogs.InfoDialog;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends LauncherAppCompatActivity {

    private Button mLoginButton;
    private Button mRegisterButton;
    private UserDatabaseHandler mUserDBHandler;
    private EditText mPassword;
    private EditText mUserName;


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
                    if (!authenticateUser()) {
                        notifyIncorrectPassword();
                    }
                }
            }


        });
    }

    /**
     * <p>Notify the user of the entered incorrect password.</p>
     */
    private void notifyIncorrectPassword() {
        ErrorDialog errorDialog = (ErrorDialog) new ErrorDialog()
                .setTitle(getString(R.string.error))
                .setMessage(getString(R.string.incorrect_password));
        errorDialog.setPositiveClickListener(new DismissDialogClickListener(errorDialog));
        errorDialog.show(getFragmentManager(), "error");
    }

    /**
     * <p>Ask with a popup AlertDialog if the not existing username should be registered in the db.</p>
     */
    private void askIfWantToRegister() {
        ApproveDialog approveDialog = new ApproveDialog();
        approveDialog.setNegativeClickListener(new DismissDialogClickListener(approveDialog))
                .setTitle(getString(R.string.create_user))
                .setMessage(getString(R.string.username_does_not_exist))
                .setPositiveClickListener(new ShouldCreateUserClickListener(this, mUserName));
        approveDialog.show(getFragmentManager(), "register");
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
     * Logs in the user and finishes this activity if authentication successful.
     *
     * @return False if authentication failed.
     */
    private boolean authenticateUser() {
        String name = mUserName.getText().toString();
        String password = mPassword.getText().toString();
        if (mUserDBHandler.authenticate(name, password)) {
            Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return false;
    }

}
