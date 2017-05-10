package com.xabaizhong.treemonistor.activity.monitor_query;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;
import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.activity.Activity_image_noAction;
import com.xabaizhong.treemonistor.adapter.Activity_monitor_query_dateList_pics_adapter;
import com.xabaizhong.treemonistor.adapter.CommonRecyclerViewAdapter;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.contant.UserSharedField;
import com.xabaizhong.treemonistor.service.AsyncTaskRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class Activity_monitor_query_date_pics extends Activity_base implements CommonRecyclerViewAdapter.CallBack<Activity_monitor_query_date_pics.ViewHolder, Activity_monitor_query_date_pics.ListItem> {
    @BindView(R.id.rv0)
    RecyclerView rv0;


    String treeId;
    String date;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_query_date_pics);
        ButterKnife.bind(this);
        treeId = getIntent().getStringExtra("treeId");
        date = getIntent().getStringExtra("date");

        initialView();
    }

    Activity_monitor_query_dateList_pics_adapter adapter0;

    private void initialView() {
        initialAdapter();
        initialRecyclerView();
        request();
    }


    private void initialRecyclerView() {
        rv0.setLayoutManager(new GridLayoutManager(this, 3));

        rv0.setAdapter(adapter0);

    }


    private void initialAdapter() {
        adapter0 = new Activity_monitor_query_dateList_pics_adapter(this, R.layout.image_item);
        adapter0.setCallBack(this);


    }

    @Override
    public void bindView(ViewHolder holder, int position, List<Activity_monitor_query_date_pics.ListItem> list) {
        Activity_monitor_query_date_pics.ListItem uri = list.get(position);
        holder.image1.setTag(uri.url);
        Picasso.with(Activity_monitor_query_date_pics.this).load(Uri.parse(uri.url)).into(holder.image1);
        holder.text.setText(uri.directory);
    }

    @Override
    public void onItemClickListener(View view, int position) {
        ImageView iv = (ImageView) view.findViewById(R.id.image_item1);
        Log.i(TAG, "onItemClickListener: " + (iv == null ? "" : iv.getTag()));

        Intent i = new Intent(this, Activity_image_noAction.class);
        i.putExtra("uri", (iv.getTag() == null ? "" : iv.getTag().toString()));
        startActivity(i);

    }


    private void initView(int index) {

    }

    private void request() {
        /*  <tem:UserID>12</tem:UserID>
         <!--Optional:-->
         <tem:TreeID>3</tem:TreeID>
         <tem:UpDate>2017-03-21</tem:UpDate>
         <!--Optional:-->
         <tem:AreaID>5</tem:AreaID>*/
        Map<String, Object> map = new HashMap<>();
        map.put("UserID", sharedPreferences.getString(UserSharedField.USERID, ""));
        map.put("TreeID", treeId);
        map.put("RecordTime", date);
        AsyncTaskRequest.instance("CheckUp", "QueryPicList_ImportantTree")
                .setParams(map)
                .setCallBack(new AsyncTaskRequest.CallBack() {
                    @Override
                    public void execute(String s) {
//                        Log.i(TAG, "execute: " + s);
                        if (s == null) {
                            return;
                        }
                        Observable.just(s)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io())
                                .subscribe(new Consumer<String>() {
                                    @Override
                                    public void accept(String s) throws Exception {
                                        ResultMessage rm = new Gson().fromJson(s, ResultMessage.class);
                                        if (rm.getErrorCode() != 0) {
                                            showToast("无返回数据");
                                            finish();
                                            return;
                                        }
                                        List<ResultMessage.ResultBean> list = rm.getResult();
                                        if (list.size() == 0) {
                                            showToast("数据被清空");
                                            finish();
                                            return;
                                        }
                                        List<ListItem> listItem = new ArrayList<ListItem>();
                                        String[] directoryList = {"全部", "东方", "南方", "西方", "北方"};
                                        for (ResultMessage.ResultBean bean : list) {
                                            for (String url : bean.getPiclist()) {

                                                listItem.add(new ListItem(directoryList[bean.getPicPlace()], url));
                                            }
                                        }
                                        adapter0.setSource(listItem);
                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        showToast("数据类型错误");
                                    }
                                });


                    }
                }).create();

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image1;
        TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            image1 = ((ImageView) itemView.findViewById(R.id.image_item1));
            text = ((TextView) itemView.findViewById(R.id.text));
        }
    }

    public static class ListItem {
        String directory;
        String url;

        public ListItem(String directory, String url) {
            this.directory = directory;
            this.url = url;
        }
    }


    public static class ResultMessage {


        /**
         * message : sus
         * error_code : 0
         * userID : test1
         * treeID : 61011200010
         * result : [{"PicPlace":0,"piclist":["http://192.168.0.118:8055/Image/UpTreeInfo/ImportTree/100000/610000/610100/610112/61011200010/20170321014624/0/1.jpg"],"Explain":"213"},{"PicPlace":1,"piclist":["http://192.168.0.118:8055/Image/UpTreeInfo/ImportTree/100000/610000/610100/610112/61011200010/20170321014624/1/1.jpg"],"Explain":"213"},{"PicPlace":2,"piclist":["http://192.168.0.118:8055/Image/UpTreeInfo/ImportTree/100000/610000/610100/610112/61011200010/20170321014624/2/1.jpg"],"Explain":"213"},{"PicPlace":3,"piclist":["http://192.168.0.118:8055/Image/UpTreeInfo/ImportTree/100000/610000/610100/610112/61011200010/20170321014624/3/1.jpg"],"Explain":"213"},{"PicPlace":4,"piclist":["http://192.168.0.118:8055/Image/UpTreeInfo/ImportTree/100000/610000/610100/610112/61011200010/20170321014624/4/1.jpg"],"Explain":"213"}]
         */

        @SerializedName("message")
        private String message;
        @SerializedName("error_code")
        private int errorCode;
        @SerializedName("userID")
        private String userID;
        @SerializedName("treeID")
        private String treeID;
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

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getTreeID() {
            return treeID;
        }

        public void setTreeID(String treeID) {
            this.treeID = treeID;
        }

        public List<ResultBean> getResult() {
            return result;
        }

        public void setResult(List<ResultBean> result) {
            this.result = result;
        }

        public static class ResultBean {
            /**
             * PicPlace : 0
             * piclist : ["http://192.168.0.118:8055/Image/UpTreeInfo/ImportTree/100000/610000/610100/610112/61011200010/20170321014624/0/1.jpg"]
             * Explain : 213
             */

            @SerializedName("PicPlace")
            private int PicPlace;
            @SerializedName("Explain")
            private String Explain;
            @SerializedName("piclist")
            private List<String> piclist;

            public int getPicPlace() {
                return PicPlace;
            }

            public void setPicPlace(int PicPlace) {
                this.PicPlace = PicPlace;
            }

            public String getExplain() {
                return Explain;
            }

            public void setExplain(String Explain) {
                this.Explain = Explain;
            }

            public List<String> getPiclist() {
                return piclist;
            }

            public void setPiclist(List<String> piclist) {
                this.piclist = piclist;
            }
        }
    }
}
