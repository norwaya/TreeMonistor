package com.xabaizhong.treemonistor.activity;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.fragment.Fragment_expert;
import com.xabaizhong.treemonistor.fragment.Fragment_function;
import com.xabaizhong.treemonistor.fragment.Fragment_news;
import com.xabaizhong.treemonistor.fragment.Fragment_setting;
import com.xabaizhong.treemonistor.myview.MyRadio;
import com.xabaizhong.treemonistor.service.service_notice.NoticeBroadCast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Activity_main extends Activity_base implements MyRadio.OnRadioCheckedListenter {
    final static int FRAGMENT_SIZE = 4;
    @BindView(R.id.tab_news)
    MyRadio tabNews;
    @BindView(R.id.tab_function)
    MyRadio tabFunction;
    @BindView(R.id.tab_expert)
    MyRadio tabExpert;
    @BindView(R.id.tab_setting)
    MyRadio tabSetting;
    @BindView(R.id.layout)
    CoordinatorLayout layout;
    //    @BindView(R.id.vp)
//    ViewPager vp;
    private int CHECKED_RADIO_ID = -1;

    List<MyRadio> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initSource();
    }



    int REQEUST_CODE_WRITER = 0x100;


    /**
     * 请求所需要的权限
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void rquestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            tabNews.setChecked(true);
        } else {
            setMyRadioUnable();
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQEUST_CODE_WRITER);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQEUST_CODE_WRITER && grantResults[0] == 0) {
            setMyRadioAble();
            tabNews.setChecked(true);
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void initSource() {

        tabNews.setOnRadioCheckedListenter(this, MyRadio.OnRadioCheckedListenter.NEWS);
        tabFunction.setOnRadioCheckedListenter(this, MyRadio.OnRadioCheckedListenter.FUNCTION);
        tabExpert.setOnRadioCheckedListenter(this, MyRadio.OnRadioCheckedListenter.EXPERT);
        tabSetting.setOnRadioCheckedListenter(this, MyRadio.OnRadioCheckedListenter.SETTING);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            list = new ArrayList<>();
            list.add(tabNews);
            list.add(tabFunction);
            list.add(tabExpert);
            list.add(tabSetting);
            rquestPermission();
        } else {
            tabNews.setChecked(true);
        }
//        tabNews.setChecked(true);
//        showFragment(R.id.tab_news);


    }

    public void showFragment(int id) {
        FragmentManager fm = getSupportFragmentManager();
        Log.d(TAG, "showFragment: ");
        fm.getFragments();
        FragmentTransaction ft = fm.beginTransaction();
        if (fm.getFragments() != null)
            for (Fragment f :
                    fm.getFragments()) {
                ft.hide(f);
            }
        Fragment show = getFragment(id, ft);
//        Log.d(TAG, "showFragment: " + show.hashCode());
        ft.show(show);
        ft.commitAllowingStateLoss();

    }


    public void switchPage(int id) {
        if (CHECKED_RADIO_ID == id)
            return;
        switch (id) {
            case R.id.tab_news:
                showFragment(R.id.tab_news);
                break;
            case R.id.tab_function:
                showFragment(R.id.tab_function);
                break;
            case R.id.tab_expert:
                showFragment(R.id.tab_expert);
                break;
            case R.id.tab_setting:
                showFragment(R.id.tab_setting);
                break;
        }
        CHECKED_RADIO_ID = id;
    }

    @OnClick({R.id.tab_news, R.id.tab_function, R.id.tab_expert, R.id.tab_setting})
    public void onClick(MyRadio view) {

        switch (view.getId()) {
            case R.id.tab_news:
                tabNews.setChecked(true);
//                vp.setCurrentItem(FragmentVpAdapter.NEWS);
//                switchPage(R.id.tab_news);
                break;
            case R.id.tab_function:
                tabFunction.setChecked(true);
//                switchPage(R.id.tab_function);
//                vp.setCurrentItem(FragmentVpAdapter.FUNCTION);
                break;
            case R.id.tab_expert:
                tabExpert.setChecked(true);
//                switchPage(R.id.tab_expert);
//                vp.setCurrentItem(FragmentVpAdapter.EXPERT);
                break;
            case R.id.tab_setting:
                tabSetting.setChecked(true);
//                switchPage(R.id.tab_setting);
//                vp.setCurrentItem(FragmentVpAdapter.SETTING);
                break;
        }
        CHECKED_RADIO_ID = view.getId();
    }


    @Override
    public void checked(int page) {
        switch (page) {
            case MyRadio.OnRadioCheckedListenter.NEWS:
                switchPage(R.id.tab_news);
                break;
            case MyRadio.OnRadioCheckedListenter.FUNCTION:
                switchPage(R.id.tab_function);
                break;
            case MyRadio.OnRadioCheckedListenter.EXPERT:
                switchPage(R.id.tab_expert);
                break;
            case MyRadio.OnRadioCheckedListenter.SETTING:
                switchPage(R.id.tab_setting);
                break;
        }
    }

    static class FragmentHolder {


        //        final static int NEWS = 0;
//        final static int FUNCTION = 1;
//        final static int EXPERT = 2;
//        final static int SETTING = 3;
        static Fragment_news fragment_news;
        static Fragment_function fragment_function;
        static Fragment_expert fragment_expert;
        static Fragment_setting fragment_setting;

        static void close() {
            fragment_news = null;
            fragment_expert = null;
            fragment_function = null;
            fragment_setting = null;
        }

    }

    private Fragment getFragment(@IdRes int fragment, FragmentTransaction ft) {
        Fragment f = null;
        switch (fragment) {
            case R.id.tab_news:
                if (FragmentHolder.fragment_news == null) {
                    FragmentHolder.fragment_news = new Fragment_news();
                    ft.add(R.id.fragment, FragmentHolder.fragment_news);
                }
                f = FragmentHolder.fragment_news;
                break;
            case R.id.tab_function:
                if (FragmentHolder.fragment_function == null) {

                    FragmentHolder.fragment_function = new Fragment_function();
                    ft.add(R.id.fragment, FragmentHolder.fragment_function);
                }
                f = FragmentHolder.fragment_function;
                break;
            case R.id.tab_expert:
                if (FragmentHolder.fragment_expert == null) {
                    FragmentHolder.fragment_expert = new Fragment_expert();
                    ft.add(R.id.fragment, FragmentHolder.fragment_expert);
                }
                f = FragmentHolder.fragment_expert;
                break;
            case R.id.tab_setting:
                if (FragmentHolder.fragment_setting == null) {
                    FragmentHolder.fragment_setting = new Fragment_setting();
                    ft.add(R.id.fragment, FragmentHolder.fragment_setting);
                }
                f = FragmentHolder.fragment_setting;
                break;
        }
        return f;
    }

    private void closeFragment() {
        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
        FragmentHolder.close();
//        for (Fragment f : fm.getFragments()) {
//            ft.remove(f);
//        }
//        ft.commit();
    }

    /**
     * 申请权限时，下面的tab不可用
     */
    private void setMyRadioUnable() {
        for (MyRadio myRadio :
                list) {
            myRadio.setEnabled(false);
        }
    }

    private void setMyRadioAble() {
        for (MyRadio myRadio :
                list) {
            myRadio.setEnabled(true);
        }
    }

    long date = 0;

    private void ifClose() {
        Log.d(TAG, "ifClose: " + date + "\t" + System.nanoTime() + "\t" + (System.nanoTime() - date) + "\t" + 2e6d);
        if ((System.nanoTime() - date) < 2e9d) {
            Log.d(TAG, "ifClose: ");
            finish();
        } else {
            date = System.nanoTime();
            Snackbar.make(layout, getString(R.string.close_app), Snackbar.LENGTH_SHORT).show();
        }

    }





    @Override
    public void onBackPressed() {
        ifClose();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        closeFragment();
        super.onDestroy();
    }
}

