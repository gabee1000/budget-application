package com.example.gabor.mybudget.Presenter.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gabor.mybudget.R;

/**
 * Created by Gabor on 2017. 05. 21..
 */

public class MainMenuGridAdapter extends BaseAdapter {
    private final GridItemResources mGridItemResources;
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

//        ImageView imageView;
//        if (convertView == null) {
//            // if it's not recycled, initialize some attributes
//            imageView = new ImageView(mContext);
//            imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageView.setPadding(8, 8, 8, 8);
//        } else {
//            imageView = (ImageView) convertView;
//        }
//        imageView.setImageResource(mThumbIds[position]);
//        return imageView;
    }

    // references to our images
    final class GridItemResources {
        final static int LENGTH = 4;

        public final Integer[] mImages = {
                R.drawable.ic_euro_symbol_black_48dp, R.drawable.ic_euro_symbol_black_48dp,
                R.drawable.ic_euro_symbol_black_48dp, R.drawable.ic_euro_symbol_black_48dp
        };

        public final String[] mDescriptions = {
                mContext.getString(R.string.add_budget), mContext.getString(R.string.add_item),
                mContext.getString(R.string.show_budget), mContext.getString(R.string.statistics)
        };
    }

}
