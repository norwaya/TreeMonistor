package com.xabaizhong.treemonitor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.widget.Button;

import com.xabaizhong.treemonitor.R;
import com.xabaizhong.treemonitor.base.Activity_base;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by admin on 2017/2/28.
 */

public class Activity_add_tree extends Activity_base {
    ArrayList<String> list;

    static final int REQUEST_IMAGE = 0x123;
    @BindView(R.id.layout)
    CoordinatorLayout layout;
    @BindView(R.id.btn)
    Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn)
    public void onClick() {
//        Snackbar.make(layout,"abc",Snackbar.LENGTH_LONG).show();
       /* MultiImageSelector.create(getApplicationContext())
                .showCamera(true) // show camera or not. true by default
                .count(4) // max select image size, 9 by default. used width #.multi()
                .single() // single mode
                .multi() // multi mode, default mode;
                .origin(list) // original select data set, used width #.multi()
                .start(Activity_add_tree.this, REQUEST_IMAGE);*/


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                // Get the result list of select image paths
                list = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                // do your logic ....
                Log.d(TAG, "onActivityResult: " + list.size());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
