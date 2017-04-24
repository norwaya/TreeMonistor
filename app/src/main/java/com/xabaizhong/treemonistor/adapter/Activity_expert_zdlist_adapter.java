package com.xabaizhong.treemonistor.adapter;

import android.content.Context;

import com.xabaizhong.treemonistor.activity.expert_zd.Activity_expert_zd;
import com.xabaizhong.treemonistor.entity.Data_news;
import com.xabaizhong.treemonistor.fragment.Fragment_news;
import com.xabaizhong.treemonistor.service.model.Expert_obtain_list;

/**
 * Created by [#author] on 2017/2/24.
 *
 * @author norwaya
 */
public class Activity_expert_zdlist_adapter extends CommonRecyclerViewAdapter<Activity_expert_zd.ViewHolder, Expert_obtain_list.CheckinfolistBean> {

    public Activity_expert_zdlist_adapter(Context context, int layout) {
        super(context, layout);
    }
}
