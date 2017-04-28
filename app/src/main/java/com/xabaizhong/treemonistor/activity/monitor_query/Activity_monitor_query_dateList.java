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

public class Activity_monitor_query_dateList extends Activity_base implements CommonRecyclerViewAdapter.CallBack<Activity_monitor_query_dateList.ViewHolder, Activity_monitor_query_dateList.ResultMessage.QueryimportinfolistBean> {

    String treeId;
    @BindView(R.id.rv)
    RecyclerView rv;


    List<ResultMessage.QueryimportinfolistBean> list;

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
        map.put("UserID", "");
        map.put("TreeID", "");
        map.put("QueryType", "1");
        map.put("AreaID", "");


        AsyncTaskRequest.instance("DataQuerySys", "ImportTreelInfo")
                .setParams(map)
                .setCallBack(new AsyncTaskRequest.CallBack() {
                    @Override
                    public void execute(String s) {
                        String result = "{\n" +
                                "    \"message\": \"sus\",\n" +
                                "    \"error_code\": \"0\",\n" +
                                "    \"areaid\": \"6100323\",\n" +
                                "    \"userid\": \"610323001\",\n" +
                                "    \"treeid\": \" 61032900001\",\n" +
                                "    \"queryimportinfolist\": [\n" +
                                "        { \"updatetime\": 2017-04-01 },\n" +
                                "        { \"updatetime\": 2017-04-02 },\n" +
                                "        { \"updatetime\": 2017-04-03 }\n" +
                                "    ]\n" +
                                "}\n";
                        list = new Gson().fromJson(result, ResultMessage.class).getQueryimportinfolist();
                        adapter.setSource(list);
                    }
                }).create(TAG);
    }

    @Override
    public void bindView(ViewHolder holder, int position, List<ResultMessage.QueryimportinfolistBean> list) {
        holder.text1.setText(list.get(position).getUpdatetime());
    }

    @Override
    public void onItemClickListener(View view, int position) {
        Log.i(TAG, "onItemClickListener: ");

        String date = list.get(position).getUpdatetime();
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
         * areaid : 6100323
         * userid : 610323001
         * treeid :  61032900001
         * queryimportinfolist : [{"updatetime":"2017-04-01"},{"updatetime":"2017-04-02"},{"updatetime":"2017-04-03"}]
         */

        @SerializedName("message")
        private String message;
        @SerializedName("error_code")
        private String errorCode;
        @SerializedName("areaid")
        private String areaid;
        @SerializedName("userid")
        private String userid;
        @SerializedName("treeid")
        private String treeid;
        @SerializedName("queryimportinfolist")
        private List<QueryimportinfolistBean> queryimportinfolist;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public String getAreaid() {
            return areaid;
        }

        public void setAreaid(String areaid) {
            this.areaid = areaid;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getTreeid() {
            return treeid;
        }

        public void setTreeid(String treeid) {
            this.treeid = treeid;
        }

        public List<QueryimportinfolistBean> getQueryimportinfolist() {
            return queryimportinfolist;
        }

        public void setQueryimportinfolist(List<QueryimportinfolistBean> queryimportinfolist) {
            this.queryimportinfolist = queryimportinfolist;
        }

        public static class QueryimportinfolistBean {
            /**
             * updatetime : 2017-04-01
             */

            @SerializedName("updatetime")
            private String updatetime;

            public String getUpdatetime() {
                return updatetime;
            }

            public void setUpdatetime(String updatetime) {
                this.updatetime = updatetime;
            }
        }
    }
}
