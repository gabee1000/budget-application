package com.example.gabor.mybudget.Presenter.Adapters;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.Toast;

import com.example.gabor.mybudget.R;
import com.example.gabor.mybudget.View.Fragments.SummaryFragment;


public class StatisticsPagerAdapter extends FragmentStatePagerAdapter {

    private final int NUM_PAGES = 13;
    private final Context context;
    private int mSelectedYear;

    public StatisticsPagerAdapter(Context context, FragmentManager fm, int selectedYear) {
        super(fm);
        this.context = context;
        this.mSelectedYear = selectedYear;
    }

    @Override
    public Fragment getItem(int position) {
        SummaryFragment fragment = new SummaryFragment();
        fragment.showSummaryAtPositionByDate(position, mSelectedYear);
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title;
        switch (position) {
            case 0:
                title = String.valueOf(mSelectedYear);
                break;
            default:
                String[] months = context.getResources().getStringArray(R.array.months);
                title = months[position - 1];
                break;
        }
        return title;
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

    public void refreshState(int selectedYear) {
        this.mSelectedYear = selectedYear;
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}