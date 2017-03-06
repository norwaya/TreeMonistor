package com.xabaizhong.treemonistor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.entity.ResultOfExpert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/2/27.
 */

public class Fragment_expert_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<ResultOfExpert> list = new ArrayList<>();

    LayoutInflater inflate;

    public Fragment_expert_adapter(Context context) {
        inflate = LayoutInflater.from(context);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEWHOLDER_HEAD:
                return new Fragment_expert_adapter_viewHold(
                        inflate.inflate(R.layout.fragment_expert_item2, parent, false));
            case VIEWHOLDER_CONTENT:
                return new Fragment_expert_adapter_viewHold(
                        inflate.inflate(R.layout.fragment_expert_item, parent, false));
        }
        return null;
    }

    private static final int VIEWHOLDER_HEAD = 0x123;
    private static final int VIEWHOLDER_CONTENT = 0x122;

    @Override
    public int getItemViewType(int position) {
        if (list.get(position) == null)
            return VIEWHOLDER_HEAD;
        return VIEWHOLDER_CONTENT;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d("expert-adapter", "onBindViewHolder: " + position);
        if (list.get(position) != null)
            ((Fragment_expert_adapter_viewHold) holder).tv_content.setText(list.get(position).getResult());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void refreshList(List<ResultOfExpert> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public class Fragment_expert_adapter_viewHold extends RecyclerView.ViewHolder {

        TextView tv_content;
        int type = VIEWHOLDER_CONTENT;

        public Fragment_expert_adapter_viewHold(View itemView) {
            super(itemView);
            tv_content = (TextView) itemView.findViewById(R.id.cardview_item_tv);
        }
    }

    public class Fragment_expert_adapter_viewHold_head extends RecyclerView.ViewHolder {

        int type = VIEWHOLDER_HEAD;

        public Fragment_expert_adapter_viewHold_head(View itemView) {
            super(itemView);
        }
    }

}
