package com.xabaizhong.treemonitor.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.xabaizhong.treemonitor.R;
import com.xabaizhong.treemonitor.base.Activity_base;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/3/7.
 */

public class Activity_news_show extends Activity_base {
    String url;
    String title;

    @BindView(R.id.webv)
    WebView webv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_show);
        ButterKnife.bind(this);
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        getSupportActionBar().setTitle(title);
        initWebView();
    }

    private void initWebView() {
        webv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        webv.loadUrl(url);
        WebSettings webSettings = webv.getSettings();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webv.canGoBack()){
            webv.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
