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

public class Fragment_function_adapter extends RecyclerView.Adapter<Fragment_function_adapter.FunctionViewHoler> {
    private List<String> itemList = new ArrayList<>();
    private List<Integer> imageIdList = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;

    public Fragment_function_adapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setResource(List<Integer> images, List<String> items) {
        this.imageIdList = images;
        this.itemList = items;
        notifyDataSetChanged();
    }


    @Override
    public FunctionViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_function_list_item, parent, false);
        return new FunctionViewHoler(view);
    }

    @Override
    public void onBindViewHolder(FunctionViewHoler holder, final int position) {
        //权限控制 ，根据权限 控制显示的功能选项
//        boolean flag = position != 2;
//        if (flag) {
            Picasso.with(context)
                    .load(imageIdList.get(position))
                    .into(holder.iv);
            holder.tv.setText(itemList.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener!=null)
                        onItemClickListener.onItemClick(v,position);
                }
            });
//        }
//        holder.setVisibility(flag);
        holder.setVisibility(true);

    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class FunctionViewHoler extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv;

        public FunctionViewHoler(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv);
            tv = (TextView) itemView.findViewById(R.id.text);
        }

        public void setVisibility(boolean isVisible) {
            RecyclerView.LayoutParams param = (RecyclerView.LayoutParams) itemView.getLayoutParams();
            if (isVisible) {
                param.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                param.width = LinearLayout.LayoutParams.MATCH_PARENT;
                itemView.setVisibility(View.VISIBLE);
            } else {
                itemView.setVisibility(View.GONE);
                param.height = 0;
                param.width = 0;
            }
            itemView.setLayoutParams(param);
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
