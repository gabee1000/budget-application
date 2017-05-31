package com.example.gabor.mybudget.Presenter.Listeners;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.EditText;

import com.example.gabor.mybudget.Model.Constants.Constants;
import com.example.gabor.mybudget.View.Activities.LoginActivity;
import com.example.gabor.mybudget.View.Activities.RegisterActivity;

public class ShouldCreateUserClickListener implements DialogInterface.OnClickListener {
    private final Context context;
    private final EditText user;

    public ShouldCreateUserClickListener(Context context, EditText user) {
        this.context = context;
        this.user = user;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        Intent intent = new Intent(context, RegisterActivity.class);
        String name = user.getText().toString();
        intent.putExtra(Constants.Extra.REGISTER_NAME, name);
        context.startActivity(intent);
    }
}
