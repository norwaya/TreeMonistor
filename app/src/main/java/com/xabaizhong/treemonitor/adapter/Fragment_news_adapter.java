package com.xabaizhong.treemonitor.adapter;

import android.content.Context;

import com.xabaizhong.treemonitor.entity.Data_news;
import com.xabaizhong.treemonitor.fragment.Fragment_news;
import com.xabaizhong.treemonitor.service.entity.Response_news;

/**
 * Created by [#author] on 2017/2/24.
 * @author norwaya
 */
public class Fragment_news_adapter extends CommonRecyclerViewAdapter<Fragment_news.Fragment_news_adapter_viewHold, Data_news> {

    public Fragment_news_adapter(Context context, int layout) {
        super(context, layout);
    }
}
