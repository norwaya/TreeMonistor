package com.xabaizhong.treemonistor.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.squareup.picasso.Picasso;
import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.activity.Activity_main;
import com.xabaizhong.treemonistor.activity.expert.Activity_expert_age;
import com.xabaizhong.treemonistor.activity.expert.Activity_expert_weak;
import com.xabaizhong.treemonistor.activity.expert.Activity_monitor_growth;
import com.xabaizhong.treemonistor.activity.expert.Activity_species;
import com.xabaizhong.treemonistor.adapter.Fragment_expert_adapter;
import com.xabaizhong.treemonistor.adapter.HeaderAndFooterWrapper;
import com.xabaizhong.treemonistor.base.Fragment_base;
import com.xabaizhong.treemonistor.contant.UserSharedField;
import com.xabaizhong.treemonistor.entity.ResultOfExpert;
import com.xabaizhong.treemonistor.service.AsyncTaskRequest;
import com.xabaizhong.treemonistor.utils.RecycleViewDivider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import terranovaproductions.newcomicreader.FloatingActionMenu;

import static android.support.v7.widget.RecyclerView.VERTICAL;

/**
 * Created by admin on 2017/2/24.
 */

public class Fragment_expert extends Fragment_base {

    String TAG = "fragment-expert";
    /* @BindView(R.id.menu_yellow)
     FloatingActionMenu menuYellow;
     @BindView(R.id.fab_tree_unknow)
     FloatingActionButton fabTreeUnknow;
     @BindView(R.id.fab_tree_weakness)
     FloatingActionButton fabTreeWeakness;
     private Context context;*/
    @BindView(R.id.xRecyclerView)
    XRecyclerView xRecyclerView;
    @BindView(R.id.fab_father)
    FloatingActionMenu fabFather;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expert, container, false);
        ButterKnife.bind(this, view);

        initView();
        return view;
    }

    Fragment_expert_adapter adapter;

    private void initView() {
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
                 /*   case R.id.fab_tree_bug:
                        Log.d(TAG, "onMenuItemClick: fab_tree_bug");
                        startActivity(new Intent(context, Activity_expert_bug.class));
                        break;*/
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
       /* menuYellow.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                Log.d(TAG, "onMenuToggle: " + opened);
            }
        });*/

        View view = LayoutInflater.from(context).inflate(R.layout.fragment_expert_header, null);
        /*view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));*/
        ImageView iv = (ImageView) view.findViewById(R.id.iv);
        Picasso.with(context)
                .load(R.drawable.tree_header)
                .into(iv);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        xRecyclerView.addItemDecoration(new RecycleViewDivider(context, VERTICAL, R.drawable.divider));
      /*  xRecyclerView.addHeaderView(view);*/
        adapter = new Fragment_expert_adapter(context);

        HeaderAndFooterWrapper headerAdapter = new HeaderAndFooterWrapper(adapter);
        headerAdapter.addHeaderView(view);

        xRecyclerView.setAdapter(headerAdapter);
        xRecyclerView.setPullRefreshEnabled(true);
        xRecyclerView.setLoadingMoreEnabled(true);


        xRecyclerView.setLoadingListener(
                new XRecyclerView.LoadingListener() {
                    @Override
                    public void onRefresh() {
                        request();
                    }

                    @Override
                    public void onLoadMore() {
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        xRecyclerView.loadMoreComplete();
                                    }
                                });
                            }
                        }.start();
                    }
                }
        );
    }


    private void request() {
        Activity_main main = ((Activity_main) getActivity());

        Map<String, Object> map = new HashMap<>();
        map.put("UserID", main.sharedPreferences.getString(UserSharedField.USERID, ""));
        map.put("AreaID", main.sharedPreferences.getString(UserSharedField.AREAID, ""));
        AsyncTaskRequest.instance("CheckUp", "Query_CheckResult1")
                .setParams(map)
                .setCallBack(new AsyncTaskRequest.CallBack() {
                    @Override
                    public void execute(String s) {
                        Log.i(TAG, "execute: " + s);
                        xRecyclerView.refreshComplete();
                        if (s == null) {
                            showToast("数据异常");
                            return;
                        }
                        Observable.just(s)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io())
                                .subscribe(new Consumer<String>() {
                                    @Override
                                    public void accept(String s) throws Exception {
                                        ResultMessage rm = new Gson().fromJson(s, ResultMessage.class);
                                        if (rm.getErrorCode() == 0)
                                            adapter.setResource(rm.getList());
                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        showToast("解析数据失败");
                                    }
                                });
                    }
                }).create();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick(R.id.fab_main)
    public void onViewClicked() {
        if (fabFather.isOpened()) {
            fabFather.close();
        } else {
            fabFather.open();
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
