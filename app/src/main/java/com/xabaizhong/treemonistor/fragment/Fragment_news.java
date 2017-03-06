package com.xabaizhong.treemonistor.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.adapter.CommonRecyclerViewAdapter;
import com.xabaizhong.treemonistor.adapter.Fragment_news_adapter;
import com.xabaizhong.treemonistor.base.Fragment_base;
import com.xabaizhong.treemonistor.entity.News;
import com.xabaizhong.treemonistor.utils.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v7.widget.RecyclerView.VERTICAL;

/**
 * Created by admin on 2017/2/24.
 */

public class Fragment_news extends Fragment_base {
    String TAG = "fragment-news";


    @BindView(R.id.fragment_news_rv)
    RecyclerView fragmentNewsRv;
    private Context context;

    public Fragment_news() {
        Log.d(TAG, "Fragment_news: ");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);
        Log.d(TAG, "onCreateView: ");
        Log.d(TAG, "onCreateView: +++++++++"+(savedInstanceState == null));
        initView();
        return view;
    }
    private void initView(){
        Fragment_news_adapter adapter = new Fragment_news_adapter(context,R.layout.fragment_news_newsitem);
        adapter.setCallBack(new CommonRecyclerViewAdapter.CallBack<Fragment_news_adapter_viewHold, News>() {
            @Override
            public void bindView(Fragment_news_adapter_viewHold holder, int position, List<News> list) {
                holder.tv_content.setText(list.get(position).getContent()+"\t"+position);
            }
        });
        fragmentNewsRv.setLayoutManager(new LinearLayoutManager(context));
        fragmentNewsRv.addItemDecoration(new RecycleViewDivider(context,VERTICAL,R.drawable.divider2));
        fragmentNewsRv.setAdapter(adapter);
        if(list== null){
            list = getNews();
            Log.d(TAG, "initView: list is null");
        }else{
            Log.d(TAG, "initView: list is not null");
        }

        adapter.setSource(list);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        Log.d(TAG, "onAttach: ");
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.d(TAG, "onStart: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: ");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: "+(outState == null));
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d(TAG, "onViewStateRestored: "+(savedInstanceState == null));
    }
    ArrayList<News> list;
    private ArrayList<News> getNews(){
        list = new ArrayList<News>();
        for (int i = 0; i < 100; i++) {
            list.add(new News("content"+ i,i));
        }
        return  list;
    }


    /**
     * Created by admin on 2017/3/2.
     */
    public static class Fragment_news_adapter_viewHold extends RecyclerView.ViewHolder {
        TextView tv_content;

        public Fragment_news_adapter_viewHold(View itemView) {
            super(itemView);
            tv_content = (TextView) itemView.findViewById(R.id.cardview_item_tv);
        }
    }
}
