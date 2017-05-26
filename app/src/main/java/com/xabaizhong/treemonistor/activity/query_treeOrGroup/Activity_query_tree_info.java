package com.xabaizhong.treemonistor.activity.query_treeOrGroup;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Layout;
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
import com.xabaizhong.treemonistor.service.WebserviceHelper;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2017/4/11 0011.
 */

public class Activity_query_tree_info extends Activity_base implements AdapterView.OnItemClickListener {

    @BindView(R.id.lv)
    ListView lv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_tree_info);
        ButterKnife.bind(this);
        initialListView();
        query();
    }

    private void initialListView() {
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
        lv.setOnItemClickListener(this);
    }

    Activity_query_tree_info_adapter adapter;

    private void initialAdapter() {
        adapter = new Activity_query_tree_info_adapter(this);
    }

    AsyncTask asyncTask;

    private void query() {
        asyncTask = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    return WebserviceHelper.GetWebService(
                            "DataQuerySys", "QueryTreeInfoMethod1", getParms());
                } catch (ConnectException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
                Log.i(TAG, "accept: " + s);
                if (s == null) {
                    showToast("请求错误");
                    return;
                }
                Observable.just(s)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                Bean bean = new Gson().fromJson(s, Bean.class);
                                if (bean.getErrorCode() == 0) {
                                    list = bean.getList();
                                    adapter.setList(list);
                                } else {
                                    showToast("无统计数据");
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.i(TAG, "accept: " + throwable.getMessage());
                            }
                        });
            }
        }.execute();
    }


    private Map<String, Object> getParms() {
        Map<String, Object> map = new HashMap<>();
        map.put("UserID", sharedPreferences.getString(UserSharedField.USERID, ""));
        map.put("AreaID", sharedPreferences.getString(UserSharedField.AREAID, ""));
        return map;
    }

    List<Bean.ListBean> list;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0)
            return;
        Bean.ListBean bean = list.get(position - 1);
        int index = bean.getIndex();
        Intent i = new Intent(this, Activity_query_tree_info_list.class);
        i.putExtra("index", index);
        if (index == -1) {
            i.putExtra("type",1);
        }else{
            i.putExtra("type",0);
        }
        startActivity(i);
    }


    class ViewHolder {
        TextView title;
        TextView content;

        public ViewHolder(View view) {
            title = ((TextView) view.findViewById(R.id.title));
            content = ((TextView) view.findViewById(R.id.content));
        }
    }

    static class Bean {
        /**
         * message : sus
         * error_code : 0
         * list : [{"num":244,"classical":"古树","name":"全部"},{"num":0,"classical":"古树群","name":"全部"},{"num":0,"classical":"特级","name":"等级"},{"num":0,"classical":"特级","name":"等级"},{"num":242,"classical":"一级","name":"等级"},{"num":0,"classical":"一级","name":"等级"},{"num":0,"classical":"二级","name":"等级"},{"num":0,"classical":"二级","name":"等级"},{"num":0,"classical":"三级","name":"等级"},{"num":2,"classical":"三级","name":"等级"},{"num":244,"classical":"农村","name":"区域"},{"num":0,"classical":"城市","name":"区域"},{"num":2,"classical":"个人","name":"权属"},{"num":0,"classical":"个人","name":"权属"},{"num":0,"classical":"集体","name":"权属"},{"num":242,"classical":"集体","name":"权属"},{"num":0,"classical":"国家","name":"权属"},{"num":0,"classical":"国家","name":"权属"},{"num":1,"classical":"正常株","name":"生长势"},{"num":0,"classical":"正常株","name":"生长势"},{"num":0,"classical":"正常株","name":"生长势"},{"num":0,"classical":"衰弱株","name":"生长势"},{"num":5,"classical":"衰弱株","name":"生长势"},{"num":0,"classical":"衰弱株","name":"生长势"},{"num":0,"classical":"濒危株","name":"生长势"},{"num":0,"classical":"濒危株","name":"生长势"},{"num":238,"classical":"濒危株","name":"生长势"},{"num":0,"classical":"死亡株","name":"生长势"},{"num":0,"classical":"死亡株","name":"生长势"},{"num":0,"classical":"死亡株","name":"生长势"},{"num":0,"classical":"远郊野外","name":"生长场所"},{"num":0,"classical":"远郊野外","name":"生长场所"},{"num":6,"classical":"乡村街道","name":"生长场所"},{"num":0,"classical":"乡村街道","name":"生长场所"},{"num":0,"classical":"城区","name":"生长场所"},{"num":238,"classical":"城区","name":"生长场所"},{"num":0,"classical":"历史文化街区","name":"生长场所"},{"num":0,"classical":"历史文化街区","name":"生长场所"},{"num":0,"classical":"风景名胜古区","name":"生长场所"},{"num":0,"classical":"风景名胜古区","name":"生长场所"},{"num":1,"classical":"良好","name":"生长环境"},{"num":0,"classical":"良好","name":"生长环境"},{"num":0,"classical":"差","name":"生长环境"},{"num":0,"classical":"差","name":"生长环境"},{"num":0,"classical":"极差","name":"生长环境"},{"num":0,"classical":"极差","name":"生长环境"}]
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
             * num : 244
             * classical : 古树
             * name : 全部
             * index : 1
             */

            @SerializedName("num")
            private int num;
            @SerializedName("classical")
            private String classical;
            @SerializedName("name")
            private String name;
            @SerializedName("index")
            private int index;

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
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

            public int getIndex() {
                return index;
            }

            public void setIndex(int index) {
                this.index = index;
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
            viewHoler.title.setText(mContext.getString(R.string.two_item_t,
                    listBean.getName(), listBean.getClassical()));
            viewHoler.content.setText(String.valueOf(listBean.getNum()));
            return convertView;
        }

        static class ViewHolder {
            TextView title;
            TextView content;

            public ViewHolder(View itemview) {
                title = (TextView) itemview.findViewById(R.id.title);
                content = (TextView) itemview.findViewById(R.id.content);
            }
        }
    }
}
