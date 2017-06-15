package com.xabaizhong.treemonistor.activity.expert_zd;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
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
import com.xabaizhong.treemonistor.utils.RecycleViewDivider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v7.widget.RecyclerView.VERTICAL;

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

    @Override
    protected void onResume() {
        super.onResume();
        refreshView();
    }

    private void initialView() {
        initialAdapter();
        xRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        xRecyclerView.addItemDecoration(new RecycleViewDivider(this, VERTICAL, R.drawable.divider2));
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

        asyncTaskRequest = AsyncTaskRequest.instance("CheckUp", "ExpertLookResultList")
                .setCallBack(new AsyncTaskRequest.CallBack() {
                    @Override
                    public void execute(String s) {
                        Log.i(TAG, "execute: " + s + "\n");
                        if (s == null) {
                            showToast("网络错误");
                            return;
                        }
                        Expert_obtain_list list = new Gson().fromJson(s, Expert_obtain_list.class);
                        if (list.getError_code() == 0) {
                            mList = list.getCheckinfolist();
                            adapter.setSource(list.getCheckinfolist());

                        }
                        asyncTaskRequest = null;//do anything before this;
                    }
                }).setParams(params)
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
        holder.text1.setText(getType(list.get(position).getAuthType()));
        holder.text2.setText(list.get(position).getUserID());
        holder.text3.setText(list.get(position).getDateTime().substring(0, 10));
    }

    // Type (0,树种；1,生长势；2,树龄；3树病；4虫害)
    private String getType(int type) {
        String name = "";
        switch (type) {
            case 0:
                name = "树种鉴定";
                break;
            case 1:
                name = "生长势";
                break;
            case 2:
                name = "树龄";
                break;
            case 3:
                name = "树病";
                break;
            case 4:
                name = "虫害";
                break;
            default:
                break;
        }
        return name;
    }


    @Override
    public void onItemClickListener(View view, int position) {
        Intent i = new Intent(Activity_expert_zd.this, Activity_expert_zd_detail.class);
        i.putExtra("type", mList.get(position).getAuthType());
        i.putExtra("tid", mList.get(position).getTid());
        startActivity(i);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView text1;
        TextView text2;
        TextView text3;

        public ViewHolder(View itemView) {
            super(itemView);
            text1 = ((TextView) itemView.findViewById(R.id.text1));
            text2 = ((TextView) itemView.findViewById(R.id.text2));
            text3 = ((TextView) itemView.findViewById(R.id.text3));
        }
    }


}
