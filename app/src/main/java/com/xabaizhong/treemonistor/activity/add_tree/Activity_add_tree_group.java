package com.xabaizhong.treemonistor.activity.add_tree;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.myview.C_dialog_radio;
import com.xabaizhong.treemonistor.myview.C_info_gather_item1;
import com.xabaizhong.treemonistor.utils.MessageEvent;
import com.xabaizhong.treemonistor.utils.RxBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by admin on 2017/2/28.
 */

public class Activity_add_tree_group extends Activity_base  {
    ArrayList<String> list;


    Disposable disposable;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();


    }

    private void init() {
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
        switch (requestCode) {
            case ResultCode.REQUEST_IMAGE:
                if (resultCode == RESULT_OK) {
                    // Get the result list of select image paths
                    list = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    // do your logic ....
                    Log.d(TAG, "onActivityResult: " + list.size());
                }
            case ResultCode.REQUEST_CODE_REGION:
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    interface ResultCode {
        int REQUEST_CODE_REGION = 0x100;
        int REQUEST_IMAGE = 0x123;
    }


    @Override
    protected void onDestroy() {
        if (disposable != null && !disposable.isDisposed())
            disposable.dispose();
        super.onDestroy();
    }
}
