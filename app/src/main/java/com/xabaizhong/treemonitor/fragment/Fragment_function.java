package com.xabaizhong.treemonitor.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.xabaizhong.treemonitor.R;
import com.xabaizhong.treemonitor.activity.Activity_add_manage;
import com.xabaizhong.treemonitor.activity.Activity_monitor;
import com.xabaizhong.treemonitor.activity.Activity_query;
import com.xabaizhong.treemonitor.base.Fragment_base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/2/24.
 */

public class Fragment_function extends Fragment_base implements AdapterView.OnItemClickListener {
    String TAG = "fragment-function";

    private Context mContext;

    @BindView(R.id.lv)
    ListView lv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_function, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        contentArray = mContext.getResources().getStringArray(R.array.function_array);
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_function_header, null);
        lv.addHeaderView(view);
        lv.setAdapter(new SimpleAdapter(getContext(), getList(), R.layout.fragment_function_list_item, new String[]{"image", "name"}, new int[]{R.id.iv, R.id.text1}));
        lv.setOnItemClickListener(this);
    }

    int[] images = {R.drawable.ic_fragment_functin_tree,
            R.drawable.ic_fragment_function_query,
            R.drawable.ic_fragment_function_monistor};
    String[] contentArray;

    private List<Map<String, Object>> getList() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", contentArray[i]);
            map.put("image", images[i]);
            list.add(map);

        }
        return list;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    final static int ACTIVITY_ADD = 1;
    final static int ACTIVITY_QUERY = 2;
    final static int ACTIVITY_MONITOR = 3;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onItemClick: " + position);
        Log.d(TAG, "onItemClick: " + getContext().getPackageName());
        Intent i = new Intent();
        switch (position) {
            case ACTIVITY_ADD:
                i.setClass(getContext(), Activity_add_manage.class);
                break;
            case ACTIVITY_QUERY:
                i.setClass(getContext(), Activity_query.class);
                break;
            case ACTIVITY_MONITOR:
                i.setClass(getContext(), Activity_monitor.class);
                break;
        }
        getActivity().startActivity(i);
    }
}
