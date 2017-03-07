package com.xabaizhong.treemonitor.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.squareup.picasso.Picasso;
import com.xabaizhong.treemonitor.R;
import com.xabaizhong.treemonitor.activity.Activity_news_show;
import com.xabaizhong.treemonitor.adapter.CommonRecyclerViewAdapter;
import com.xabaizhong.treemonitor.adapter.Fragment_news_adapter;
import com.xabaizhong.treemonitor.base.App;
import com.xabaizhong.treemonitor.base.Fragment_base;
import com.xabaizhong.treemonitor.contant.Contant;
import com.xabaizhong.treemonitor.entity.Data_news;
import com.xabaizhong.treemonitor.entity.Data_newsDao;
import com.xabaizhong.treemonitor.service.ApiService;
import com.xabaizhong.treemonitor.service.RetrofitUtil;
import com.xabaizhong.treemonitor.service.entity.Response_news;
import com.xabaizhong.treemonitor.utils.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static android.support.v7.widget.RecyclerView.VERTICAL;

/**
 * Created by admin on 2017/2/24.
 */

public class Fragment_news extends Fragment_base {
    String TAG = "fragment-news";


    @BindView(R.id.fragment_news_rv)
    XRecyclerView xRecyclerView;
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
        Log.d(TAG, "onCreateView: ");
        Log.d(TAG, "onCreateView: +++++++++" + (savedInstanceState == null));
        initView();

        return view;
    }

    Fragment_news_adapter adapter;

    private void initView() {
        dao = ((App) context.getApplicationContext()).getDaoSession().getData_newsDao();

        xRecyclerView.setPullRefreshEnabled(true);
        xRecyclerView.setLoadingMoreEnabled(true);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                requestNews();
            }

            @Override
            public void onLoadMore() {
                Log.d(TAG, "onLoadMore: ");
                adapter.setSource(loadMore());
                xRecyclerView.loadMoreComplete();
            }
        });
        adapter = new Fragment_news_adapter(context, R.layout.fragment_news_newsitem);
        adapter.setCallBack(new CommonRecyclerViewAdapter.CallBack<Fragment_news_adapter_viewHold, Data_news>() {
            private List<Data_news> list;
            @Override
            public void bindView(Fragment_news_adapter_viewHold holder, int position, List<Data_news> list) {
                this.list = list;
                Data_news data = list.get(position);
                holder.tv_content.setText(data.getCategory() + "\t" + data.getDate() + "\t" + data.getTitle());
                String url = data.getThumbnail_pic_s();
                if (!TextUtils.isEmpty(url))
                    Picasso.with(context).load(Uri.parse(url)).into(holder.image);
                holder.tv_date.setText(data.getDate());
            }

            @Override
            public void onItemClickListener(View view, int position) {
                Intent i = new Intent(context, Activity_news_show.class);
                i.putExtra("url",list.get(position).getUrl());
                i.putExtra("title",list.get(position).getTitle());
                startActivity(i);
            }
        });

        xRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        xRecyclerView.addItemDecoration(new RecycleViewDivider(context, VERTICAL, R.drawable.divider2));
        xRecyclerView.setAdapter(adapter);
        if (list == null) {
            list = queryNews();
            Log.d(TAG, "initView: list is null");
        } else {
            Log.d(TAG, "initView: list is not null");
        }
        adapter.setSource(list);
    }

    private void requestNews() {
        RetrofitUtil.instance().create(ApiService.class)
                .obtainNews("guoji", Contant.NEW.APP_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Response_news>() {
                               @Override
                               public void accept(Response_news news) throws Exception {
                                   saveNews(news);
                                   adapter.setSource(queryNews());
                                   xRecyclerView.refreshComplete();
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   throwable.printStackTrace();
                                   xRecyclerView.refreshComplete();
                               }
                           }, new Action() {
                               @Override
                               public void run() throws Exception {
                                   Log.d(TAG, "run: complete");
                                   xRecyclerView.refreshComplete();
                               }
                           }
                );
    }

    Data_newsDao dao;

    private void saveNews(Response_news news) {
        Log.d(TAG, "saveNews: error-code" + news.getError_code());
        if (news.getError_code() == 0) {
            List<Data_news> list = news.getResult().getData();
            Log.d(TAG, "saveNews: size" + list.size());
            for (Data_news data : list) {
                if (!findNews(data))
                    dao.save(data);
            }
        }
    }

    private boolean findNews(Data_news news) {
        return dao.queryBuilder().where(Data_newsDao.Properties.Uniquekey.eq(news.getUniquekey())).orderDesc(Data_newsDao.Properties.Date).build().list().size() > 0;

    }


    int limit = Contant.NEW.LOADING_NEWS_SIZE;

    private List<Data_news> queryNews() {
        limit = Contant.NEW.LOADING_NEWS_SIZE;
        List<Data_news> list = dao.queryBuilder().limit(limit).build().list();
        Log.d(TAG, "queryNews: " + list.size());
        return list;
    }

    private List<Data_news> loadMore() {
        Data_newsDao dao = ((App) context.getApplicationContext()).getDaoSession().getData_newsDao();
        List<Data_news> list = dao.queryBuilder().limit(limit += 5).build().list();
        limit = list.size();
        return list;
    }

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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: " + (outState == null));
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d(TAG, "onViewStateRestored: " + (savedInstanceState == null));
    }

    List<Data_news> list;


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
