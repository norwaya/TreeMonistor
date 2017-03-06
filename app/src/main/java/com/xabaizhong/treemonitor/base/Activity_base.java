package com.xabaizhong.treemonitor.base;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by admin on 2017/2/28.
 */

public class Activity_base extends AppCompatActivity {
    protected String TAG;
    protected SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.TAG = getClass().getSimpleName();
        initSharedPreferences();
    }

    private void initSharedPreferences() {
        sharedPreferences = getSharedPreferences("dic", MODE_PRIVATE);
    }


}
