package com.xabaizhong.treemonistor.activity.query_treeOrGroup;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.adapter.CommonRecyclerViewAdapter;
import com.xabaizhong.treemonistor.adapter.QueryTreeInfoListAdapter;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.contant.UserSharedField;
import com.xabaizhong.treemonistor.myview.ProgressDialogUtil;
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
import io.reactivex.schedulers.Schedulers;

import static android.widget.LinearLayout.VERTICAL;

/**
 * 古树群 信息 列表
 */
public class Activity_query_tree_info_list extends Activity_base implements CommonRecyclerViewAdapter.CallBack<Activity_query_tree_info_list.ViewHolder, QueryTreeInfoList.ListBean>, XRecyclerView.LoadingListener, AdapterView.OnItemClickListener {

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

    String mType;
//    int type;

    private void initialRequest() {
        mType = getIntent.getStringExtra("index");
//        type = getIntent.getIntExtra("type", 0);
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
    public void request(String item, int page, int col) {

        int index = col;
        Log.i(TAG, "request: index -> " + index);

        final Map<String, Object> map = new HashMap<>();
        map.put("index", item);
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
        judgeToGo(mList.get(position).getTreeID());
//        Intent i = null;
//        if (mType.equals("72")) {
//            i = new Intent(this, Activity_tree_group_detailInfo.class);
//        } else {
//            i = new Intent(this, Activity_tree_detailInfo.class);
//        }
//        i.putExtra("treeId", mList.get(position).getTreeID());
//        startActivity(i);
    }

    private void judgeToGo(final String treeId) {

        /* <tem:UserID>610102001</tem:UserID>
         <tem:TreeType>0</tem:TreeType>
         <!--Optional:-->
         <tem:TreeID>61010200001</tem:TreeID>
         <!--Optional:-->
         <tem:AreaID>610102</tem:AreaID>
         <!--Optional:-->
         <tem:Date></tem:Date>*/
        final ProgressDialog dialog = ProgressDialogUtil.getInstance(this).initial("请求数据中...", null);
        final Map<String, Object> map = new HashMap<>();
        map.put("UserID ", sharedPreferences.getString(UserSharedField.USERID, ""));
        map.put("AreaID", sharedPreferences.getString(UserSharedField.AREAID, ""));
        map.put("TreeType", mType.equals("72") ? 1 : 0);
        map.put("TreeID", treeId);
        map.put("Date", null);

        Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {
                        String result = null;
                        try {
                            result = WebserviceHelper.GetWebService(
                                    "DataQuerySys", "TreeDelInfo1", map);
                        } catch (Exception ex) {
                            e.onError(ex);
                        }
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
                    public void onNext(String value) {
                        Log.i(TAG, "onNext: " + value);
                        Result bean = new Gson().fromJson(value, Result.class);
                        if (bean.errorCode == 0) {
                            Intent intent = null;
                            if (bean.getIsList() == 1) {
                                showListDialogFromBottom(new Gson().fromJson(value, ResultBean.class));
                            } else if (bean.getTreetype() == 0) {
                                intent = new Intent(Activity_query_tree_info_list.this, Activity_tree_detailInfo.class);
                                intent.putExtra("treeId", treeId);
//                                intent.putExtra("date", "");
                            } else if (bean.getTreetype() == 1) {
                                intent = new Intent(Activity_query_tree_info_list.this, Activity_tree_group_detailInfo.class);
                                intent.putExtra("treeId", treeId);
                            }
                            if (intent != null)
                                startActivity(intent);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "accept: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        dialog.dismiss();
                    }
                });

    }

    ResultBean currentBean;

    private void showListDialogFromBottom(ResultBean bean) {
        currentBean = bean;

        AlertDialog.Builder builder = new  AlertDialog.Builder(this);
        builder.setTitle("日期列表");
        View view = getLayoutInflater().inflate(R.layout.bottom_list_view, null);
        ListView lv = (ListView) view.findViewById(R.id.lv);
        ListAdapter adapter = getAdapter(bean.getResult());
        AlertDialog dialog = builder.create();
        builder.setSingleChoiceItems(adapter, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = null;
                if (currentBean.getTreetype() == 0) {
                    intent = new Intent(Activity_query_tree_info_list.this, Activity_tree_detailInfo.class);
                    intent.putExtra("treeId", currentBean.getTreeId());
                    intent.putExtra("date", currentBean.getResult().get(which));
                } else if (currentBean.getTreetype() == 1) {
                    intent = new Intent(Activity_query_tree_info_list.this, Activity_tree_group_detailInfo.class);
                    intent.putExtra("treeId", currentBean.getTreeId());
                    intent.putExtra("date", currentBean.getResult().get(which));
                }
                if (intent != null)
                    startActivity(intent);
            }
        });
//        dialog.setContentView(view);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);

//        Window dialogWindow = dialog.getWindow();
//        if (dialogWindow != null) {
//            dialogWindow.setGravity(Gravity.BOTTOM);
//            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//            dialogWindow.setAttributes(lp);
//        }
        builder.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


    }

    ListAdapter getAdapter(List<String> list) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.simple_text_center, R.id.text1, list);

        return adapter;

    }


    static class Result {
        @SerializedName("message")
        private String message;
        @SerializedName("error_code")
        private int errorCode;
        @SerializedName("treetype")
        private int treetype;
        @SerializedName("isList")
        private int isList;

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

        public int getTreetype() {
            return treetype;
        }

        public void setTreetype(int treetype) {
            this.treetype = treetype;
        }

        public int getIsList() {
            return isList;
        }

        public void setIsList(int isList) {
            this.isList = isList;
        }
    }


    static class ResultBean {

        /**
         * message : sus
         * error_code : 0
         * treetype : 0
         * isList : 1
         * result : ["2017-06-09","2017-06-08","2016-06-29","2016-06-27","2016-06-16"]
         */

        @SerializedName("treeid")
        private String treeId;
        @SerializedName("message")
        private String message;
        @SerializedName("error_code")
        private int errorCode;
        @SerializedName("treetype")
        private int treetype;
        @SerializedName("isList")
        private int isList;
        @SerializedName("result")
        private List<String> result;

        public String getTreeId() {
            return treeId;
        }

        public void setTreeId(String treeId) {
            this.treeId = treeId;
        }

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

        public int getTreetype() {
            return treetype;
        }

        public void setTreetype(int treetype) {
            this.treetype = treetype;
        }

        public int getIsList() {
            return isList;
        }

        public void setIsList(int isList) {
            this.isList = isList;
        }

        public List<String> getResult() {
            return result;
        }

        public void setResult(List<String> result) {
            this.result = result;
        }
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
