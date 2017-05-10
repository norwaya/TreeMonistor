package com.xabaizhong.treemonistor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.entity.ResultOfExpert;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/2/27.
 */

public class Fragment_expert_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<ResultOfExpert> list = new ArrayList<>();

    LayoutInflater inflate;

    public void setResource(List<ResultOfExpert> list) {
        this.list = list;
    }

    public Fragment_expert_adapter(Context context) {
        inflate = LayoutInflater.from(context);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Fragment_expert_adapter_viewHold(
                inflate.inflate(R.layout.fragment_expert_item2, parent, false));
    }

/*    private static final int VIEWHOLDER_HEAD = 0x123;
    private static final int VIEWHOLDER_CONTENT = 0x122;

    @Override
    public int getItemViewType(int position) {
        if (list.get(position) == null)
            return VIEWHOLDER_HEAD;
        return VIEWHOLDER_CONTENT;
    }*/

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ResultOfExpert roe = list.get(position);
        Fragment_expert_adapter_viewHold viewHolder = (Fragment_expert_adapter_viewHold) holder;
        viewHolder.viewHolder.tid.setText(roe.getTID());
        String type = "";
        switch (roe.getAuthType()) {
            case 0:
                type = "树种鉴定";
                break;
            case 1:
                break;
            case 2:
                break;

        }

        viewHolder.viewHolder.type.setText(type);
        viewHolder.viewHolder.date.setText(roe.getDateTime().substring(0, 10));
        if (roe.getJDResult() == null) {
            viewHolder.viewHolder.checked.setText("否");
            viewHolder.viewHolder.layoutChecked.setVisibility(View.GONE);
        } else {
            viewHolder.viewHolder.checked.setText("是");
            viewHolder.viewHolder.layoutChecked.setVisibility(View.VISIBLE);
            viewHolder.viewHolder.dateJd.setText(roe.getJDResult().getCheckTime());
            viewHolder.viewHolder.result.setText(roe.getJDResult().getResult());
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class Fragment_expert_adapter_viewHold extends RecyclerView.ViewHolder {

        ViewHolder viewHolder;

        public Fragment_expert_adapter_viewHold(View itemView) {
            super(itemView);
            viewHolder = new ViewHolder(itemView);
        }
    }


    static class ViewHolder {
        @BindView(R.id.tid)
        TextView tid;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.type)
        TextView type;
        @BindView(R.id.checked)
        TextView checked;
        @BindView(R.id.date_jd)
        TextView dateJd;
        @BindView(R.id.result)
        TextView result;
        @BindView(R.id.layout_checked)
        LinearLayout layoutChecked;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
