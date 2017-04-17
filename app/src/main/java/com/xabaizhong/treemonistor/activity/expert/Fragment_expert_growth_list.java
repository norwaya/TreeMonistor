package com.xabaizhong.treemonistor.activity.expert;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.xabaizhong.treemonistor.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by norwaya on 17-4-15.
 */

public class Fragment_expert_growth_list extends Fragment implements AdapterView.OnItemClickListener {
    @BindView(R.id.list_view)
    ListView listView;
    Unbinder unbinder;
    List<Map<String, String>> list;

    CallBack callBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expert_growth_list, container);
        unbinder = ButterKnife.bind(this, view);
        callBack = (CallBack) getActivity();
        initView();
        return view;
    }

    private void initView() {
        Adapter adapter = new Adapter(Activity_monitor_growth.GrowthInfo.list, getContext());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }





    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (callBack != null)
            callBack.onClickItem(position);
    }

    public interface CallBack {
        void onClickItem(int position);
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    private class Adapter extends BaseAdapter {
        private final List<Activity_monitor_growth.GrowthInfo.GrowthBean> list;
        LayoutInflater inflater;

        public Adapter(List<Activity_monitor_growth.GrowthInfo.GrowthBean> list, Context context) {
            this.list = list;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return list.get(position).hashCode();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            view.setText(list.get(position).getName());
            return view;
        }
    }
}
