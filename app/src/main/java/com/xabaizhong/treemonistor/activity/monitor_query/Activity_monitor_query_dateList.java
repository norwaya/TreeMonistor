package com.xabaizhong.treemonistor.activity.monitor_query;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.activity.query_treeOrGroup.Activity_tree_detailInfo;
import com.xabaizhong.treemonistor.adapter.Activity_monitor_query_dateList_adapter;
import com.xabaizhong.treemonistor.adapter.CommonRecyclerViewAdapter;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.contant.UserSharedField;
import com.xabaizhong.treemonistor.service.AsyncTaskRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/4/24 0024.
 */

public class Activity_monitor_query_dateList extends Activity_base implements CommonRecyclerViewAdapter.CallBack<Activity_monitor_query_dateList.ViewHolder, String> {

    String treeId;
    @BindView(R.id.rv)
    RecyclerView rv;


    List<String> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_query_datelist);
        ButterKnife.bind(this);
        treeId = getIntent().getStringExtra("tree-id");
        setTitle(treeId);
        Log.i(TAG, "onCreate: " + treeId);
        initialView();
    }

    private void initialView() {
        initAdapter();
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
        request();
    }

    Activity_monitor_query_dateList_adapter adapter;

    private void initAdapter() {
        adapter = new Activity_monitor_query_dateList_adapter(this, R.layout.simple_text2);
        adapter.setCallBack(this);
    }

    private void request() {
        /*UserID	String	用户名
        TreeID	String
        QueryType	int	0:详细信息
        1：上传时间列表
        AreaID	String	区域ID*/

        Map<String, Object> map = new HashMap<>();
        map.put("UserID", sharedPreferences.getString(UserSharedField.USERID,""));
        map.put("TreeID", treeId);


        AsyncTaskRequest.instance("CheckUp", "QueryTimeList_ImportantTree")
                .setParams(map)
                .setCallBack(new AsyncTaskRequest.CallBack() {
                    @Override
                    public void execute(String s) {
                        Log.i(TAG, "execute: "+s);
                        if(s == null)
                            return;
                        list = new Gson().fromJson(s, ResultMessage.class).getResult();
                        adapter.setSource(list);
                    }
                }).create(TAG);
    }

    @Override
    public void bindView(ViewHolder holder, int position, List<String> list) {
        holder.text1.setText(list.get(position));
    }

    @Override
    public void onItemClickListener(View view, int position) {
        Log.i(TAG, "onItemClickListener: ");

        String date = list.get(position);
        Intent i = new Intent(this, Activity_monitor_query_date_pics.class);
        i.putExtra("treeId", treeId);
        i.putExtra("date", date);
        startActivity(i);
    }


    @OnClick(R.id.button)
    public void onViewClicked() {
        Intent i = new Intent(this, Activity_tree_detailInfo.class);
        i.putExtra("treeId", treeId);
        startActivity(i);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView text1;

        public ViewHolder(View itemView) {
            super(itemView);
            text1 = ((TextView) itemView.findViewById(R.id.text1));
        }
    }

    public static class ResultMessage {


        /**
         * message : sus
         * error_code : 0
         * result : ["2017/4/15 14:30:01.000"]
         */

        @SerializedName("message")
        private String message;
        @SerializedName("error_code")
        private int errorCode;
        @SerializedName("result")
        private List<String> result;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(int errorCode) {
            this.errorCode = errorCode;
        }

        public List<String> getResult() {
            return result;
        }

        public void setResult(List<String> result) {
            this.result = result;
        }
    }
}
