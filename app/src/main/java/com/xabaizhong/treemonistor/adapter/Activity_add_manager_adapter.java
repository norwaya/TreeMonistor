package com.xabaizhong.treemonistor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.activity.add_tree.Activity_add_manage;
import com.xabaizhong.treemonistor.entity.Data_news;
import com.xabaizhong.treemonistor.entity.TreeTypeInfo;
import com.xabaizhong.treemonistor.fragment.Fragment_news;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by [#author] on 2017/2/24.
 *
 * @author norwaya
 */
public class Activity_add_manager_adapter extends BaseAdapter {
    private List<TreeTypeInfo> list = new ArrayList<>();
    private LayoutInflater inflater;

    public Activity_add_manager_adapter(Context context) {
        inflater = LayoutInflater.from(context);
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
    public View getView(int position, View convertView, ViewGroup parent) {
        Viewhloder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_add_manager_item, parent, false);
            holder = new Viewhloder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (Viewhloder) convertView.getTag();
        }
        holder.tv1.setText(list.get(position).getTreeId());
        holder.tv2.setText(list.get(position).getTypeId() == 0 ? "古树" : "古树群");
        holder.tv3.setText(getDate(list.get(position).getDate()));
        return convertView;
    }

    private String getDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public void setSource(List<TreeTypeInfo> source) {
        this.list = source;
        notifyDataSetChanged();
    }

    class Viewhloder {
        TextView tv1;
        TextView tv2;
        TextView tv3;

        public Viewhloder(View itemView) {
            tv1 = (TextView) itemView.findViewById(R.id.tv1);
            tv2 = (TextView) itemView.findViewById(R.id.tv2);
            tv3 = (TextView) itemView.findViewById(R.id.tv3);
            tv1.setPadding(18,8,18,8);
            tv2.setPadding(18,8,18,8);
            tv3.setPadding(18,8,18,8);
        }
    }
}
