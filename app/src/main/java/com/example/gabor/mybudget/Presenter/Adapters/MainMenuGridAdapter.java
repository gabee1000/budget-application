package com.example.gabor.mybudget.Presenter.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gabor.mybudget.R;

public class MainMenuGridAdapter extends BaseAdapter {
    public final GridItemResources mGridItemResources;
    private Context mContext;

    public MainMenuGridAdapter(Context c) {
        this.mContext = c;
        this.mGridItemResources = new GridItemResources();
    }

    public int getCount() {
        return GridItemResources.LENGTH;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view;
        if (convertView == null) {
            view = inflater.inflate(R.layout.main_menu_grid_item,parent,false);
            ((TextView) view.findViewById(R.id.grid_item_description)).setText(mGridItemResources.mDescriptions[position]);
            ((ImageView) view.findViewById(R.id.grid_item_image)).setImageResource(mGridItemResources.mImages[position]);
        } else {
            return convertView;
        }
        return view;
    }

    // references to our images
    public final class GridItemResources {
        public final static int LENGTH = 4;

        public final Integer[] mImages = {
                R.drawable.ic_note_add_white_48dp, R.drawable.ic_description_white_48dp,
                R.drawable.ic_find_in_page_white_48dp, R.drawable.ic_assessment_white_48dp
        };

        public final String[] mDescriptions = {
                mContext.getString(R.string.new_transaction), mContext.getString(R.string.items),
                mContext.getString(R.string.show_transactions), mContext.getString(R.string.statistics)
        };
    }

}
