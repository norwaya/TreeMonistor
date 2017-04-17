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

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.squareup.picasso.Picasso;
import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.activity.expert.Activity_expert_bug;
import com.xabaizhong.treemonistor.activity.expert.Activity_expert_weak;
import com.xabaizhong.treemonistor.activity.expert.Activity_monitor_growth;
import com.xabaizhong.treemonistor.activity.expert.Activity_species;
import com.xabaizhong.treemonistor.adapter.Fragment_expert_adapter;
import com.xabaizhong.treemonistor.adapter.HeaderAndFooterWrapper;
import com.xabaizhong.treemonistor.base.Fragment_base;
import com.xabaizhong.treemonistor.entity.ResultOfExpert;
import com.xabaizhong.treemonistor.utils.RecycleViewDivider;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        requestData();
                                        xRecyclerView.refreshComplete();
                                    }
                                });
                            }
                        }.start();
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
                                        requestData();
                                        xRecyclerView.loadMoreComplete();
                                    }
                                });
                            }
                        }.start();
                    }
                }
        );
    }


    ArrayList<ResultOfExpert> list;

    private void requestData() {
        if (list == null)
            list = new ArrayList<>();
        list.clear();
        ResultOfExpert expert = null;
        for (int i = 0; i < 10; i++) {

            if (i == 0 || i == 3) {
                expert = null;
            } else {
                expert = new ResultOfExpert(i, "content" + i, null);
            }
            list.add(expert);
        }
        xRecyclerView.refreshComplete();
        adapter.refreshList(list);
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


    /*@OnClick({R.id.fab_tree_unknow, R.id.fab_tree_weakness})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_tree_unknow:
                Log.d(TAG, "onClick: unknow");
                break;
            case R.id.fab_tree_weakness:
                Log.d(TAG, "onClick: weakness");
                break;
        }
        menuYellow.close(true);
    }*/

}
