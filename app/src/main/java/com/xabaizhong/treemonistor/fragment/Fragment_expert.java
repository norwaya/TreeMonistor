package com.xabaizhong.treemonistor.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.activity.Activity_main;
import com.xabaizhong.treemonistor.activity.expert.Activity_expert_age;
import com.xabaizhong.treemonistor.activity.expert.Activity_expert_bug;
import com.xabaizhong.treemonistor.activity.expert.Activity_expert_detail;
import com.xabaizhong.treemonistor.activity.expert.Activity_expert_weak;
import com.xabaizhong.treemonistor.activity.expert.Activity_monitor_growth;
import com.xabaizhong.treemonistor.activity.expert.Activity_species;
import com.xabaizhong.treemonistor.adapter.Fragment_expert_adapter;
import com.xabaizhong.treemonistor.base.Fragment_base;
import com.xabaizhong.treemonistor.contant.UserSharedField;
import com.xabaizhong.treemonistor.entity.ResultOfExpert;
import com.xabaizhong.treemonistor.service.WebserviceHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import terranovaproductions.newcomicreader.FloatingActionMenu;

/**
 * Created by admin on 2017/2/24.
 */

public class Fragment_expert extends Fragment_base implements Fragment_expert_adapter.OnClickListener {
    String TAG = "fragment-expert";
    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.fab_father)
    FloatingActionMenu fabFather;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.float_btn)
    FloatingActionButton floatBtn;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    Unbinder unbinder;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    boolean isShow;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Set<String> set = sharedPreferences.getStringSet(UserSharedField.ROLEID, new HashSet<String>());
        List<String> roles = new ArrayList<>();
        roles.addAll(set);
        View view = null;
        if (roles.contains("3")) {
            view = inflater.inflate(R.layout.fragment_expert, container, false);
            unbinder = ButterKnife.bind(this, view);
            initView();
            isShow = true;
        } else {
            view = inflater.inflate(R.layout.simple_text, container, false);
            TextView text = (TextView) view.findViewById(R.id.text1);
            text.setText("此用户不具备该权限，无法使用此功能");
            showToast("此用户不具备该权限，无法使用此功能");
            isShow = false;
        }

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        if (isShow) {
            if (!fabFather.isOpened()) {
                floatBtn.setVisibility(View.VISIBLE);
            }
        }
    }

    Fragment_expert_adapter adapter;

    private void initView() {
        initialFloatView();
        collapsingToolbar.setExpandedTitleColor(Color.WHITE);
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("鉴定");
        adapter = new Fragment_expert_adapter(context);
        adapter.setOnClickListener(this);
        lv.setAdapter(adapter);
        request();
    }

    private void initialFloatView() {
        fabFather.setIsCircle(false);
        fabFather.setmItemGap(20);
        fabFather.setOnMenuItemClickListener(new FloatingActionMenu.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(FloatingActionMenu floatingActionMenu, int i, FloatingActionButton floatingActionButton) {
                switch (floatingActionButton.getId()) {
                    case R.id.fab_tree_weakness:
                        Log.d(TAG, "onMenuItemClick: fab_tree_weakness");
                        startActivity(new Intent(context, Activity_expert_weak.class));
                        break;
                    case R.id.fab_tree_bug:
                        Log.d(TAG, "onMenuItemClick: fab_tree_bug");
                        startActivity(new Intent(context, Activity_expert_bug.class));
                        break;
                    case R.id.fab_tree_unknow:
                        startActivity(new Intent(context, Activity_species.class));
                        Log.d(TAG, "onMenuItemClick: fab_tree_unknow");
                        break;
                    case R.id.fab_tree_growth:
                        startActivity(new Intent(context, Activity_monitor_growth.class));
                        Log.d(TAG, "onMenuItemClick: fab_tree_growth");
                        break;
                    case R.id.fab_tree_age:
                        startActivity(new Intent(context, Activity_expert_age.class));
                        Log.d(TAG, "onMenuItemClick: fab_tree_age");
                        break;
                }
            }
        });
    }


    private void request() {
        Activity_main main = ((Activity_main) getActivity());
        final Map<String, Object> map = new HashMap<>();
        map.put("UserID", main.sharedPreferences.getString(UserSharedField.USERID, ""));
        map.put("AreaID", main.sharedPreferences.getString(UserSharedField.AREAID, ""));
        Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {
                        String result = null;
                        try {
                            result = WebserviceHelper.GetWebService("CheckUp", "Query_CheckResult1", map);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        if (result == null) {
                            e.onError(new RuntimeException("error"));
                        } else {
                            e.onNext(result);
                        }
                        e.onComplete();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String value) {
                        Log.i(TAG, "onNext: " + value);
                        ResultMessage rm = new Gson().fromJson(value, ResultMessage.class);
                        if (rm.getErrorCode() == 0) {
                            adapter.setResource(rm.getList());
//                            Log.i(TAG, "onNext: " + Thread.currentThread().getName());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
//                        refreshLayout.setRefreshing(false);
                        showToast("解析数据失败");
//                        Log.i(TAG, "onError: ");
                    }

                    @Override
                    public void onComplete() {
                        showToast("刷新完成");
                    }
                });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
       if(unbinder!=null){
           unbinder.unbind();
       }

    }


    @Override
    public void onClick(View view, int position, Object obj) {
        ResultOfExpert resultOfExpert = (ResultOfExpert) obj;
        Intent i = new Intent(getActivity(), Activity_expert_detail.class);
        i.putExtra("tid", resultOfExpert.getTID());
        i.putExtra("type", resultOfExpert.getAuthType());
        startActivity(i);
    }

    @OnClick({R.id.float_btn, R.id.fab_main})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.float_btn:
                request();
                break;
            case R.id.fab_main:
                if (fabFather.isOpened()) {
                    fabFather.close();
                    floatBtn.setVisibility(View.VISIBLE);
                } else {
                    fabFather.open();
                    floatBtn.setVisibility(View.INVISIBLE);
                }
                break;
        }
    }


    static class ResultMessage {


        /**
         * message : sus
         * error_code : 0
         * list : [{"DateTime":"2017-05-04 04:53:52.00","LeafShape":3,"FruitType":2,"LeafColor":1,"JDResult":{"CheckPerson":"陈教授","CheckTime":"2017-05-08 05:00:29.00","Result":"54545454"},"UserID":"test1","AreaID":"610000","FruitColor":5,"TID":"6100002017050404535200","FlowerColor":4,"FlowerType":2,"AuthType":0},{"DateTime":"2017-05-04 04:43:59.00","LeafShape":6,"FruitType":3,"LeafColor":2,"JDResult":{"CheckPerson":"陈教授","CheckTime":"2017-05-08 05:06:23.00","Result":"6545"},"UserID":"test1","AreaID":"610000","FruitColor":5,"TID":"6100002017050404435900","FlowerColor":5,"FlowerType":2,"AuthType":0},{"DateTime":"2017-05-04 04:43:59.00","LeafShape":6,"FruitType":3,"LeafColor":2,"JDResult":{"CheckPerson":"陈教授","CheckTime":"2017-05-09 03:51:07.00","Result":"asdsdasd"},"UserID":"test1","AreaID":"610000","FruitColor":5,"TID":"6100002017050404435900","FlowerColor":5,"FlowerType":2,"AuthType":0},{"DateTime":"2017-05-04 04:49:35.00","LeafShape":1,"FruitType":3,"LeafColor":0,"JDResult":{},"UserID":"test1","AreaID":"610000","FruitColor":4,"TID":"6100002017050404493500","FlowerColor":5,"FlowerType":2,"AuthType":0}]
         */

        @SerializedName("message")
        private String message;
        @SerializedName("error_code")
        private int errorCode;
        @SerializedName("list")
        private List<ResultOfExpert> list;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(int errorCode) {
            this.errorCode = errorCode;
        }

        public List<ResultOfExpert> getList() {
            return list;
        }

        public void setList(List<ResultOfExpert> list) {
            this.list = list;
        }


    }


}
