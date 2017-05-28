package com.example.gabor.mybudget.View.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.gabor.mybudget.Model.Constants.Constants;
import com.example.gabor.mybudget.Model.Entities.Item;
import com.example.gabor.mybudget.Presenter.Adapters.ItemsListArrayAdapter;
import com.example.gabor.mybudget.Presenter.Callbacks.ResultListener;
import com.example.gabor.mybudget.Presenter.Utils.SignedInAppCompatActivity;
import com.example.gabor.mybudget.R;
import com.example.gabor.mybudget.View.Dialogs.NewItemDialog;

// TODO When inserting anything to DB, use toLowerCase() method on everything to check for existing names in DB.

public class ItemsActivity extends SignedInAppCompatActivity implements ResultListener {
    private ListView mListView;
//    private ItemsListBaseAdapter mAdapter;
    private ItemsListArrayAdapter mAdapter;

    @Override
    protected void init() {
        setContentView(R.layout.items_activity);
        mListView = (ListView) findViewById(R.id.items_list_view);
//        mAdapter = new ItemsListBaseAdapter(this, itemDBHandler);
        mAdapter = new ItemsListArrayAdapter(this, android.R.layout.simple_list_item_1, itemDBHandler.getAllItems());
        mListView.setAdapter(mAdapter);
        toolbar.setTitle(getString(R.string.items));
    }

    @Override
    protected void actions() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent data = new Intent();
//                Item item = mAdapter.getItem(position);
                Item item = mAdapter.mItemList.get(position);
                data.putExtra(Constants.Extra.ITEM, item);
                inflateNewItemDialog(Constants.RequestCodes.INFLATE_ITEM_EDITOR, data);
            }
        });
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
                inflateNewItemDialog(Constants.RequestCodes.INFLATE_ADD_NEW_ITEM, null);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * <p>Inflate item editor dialog.</p>
     *
     * @param inflateCode of the inflation type
     * @param data
     */
    private void inflateNewItemDialog(int inflateCode, @Nullable Intent data) {
        NewItemDialog newItemDialog = new NewItemDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.Extra.LAYOUT_RES, R.layout.add_new_item_layout);
        switch (inflateCode) {
            case Constants.RequestCodes.INFLATE_ADD_NEW_ITEM:
                break;
            case Constants.RequestCodes.INFLATE_ITEM_EDITOR:
                if (data.hasExtra(Constants.Extra.ITEM)) {
                    bundle.putParcelable(Constants.Extra.ITEM, data.getParcelableExtra(Constants.Extra.ITEM));
                    bundle.putInt(Constants.Extra.EDIT_ITEM, 1);
                }
                break;
        }
        newItemDialog.setArguments(bundle);
        newItemDialog.show(getFragmentManager(), "new_item");
    }

    /**
     * <p>Called when the new item dialog finishes by clicking its 'OK' button.</p>
     *
     * @param resultCode of the callback
     * @param data       of the result
     */
    @Override
    public void onResult(int resultCode, Intent data) {
        switch (resultCode) {
            case Constants.ResultCodes.NEW_ITEM_REQUEST:
                processNewItemRequest(data, -2);
                break;
            case Constants.ResultCodes.EMPTY_EDIT_TEXTS:
                showErrorDialog(getString(R.string.fields_were_missing));
                break;
            case Constants.ResultCodes.EDIT_ITEM_REQUEST:
                processNewItemRequest(data, resultCode);
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
    private boolean processNewItemRequest(Intent data, int code) {
        if (hasEditTextExtras(data)) {
            Bundle bundle = data.getExtras();
            String itemName = bundle.getString(Constants.Extra.ITEM_NAME);
            String categoryName = bundle.getString(Constants.Extra.CATEGORY_NAME);
            long value = bundle.getLong(Constants.Extra.VALUE);
            boolean isIncome = bundle.getBoolean(Constants.Extra.IS_INCOME);

            if (itemDBHandler.getItem(itemName) != null) {
                showErrorDialog(getString(R.string.the_item) + " [" + itemName + "] " + getString(R.string.already_exists_in_the_db));
                return false;
            }

            long categoryId = 0;
            if (code == Constants.ResultCodes.EDIT_ITEM_REQUEST) {
                if (data.hasExtra(Constants.Extra.CATEGORY_ID)) {
                    categoryId = bundle.getLong(Constants.Extra.CATEGORY_ID);
                    categoryDBHandler.updateCategoryById(categoryName, categoryId);
                }
            } else {
                categoryDBHandler.addCategory(categoryName);
                categoryId = categoryDBHandler.getCategoryId(categoryName);
            }

            Item item = new Item(0, itemName, categoryId, value, isIncome);
            if (code == Constants.ResultCodes.EDIT_ITEM_REQUEST) {
                if (data.hasExtra(Constants.Extra.ITEM_ID)) {
                    long itemId = bundle.getLong(Constants.Extra.ITEM_ID);
                    itemDBHandler.updateItemById(item, itemId);
                }
            } else {
                itemDBHandler.addItem(item);
            }
            mAdapter.notifyDBChanged();
            return true;
        }
        return false;
    }

    private boolean hasEditTextExtras(Intent data) {
        return data.hasExtra(Constants.Extra.ITEM_NAME)
                && data.hasExtra(Constants.Extra.CATEGORY_NAME)
                && data.hasExtra(Constants.Extra.VALUE)
                && data.hasExtra(Constants.Extra.IS_INCOME);
    }
}
