package com.xabaizhong.treemonistor.adapter;

import android.content.Context;

import com.xabaizhong.treemonistor.activity.query_treeOrGroup.Activity_query_tree_info_list;
import com.xabaizhong.treemonistor.service.model.QueryTreeInfoList;

/**
 * Created by norwaya on 17-4-14.
 */

public class QueryTreeInfoListAdapter extends CommonRecyclerViewAdapter<Activity_query_tree_info_list.ViewHolder, QueryTreeInfoList.ListBean> {
    public QueryTreeInfoListAdapter(Context context, int layout) {
        super(context, layout);
    }
}
