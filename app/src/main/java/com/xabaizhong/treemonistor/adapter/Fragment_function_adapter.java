package com.xabaizhong.treemonistor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.xabaizhong.treemonistor.R;

/**
 * Created by admin on 2017/2/24.
 */

public class Fragment_function_adapter extends BaseAdapter {
    String[] contentArray;
    int[] images = {R.drawable.ic_menu_camera,
            R.drawable.ic_menu_camera,
            R.drawable.ic_menu_camera};
    LayoutInflater inflater;

    public Fragment_function_adapter(Context context) {
        inflater.from(context);
        contentArray = context.getResources().getStringArray(R.array.function_array);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return images[position];
    }

    @Override
    public long getItemId(int position) {
        return images[position];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
