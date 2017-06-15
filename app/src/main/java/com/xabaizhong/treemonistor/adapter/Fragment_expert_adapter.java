package com.xabaizhong.treemonistor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.activity.expert_zd.fragment.Fragment_Expert_Bug;
import com.xabaizhong.treemonistor.entity.ResultOfExpert;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/2/27.
 */

public class Fragment_expert_adapter extends BaseAdapter {
    private List<ResultOfExpert> list = new ArrayList<>();
    private LayoutInflater inflate;

    public void setResource(List<ResultOfExpert> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    public Fragment_expert_adapter(Context context) {
        inflate = LayoutInflater.from(context);
    }



    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).hashCode();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Fragment_expert_adapter_viewHold viewHolder;
        if (convertView == null) {
            convertView = inflate.inflate(R.layout.fragment_expert_item2, parent, false);
            viewHolder = new Fragment_expert_adapter_viewHold(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (Fragment_expert_adapter_viewHold) convertView.getTag();
        }
        ResultOfExpert roe = list.get(position);
//        Log.i("check same", "onBindViewHolder: " + roe.getTID() + "\t" + roe.isChecked());
        viewHolder.tid.setText(roe.getTID());
        if (roe.isChecked()) {
            viewHolder.checked.setText("是");
        } else {
            viewHolder.checked.setText("否");
        }
        viewHolder.date.setText(roe.getDateTime().substring(0, 10));
        String type = "";
        switch (roe.getAuthType()) {
            case 0:
                type = "树种鉴定";
                break;
            case 1:
                type = "";
                break;
            case 2:
                type = "";
                break;
            case 3:
                type = "树病鉴定";
                break;
            case 4:
                type = "虫害鉴定";
                break;
            default:
                break;

        }
        viewHolder.type.setText(type);

        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onClick(v, position, list.get(position));
                }
            }
        });

        return convertView;
    }
    public class Fragment_expert_adapter_viewHold {

        TextView tid;
        TextView date;
        TextView type;
        TextView checked;
        View view;
        public Fragment_expert_adapter_viewHold(View view) {
            this.view = view;
            tid = ((TextView) view.findViewById(R.id.tid));
            date = ((TextView) view.findViewById(R.id.date));
            type = ((TextView) view.findViewById(R.id.type));
            checked = ((TextView) view.findViewById(R.id.checked));
        }
    }


    private OnClickListener onClickListener;

    public interface OnClickListener {
        void onClick(View view, int position, Object obj);
    }
}
