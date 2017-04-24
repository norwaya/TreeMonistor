package com.xabaizhong.treemonistor.activity.expert_zd;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.map.Text;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.adapter.Activity_expert_zdlist_adapter;
import com.xabaizhong.treemonistor.adapter.CommonRecyclerViewAdapter;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.contant.UserSharedField;
import com.xabaizhong.treemonistor.service.AsyncTaskRequest;
import com.xabaizhong.treemonistor.service.model.Expert_obtain_list;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/24 0024.
 */

public class Activity_expert_zd extends Activity_base implements XRecyclerView.LoadingListener, CommonRecyclerViewAdapter.CallBack<Activity_expert_zd.ViewHolder, Expert_obtain_list.CheckinfolistBean> {
    @BindView(R.id.xRecyclerView)
    XRecyclerView xRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_zd_list);
        ButterKnife.bind(this);
        initialView();
    }

    private void initialView() {
        initialAdapter();
        xRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        xRecyclerView.setPullRefreshEnabled(true);
        xRecyclerView.setLoadingListener(this);
        xRecyclerView.setAdapter(adapter);
        refreshView();
    }

    Activity_expert_zdlist_adapter adapter;

    private void initialAdapter() {
        adapter = new Activity_expert_zdlist_adapter(this, R.layout.activity_expert_zd_list_item);
        adapter.setCallBack(this);
    }

    @Override
    public void onRefresh() {
        if (asyncTaskRequest == null) {
            refreshView();
        }
        xRecyclerView.refreshComplete();
    }

    @Override
    public void onLoadMore() {
        xRecyclerView.loadMoreComplete();
    }

    AsyncTaskRequest asyncTaskRequest;
    List<Expert_obtain_list.CheckinfolistBean> mList;
    private void refreshView() {
        /*<UserID>string</UserID>
      <CheckID>int</CheckID>
      <AreaID>string</AreaID>*/
        String userId = sharedPreferences.getString(UserSharedField.USERID, "");
        String areaId = sharedPreferences.getString(UserSharedField.AREAID, "");
        Map<String, Object> params = new HashMap<>();
        params.put("UserID", userId);
        params.put("CheckID", 0);//默认为全部
        params.put("AreaID", areaId);

        asyncTaskRequest = AsyncTaskRequest.instance("DataQuerySys", "QueryTreeInfoMethod")
                .setCallBack(new AsyncTaskRequest.CallBack() {
                    @Override
                    public void execute(String s) {
                        Log.i(TAG, "execute: " + s + "\n");
                        String result = "{\n" +
                                "    \"message\": \"sus\",\n" +
                                "    \"error_code\": \"0\",\n" +
                                "    \"areaid\": \"6100323\",\n" +
                                "    \"userid\": \"610323001\",\n" +
                                "    \"Checkinfolist\": [\n" +
                                "        { \"TID\": 1, \"treeid\": \" 61032900001\", \"CheckType\": 0 },\n" +
                                "        { \"TID\": 2, \"treeid\": \" 61032900001\", \"CheckType\": 1 },\n" +
                                "        { \"TID\": 3, \"treeid\": \" 61032900001\", \"CheckType\": 2 }\n" +
                                "    ]\n" +
                                "}";
                        Expert_obtain_list list = new Gson().fromJson(result, Expert_obtain_list.class);
                        if (list.getErrorCode() == 0) {
                            mList = list.getCheckinfolist();
                            for (Expert_obtain_list.CheckinfolistBean bean : list.getCheckinfolist()
                                    ) {
                                Log.i(TAG, "execute: " + bean.getTID() + "\t" + bean.getCheckType());
                            }
                            adapter.setSource(list.getCheckinfolist());

                        }
                        asyncTaskRequest = null;//do anything before this;
                    }
                }).setParams(null)
                .create();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        xRecyclerView.loadMoreComplete();
        xRecyclerView.refreshComplete();
    }


    /**
     * adapter item click event , bindView
     *
     * @param holder
     * @param position
     * @param list
     */
    @Override
    public void bindView(ViewHolder holder, int position, List<Expert_obtain_list.CheckinfolistBean> list) {
        holder.text1.setText(list.get(position).getTID() + "");
        holder.text2.setText(list.get(position).getCheckType() + "");//list.get(position).getCheckType()
    }


    @Override
    public void onItemClickListener(View view, int position) {
        Intent i = new Intent(Activity_expert_zd.this, Activity_expert_zd_detail.class);
        i.getIntExtra("type",mList.get(position).getCheckType());
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


}
