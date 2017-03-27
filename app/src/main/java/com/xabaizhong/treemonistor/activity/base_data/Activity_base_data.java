package com.xabaizhong.treemonistor.activity.base_data;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.base.Activity_base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/3/14.
 */

public class Activity_base_data extends Activity_base implements AdapterView.OnItemClickListener {
    @BindView(R.id.lv)
    ListView lv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_base);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(this).inflate(R.layout.fragment_function_header, lv, false);
        ImageView iv = (ImageView) view.findViewById(R.id.iv);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            iv.setImageDrawable(this.getDrawable(R.drawable.tree_header));
        } else {
            iv.setImageDrawable(getResources().getDrawable(R.drawable.tree_header));
        }
        lv.addHeaderView(view);
        lv.setOnItemClickListener(this);
        lv.setAdapter(new SimpleAdapter(this, initList(), R.layout.fragment_function_list_item, new String[]{"image", "name"}, new int[]{R.id.iv, R.id.text}));
    }

    List<Map<String, Object>> initList() {
        List<Map<String, Object>> list = new ArrayList<>();
        String[] array = getResources().getStringArray(R.array.data_base);
        int[] images = {R.drawable.ic_fragment_function_monistor,
                R.drawable.ic_fragment_function_monistor,
                R.drawable.ic_fragment_function_monistor,
                R.drawable.ic_fragment_function_monistor,
                R.drawable.ic_fragment_function_monistor};
        Map<String, Object> map;
        for (int i = 0; i < array.length; i++) {
            map = new HashMap<>();
            map.put("name", array[i]);
            map.put("image", images[i]);

            list.add(map);
        }
        return list;
    }


    static final int TREE_BASE_DATA = 1;
    static final int TREE_BASE_BUG = 2;
    static final int TREE_BASE_DISEASE = 3;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "onItemClick: " + position);
        switch (position) {
            case TREE_BASE_DATA:
                startActivity(new Intent(this, Activity_tree_base.class));
                break;
            case TREE_BASE_BUG:
                startActivity(new Intent(this, Activity_base_pest.class));
                break;
            case TREE_BASE_DISEASE:
                startActivity(new Intent(this, Activity_base_weakness.class));
                break;
        }
    }
}
