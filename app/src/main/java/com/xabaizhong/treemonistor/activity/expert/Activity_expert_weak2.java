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
import com.xabaizhong.treemonistor.adapter.Activity_expert_weak2_adapter;
import com.xabaizhong.treemonistor.adapter.CommonRecyclerViewAdapter;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.base.App;
import com.xabaizhong.treemonistor.entity.DaoSession;
import com.xabaizhong.treemonistor.entity.Weakness;
import com.xabaizhong.treemonistor.entity.WeaknessDao;
import com.xabaizhong.treemonistor.entity.Weakness_f1Dao;
import com.xabaizhong.treemonistor.entity.Weakness_f2;
import com.xabaizhong.treemonistor.entity.Weakness_f2Dao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by norwaya on 17-4-17.
 */

public class Activity_expert_weak2 extends Activity_base implements CommonRecyclerViewAdapter.CallBack<Activity_expert_weak.ViewHolder, Weakness_f2> {
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

    List<Weakness_f2> mList;
    int f1Id;
    private void fillData() {
        f1Id = getIntent().getIntExtra("f1Id", 1);
        Log.i(TAG, "fillData: " + f1Id);
        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        Weakness_f2Dao weakness_f2Dao = daoSession.getWeakness_f2Dao();
        mList = weakness_f2Dao
                .queryBuilder()
                .where(Weakness_f2Dao.Properties.F1Id.eq(f1Id))
                .build()
                .list();
        adapter.setSource(mList
        );
    }

    Activity_expert_weak2_adapter adapter;

    private void initialView() {

        //initial adapter;
        adapter = new Activity_expert_weak2_adapter(this, R.layout.activity_expert_weak_item);
        adapter.setCallBack(this);
        //initial RecyclerView
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
    }

    @OnClick(R.id.submit)
    public void onViewClicked() {
        if (mPosition == -1) {
            showToast("请选择条件");
            return;
        }
        Log.i(TAG, "onViewClicked: " + f1Id + "\t" + mPosition);
       /* Intent i = new Intent(this, null);
        i.putExtra("f1Id", id);
        i.putExtra("f2Id", mPosition);
        startActivity(i);*/
        WeaknessDao weaknessDao = ((App) getApplication()).getDaoSession().getWeaknessDao();

        List<Weakness> list = weaknessDao.queryBuilder().where(WeaknessDao.Properties.F2Id.eq(mPosition)).build().list();
        if (list.size() > 0)
            showToast(list.size() + list.get(0).getName());
    }


    @Override
    public void bindView(Activity_expert_weak.ViewHolder holder, int position, List<Weakness_f2> list) {
        holder.title.setText(list.get(position).getF1Id() + "\t" + list.get(position).getFId() + list.get(position).getName());
    }

    long mPosition = -1;

    @Override
    public void onItemClickListener(View view, int position) {
        mPosition = mList.get(position).getId();
        title.setText("已选择: " + mList.get(position).getName());
    }


}
