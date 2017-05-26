package com.xabaizhong.treemonistor.activity.base_data;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.base.Activity_base;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/21 0021.
 */

public class Activity_pic_vp extends Activity_base {
    @BindView(R.id.vp)
    ViewPager vp;
    ArrayList<String> picList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_vp);
        ButterKnife.bind(this);
        initialView();
    }

    ViewPagerAdapter adapter;
    int currentItem ;
    private void initialView() {
        picList = getIntent().getStringArrayListExtra("picList");
        currentItem = getIntent().getIntExtra("current", 0);
        adapter = new ViewPagerAdapter(this);
        adapter.setSource(picList);
        vp.setAdapter(adapter);
        vp.setCurrentItem(currentItem);
    }

    private class ViewPagerAdapter extends PagerAdapter {
        LayoutInflater inflater;
        Context context;

        ViewPagerAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        ArrayList<String> list = new ArrayList<>();
        List<View> mViewList = new ArrayList<>();

        public void setSource(ArrayList<String> list) {
            this.list = list;
//            notifyDataSetChanged();
            for (String uri : list
                    ) {
                View view = inflater.inflate(R.layout.image_item, null);
                ImageView iv = ((ImageView) view.findViewById(R.id.image_item1));
                Picasso.with(context).load(Uri.parse(uri)).into(iv);
                mViewList.add(view);
                Log.i(TAG, "setSource: " + uri);
            }
        }

        @Override
        public int getCount() {
            return mViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position));
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {//必须实现，销毁
            container.removeView(mViewList.get(position));
        }

    }
}
