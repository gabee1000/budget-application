package com.example.gabor.mybudget.View.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.gabor.mybudget.Presenter.Adapters.MainMenuGridAdapter;
import com.example.gabor.mybudget.Presenter.Utils.SignedInAppCompatActivity;
import com.example.gabor.mybudget.R;

/**
 * Created by Gabor on 2017. 05. 20..
 */

public class MainMenuActivity extends SignedInAppCompatActivity {
    private GridView mMenuGridView;
    private MainMenuGridAdapter mMainMenuGridAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        actions();
    }

    @Override
    protected void init() {
        setContentView(R.layout.main_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle(R.string.my_budget);
        initGridView();
    }

    private void initGridView() {
        mMenuGridView = (GridView) findViewById(R.id.gridview);
        mMainMenuGridAdapter = new MainMenuGridAdapter(this);
        mMenuGridView.setAdapter(mMainMenuGridAdapter);
    }

    @Override
    protected void actions() {
        mMenuGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                processItemClick(parent, v, position, id);
            }
        });
    }

    private void processItemClick(AdapterView<?> parent, View v, int position, long id) {
        String selectedItem = mMainMenuGridAdapter.mGridItemResources.mDescriptions[position];
        final String addBudget = getString(R.string.add_budget);
        final String addItem = getString(R.string.items);
        final String showBudget = getString(R.string.show_budget);
        final String statistics = getString(R.string.statistics);
        if (selectedItem.equals(addBudget)) {

        } else if (selectedItem.equals(addItem)) {

        } else if (selectedItem.equals(showBudget)) {

        } else if (selectedItem.equals(statistics)) {

        }
    }
}
