package com.xabaizhong.treemonistor.activity.expert;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.myview.C_info_gather_item1;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *  专家鉴定 生长势鉴定
 */
public class Activity_expert_growth_detail extends Activity_base {
    int type;
    @BindView(R.id.c1)
    C_info_gather_item1 c1;
    @BindView(R.id.c2)
    C_info_gather_item1 c2;
    @BindView(R.id.c3)
    C_info_gather_item1 c3;
    @BindView(R.id.c4)
    C_info_gather_item1 c4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_growth_detail);
        ButterKnife.bind(this);
        type = getIntent().getIntExtra("type", -1);
        if (type != -1) {
            initialView();
        }
    }
    // 初始化 view
    private void initialView() {
        GrowthInfo.GrowthBean bean = GrowthInfo.list.get(type);
        c1.setText(bean.getName());
        setTitle(bean.getName());
        c2.setText(bean.getLeaf());
        c3.setText(bean.getBranch());
        c4.setText(bean.getTrunk());

    }
    // 初始化 生长势 字符 资源；
    public static class GrowthInfo {
        public static List<GrowthBean> list = new ArrayList<>();

        static {
            list.clear();
            list.add(new GrowthBean("正常株", "正常叶片量占叶片总量大于95%", "枝条生长正常、新枝数量多，无枯枝枯梢。", "树干基本完好无坏死。"));
            list.add(new GrowthBean("衰弱株", "正常叶片量占叶片总量的95%-50%。", "新梢生长偏弱，枝条有少量枯死。", "树干局部有轻伤或少量坏死。"));
            list.add(new GrowthBean("濒危株", "正常叶片量占叶片总量小于50%。", "枝杈枯死较多。", "树干多为坏死，干枯或成凹洞。"));
            list.add(new GrowthBean("死亡株", "无正常叶片。", "枝条枯死，无新梢和萌条。", "树干坏死，损伤，腐朽现象严重，无活树皮。"));
        }
         // 生长势 鉴定 bean
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
