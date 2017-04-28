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
import com.xabaizhong.treemonistor.service.AsyncTaskRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Activity_monitor_query_date_pics extends Activity_base implements CommonRecyclerViewAdapter.CallBack<Activity_monitor_query_date_pics.ViewHolder, String> {
    @BindView(R.id.text0)
    TextView text0;
    @BindView(R.id.rv0)
    RecyclerView rv0;
    @BindView(R.id.layout0)
    LinearLayout layout0;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.rv1)
    RecyclerView rv1;
    @BindView(R.id.layout1)
    LinearLayout layout1;
    @BindView(R.id.text2)
    TextView text2;
    @BindView(R.id.rv2)
    RecyclerView rv2;
    @BindView(R.id.layout2)
    LinearLayout layout2;
    @BindView(R.id.text3)
    TextView text3;
    @BindView(R.id.rv3)
    RecyclerView rv3;
    @BindView(R.id.layout3)
    LinearLayout layout3;
    @BindView(R.id.text4)
    TextView text4;
    @BindView(R.id.rv4)
    RecyclerView rv4;
    @BindView(R.id.layout4)
    LinearLayout layout4;


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
    Activity_monitor_query_dateList_pics_adapter adapter1;
    Activity_monitor_query_dateList_pics_adapter adapter2;
    Activity_monitor_query_dateList_pics_adapter adapter3;
    Activity_monitor_query_dateList_pics_adapter adapter4;

    private void initialView() {
        initLayout();
        initialAdapter();
        initialRecyclerView();
        request();
    }

    private void initLayout() {
        layout0.setVisibility(View.GONE);
        layout1.setVisibility(View.GONE);
        layout2.setVisibility(View.GONE);
        layout3.setVisibility(View.GONE);
        layout4.setVisibility(View.GONE);
    }

    private void initialRecyclerView() {
        rv0.setLayoutManager(new GridLayoutManager(this, 3));

        rv1.setLayoutManager(new GridLayoutManager(this, 3));
        rv2.setLayoutManager(new GridLayoutManager(this, 3));
        rv3.setLayoutManager(new GridLayoutManager(this, 3));
        rv4.setLayoutManager(new GridLayoutManager(this, 3));
        rv0.setAdapter(adapter0);
        rv1.setAdapter(adapter1);
        rv2.setAdapter(adapter2);
        rv3.setAdapter(adapter3);
        rv4.setAdapter(adapter4);
    }

    private void initialAdapter() {
        adapter0 = new Activity_monitor_query_dateList_pics_adapter(this, R.layout.image_item);
        adapter1 = new Activity_monitor_query_dateList_pics_adapter(this, R.layout.image_item);
        adapter2 = new Activity_monitor_query_dateList_pics_adapter(this, R.layout.image_item);
        adapter3 = new Activity_monitor_query_dateList_pics_adapter(this, R.layout.image_item);
        adapter4 = new Activity_monitor_query_dateList_pics_adapter(this, R.layout.image_item);
        adapter0.setCallBack(this);
        adapter1.setCallBack(this);
        adapter2.setCallBack(this);
        adapter3.setCallBack(this);
        adapter4.setCallBack(this);

    }

    @Override
    public void bindView(ViewHolder holder, int position, List<String> list) {
        String uri = list.get(position);
        String muri = "https://www.greenjsq.me/templates/green/images/user-rating-1.jpg";
        holder.image1.setTag(muri);
        Picasso.with(Activity_monitor_query_date_pics.this).load(Uri.parse(muri)).into(holder.image1);
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
        map.put("UserID", "");
        map.put("TreeID", "");
        map.put("UpDate", "2017-03-21");
        map.put("AreaID", "");
        AsyncTaskRequest.instance("DataQuerySys", "ImportTreelPicInfo")
                .setParams(map)
                .setCallBack(new AsyncTaskRequest.CallBack() {
                    @Override
                    public void execute(String s) {
                        Log.i(TAG, "execute: " + s);
                        String result = "{\n" +
                                "  \"treeId\": \"61032201\",\n" +
                                "  \"UserID\": \"6100000001\",\n" +
                                "  \"date\": \"2017-03-2113:46:24\",\n" +
                                "  \"AreaID\": \"610329\",\n" +
                                "  \"picinfo\": [\n" +
                                "    {\n" +
                                "      \"PicPlace\": 1,\n" +
                                "      \"Explain\": \"213\",\n" +
                                "      \"piclist\": [\n" +
                                "        \"image\",\n" +
                                "        \"image\"\n" +
                                "      ]\n" +
                                "    },\n" +
                                "    {\n" +
                                "      \"PicPlace\": 2,\n" +
                                "      \"Explain\": \"213\",\n" +
                                "      \"piclist\": [\n" +
                                "        \"image\",\n" +
                                "        \"image\"\n" +
                                "      ]\n" +
                                "    }\n" +
                                "  ]\n" +
                                "}";
                        List<ResultMessage.PicinfoBean> list = new Gson().fromJson(result, ResultMessage.class).getPicinfo();
                        for (ResultMessage.PicinfoBean bean : list) {
                            switch (bean.getPicPlace()) {
                                case 0:
                                    layout0.setVisibility(View.VISIBLE);
                                    text0.setText("全部");
                                    adapter0.setSource(bean.getPiclist());
                                    break;
                                case 1:
                                    layout1.setVisibility(View.VISIBLE);
                                    text1.setText("方向：东");
                                    adapter1.setSource(bean.getPiclist());
                                    break;
                                case 2:
                                    layout2.setVisibility(View.VISIBLE);
                                    text2.setText("方向：南");
                                    adapter2.setSource(bean.getPiclist());
                                    break;
                                case 3:
                                    layout3.setVisibility(View.VISIBLE);
                                    text3.setText("方向：西");
                                    adapter3.setSource(bean.getPiclist());
                                    break;
                                case 4:
                                    layout4.setVisibility(View.VISIBLE);
                                    text4.setText("方向：北");
                                    adapter4.setSource(bean.getPiclist());
                                    break;
                            }
                        }
                    }
                }).create();

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image1;

        public ViewHolder(View itemView) {
            super(itemView);
            image1 = ((ImageView) itemView.findViewById(R.id.image_item1));
        }
    }

    public static class ResultMessage {

        /**
         * treeId : 61032201
         * UserID : 6100000001
         * date : 2017-03-2113:46:24
         * AreaID : 610329
         * picinfo : [{"PicPlace":0,"Explain":"213","piclist":["image","image","image"]},{"PicPlace":1,"Explain":"213","piclist":["image","image","image"]},{"PicPlace":2,"Explain":"213","piclist":["image","image","image"]},{"PicPlace":3,"Explain":"213","piclist":["image","image","image"]},{"PicPlace":4,"Explain":"213","piclist":["image","image","image"]}]
         */

        @SerializedName("treeId")
        private String treeId;
        @SerializedName("UserID")
        private String UserID;
        @SerializedName("date")
        private String date;
        @SerializedName("AreaID")
        private String AreaID;
        @SerializedName("picinfo")
        private List<PicinfoBean> picinfo;

        public String getTreeId() {
            return treeId;
        }

        public void setTreeId(String treeId) {
            this.treeId = treeId;
        }

        public String getUserID() {
            return UserID;
        }

        public void setUserID(String UserID) {
            this.UserID = UserID;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getAreaID() {
            return AreaID;
        }

        public void setAreaID(String AreaID) {
            this.AreaID = AreaID;
        }

        public List<PicinfoBean> getPicinfo() {
            return picinfo;
        }

        public void setPicinfo(List<PicinfoBean> picinfo) {
            this.picinfo = picinfo;
        }

        public static class PicinfoBean {
            /**
             * PicPlace : 0
             * Explain : 213
             * piclist : ["image","image","image"]
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
