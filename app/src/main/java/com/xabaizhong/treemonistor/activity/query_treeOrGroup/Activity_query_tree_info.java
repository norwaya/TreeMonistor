package com.xabaizhong.treemonistor.activity.query_treeOrGroup;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.contant.UserSharedField;
import com.xabaizhong.treemonistor.myview.C_info_gather_item1;
import com.xabaizhong.treemonistor.service.WebserviceHelper;

import java.net.ConnectException;
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

/**
 * Created by Administrator on 2017/4/11 0011.
 */

public class Activity_query_tree_info extends Activity_base implements C_info_gather_item1.Mid_CallBack {

    //    @BindView(R.id.lv)
//    ListView lv;
    @BindView(R.id.layout)
    LinearLayout layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_tree_info);
        ButterKnife.bind(this);
        initialTitleArray();
//        initialListView();
        query();

    }

   /* private void initialListView() {
        initialAdapter();
        View view = LayoutInflater.from(this).inflate(R.layout.activity_statistics_item, null);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView content = (TextView) view.findViewById(R.id.content);
        title.setBackgroundColor(Color.TRANSPARENT);
        content.setBackgroundColor(Color.TRANSPARENT);
        title.setText("统计分类");
        content.setText("数量");
        lv.addHeaderView(view);
        lv.setAdapter(adapter);
    }*/

    Activity_query_tree_info_adapter adapter;

    private void initialAdapter() {
        adapter = new Activity_query_tree_info_adapter(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }

    Disposable disposable;

    private void query() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                String result = null;
                try {
                    result = WebserviceHelper.GetWebService(
                            "DataQuerySys", "QueryTreeInfoMethod1", getParams());
                } catch (Exception ex) {
                    e.onError(ex);
                }
                if (result == null) {
                    e.onError(new RuntimeException("返回为空"));
                } else {
                    e.onNext(result);
                }
                e.onComplete();
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(String value) {
                        Bean bean = new Gson().fromJson(value, Bean.class);
                        for (Bean.ListBean listBean : bean.getList()) {
                            layout.addView(getTitle(titleArray[listBean.getGroupIndex()]));
                            for (Bean.ListBean.ItemsBean item : listBean.getItems()) {
                                layout.addView(getView(item.getClassical(), item.getNum() + "", listBean.getGroupIndex() + "" + item.getIndex()));

                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        disposable = null;
                    }
                });


//      new AsyncTask<Void, Void, String>() {
//            @Override
//            protected String doInBackground(Void... params) {
//                try {
//                    return WebserviceHelper.GetWebService(
//                            "DataQuerySys", "QueryTreeInfoMethod1", getParams());
//                } catch (ConnectException e) {
//                    e.printStackTrace();
//                    return null;
//                }
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                Log.i(TAG, "accept: " + s);
//                if (s == null) {
//                    showToast("请求错误");
//                    return;
//                }
//                Observable.just(s)
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Consumer<String>() {
//                            @Override
//                            public void accept(String s) throws Exception {
//                                Bean bean = new Gson().fromJson(s, Bean.class);
//                                for (Bean.ListBean listBean:bean.getList()
//                                     ) {
//                                    layout.addView(getTitle(titleArray[listBean.getGroupIndex()]));
//                                    for (Bean.ListBean.ItemsBean item:listBean.getItems()
//                                         ) {
//                                        layout.addView(getView(item.getClassical(),item.getNum()+"",listBean.getGroupIndex()+""+item.getIndex()));
//
//                                    }
//                                }
//
//                            }
//                        }, new Consumer<Throwable>() {
//                            @Override
//                            public void accept(Throwable throwable) throws Exception {
//                                Log.i(TAG, "accept: " + throwable.getMessage());
//                            }
//                        });
//            }
//        }.execute();
    }

    String[] titleArray;

    private void initialTitleArray() {
//        0：等级 1：区域 2：权属 3：生长势 4：生长厂所 5：生长环境 6：树种 全部：7
        titleArray = new String[]{"等级", "区域", " 权属", "生长势", "生长厂所", "生长环境", "树种", "全部"};
    }

    private View getTitle(String title) {
        View view = getLayoutInflater().inflate(R.layout.include_title, null);
        ((TextView) view.findViewById(R.id.text1)).setText(title);
        return view;
    }

    LayoutInflater inflater;

    private View getView(String left, String mid, String tag) {
        if (inflater == null) {
            inflater = LayoutInflater.from(this);
        }
        View view = inflater.inflate(R.layout.c_view, null);
        C_info_gather_item1 cv = (C_info_gather_item1) view.findViewById(R.id.cv);
        cv.setCallback_mid(this);
        cv.setTag(tag);
        cv.getMid().setTag(tag);
        cv.setLeftText(left);
        cv.setText(mid);
//        view.setOnClickListener(this);
//        view.setTag(tag);
        return view;
    }

    @Override
    public void onClickListener(View et) {
        Object tag = et.getTag();
        if (tag.equals("70")) {
            showToast("古树总量只有统计总数");
            return;
        }
        Intent i = new Intent(this, Activity_query_tree_info_list.class);
        i.putExtra("index", ((String) tag));
        startActivity(i);
    }

    private Map<String, Object> getParams() {
        Map<String, Object> map = new HashMap<>();
        map.put("UserID", sharedPreferences.getString(UserSharedField.USERID, ""));
        map.put("AreaID", sharedPreferences.getString(UserSharedField.AREAID, ""));
        return map;
    }

    List<Bean.ListBean> list;

    



  /*  @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0)
            return;
        Bean.ListBean bean = list.get(position - 1);
        int index = bean.getIndex();
        Intent i = new Intent(this, Activity_query_tree_info_list.class);
        i.putExtra("index", index);
        if (index == -1) {
            i.putExtra("type", 1);
        } else {
            i.putExtra("type", 0);
        }
        startActivity(i);
    }*/

/*
    class ViewHolder {
        TextView title;
        TextView mid;
        TextView content;

        public ViewHolder(View view) {
            title = ((TextView) view.findViewById(R.id.title));
            mid = ((TextView) view.findViewById(R.id.mid));
            content = ((TextView) view.findViewById(R.id.content));
        }
    }*/

    static class Bean {

        /**
         * message : sus
         * error_code : 0
         * list : [{"items":[{"num":13,"index":1,"classical":"古树","name":"全部"},{"num":18,"index":2,"classical":"古树群","name":"全部"},{"num":26,"index":0,"classical":"古树及古树群","name":"全部"}],"groupIndex":7},{"items":[{"num":0,"index":1,"classical":"特级","name":"等级"},{"num":1,"index":2,"classical":"一级","name":"等级"},{"num":0,"index":3,"classical":"二级","name":"等级"},{"num":12,"index":4,"classical":"三级","name":"等级"}],"groupIndex":0},{"items":[{"num":1,"index":0,"classical":"农村","name":"区域"},{"num":12,"index":1,"classical":"城市","name":"区域"}],"groupIndex":1},{"items":[{"num":0,"index":1,"classical":"个人","name":"权属"},{"num":1,"index":2,"classical":"集体","name":"权属"},{"num":12,"index":3,"classical":"国家","name":"权属"}],"groupIndex":2},{"items":[{"num":11,"index":1,"classical":"正常株","name":"生长势"},{"num":2,"index":2,"classical":"衰弱株","name":"生长势"},{"num":0,"index":3,"classical":"濒危株","name":"生长势"},{"num":0,"index":4,"classical":"死亡株","name":"生长势"}],"groupIndex":3},{"items":[{"num":0,"index":0,"classical":"远郊野外","name":"生长场所"},{"num":1,"index":1,"classical":"乡村街道","name":"生长场所"},{"num":10,"index":2,"classical":"城区","name":"生长场所"},{"num":0,"index":3,"classical":"历史文化街区","name":"生长场所"},{"num":2,"index":4,"classical":"风景名胜古区","name":"生长场所"}],"groupIndex":4},{"items":[{"num":10,"index":1,"classical":"良好","name":"生长环境"},{"num":3,"index":2,"classical":"差","name":"生长环境"},{"num":0,"index":3,"classical":"极差","name":"生长环境"}],"groupIndex":5}]
         */

        @SerializedName("message")
        private String message;
        @SerializedName("error_code")
        private int errorCode;
        @SerializedName("list")
        private List<ListBean> list;

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

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * items : [{"num":13,"index":1,"classical":"古树","name":"全部"},{"num":18,"index":2,"classical":"古树群","name":"全部"},{"num":26,"index":0,"classical":"古树及古树群","name":"全部"}]
             * groupIndex : 7
             */

            @SerializedName("groupIndex")
            private int groupIndex;
            @SerializedName("items")
            private List<ItemsBean> items;

            public int getGroupIndex() {
                return groupIndex;
            }

            public void setGroupIndex(int groupIndex) {
                this.groupIndex = groupIndex;
            }

            public List<ItemsBean> getItems() {
                return items;
            }

            public void setItems(List<ItemsBean> items) {
                this.items = items;
            }

            public static class ItemsBean {
                /**
                 * num : 13
                 * index : 1
                 * classical : 古树
                 * name : 全部
                 */

                @SerializedName("num")
                private int num;
                @SerializedName("index")
                private int index;
                @SerializedName("classical")
                private String classical;
                @SerializedName("name")
                private String name;

                public int getNum() {
                    return num;
                }

                public void setNum(int num) {
                    this.num = num;
                }

                public int getIndex() {
                    return index;
                }

                public void setIndex(int index) {
                    this.index = index;
                }

                public String getClassical() {
                    return classical;
                }

                public void setClassical(String classical) {
                    this.classical = classical;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }
        }
    }

    private static class Activity_query_tree_info_adapter extends BaseAdapter {
        List<Bean.ListBean> list = new ArrayList<>();
        LayoutInflater inflater;
        Context mContext;

        private Activity_query_tree_info_adapter(Context context) {
            inflater = LayoutInflater.from(context);
            mContext = context;
        }

        public void setList(List<Bean.ListBean> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return list.get(position).hashCode();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHoler;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.activity_statistics_item, parent, false);
                viewHoler = new ViewHolder(convertView);
                convertView.setTag(viewHoler);
            } else {
                viewHoler = (ViewHolder) convertView.getTag();
            }
            Bean.ListBean listBean = list.get(position);
//            viewHoler.title.setText(listBean.getName()+"\t"+listBean.getClassical());
          /*  viewHoler.title.setText(listBean.getName());
            viewHoler.mid.setText(listBean.getClassical());
            viewHoler.content.setText(String.valueOf(listBean.getNum()));*/
            return convertView;
        }

        static class ViewHolder {
            TextView title;
            TextView mid;
            TextView content;

            public ViewHolder(View view) {
                title = ((TextView) view.findViewById(R.id.title));
                mid = ((TextView) view.findViewById(R.id.mid));
                content = ((TextView) view.findViewById(R.id.content));
            }
        }
    }
}
