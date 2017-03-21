package com.xabaizhong.treemonistor.adapter;

import android.content.Context;

import com.xabaizhong.treemonistor.activity.add_tree.Activity_tree_cname;
import com.xabaizhong.treemonistor.activity.base_data.Activity_tree_base;
import com.xabaizhong.treemonistor.entity.TreeSpecial;

/**
 * Created by admin on 2017/3/13.
 */

public class Activity_tree_base_adapter extends CommonRecyclerViewAdapter<Activity_tree_base.ViewHolder,TreeSpecial> {

    public Activity_tree_base_adapter(Context context, int layout) {
        super(context, layout);
    }
}
