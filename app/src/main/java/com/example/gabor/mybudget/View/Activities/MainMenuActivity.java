package com.example.gabor.mybudget.View.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.gabor.mybudget.Presenter.Adapters.MainMenuGridAdapter;
import com.example.gabor.mybudget.Presenter.Utils.SignedInAppCompatActivity;
import com.example.gabor.mybudget.R;

/**
 * Created by Gabor on 2017. 05. 20..
 */

public class MainMenuActivity extends SignedInAppCompatActivity {

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
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new MainMenuGridAdapter(this));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(MainMenuActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void actions() {

    }
}
