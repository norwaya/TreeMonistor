package com.xabaizhong.treemonistor.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.squareup.picasso.Picasso;
import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.myview.MyZoomImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
@Deprecated
public class Activity_image_noAction extends Activity_base {
    @BindView(R.id.zoom_image_view)
    MyZoomImageView zoomImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String uri = getIntent().getStringExtra("uri");
        setContentView(R.layout.activity_image_noaction);
        ButterKnife.bind(this);
        if(uri.equals("")){
            Log.i(TAG, "onCreate: null");
        }else{
            Log.i(TAG, "onCreate: "+uri);
            Picasso.with(this).load(Uri.parse(uri)).into(zoomImageView);
        }
    }
}
