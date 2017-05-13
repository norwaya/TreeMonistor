package com.xabaizhong.treemonistor.activity.add_tree;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.adapter.Activity_add_manager_adapter;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.base.App;
import com.xabaizhong.treemonistor.entity.DaoSession;
import com.xabaizhong.treemonistor.entity.PicDao;
import com.xabaizhong.treemonistor.entity.TreeDao;
import com.xabaizhong.treemonistor.entity.TreeTypeInfo;
import com.xabaizhong.treemonistor.entity.TreeTypeInfoDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 2017/2/28.
 */

public class Activity_add_manage extends Activity_base {

    final static int LOAD_NUM = 20;
    @BindView(R.id.lv)
    ListView lv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_manager);
        ButterKnife.bind(this);
        initView();
        registerForContextMenu(lv);
    }


    int size;

    @Override
    protected void onStart() {
        super.onStart();
    }


    String[] contentArray;
    Activity_add_manager_adapter adapter;
    List<TreeTypeInfo> mList;

    private void initAdapter() {
        adapter = new Activity_add_manager_adapter(getApplicationContext());

    }


    private void initView() {


        initAdapter();
        contentArray = getResources().getStringArray(R.array.add_tree_list);
        View view = LayoutInflater.from(this).inflate(R.layout.activity_add_manager_item, null);
        initHeaderView(view);
        lv.addHeaderView(view);
        lv.setAdapter(adapter);
        test();
    }

    private void initHeaderView(View view) {
        TextView tv1 = ((TextView) view.findViewById(R.id.tv1));
        TextView tv2 = ((TextView) view.findViewById(R.id.tv2));
        TextView tv3 = ((TextView) view.findViewById(R.id.tv3));
        tv1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv1.setText("古树[群]编号");
        tv2.setText("类型");
        tv3.setText("时间");
    }

    private void test() {
        initDaoSession();
        try {
            List<TreeTypeInfo> list = treeTypeInfoDao.queryBuilder().build().list();
            for (TreeTypeInfo type : list
                    ) {
                Log.i(TAG, "test: " + type.getTreeId());

            }
            adapter.setSource(list);

        } catch (Exception e) {

        }
    }

    TreeTypeInfoDao treeTypeInfoDao;
    TreeDao treeDao;
    PicDao picDao;

    private void initDaoSession() {
        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        treeTypeInfoDao = daoSession.getTreeTypeInfoDao();
        treeDao = daoSession.getTreeDao();
        picDao = daoSession.getPicDao();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final int index = ContextMenu.FIRST;

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Log.i(TAG, "onContextItemSelected: "+(info.position-1));
//        Object obj = adapter.getItem(info.position);
        switch (item.getItemId()) {
            case index + 1:
                break;
            case index + 2:
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("操作");
        menu.add(0, ContextMenu.FIRST + 1, 0, "删除");
        menu.add(0, ContextMenu.FIRST + 2, 0, "查看并修改");
        super.onCreateContextMenu(menu, v, menuInfo);
    }


    @OnClick({R.id.btn_tree, R.id.btn_treeGroup, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_tree:
                startActivity(new Intent(this, Activity_add_tree.class));
                break;
            case R.id.btn_treeGroup:
                startActivity(new Intent(this, Activity_add_tree_group.class));
                break;
            case R.id.btn_submit:
                submit();
                break;
        }
    }

    private void submit() {
    }
}
