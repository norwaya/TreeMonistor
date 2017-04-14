package com.xabaizhong.treemonistor.activity.query_treeOrGroup;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.activity.add_tree.Activity_add_tree;
import com.xabaizhong.treemonistor.adapter.CommonRecyclerViewAdapter;
import com.xabaizhong.treemonistor.adapter.Fragment_expert_adapter_spacesItemDecoration;
import com.xabaizhong.treemonistor.adapter.QueryTreeInfoListAdapter;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.contant.UserSharedField;
import com.xabaizhong.treemonistor.service.WebserviceHelper;
import com.xabaizhong.treemonistor.service.model.QueryTreeInfoList;
import com.xabaizhong.treemonistor.service.model.ResultMessage;
import com.xabaizhong.treemonistor.utils.RecycleViewDivider;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.widget.LinearLayout.VERTICAL;

/**
 * Created by Administrator on 2017/4/11 0011.
 */

public class Activity_query_tree_info_list extends Activity_base implements CommonRecyclerViewAdapter.CallBack<Activity_query_tree_info_list.ViewHolder, QueryTreeInfoList.QueryinfolistBean> {
    @BindView(R.id.list_rv)
    RecyclerView listRv;

    Intent getIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_tree_info_list);
        ButterKnife.bind(this);
        getIntent = getIntent();
        initRecycleView();
        query();
    }

    AsyncTask asyncTask;
    List<QueryTreeInfoList.QueryinfolistBean> list;

    private void query() {
        asyncTask = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    return WebserviceHelper.GetWebService(
                            "DataQuerySys", "TreeInfoListMethod", getParms());
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
                QueryTreeInfoList queryinfolist = new Gson().fromJson(s, QueryTreeInfoList.class);
                if (queryinfolist.getError_code().equals("0")) {
                    list = queryinfolist.getQueryinfolist();
                    Log.i(TAG, "onPostExecute: " + list.size());
                    adapter.setSource(list);
                }
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
        map.put("UserID", sharedPreferences.getString(UserSharedField.USERID, ""));
        map.put("TreeType", getIntent.getIntExtra("TreeType", 0));
        map.put("DetType", getIntent.getIntExtra("DetType", 0));
        map.put("AreaID", sharedPreferences.getString(UserSharedField.AREAID, ""));
        return map;
    }

    QueryTreeInfoListAdapter adapter;

    private void initRecycleView() {
        listRv.setLayoutManager(new LinearLayoutManager(this));
        listRv.addItemDecoration(new RecycleViewDivider(this, VERTICAL, R.drawable.divider));
        adapter = new QueryTreeInfoListAdapter(this, R.layout.activity_query_tree_info_list_item);

        listRv.setAdapter(adapter);

        adapter.setCallBack(this);
    }

    @Override
    public void bindView(ViewHolder holder, int position, List<QueryTreeInfoList.QueryinfolistBean> list) {
        holder.id.setText(list.get(position).getTreeid());
        holder.name.setText(list.get(position).getTreename());
        holder.area.setText(list.get(position).getAreaname());

    }

    @Override
    public void onItemClickListener(View view, int position) {
        Intent i = new Intent(this, Activity_tree_detailInfo.class);
        i.putExtra("treeId", list.get(position).getTreeid());
        startActivity(i);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView id;
        TextView name;
        TextView area;

        public ViewHolder(View itemView) {
            super(itemView);
            id = ((TextView) itemView.findViewById(R.id.tree_id));
            name = ((TextView) itemView.findViewById(R.id.tree_name));
            area = ((TextView) itemView.findViewById(R.id.tree_area));
        }
    }
}
