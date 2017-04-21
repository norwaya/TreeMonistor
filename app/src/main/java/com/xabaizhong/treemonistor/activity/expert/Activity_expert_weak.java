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
import com.xabaizhong.treemonistor.entity.Weakness_f1;
import com.xabaizhong.treemonistor.entity.Weakness_f1Dao;
import com.xabaizhong.treemonistor.utils.RecycleViewDivider;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.v7.widget.RecyclerView.VERTICAL;

/**
 * Created by norwaya on 17-4-17.
 */

public class Activity_expert_weak extends Activity_base implements CommonRecyclerViewAdapter.CallBack<Activity_expert_weak.ViewHolder, Weakness_f1> {
    Button submit;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.title)
    TextView title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_weak);
        ButterKnife.bind(this);
        initialView();
        fillData();
    }

    List<Weakness_f1> mList;

    private void fillData() {

        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        Weakness_f1Dao weakness_f1Dao = daoSession.getWeakness_f1Dao();
        mList = weakness_f1Dao.queryBuilder().build().list();
        adapter.setSource(mList);
    }

    Activity_expert_weak_adapter adapter;

    private void initialView() {

        //initial adapter;
        adapter = new Activity_expert_weak_adapter(this, R.layout.activity_expert_weak_item);
        adapter.setCallBack(this);
        //initial RecyclerView
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new RecycleViewDivider(this, VERTICAL));
        rv.setAdapter(adapter);
    }

    int mPosition = -1;

    @OnClick(R.id.submit)
    public void onViewClicked() {
        if (mPosition == -1) {
            showToast("请选择条件");
            return;
        }
    }

    @Override
    public void bindView(ViewHolder holder, int position, List<Weakness_f1> list) {

        holder.title.setText(list.get(position).getName());
    }

    @Override
    public void onItemClickListener(View view, int position) {
        title.setText("已选择: " + mList.get(position).getName());
        mPosition = position + 1;
        switch (position) {
            case 0:
            case 1:
            case 2:

                Intent i = new Intent(this, Activity_expert_weak2.class);
                i.putExtra("f1Id", position + 1);
                startActivity(i);
                Log.i(TAG, "onItemClickListener: go intent " + position);
                break;
            case 4:
            default:

                Log.i(TAG, "onItemClickListener: " + position);
                break;
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            title = ((TextView) itemView.findViewById(R.id.title));
        }

    }
}
