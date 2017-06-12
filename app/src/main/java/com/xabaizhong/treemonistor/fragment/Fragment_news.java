package com.xabaizhong.treemonistor.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DebugUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.squareup.picasso.Picasso;
import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.activity.Activity_news_show;
import com.xabaizhong.treemonistor.adapter.CommonRecyclerViewAdapter;
import com.xabaizhong.treemonistor.adapter.Fragment_news_adapter;
import com.xabaizhong.treemonistor.base.Fragment_base;
import com.xabaizhong.treemonistor.contant.UserSharedField;
import com.xabaizhong.treemonistor.entity.Data_news;
import com.xabaizhong.treemonistor.service.WebserviceHelper;
import com.xabaizhong.treemonistor.utils.RecycleViewDivider;

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

import static android.support.v7.widget.RecyclerView.VERTICAL;

/**
 * Created by admin on 2017/2/24.
 */

public class Fragment_news extends Fragment_base {
    String TAG = "fragment-news";

    @BindView(R.id.fragment_news_rv)
    XRecyclerView xRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private Context context;

    public Fragment_news() {
        Log.d(TAG, "Fragment_news: ");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);
        initView();
        initNews();
        return view;
    }

    private void initNews() {
//        if (list == null) {
//            requestNews();
//        }
    }

    private void initToolbar() {
        toolbar.setTitle("资讯");
        toolbar.setTitleTextColor(Color.WHITE);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

    }

    @Override
    public void onStart() {
        super.onStart();
        initToolbar();
    }

    Fragment_news_adapter adapter;

    private void initView() {

//        dao = ((App) context.getApplicationContext()).getDaoSession().getData_newsDao();

        xRecyclerView.setPullRefreshEnabled(true);
        xRecyclerView.setLoadingMoreEnabled(false);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                requestNews();
            }

            @Override
            public void onLoadMore() {
//                Log.d(TAG, "onLoadMore: ");
//                adapter.setSource(loadMore());
//                xRecyclerView.loadMoreComplete();
            }
        });
        adapter = new Fragment_news_adapter(context, R.layout.fragment_news_newsitem);
        adapter.setCallBack(new CommonRecyclerViewAdapter.CallBack<Fragment_news_adapter_viewHold, Result.ResultBean>() {
            private List<Result.ResultBean> list;

            @Override
            public void bindView(Fragment_news_adapter_viewHold holder, int position, List<Result.ResultBean> list) {
                this.list = list;
                Result.ResultBean data = list.get(position);
                holder.tv_content.setText(data.getNewsName());
                String url = data.getNewsPicPath();
                if (!TextUtils.isEmpty(url))
                    Picasso.with(context).load(Uri.parse(url)).into(holder.image);
                holder.tv_date.setText(data.getNewsDate());
            }

            @Override
            public void onItemClickListener(View view, int position) {
                Intent i = new Intent(context, Activity_news_show.class);
                i.putExtra("url", list.get(position).getNewsUrl());
                i.putExtra("title", list.get(position).getNewsName());
                startActivity(i);
            }
        });

        xRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        xRecyclerView.addItemDecoration(new RecycleViewDivider(context, VERTICAL, R.drawable.divider2));
        xRecyclerView.setAdapter(adapter);
        if (list == null) {
            requestNews();
        }
    }

    Disposable disposable;

    //    http://192.168.0.118:8055/DataQuerySysOther.asmx?op=GetNews
    private void requestNews() {
        final Map<String, Object> map = new HashMap<>();
        map.put("UserID", sharedPreferences.getString(UserSharedField.USERID, ""));

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                String result = null;
                try {
                    result = WebserviceHelper.GetWebService(
                            "DataQuerySysOther", "GetNews", map);
                } catch (Exception ex) {
//                    e.onError(ex);
                    ex.printStackTrace();
                }
                if (result == null) {

//                    e.onError(new RuntimeException("null"));
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
                        Log.i(TAG, "onNext: " + value);
                        Result result = new Gson().fromJson(value, Result.class);
                        if (result.getErrorCode() == 0) {
                            list = result.getResult();
                            adapter.setSource(result.getResult());
                        } else {
                            showToast(result.getMessage());
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        xRecyclerView.refreshComplete();
                        disposable = null;
                    }
                });

    }

    public static class Result {


        /**
         * message : sus
         * error_code : 0
         * result : [{"NewsDate":"2017-05-11 00:00:00","NewsUrl":"http://192.168.0.118:8055/news/1.html","NewsName":"国家林业局林木品种审定委员会关于做好2017年国家级林木品种审定申报工作的通知","NewsPicPath":"http://192.168.0.118:8055/Image/News/Default/1.jpg"}]
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
             * NewsDate : 2017-05-11 00:00:00
             * NewsUrl : http://192.168.0.118:8055/news/1.html
             * NewsName : 国家林业局林木品种审定委员会关于做好2017年国家级林木品种审定申报工作的通知
             * NewsPicPath : http://192.168.0.118:8055/Image/News/Default/1.jpg
             */

            @SerializedName("NewsDate")
            private String NewsDate;
            @SerializedName("NewsUrl")
            private String NewsUrl;
            @SerializedName("NewsName")
            private String NewsName;
            @SerializedName("NewsPicPath")
            private String NewsPicPath;

            public String getNewsDate() {
                return NewsDate;
            }

            public void setNewsDate(String NewsDate) {
                this.NewsDate = NewsDate;
            }

            public String getNewsUrl() {
                return NewsUrl;
            }

            public void setNewsUrl(String NewsUrl) {
                this.NewsUrl = NewsUrl;
            }

            public String getNewsName() {
                return NewsName;
            }

            public void setNewsName(String NewsName) {
                this.NewsName = NewsName;
            }

            public String getNewsPicPath() {
                return NewsPicPath;
            }

            public void setNewsPicPath(String NewsPicPath) {
                this.NewsPicPath = NewsPicPath;
            }
        }
    }


//    private void requestNews() {
//        RetrofitUtil.instance().create(ApiService.class)
//                .obtainNews("shehui", Contant.NEW.APP_KEY)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Response_news>() {
//                               @Override
//                               public void accept(Response_news news) throws Exception {
//                                   saveNews(news);
//                                   adapter.setSource(queryNews());
//                                   xRecyclerView.refreshComplete();
//                               }
//                           }, new Consumer<Throwable>() {
//                               @Override
//                               public void accept(Throwable throwable) throws Exception {
//                                   throwable.printStackTrace();
//                                   xRecyclerView.refreshComplete();
//                               }
//                           }, new Action() {
//                               @Override
//                               public void run() throws Exception {
//                                   Log.d(TAG, "run: complete");
//                                   xRecyclerView.refreshComplete();
//                               }
//                           }
//                );
//    }

//    Data_newsDao dao;
//
//    private void saveNews(Response_news news) {
//        Log.d(TAG, "saveNews: error-code" + news.getError_code());
//        if (news.getError_code() == 0) {
//            List<Data_news> list = news.getResult().getData();
//            Log.d(TAG, "saveNews: size" + list.size());
//            for (Data_news data : list) {
//                if (!findNews(data))
//                    dao.save(data);
//            }
//        }
//        dao.deleteInTx(dao.queryBuilder().orderDesc(Data_newsDao.Properties.Id).limit(500).offset(100).build().list());
//    }

//    private boolean findNews(Data_news news) {
//        return dao.queryBuilder().where(Data_newsDao.Properties.Uniquekey.eq(news.getUniquekey())).orderDesc(Data_newsDao.Properties.Date).build().list().size() > 0;
//
//    }


//    int limit = Contant.NEW.LOADING_NEWS_SIZE;
//
//    private List<Data_news> queryNews() {
//        limit = Contant.NEW.LOADING_NEWS_SIZE;
//        List<Data_news> list = dao.queryBuilder().limit(limit).orderDesc(Data_newsDao.Properties.Id).build().list();
//        Log.d(TAG, "queryNews: " + list.size());
//        return list;
//    }

//    private List<Data_news> loadMore() {
//        Data_newsDao dao = ((App) context.getApplicationContext()).getDaoSession().getData_newsDao();
//        List<Data_news> list = dao.queryBuilder().limit(limit += 5).orderDesc(Data_newsDao.Properties.Id).build().list();
//        limit = list.size();
//        return list;
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        Log.d(TAG, "onAttach: ");
    }


    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        xRecyclerView.refreshComplete();
        xRecyclerView.loadMoreComplete();
    }

   /* @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }*/

    List<Result.ResultBean> list;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    /**
     * Created by admin on 2017/3/2.
     */
    public static class Fragment_news_adapter_viewHold extends RecyclerView.ViewHolder {
        View view;

        ImageView image;
        TextView tv_content;
        TextView tv_date;

        public Fragment_news_adapter_viewHold(View itemView) {
            super(itemView);
            this.view = itemView;
            image = (ImageView) itemView.findViewById(R.id.item_iv);
            tv_content = (TextView) itemView.findViewById(R.id.cardview_item_tv);
            tv_date = (TextView) itemView.findViewById(R.id.item_date);
        }
    }
}
