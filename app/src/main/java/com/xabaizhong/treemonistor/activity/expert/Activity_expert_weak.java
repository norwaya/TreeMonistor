package com.xabaizhong.treemonistor.activity.expert;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.adapter.Activity_expert_weak_adapter;
import com.xabaizhong.treemonistor.adapter.CommonRecyclerViewAdapter;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.base.App;
import com.xabaizhong.treemonistor.entity.DaoSession;
import com.xabaizhong.treemonistor.entity.Tree_weak_part;
import com.xabaizhong.treemonistor.entity.Tree_weak_partDao;
import com.xabaizhong.treemonistor.utils.RecycleViewDivider;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.v7.widget.RecyclerView.VERTICAL;

/**
 * 专家鉴定  树病鉴定
 */
public class Activity_expert_weak extends Activity_base implements CommonRecyclerViewAdapter.CallBack<Activity_expert_weak.ViewHolder, Tree_weak_part> {
    @BindView(R.id.rv)
    RecyclerView rv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_weak);
        ButterKnife.bind(this);
        initialView();
        fillData();
    }

    List<Tree_weak_part> mList;
    //  databse  -> views
    private void fillData() {

        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        Tree_weak_partDao tree_weak_partDao = daoSession.getTree_weak_partDao();
        mList = tree_weak_partDao.queryBuilder().build().list();
        adapter.setSource(mList);
    }

    Activity_expert_weak_adapter adapter;
    // 初始化 adapters
    private void initialView() {

        //initial adapter;
        adapter = new Activity_expert_weak_adapter(this, R.layout.activity_expert_weak_item);
        adapter.setCallBack(this);
        //initial RecyclerView
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new RecycleViewDivider(this, VERTICAL));
        rv.setAdapter(adapter);
    }


    @Override
    public void bindView(ViewHolder holder, int position, List<Tree_weak_part> list) {

        holder.title.setText(list.get(position).getName());
    }

    @Override
    public void onItemClickListener(View view, int position) {
        Intent i = new Intent(this, Activity_expert_weak2.class);
        i.putExtra("part", position + 1);
        startActivity(i);
        Log.i(TAG, "onItemClickListener: go intent " + position);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            title = ((TextView) itemView.findViewById(R.id.title));
        }

    }
}
