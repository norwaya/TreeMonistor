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
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.adapter.Activity_monitor_query_adapter;
import com.xabaizhong.treemonistor.adapter.CommonRecyclerViewAdapter;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.service.AsyncTaskRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/24 0024.
 */

public class Activity_monitor_query extends Activity_base implements XRecyclerView.LoadingListener, CommonRecyclerViewAdapter.CallBack<Activity_monitor_query.ViewHolder, Activity_monitor_query.ImportTree.QueryimportinfolistBean> {
    @BindView(R.id.xRecyclerView)
    XRecyclerView xRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_query);
        ButterKnife.bind(this);
        initialView();

    }

    private void initialView() {
        initAdapter();
        initRecyclerView();
    }

    Activity_monitor_query_adapter adapter;

    private void initAdapter() {
        adapter = new Activity_monitor_query_adapter(this, R.layout.activity_monitor_query_item);
        adapter.setCallBack(this);
    }

    private void initRecyclerView() {
        xRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        xRecyclerView.setPullRefreshEnabled(true);
        xRecyclerView.setLoadingListener(this);
        xRecyclerView.setAdapter(adapter);
        refreshView();
    }

    List<ImportTree.QueryimportinfolistBean> list;
    AsyncTaskRequest ayncTaskRequest;

    private void refreshView() {
      /* UserID	String	用户名
      AreaID	String	区域ID*/


        Map<String, Object> map = new HashMap<>();
        map.put("UserID", "lizhuang");
        map.put("AreaID", "admin");


        ayncTaskRequest = AsyncTaskRequest.instance("DataQuerySys", "ImportTreelInfoList")
                .setParams(map)
                .setCallBack(new AsyncTaskRequest.CallBack() {
                    @Override
                    public void execute(String s) {
                        Log.i(TAG, "execute: " + s);
                        String result = "{\n" +
                                "  \"message\": \"sus\",\n" +
                                "  \"error_code\": 0,\n" +
                                "  \"areaid\": \"6100323\",\n" +
                                "  \"userid\": \"610323001\",\n" +
                                "  \"queryimportinfolist\": [\n" +
                                "    {\n" +
                                "      \"treeid\": \" 61032900001\",\n" +
                                "      \"treename\": \"槐树\"\n" +
                                "    },\n" +
                                "    {\n" +
                                "      \" treeid\": \" 61032900002\",\n" +
                                "      \" treename\": \"槐树\"\n" +
                                "    },\n" +
                                "    {\n" +
                                "      \" treeid\": \"61032900003\",\n" +
                                "      \" treename\": \"槐树\"\n" +
                                "    }\n" +
                                "  ]\n" +
                                "}";
                        list = new Gson().fromJson(result, ImportTree.class).getQueryimportinfolist();
                        Log.i(TAG, "execute: "+list.size());
                        adapter.setSource(list);
                        xRecyclerView.refreshComplete();
                        ayncTaskRequest = null;
                    }
                }).create();
    }

    @Override
    public void onRefresh() {
        if (ayncTaskRequest == null) {
            refreshView();
        } else {
            xRecyclerView.refreshComplete();
        }
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void bindView(ViewHolder holder, int position, List<ImportTree.QueryimportinfolistBean> list) {
        holder.text1.setText(list.get(position).getTreeid());
        holder.text1.setText("text1");
        holder.text2.setText(list.get(position).getTreename());
    }

    @Override
    public void onItemClickListener(View view, int position) {
       /* Intent i = new Intent(this, Activity_monitor_query_dateList.class);
        i.putExtra("tree-id", list.get(position).getTreeid());
        startActivity(i);*/
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView text1;
        TextView text2;

        public ViewHolder(View itemView) {
            super(itemView);
            text1 = ((TextView) itemView.findViewById(R.id.text1));
            text2 = ((TextView) itemView.findViewById(R.id.text2));
        }
    }


    public static class ImportTree {


        /**
         * message : sus
         * error_code : 0
         * areaid : 6100323
         * userid : 610323001
         * queryimportinfolist : [{"treeid":" 61032900001","treename":"槐树"}]
         */

        @SerializedName("message")
        private String message;
        @SerializedName("error_code")
        private int errorCode;
        @SerializedName("areaid")
        private String areaid;
        @SerializedName("userid")
        private String userid;
        @SerializedName("queryimportinfolist")
        private List<QueryimportinfolistBean> queryimportinfolist;

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

        public List<QueryimportinfolistBean> getQueryimportinfolist() {
            return queryimportinfolist;
        }

        public void setQueryimportinfolist(List<QueryimportinfolistBean> queryimportinfolist) {
            this.queryimportinfolist = queryimportinfolist;
        }

        public static class QueryimportinfolistBean {
            /**
             * treeid :  61032900001
             * treename : 槐树
             */

            @SerializedName("treeid")
            private String treeid;
            @SerializedName("treename")
            private String treename;

            public String getTreeid() {
                return treeid;
            }

            public void setTreeid(String treeid) {
                this.treeid = treeid;
            }

            public String getTreename() {
                return treename;
            }

            public void setTreename(String treename) {
                this.treename = treename;
            }
        }
    }
}
