package com.example.gabor.mybudget.Presenter.Utils;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.example.gabor.mybudget.Model.Constants.Constants;
import com.example.gabor.mybudget.Model.Database.CategoryDatabaseHandler;
import com.example.gabor.mybudget.Model.Database.ItemDatabaseHandler;
import com.example.gabor.mybudget.Model.Database.TransactionDatabaseHandler;
import com.example.gabor.mybudget.Model.Database.UserDatabaseHandler;
import com.example.gabor.mybudget.Presenter.Listeners.DismissDialogClickListener;
import com.example.gabor.mybudget.R;
import com.example.gabor.mybudget.View.Dialogs.ErrorDialog;

// TODO Navigation drawer
public abstract class SignedInAppCompatActivity extends BudgetAppCompatActivity {
    public static ItemDatabaseHandler itemDBHandler;
    public static CategoryDatabaseHandler categoryDBHandler;
    public static TransactionDatabaseHandler transactionDBHandler;
    public static UserDatabaseHandler userDatabaseHandler;

    public static String loggedInUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        itemDBHandler = new ItemDatabaseHandler(this);
        categoryDBHandler = new CategoryDatabaseHandler(this);
        transactionDBHandler = new TransactionDatabaseHandler(this);
        userDatabaseHandler = new UserDatabaseHandler(this);
        if (getIntent().hasExtra(Constants.Extra.LOGGED_IN_USER)) {
            loggedInUser = getIntent().getExtras().getString(Constants.Extra.LOGGED_IN_USER);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
    }

    protected void showErrorDialog(String message) {
        ErrorDialog dialog = (ErrorDialog) new ErrorDialog()
                .setTitle(getString(R.string.error))
                .setMessage(message);
        dialog.setPositiveClickListener(new DismissDialogClickListener(dialog));
        dialog.show(getFragmentManager(), "error");
    }
}
