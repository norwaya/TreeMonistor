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
import com.xabaizhong.treemonistor.contant.UserSharedField;
import com.xabaizhong.treemonistor.service.AsyncTaskRequest;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/24 0024.
 */

public class Activity_monitor_query extends Activity_base implements XRecyclerView.LoadingListener, CommonRecyclerViewAdapter.CallBack<Activity_monitor_query.ViewHolder, Activity_monitor_query.ImportTree.ResultBean> {
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

    List<ImportTree.ResultBean> list;
    AsyncTaskRequest ayncTaskRequest;

    private void refreshView() {
      /* UserID	String	用户名
      AreaID	String	区域ID*/


        Map<String, Object> map = new HashMap<>();
        map.put("UserID", sharedPreferences.getString(UserSharedField.USERID,""));

        ayncTaskRequest = AsyncTaskRequest.instance("CheckUp", "QueryCHNameList_ImportantTree")
                .setParams(map)
                .setCallBack(new AsyncTaskRequest.CallBack() {
                    @Override
                    public void execute(String s) {
                        Log.i(TAG, "execute: " + s);
                        if (s == null)
                            return;
                        ImportTree importTree  = new Gson().fromJson(s, ImportTree.class);

                        if (importTree.getErrorCode() != 0 ) {

                            finish();
                        }else{
                            list = importTree.getResult();
                            if (list.size() == 0) {
                                finish();
                            }else{
                                importTree.getResult();
                                Log.i(TAG, "execute: " + list.size());
                                adapter.setSource(list);
                            }

                        }
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
        try {
            new FileOutputStream("");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void bindView(ViewHolder holder, int position, List<ImportTree.ResultBean> list) {
        holder.text1.setText(list.get(position).getTreeID());
        holder.text2.setText(list.get(position).getCHName());
    }

    @Override
    public void onItemClickListener(View view, int position) {
        Intent i = new Intent(this, Activity_monitor_query_dateList.class);
        i.putExtra("tree-id", list.get(position).getTreeID());
        startActivity(i);
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
         * result : [{"TreeID":"61011100015","CHName":"槐树"},{"TreeID":"61011100019","CHName":"皂荚"},{"TreeID":"61011100029","CHName":"槐树"},{"TreeID":"61011100039","CHName":"槐树"}]
         */

        @SerializedName("message")
        private String message;
        @SerializedName("error_code")
        private int errorCode;
        @SerializedName("result")
        private List<ResultBean> result;

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

        public List<ResultBean> getResult() {
            return result;
        }

        public void setResult(List<ResultBean> result) {
            this.result = result;
        }

        public static class ResultBean {
            /**
             * TreeID : 61011100015
             * CHName : 槐树
             */

            @SerializedName("TreeID")
            private String TreeID;
            @SerializedName("CHName")
            private String CHName;

            public String getTreeID() {
                return TreeID;
            }

            public void setTreeID(String TreeID) {
                this.TreeID = TreeID;
            }

            public String getCHName() {
                return CHName;
            }

            public void setCHName(String CHName) {
                this.CHName = CHName;
            }
        }
    }
}
