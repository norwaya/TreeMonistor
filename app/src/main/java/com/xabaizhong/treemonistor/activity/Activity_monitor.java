package com.xabaizhong.treemonistor.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.base.Activity_base;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 2017/2/28.
 */

public class Activity_monitor extends Activity_base {

    ArrayList<String> imageList ;

    @BindView(R.id.tree_code)
    TextView treeCode;
    @BindView(R.id.rv)
    RecyclerView rv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);
        ButterKnife.bind(this);

        initRv();
    }

    private void initRv() {

    }

    @OnClick({R.id.btn, R.id.tree_code, R.id.submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn:
                break;
            case R.id.tree_code:
                break;
            case R.id.submit:
                break;
        }
    }
}
