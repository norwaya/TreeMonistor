package com.xabaizhong.treemonistor.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by admin on 2017/3/3.
 */

public class Fragment_expert_adapter_spacesItemDecoration extends RecyclerView.ItemDecoration {

    int offset;
    public Fragment_expert_adapter_spacesItemDecoration(int offset){
        this.offset = offset;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        outRect.left = offset;
        outRect.right = offset;
        outRect.bottom = offset;

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildLayoutPosition(view) == 0)
            outRect.top = offset;
    }


}
