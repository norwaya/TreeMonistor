package com.xabaizhong.treemonistor.activity.query_treeOrGroup;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.contant.UserSharedField;
import com.xabaizhong.treemonistor.service.WebserviceHelper;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/4/11 0011.
 */

public class Activity_query_tree_info extends Activity_base {
    @BindView(R.id.tree)
    RadioButton tree;
    @BindView(R.id.group)
    RadioButton group;
    @BindView(R.id.level1)
    RadioButton level1;
    @BindView(R.id.level2)
    RadioButton level2;
    @BindView(R.id.level3)
    RadioButton level3;
    @BindView(R.id.countryside)
    RadioButton countryside;
    @BindView(R.id.city)
    RadioButton city;
    @BindView(R.id.country)
    RadioButton country;
    @BindView(R.id.unit)
    RadioButton unit;
    @BindView(R.id.person)
    RadioButton person;
    @BindView(R.id.other)
    RadioButton other;
    @BindView(R.id.normal)
    RadioButton normal;
    @BindView(R.id.weak)
    RadioButton weak;
    @BindView(R.id.weaker)
    RadioButton weaker;
    @BindView(R.id.yjyw)
    RadioButton yjyw;
    @BindView(R.id.street)
    RadioButton street;
    @BindView(R.id.city_area)
    RadioButton cityArea;
    @BindView(R.id.fjms)
    RadioButton fjms;
    @BindView(R.id.env_lh)
    RadioButton envLh;
    @BindView(R.id.evn_c)
    RadioButton evnC;
    @BindView(R.id.evn_jc)
    RadioButton evnJc;
    @BindView(R.id.tree1)
    RadioButton tree1;
    @BindView(R.id.tree2)
    RadioButton tree2;
    @BindView(R.id.tree3)
    RadioButton tree3;
    @BindView(R.id.tree4)
    RadioButton tree4;
    @BindView(R.id.tree5)
    RadioButton tree5;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_tree_info);
        ButterKnife.bind(this);
        query();
        initView();

    }

    AsyncTask asyncTask;

    private void query() {
        asyncTask = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    return WebserviceHelper.GetWebService(
                            "DataQuerySys", "QueryTreeInfoMethod", getParms());
                } catch (ConnectException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
                if (s == null) {
                    showToast("请求错误");
                }
                Log.i(TAG, "onPostExecute: " + s);
            }
        }.execute();
    }

    /*<TreeInfoListMethod xmlns="http://tempuri.org/">
     <UserID>string</UserID>
     <TreeType>int</TreeType>
     <DetType>int</DetType>
     <AreaID>string</AreaID>
   </TreeInfoListMethod>*/
    private Map<String, Object> getParms() {
        Map<String, Object> map = new HashMap<>();
//        map.put("UserID", sharedPreferences.getString(UserSharedField.USERID, ""));
//        map.put("TreeType", getIntent.getStringExtra("TreeType"));
//        map.put("DetType", getIntent.getStringExtra("DetType"));
//        map.put("AreaID", sharedPreferences.getString(UserSharedField.AREAID, ""));
        return map;
    }
    List<RadioButton> list;

    private void initView() {
       fillList();

    }

    private void fillList() {

        list = new ArrayList<>();
        list.add(tree);
        list.add(group);
        list.add(level1);
        list.add(level2);
        list.add(level3);
        list.add(countryside);
        list.add(city);
        list.add(country);
        list.add(unit);
        list.add(person);
        list.add(other);
        list.add(normal);
        list.add(weak);
        list.add(weaker);
        list.add(yjyw);
        list.add(street);
        list.add(cityArea);
        list.add(fjms);
        list.add(envLh);
        list.add(evnC);
        list.add(evnJc);
        list.add(tree1);
        list.add(tree2);
        list.add(tree3);
        list.add(tree4);
        list.add(tree5);
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        for (RadioButton rb : list) {
            if (rb.isChecked())
                getPropAndIntent(rb);
        }

    }

    //    TreeType与DetType
    int TreeType = 0;
    int DetType = 0;

    public void getPropAndIntent(RadioButton rb) {
        Intent i = new Intent(this, Activity_query_tree_info_list.class);

        switch (rb.getId()) {
            case R.id.tree:
                TreeType = 0;
                DetType = 1;
                break;
            case R.id.group:
                TreeType = 0;
                DetType = 2;
                break;
            case R.id.level1:
                TreeType = 1;
                DetType = 0;
                break;
            case R.id.level2:
                TreeType = 1;
                DetType = 0;
                break;
            case R.id.level3:
                TreeType = 1;
                DetType = 0;
                break;
            case R.id.countryside:
                TreeType = 0;
                DetType = 0;
                break;
            case R.id.city:
                TreeType = 1;
                DetType = 1;
                break;
            case R.id.country:
                TreeType = 1;
                DetType = 1;
                break;
            case R.id.unit:
                TreeType = 1;
                DetType = 2;
                break;
            case R.id.person:
                TreeType = 1;
                DetType = 2;
                break;
            case R.id.other:
                TreeType = 0;
                DetType = 0;
                break;
            case R.id.normal:
                TreeType = 0;
                DetType = 0;
                break;
            case R.id.weak:
                TreeType = 0;
                DetType = 0;
                break;
            case R.id.weaker:
                TreeType = 0;
                DetType = 0;
                break;
            case R.id.yjyw:
                TreeType = 0;
                DetType = 0;
                break;
            case R.id.street:
                TreeType = 0;
                DetType = 0;
                break;
            case R.id.city_area:
                TreeType = 0;
                DetType = 0;
                break;
            case R.id.fjms:
                TreeType = 0;
                DetType = 0;
                break;
            case R.id.env_lh:
                TreeType = 0;
                DetType = 0;
                break;
            case R.id.evn_c:
                TreeType = 0;
                DetType = 0;
                break;
            case R.id.evn_jc:
                TreeType = 0;
                DetType = 0;
                break;
            case R.id.tree1:
                TreeType = 0;
                DetType = 0;
                break;
            case R.id.tree2:
                TreeType = 0;
                DetType = 0;
                break;
            case R.id.tree3:
                TreeType = 0;
                DetType = 0;
                break;
            case R.id.tree4:
                TreeType = 0;
                DetType = 0;
                break;
            case R.id.tree5:
                TreeType = 0;
                DetType = 0;
                break;

        }
        i.putExtra("TreeType", TreeType);
        i.putExtra("DetType", DetType);
        startActivity(i);
    }

}
