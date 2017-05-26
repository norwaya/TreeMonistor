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
    private List<ResultOfExpert> list = new ArrayList<>();
    private LayoutInflater inflate;

    public void setResource(List<ResultOfExpert> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }
    public Fragment_expert_adapter(Context context) {
        inflate = LayoutInflater.from(context);
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Fragment_expert_adapter_viewHold(
                inflate.inflate(R.layout.fragment_expert_item2, parent, false));
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ResultOfExpert roe = list.get(position);
        Fragment_expert_adapter_viewHold viewHolder = (Fragment_expert_adapter_viewHold) holder;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onClick(v, position, list.get(position));
                }
            }
        });
        viewHolder.viewHolder.tid.setText(roe.getTID());
        String type = "";
        switch (roe.getAuthType()) {
            case 0:
                type = "树种鉴定";
                break;
            case 1:
                type="";
                break;
            case 2:
                type="";
                break;
            case 3:
                type="树病鉴定";
                break;
            case 4:
                type="";
                break;
            default:
                break;

        }
        viewHolder.viewHolder.type.setText(type);
        viewHolder.viewHolder.date.setText(roe.getDateTime().substring(0, 10));
        Log.i("isChecked", "onBindViewHolder: "+roe.isChecked());
        if (roe.isChecked()) {
            viewHolder.viewHolder.checked.setText("是");
        } else {
            viewHolder.viewHolder.checked.setText("否");
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
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

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private OnClickListener onClickListener;

    public  interface OnClickListener {
        void onClick(View view, int position, Object obj);
    }
}
