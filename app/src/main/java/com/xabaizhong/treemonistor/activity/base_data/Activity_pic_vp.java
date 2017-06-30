package com.xabaizhong.treemonistor.activity.base_data;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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

/**
 *  图片 查看
 */
public class Activity_pic_vp extends Activity_base implements View.OnClickListener {
    @BindView(R.id.vp)
    ViewPager vp;
    ArrayList<String> picList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_vp);
        ButterKnife.bind(this);
        setScreen();
        initialView();
    }

    private void setScreen() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_FULLSCREEN|
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );


    }

    ViewPagerAdapter adapter;
    int currentItem;

    private void initialView() {
        picList = getIntent().getStringArrayListExtra("picList");
        if (picList == null || picList.size() == 0) {
            finish();
        }

        currentItem = getIntent().getIntExtra("current", 0);
        adapter = new ViewPagerAdapter(this);
        adapter.setSource(picList);
        vp.setAdapter(adapter);
        vp.setCurrentItem(currentItem);
    }

    @Override
    public void onClick(View v) {
        finish();
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
                iv.setOnClickListener(Activity_pic_vp.this);
                Picasso.with(context).load(Uri.parse(uri))
                        .placeholder(R.drawable.image_viewholder)
                        .error(R.drawable.error_img)
                        .into(iv);
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
