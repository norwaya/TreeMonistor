package com.xabaizhong.treemonistor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
    boolean flag = false;
    List<Integer> checkItem = new ArrayList<>();

    public Activity_add_manager_adapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public List<Integer> getCheckItem() {
        return checkItem;
    }

    public void setFlag() {
        flag = !flag;
        checkItem.clear();
        mChooseAll = false;
        notifyDataSetChanged();
    }

    public boolean getFlag() {
        return flag;
    }

    private boolean mChooseAll = false;

    public void setChooseAll(boolean chooseAll) {
        if (flag)
            if (chooseAll)
                for (int i = 0; i < list.size(); i++) {
                    checkItem.add(i);
                }
            else
                checkItem.clear();
        notifyDataSetChanged();

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
        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkItem.contains(position) && !isChecked) {
                    checkItem.remove(position);
                } else if (isChecked) {

                    checkItem.add(position);
                }
                if (!isChecked) {
                    mChooseAll = false;
                }

            }
        });

        if (flag) {
            holder.cb.setVisibility(View.VISIBLE);
            if (checkItem.contains(position)) {
                holder.cb.setChecked(true);
            } else {
                holder.cb.setChecked(false);
            }
        } else {
            holder.cb.setVisibility(View.GONE);
        }

        Log.i("adapter", "getView: " + position + "\t" + checkItem.size());
        return convertView;
    }

    private String getDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public void setSource(List<TreeTypeInfo> source) {
        this.list = source;
        checkItem.clear();
        notifyDataSetChanged();
    }

    class Viewhloder {
        TextView tv1;
        TextView tv2;
        TextView tv3;
        CheckBox cb;

        public Viewhloder(View itemView) {
            tv1 = (TextView) itemView.findViewById(R.id.tv1);
            tv2 = (TextView) itemView.findViewById(R.id.tv2);
            tv3 = (TextView) itemView.findViewById(R.id.tv3);
            cb = (CheckBox) itemView.findViewById(R.id.cb);
//            tv1.setPadding(18,8,18,8);
//            tv2.setPadding(18,8,18,8);
//            tv3.setPadding(18,8,18,8);
        }
    }
}
