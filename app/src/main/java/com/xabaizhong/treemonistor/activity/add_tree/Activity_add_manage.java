package com.xabaizhong.treemonistor.activity.add_tree;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.base.Activity_base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/2/28.
 */

public class Activity_add_manage extends Activity_base implements AdapterView.OnItemClickListener {

    final static int LOAD_NUM = 20;
    @BindView(R.id.lv)
    ListView lv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_manager);
        ButterKnife.bind(this);
        initView();
    }


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_add_manager, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.activity_add_manager_add:

                break;
            case R.id.activity_add_manager_add_gsq:
                startActivity(new Intent(this, Activity_add_tree_group.class));
                break;
            case R.id.activity_add_manager_upload:
                showToast("上传操作");
                break;
        }
        return super.onOptionsItemSelected(item);
    }*/





    int size;

    @Override
    protected void onStart() {
        super.onStart();

    }


    String[] contentArray;
    private void initView() {
        contentArray = getResources().getStringArray(R.array.add_tree_list);
        View view = LayoutInflater.from(this).inflate(R.layout.fragment_function_header, null);
        lv.addHeaderView(view);
        lv.setAdapter(new SimpleAdapter(this, getList(), R.layout.fragment_function_list_item, new String[]{"image", "name"}, new int[]{R.id.iv, R.id.text1}));
        lv.setOnItemClickListener(this);
    }

    int[] images = {R.drawable.ic_fragment_functin_tree,
            R.drawable.ic_fragment_function_monistor,
            R.drawable.ic_fragment_function_monistor,
            R.drawable.ic_fragment_function_monistor,
            R.drawable.ic_fragment_function_monistor,
            R.drawable.ic_fragment_function_monistor};
    private List<Map<String, Object>> getList() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < contentArray.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", contentArray[i]);
            map.put("image", images[i]);
            list.add(map);

        }
        return list;
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "onItemClick: "+position);
        switch(position){
            case 1:
                startActivity(new Intent(this, Activity_add_tree.class));
                break;
            case 2:
                startActivity(new Intent(this, Activity_add_tree_group.class));
                break;
        }
    }
}
