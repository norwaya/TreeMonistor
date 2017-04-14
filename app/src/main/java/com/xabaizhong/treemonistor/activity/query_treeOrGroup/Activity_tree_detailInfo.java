package com.xabaizhong.treemonistor.activity.query_treeOrGroup;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.contant.UserSharedField;
import com.xabaizhong.treemonistor.service.WebserviceHelper;
import com.xabaizhong.treemonistor.service.model.QueryTreeInfoList;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/2/28.
 */

public class Activity_tree_detailInfo extends Activity_base {
    String treeId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_detail_info);
        treeId = getIntent().getStringExtra("treeId");
        query();

    }

    AsyncTask asyncTask;

    private void query() {
        asyncTask = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    return WebserviceHelper.GetWebService(
                            "DataQuerySys", "TreeDelInfo", getParms());
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

    /* <UserID>string</UserID>
      <TreeType>int</TreeType>
      <TreeID>string</TreeID>
      <AreaID>string</AreaID>*/
    private Map<String, Object> getParms() {
        Map<String, Object> map = new HashMap<>();
        map.put("UserID", sharedPreferences.getString(UserSharedField.USERID, ""));
        map.put("TreeType", "0");
        map.put("TreeID", treeId);
        map.put("AreaID", sharedPreferences.getString(UserSharedField.AREAID, ""));
        return map;
    }
}
