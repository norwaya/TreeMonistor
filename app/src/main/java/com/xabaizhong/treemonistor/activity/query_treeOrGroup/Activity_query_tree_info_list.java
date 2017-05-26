package com.xabaizhong.treemonistor.activity.query_treeOrGroup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.adapter.CommonRecyclerViewAdapter;
import com.xabaizhong.treemonistor.adapter.QueryTreeInfoListAdapter;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.contant.UserSharedField;
import com.xabaizhong.treemonistor.service.WebserviceHelper;
import com.xabaizhong.treemonistor.service.model.QueryTreeInfoList;
import com.xabaizhong.treemonistor.utils.RecycleViewDivider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static android.widget.LinearLayout.VERTICAL;


public class Activity_query_tree_info_list extends Activity_base implements CommonRecyclerViewAdapter.CallBack<Activity_query_tree_info_list.ViewHolder, QueryTreeInfoList.ListBean>, XRecyclerView.LoadingListener {

    Intent getIntent;
    @BindView(R.id.xRecyclerView)
    XRecyclerView xRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_tree_info_list);
        ButterKnife.bind(this);
        getIntent = getIntent();
        initRecycleView();
        initialRequest();

    }

    int mType;
    int type;

    private void initialRequest() {
        mType = getIntent.getIntExtra("index", -1);
        type = getIntent.getIntExtra("type", 0);
        mPage = 0;
        mCol = 0;
        request(mType, mPage, mCol);
    }


    QueryTreeInfoListAdapter adapter;

    private void initRecycleView() {
        initialAdater();
        xRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        xRecyclerView.addItemDecoration(new RecycleViewDivider(this, VERTICAL, R.drawable.divider));
        xRecyclerView.setAdapter(adapter);

        xRecyclerView.setPullRefreshEnabled(true);
        xRecyclerView.setLoadingMoreEnabled(true);
        xRecyclerView.setLoadingListener(this);
    }

    List<QueryTreeInfoList.ListBean> mList = new ArrayList<>();

    int mCol = 0;//每一组为10条数据
    int mPage = 0;//每一页10组数据

    boolean noMore = true;

    @Override
    public void onRefresh() {
        if (mPage > 0) {
            noMore = true;
            mPage--;
            mCol = 0;
            mList.clear();
            request(mType, mPage, mCol);
        } else {
            xRecyclerView.refreshComplete();
        }

    }


    @Override
    public void onLoadMore() {
        if (!noMore) {
            xRecyclerView.loadMoreComplete();
            return;
        }
//        if (mCol == 5) {
//            mCol = 0;
//            mList.clear();
//        }
        request(mType, mPage, mCol);
    }

    /* <index>int</index>
      <AreaID>string</AreaID>
      <page>int</page>*/
    public void request(int type, int page, int col) {

        int index =   col;
        Log.i(TAG, "request: index -> " + index);

        final Map<String, Object> map = new HashMap<>();
        map.put("index", type);
        map.put("AreaID", sharedPreferences.getString(UserSharedField.AREAID, ""));
        map.put("page", index);

        Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {
                        String result = WebserviceHelper.GetWebService(
                                "DataQuerySysOther", "GetDYInfoList", map);
                        if (result == null) {
                            e.onError(new RuntimeException("error"));
                        } else {
                            e.onNext(result);
                        }
                        e.onComplete();

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String o) {
                        Log.i(TAG, "accept: " + o);
                        QueryTreeInfoList queryTreeInfoList = new Gson().fromJson(o, QueryTreeInfoList.class);
                        if (queryTreeInfoList.getErrorCode() != 0) {
                            showToast(queryTreeInfoList.getMessage());
                            return;
                        }
                        List<QueryTreeInfoList.ListBean> queryinfolist = queryTreeInfoList.getList();
                        if (queryinfolist.size() < 20) {
                            noMore = false;
                        }
                        Log.i(TAG, "onNext: " + queryinfolist.size());
                        mList.addAll(queryinfolist);
                        mCol = (mList.size() + 19) / 20;
                        Log.i(TAG, "onNext: " + mList.size() + "\t" + mCol);
                        adapter.setSource(mList);


                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "accept: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        xRecyclerView.loadMoreComplete();
                        xRecyclerView.refreshComplete();
                    }
                });
    }


    private void initialAdater() {
        adapter = new QueryTreeInfoListAdapter(this, R.layout.activity_query_tree_info_list_item);
        adapter.setCallBack(this);
        adapter.setCallBack(this);
    }

    @Override
    public void bindView(ViewHolder holder, int position, List<QueryTreeInfoList.ListBean> list) {
        holder.id.setText(list.get(position).getTreeID());
        holder.name.setText(list.get(position).getCHName());
        holder.area.setText(list.get(position).getDate());

    }

    @Override
    public void onItemClickListener(View view, int position) {
        Intent i = null;
        if (type == 1) {
            i = new Intent(this, Activity_tree_group_detailInfo.class);
        } else {
            i = new Intent(this, Activity_tree_detailInfo.class);
        }
        i.putExtra("treeId", mList.get(position).getTreeID());
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
