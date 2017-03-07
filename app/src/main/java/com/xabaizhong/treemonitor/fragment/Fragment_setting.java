package com.xabaizhong.treemonitor.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xabaizhong.treemonitor.R;
import com.xabaizhong.treemonitor.adapter.CommonRecyclerViewAdapter;
import com.xabaizhong.treemonitor.adapter.Fragment_setting_adapter;
import com.xabaizhong.treemonitor.base.Fragment_base;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/2/24.
 */

public class Fragment_setting extends Fragment_base {
    @BindView(R.id.rv)
    RecyclerView rv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this, view);
        initRv();
        return view;
    }

    Fragment_setting_adapter adapter;

    private void initRv() {
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new Fragment_setting_adapter(getContext(), R.layout.fragment_settings_item);
        adapter.setCallBack(new CommonRecyclerViewAdapter.CallBack<Fragment_setting_adapter_viewHolder, String>() {
            @Override
            public void bindView(Fragment_setting_adapter_viewHolder holder, int position, List<String> list) {
                Picasso.with(getContext())
                        .load(R.drawable.ic_fragment_expert_bug)
                        .into(holder.iv);
                holder.tv.setText(list.get(position));
            }

            @Override
            public void onItemClickListener(View view, int position) {

            }
        });
        rv.setAdapter(adapter);
        adapter.setSource(getList());
    }

    private List<String> getList() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("content \t" + i);
        }
        return list;
    }

    public static class Fragment_setting_adapter_viewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv;

        public Fragment_setting_adapter_viewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.fragment_setting_iv);
            tv = (TextView) itemView.findViewById(R.id.fragment_setting_tv);
        }
    }

}
