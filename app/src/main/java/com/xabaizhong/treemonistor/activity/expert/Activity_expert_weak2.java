package com.xabaizhong.treemonistor.activity.expert;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.activity.base_data.Activity_base_weakness;
import com.xabaizhong.treemonistor.activity.base_data.Activity_base_weakness_detail;
import com.xabaizhong.treemonistor.adapter.Activity_expert_weak2_adapter;
import com.xabaizhong.treemonistor.adapter.CommonRecyclerViewAdapter;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.base.App;
import com.xabaizhong.treemonistor.entity.DaoSession;
import com.xabaizhong.treemonistor.entity.Weakness;
import com.xabaizhong.treemonistor.entity.WeaknessDao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class Activity_expert_weak2 extends Activity_base implements CommonRecyclerViewAdapter.CallBack<Activity_expert_weak.ViewHolder, Weakness> {
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

    List<Weakness> mList;
    int partId;

    private void fillData() {
        partId = getIntent().getIntExtra("part", -1);
        Log.i(TAG, "fillData: " + partId);
        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        WeaknessDao weaknessDao = daoSession.getWeaknessDao();
        mList = weaknessDao
                .queryBuilder()
                .where(WeaknessDao.Properties.PartId.eq(partId))
                .build()
                .list();
        Log.i(TAG, "fillData: " + mList.size());
        adapter.setSource(mList);
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


    @Override
    public void bindView(Activity_expert_weak.ViewHolder holder, int position, List<Weakness> list) {
        holder.title.setText(list.get(position).getFreature());
    }

    int position;

    @Override
    public void onItemClickListener(View view, int position) {
        this.position = position;
        showDialog();
    }


    private void showDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.simple_text, null);
        initialDialogView(view);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        builder.setTitle("病害结果")
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create().show();
    }

    private void initialDialogView(View view) {

        TextView tv = ((TextView) view.findViewById(R.id.text1));
        tv.setText(mList.get(position).getName());
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_expert_weak2.this, Activity_base_weakness_detail.class);
                i.putExtra("id", mList.get(position).getId());
                startActivity(i);
            }
        });
    }

}
