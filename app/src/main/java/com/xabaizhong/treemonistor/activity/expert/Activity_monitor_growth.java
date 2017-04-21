package com.xabaizhong.treemonistor.activity.expert;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.FrameLayout;

import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.base.Activity_base;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by norwaya on 17-4-15.
 */

public class Activity_monitor_growth extends Activity_base implements Fragment_expert_growth_list.CallBack {

    @BindView(R.id.fragment_detail)
    FrameLayout fragmentDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_growth);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

    }

    @Override
    public void onClickItem(int position) {
        Log.i(TAG, "onClickItem: " + position);
        Fragment_monitor_growth_detail fragment = new Fragment_monitor_growth_detail();
        Bundle bundle = new Bundle();
        bundle.putInt("f1Id",position);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_detail,fragment).commit();
    }


    public static class GrowthInfo {
        public static List<GrowthBean> list = new ArrayList<>();

        static {
            list.clear();
            list.add(new GrowthBean("正常株", "正常叶片量占叶片总量大于95%", "枝条生长正常、新枝数量多，无枯枝枯梢。", "树干基本完好无坏死。"));
            list.add(new GrowthBean("衰弱株", "正常叶片量占叶片总量的95%-50%。", "新梢生长偏弱，枝条有少量枯死。", "树干局部有轻伤或少量坏死。"));
            list.add(new GrowthBean("濒危株", "正常叶片量占叶片总量小于50%。", "枝杈枯死较多。", "树干多为坏死，干枯或成凹洞。"));
            list.add(new GrowthBean("死亡株", "无正常叶片。", "枝条枯死，无新梢和萌条。", "树干坏死，损伤，腐朽现象严重，无活树皮。"));
        }

        public static class GrowthBean {
            private String name;
            private String leaf;
            private String branch;
            private String trunk;

            public GrowthBean(String name, String leaf, String branch, String trunk) {
                this.name = name;
                this.leaf = leaf;
                this.branch = branch;
                this.trunk = trunk;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLeaf() {
                return leaf;
            }

            public void setLeaf(String leaf) {
                this.leaf = leaf;
            }

            public String getBranch() {
                return branch;
            }

            public void setBranch(String branch) {
                this.branch = branch;
            }

            public String getTrunk() {
                return trunk;
            }

            public void setTrunk(String trunk) {
                this.trunk = trunk;
            }
        }
    }
}
