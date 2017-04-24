package com.xabaizhong.treemonistor.activity.monitor_query;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.base.Activity_base;

/**
 * Created by Administrator on 2017/4/24 0024.
 */

public class Activity_monitor_query_dateList extends Activity_base {

    String treeId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_query_datelist);
        treeId = getIntent().getStringExtra("tree-id");
    }
}
