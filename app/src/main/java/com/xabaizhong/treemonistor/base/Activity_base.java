package com.xabaizhong.treemonistor.base;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.xabaizhong.treemonistor.contant.Contant;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

/**
 * Created by admin on 2017/2/28.
 */

public class Activity_base extends AppCompatActivity {
    protected String TAG;
    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.TAG = getClass().getSimpleName();
        initSharedPreferences();
    }

    private void initSharedPreferences() {
        sharedPreferences = getSharedPreferences("dic", MODE_PRIVATE);
    }

    Toast toast;

    protected void showToast(String text) {
        if (toast == null)
            toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
        else
            toast.setText(text);
        toast.show();
    }

    protected boolean login_suc() {
        Set<String> userSet = sharedPreferences.getStringSet(Contant.KV.USER, null);
        if (userSet == null) {
            return false;
        }
        return true;
    }

    protected String getStringDate() {
        return getStringDate(null);

    }

    protected String getStringDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (date == null)
            date = new Date();
        return sdf.format(date);

    }
}
