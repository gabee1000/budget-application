package com.example.gabor.mybudget.View.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.gabor.mybudget.Model.Constants.Constants;
import com.example.gabor.mybudget.Model.Entities.Item;
import com.example.gabor.mybudget.Presenter.Adapters.ItemsListAdapter;
import com.example.gabor.mybudget.Presenter.Callbacks.ResultListener;
import com.example.gabor.mybudget.Presenter.Listeners.DismissDialogClickListener;
import com.example.gabor.mybudget.Presenter.Utils.SignedInAppCompatActivity;
import com.example.gabor.mybudget.R;
import com.example.gabor.mybudget.View.Dialogs.ErrorDialog;
import com.example.gabor.mybudget.View.Dialogs.NewItemDialog;

/**
 * Created by Gabor on 2017. 05. 26..
 */

public class ItemsActivity extends SignedInAppCompatActivity implements ResultListener {
    private ListView mListView;
    private ItemsListAdapter mAdapter;

    @Override
    protected void init() {
        setContentView(R.layout.items_activity);
        mListView = (ListView) findViewById(R.id.items_list_view);
        mAdapter = new ItemsListAdapter(this, mItemDBHandler);
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected void actions() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_new_item:
                inflateNewItemDialog();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void inflateNewItemDialog() {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.Extra.LAYOUT_RES, R.layout.add_new_item_layout);
        NewItemDialog newItemDialog = new NewItemDialog();
        newItemDialog.setArguments(bundle);
        newItemDialog.show(getFragmentManager(), "new_item");
    }

    @Override
    public void onResult(int requestCode, Intent data) {
        switch (requestCode) {
            case Constants.RequestCodes.NEW_ITEM_REQUEST:
                processNewItemRequest(data);
                break;
            case Constants.RequestCodes.EMPTY_EDIT_TEXTS:
                showErrorDialog(getString(R.string.fields_were_missing));
                break;
            default:
                break;
        }
    }

    /**
     * <p>Attempt to add new Item to item_table and new category associated with Item to category_table.</p>
     *
     * @param data where new Item informations are stored.
     * @return True if item was added successfully to DB, false if item name already exists in the DB or an error occurred.
     */
    private boolean processNewItemRequest(Intent data) {
        if (data.hasExtra(Constants.Extra.ITEM_NAME)
                && data.hasExtra(Constants.Extra.CATEGORY_NAME)
                && data.hasExtra(Constants.Extra.VALUE)
                && data.hasExtra(Constants.Extra.IS_INCOME)) {
            Bundle bundle = data.getExtras();
            String itemName = bundle.getString(Constants.Extra.ITEM_NAME);
            String categoryName = bundle.getString(Constants.Extra.CATEGORY_NAME);
            long value = bundle.getLong(Constants.Extra.VALUE);
            boolean isIncome = bundle.getBoolean(Constants.Extra.IS_INCOME);

            if (mItemDBHandler.getItem(itemName) != null) {
                showErrorDialog(getString(R.string.the_item) + " [" + itemName + "] " + getString(R.string.already_exists_in_the_db));
                return false;
            }

            mCategoryDBHandler.addCategory(categoryName);
            long categoryId = mCategoryDBHandler.getCategoryId(categoryName);
            Item item = new Item(0, itemName, categoryId, value, isIncome);
            mItemDBHandler.addItem(item);
            mAdapter.notifyDBChanged();
            return true;
        }
        return false;
    }

    private void showErrorDialog(String message) {
        ErrorDialog dialog = (ErrorDialog) new ErrorDialog()
                .setTitle(getString(R.string.error))
                .setMessage(message);
        dialog.setPositiveClickListener(new DismissDialogClickListener(dialog));
        dialog.show(getFragmentManager(), "error");
    }
}
