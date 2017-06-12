package com.xabaizhong.treemonistor.adapter;

import android.content.Context;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xabaizhong.treemonistor.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/2/24.
 */

public class Fragment_function_adapter extends BaseAdapter {
    private List<String> itemList = new ArrayList<>();
    private List<Integer> imageIdList = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;

    private List<String> permissions = new ArrayList<>();

    public Fragment_function_adapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setResource(List<String> permissions,List<Integer> images, List<String> items) {
        this.permissions = permissions;
        this.imageIdList = images;
        this.itemList = items;
        notifyDataSetChanged();
    }





    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return itemList.get(position).hashCode();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        FunctionViewHoler holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_function_list_item, parent, false);
            holder = new FunctionViewHoler(convertView);
            convertView.setTag(holder);
        }else{
            holder = ((FunctionViewHoler) convertView.getTag());
        }
        Picasso.with(context)
                .load(imageIdList.get(position))
                .into(holder.iv);
        holder.tv.setText(itemList.get(position));
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(v, position);
            }
        });
//        }
//        holder.setVisibility(flag);
        switch (position) {
            case 0:
                if (permissions.contains("3"))
                    holder.setVisibility(true);
                else
                    holder.setVisibility(false);
                break;
            case 1:
                if (permissions.contains("3"))
                    holder.setVisibility(true);
                else
                    holder.setVisibility(false);
                break;
            case 2:
                if (permissions.contains("50"))
                    holder.setVisibility(true);
                else
                    holder.setVisibility(false);
                break;
            case 3:
                if (permissions.contains("3")||permissions.contains("50"))
                    holder.setVisibility(true);
                else
                    holder.setVisibility(false);
                break;
            case 4:
                if (permissions.contains("40"))
                    holder.setVisibility(true);
                else
                    holder.setVisibility(false);
                break;
            default:
                holder.setVisibility(true);
                break;
        }
        return convertView;
    }

   private class FunctionViewHoler {
        ImageView iv;
        TextView tv;
        View view;
        public FunctionViewHoler(View itemView) {
            view = itemView;
            iv = (ImageView) itemView.findViewById(R.id.iv);
            tv = (TextView) itemView.findViewById(R.id.text);
        }

        public void setVisibility(boolean isVisible) {
            ViewGroup.LayoutParams param =  view.getLayoutParams();
            if (isVisible) {
                param.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                param.width = LinearLayout.LayoutParams.MATCH_PARENT;
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(View.GONE);
                param.height = 1;
                param.width = 0;
            }
            view.setLayoutParams(param);
        }
    }

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
