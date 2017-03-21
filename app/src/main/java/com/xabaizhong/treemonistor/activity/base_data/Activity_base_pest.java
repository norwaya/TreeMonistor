package com.xabaizhong.treemonistor.activity.base_data;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.base.App;
import com.xabaizhong.treemonistor.entity.Pest;
import com.xabaizhong.treemonistor.entity.PestDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/3/17.
 */

public class Activity_base_pest extends Activity_base {
    @BindView(R.id.lv)
    ListView lv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_weakness);
        ButterKnife.bind(this);
        initDB();
        initView();
    }
    List<Pest> list;
    private void initView() {
        lv.setAdapter(new SimpleAdapter(this,fillData(),R.layout.activity_base_data_weakness_item,new String[]{"name"},new int[]{R.id.tv}));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(),Activity_base_weakness_detail.class);
                i.putExtra("id", list.get(position).getId());
                startActivity(i);
            }
        });
    }
    void initDB(){
        PestDao pestDao = ((App)getApplicationContext()).getDaoSession().getPestDao();
        list = pestDao.queryBuilder().build().list();
        Log.d(TAG, "initDB: list len"+list.size());
    }
    List<Map<String,Object>> fillData(){
        List<Map<String,Object>> mapList = new ArrayList<>();
        Map<String, Object> map ;
        if(list != null){
            for (Pest pest:list
                 ) {
                map = new HashMap<>();
                map.put("name", pest.getCname());
                mapList.add(map);
            }
            return mapList;
        }
        return new ArrayList<>();
    }
}
