package com.example.gabor.mybudget.View.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.gabor.mybudget.Presenter.Adapters.StatisticsPagerAdapter;
import com.example.gabor.mybudget.R;

import java.util.Calendar;

/**
 * Created by Gabor on 2017. 05. 29..
 */

public class StatisticsActivity extends TransactionsActivity implements DatePickerDialog.OnDateSetListener {

    private ViewPager mSummaryViewPager;
    private StatisticsPagerAdapter mStatisticsPagerAdapter;
    private TabLayout mTabLayout;

    @Override
    protected void init() {
        setContentView(R.layout.statistics_activity);
        setSelectedYear();
        mSummaryViewPager = (ViewPager) findViewById(R.id.summary_view_pager);
        mStatisticsPagerAdapter = new StatisticsPagerAdapter(this, getSupportFragmentManager(), SELECTED_YEAR);
        mSummaryViewPager.setAdapter(mStatisticsPagerAdapter);
        mSummaryViewPager.setPageMargin(60);
        mTabLayout = (TabLayout) findViewById(R.id.tablayout);
        mTabLayout.setupWithViewPager(mSummaryViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        toolbar.setTitle(getString(R.string.statistics));
    }

    private void setSelectedYear() {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        SELECTED_YEAR = c.get(Calendar.YEAR);
    }

    @Override
    protected void actions() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_statistics, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        SELECTED_YEAR = year;
        refreshDataSet();
    }

    private void refreshDataSet() {
        mStatisticsPagerAdapter.refreshState(SELECTED_YEAR);
    }

}
