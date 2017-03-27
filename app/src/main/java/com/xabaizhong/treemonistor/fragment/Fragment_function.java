package com.xabaizhong.treemonistor.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.activity.Activity_login;
import com.xabaizhong.treemonistor.activity.Activity_monitor;
import com.xabaizhong.treemonistor.activity.add_tree.Activity_add_manage;
import com.xabaizhong.treemonistor.activity.base_data.Activity_base_data;
import com.xabaizhong.treemonistor.activity.query.Activity_query;
import com.xabaizhong.treemonistor.adapter.Fragment_function_adapter;
import com.xabaizhong.treemonistor.adapter.HeaderAndFooterWrapper;
import com.xabaizhong.treemonistor.base.Fragment_base;
import com.xabaizhong.treemonistor.contant.UserSharedField;
import com.xabaizhong.treemonistor.utils.RecycleViewDivider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.widget.LinearLayout.VERTICAL;

/**
 * Created by admin on 2017/2/24.
 */

public class Fragment_function extends Fragment_base implements Fragment_function_adapter.OnItemClickListener {
    String TAG = "fragment-function";
    @BindView(R.id.function_rv)
    RecyclerView functionRv;
    private Context mContext;
   /* @BindView(R.id.lv)
    ListView lv;*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_function, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_function_header, null);
        ImageView iv = (ImageView) view.findViewById(R.id.iv);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            iv.setImageDrawable(mContext.getDrawable(R.drawable.tree_header));
        } else {
            iv.setImageDrawable(getResources().getDrawable(R.drawable.tree_header));
        }
        //init RecyclerView
        Fragment_function_adapter function_adapter = new Fragment_function_adapter(context);
        HeaderAndFooterWrapper adapter = new HeaderAndFooterWrapper(function_adapter);

        adapter.addHeaderView(view);

        functionRv.addItemDecoration(new RecycleViewDivider(context, VERTICAL, R.drawable.divider));
        functionRv.setLayoutManager(new LinearLayoutManager(context));
        functionRv.setAdapter(adapter);

        function_adapter.setOnItemClickListener(this);

        String[] contentArray = mContext.getResources().getStringArray(R.array.function_array);

        function_adapter.setResource(Arrays.asList(
                R.drawable.ic_fragment_functin_tree,
                R.drawable.ic_fragment_function_query,
                R.drawable.ic_fragment_function_monistor,
                R.drawable.ic_fragment_function_monistor,
                R.drawable.ic_fragment_function_monistor,
                R.drawable.ic_fragment_function_monistor,
                R.drawable.ic_fragment_function_monistor,
                R.drawable.ic_fragment_function_monistor), Arrays.asList(contentArray));
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    final static int ACTIVITY_ADD = 0;
    final static int ACTIVITY_QUERY = 1;
    final static int ACTIVITY_REGULATION = 2;
    final static int ACTIVITY_REGULATION_QUERY = 3;
    final static int ACTIVITY_DATA = 4;
    final static int ACTIVITY_COMMUNICATE = 5;
    final static int ACTIVITY_NOTICE = 6;
    final static int ACTIVITY_EXPLAIN = 7;


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent i = new Intent();
        switch (position) {
            case ACTIVITY_ADD:
                i.setClass(getContext(), Activity_add_manage.class);
                getActivity().startActivity(i);
                break;
            case ACTIVITY_QUERY:
                String areaId = sharedPreferences.getString(UserSharedField.AREAID, "unlogin");
                if (areaId.equals("unlogin")) {
                    i.setClass(getContext(), Activity_login.class);
                }else{
                    i.putExtra("areaid", areaId);
                    i.setClass(getContext(), Activity_query.class);
                }
                getActivity().startActivity(i);
                break;
            case ACTIVITY_REGULATION:
                break;
            case ACTIVITY_REGULATION_QUERY:
                i.setClass(getContext(), Activity_monitor.class);
                getActivity().startActivity(i);
                break;
            case ACTIVITY_DATA:
                i.setClass(getContext(), Activity_base_data.class);
                getActivity().startActivity(i);
                break;
            case ACTIVITY_COMMUNICATE:
                break;
            case ACTIVITY_NOTICE:
                break;
            case ACTIVITY_EXPLAIN:
                break;
        }
    }
}
