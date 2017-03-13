package com.xabaizhong.treemonistor.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.activity.Activity_map;
import com.xabaizhong.treemonistor.adapter.Fragment_expert_adapter;
import com.xabaizhong.treemonistor.base.Fragment_base;
import com.xabaizhong.treemonistor.entity.ResultOfExpert;
import com.xabaizhong.treemonistor.utils.RecycleViewDivider;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.v7.widget.RecyclerView.VERTICAL;

/**
 * Created by admin on 2017/2/24.
 */

public class Fragment_expert extends Fragment_base {

    String TAG = "fragment-expert";
    @BindView(R.id.menu_yellow)
    FloatingActionMenu menuYellow;
    @BindView(R.id.fab_tree_unknow)
    FloatingActionButton fabTreeUnknow;
    @BindView(R.id.fab_tree_weakness)
    FloatingActionButton fabTreeWeakness;
    private Context context;
    @BindView(R.id.xRecyclerView)
    XRecyclerView xRecyclerView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expert, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    Fragment_expert_adapter adapter;

    private void initView() {
        menuYellow.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                Log.d(TAG, "onMenuToggle: " + opened);
            }
        });

        View view = LayoutInflater.from(context).inflate(R.layout.fragment_expert_header, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));
        initHeaderView(view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        xRecyclerView.addItemDecoration(new RecycleViewDivider(context, VERTICAL, R.drawable.divider));
        xRecyclerView.addHeaderView(view);
        adapter = new Fragment_expert_adapter(context);
        xRecyclerView.setAdapter(adapter);
        xRecyclerView.setPullRefreshEnabled(true);
        xRecyclerView.setLoadingMoreEnabled(true);


        xRecyclerView.setLoadingListener(
                new XRecyclerView.LoadingListener() {
                    @Override
                    public void onRefresh() {
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        requestData();
                                        xRecyclerView.refreshComplete();
                                    }
                                });
                            }
                        }.start();
                    }

                    @Override
                    public void onLoadMore() {
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        requestData();
                                        xRecyclerView.loadMoreComplete();
                                    }
                                });
                            }
                        }.start();
                    }
                }
        );
    }

    private void initHeaderView(View view) {
        Button header_btn =
                (Button) view.findViewById(R.id.btn_expert_btn);
        header_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), Activity_map.class));

                /*ResourceObserver<HttpResult> resourceObserver = new ResourceObserver<HttpResult>() {
                    @Override
                    public void onNext(HttpResult value) {
                        Log.d(TAG, "onNext: " + value);
                        Log.d(TAG, "onNext: " + value.getMessage());
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                };


                RetrofitUtil.instance()
                        .create(ApiService.class)
                        .login("G6190010010m01", "123456")
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(resourceObserver);*/

            }
        });
    }
//    private RequestBody requestBody(){
//    }
   /* Retrofit getRetrofit() {
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("http://117.34.115.230:8080/spring/")
                .client(client().build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create());

        return builder.build();
    }*/

    /*OkHttpClient.Builder client() {
        return new OkHttpClient.Builder()

                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)
                .addInterceptor(new LogInterceptor());//日志
    }*/

    ArrayList<ResultOfExpert> list;

    private void requestData() {
        if (list == null)
            list = new ArrayList<>();
        list.clear();
        ResultOfExpert expert = null;
        for (int i = 0; i < 10; i++) {

            if (i == 0 || i == 3) {
                expert = null;
            } else {
                expert = new ResultOfExpert(i, "content" + i, null);
            }
            list.add(expert);
        }
        xRecyclerView.refreshComplete();
        adapter.refreshList(list);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }


    @OnClick({R.id.fab_tree_unknow, R.id.fab_tree_weakness})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_tree_unknow:
                Log.d(TAG, "onClick: unknow");
                break;
            case R.id.fab_tree_weakness:
                Log.d(TAG, "onClick: weakness");
                break;
        }
        menuYellow.close(true);
    }

    /*class LogInterceptor implements Interceptor {
        private String TAG = "okhttp-interceptor";

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Log.v(TAG, "request:" + request.toString());
            long t1 = System.nanoTime();
            okhttp3.Response response = chain.proceed(chain.request());
            long t2 = System.nanoTime();
            Log.v(TAG, String.format(
                    Locale.getDefault(), "Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));
            okhttp3.MediaType mediaType = response.body().contentType();
            String content = response.body().string();
            Log.i(TAG, "response body:" + content);
            return response.newBuilder()
                    .body(okhttp3.ResponseBody.create(mediaType, content))
                    .build();
        }
    }*/
}
