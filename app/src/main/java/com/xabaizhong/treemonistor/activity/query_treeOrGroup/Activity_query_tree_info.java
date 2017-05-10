package com.xabaizhong.treemonistor.activity.query_treeOrGroup;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.baidu.mapapi.map.Text;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.contant.UserSharedField;
import com.xabaizhong.treemonistor.service.WebserviceHelper;

import java.net.ConnectException;
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

public class Activity_query_tree_info extends Activity_base {

    @BindView(R.id.layout)
    LinearLayout layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_tree_info);
        ButterKnife.bind(this);
        query();
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
                Log.i(TAG, "accept: "+s);
                if (s == null) {
                    showToast("请求错误");
                }
                Observable.just(s)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                Bean bean = new Gson().fromJson(s, Bean.class);
                                if (bean.getErrorCode() == 0) {
                                    list = bean.getList();
                                    initView();
                                } else {
                                    showToast("无统计数据");
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {

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

    private void initView() {
        for (Bean.ListBean item : list
                ) {
            layout.addView(createView(item.getName()+"\n"+item.getClassical(),item.getNum()+""));
        }
    }

    private View createView(String title,String content){
        View view = LayoutInflater.from(this).inflate(R.layout.activity_statistics_item,null);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.title.setText(title);
        viewHolder.content.setText(content);
        return view;
    }
    class ViewHolder{
        TextView title;
        TextView content;
        public ViewHolder(View view){
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
             */

            @SerializedName("num")
            private int num;
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
