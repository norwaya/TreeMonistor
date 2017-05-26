package com.xabaizhong.treemonistor.activity.expert;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xabaizhong.treemonistor.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by norwaya on 17-4-15.
 */

public class Fragment_monitor_growth_detail extends Fragment {
    @BindView(R.id.leaf)
    TextView leaf;
    @BindView(R.id.branch)
    TextView branch;
    @BindView(R.id.trunk)
    TextView trunk;
    Unbinder unbinder;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_monitor_growth_detail, container,false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        int id = getArguments().getInt("f1Id");
        Activity_monitor_growth.GrowthInfo.GrowthBean bean = Activity_monitor_growth.GrowthInfo.list.get(id);
        leaf.setText(bean.getLeaf());
        branch.setText(bean.getBranch());
        trunk.setText(bean.getTrunk());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
