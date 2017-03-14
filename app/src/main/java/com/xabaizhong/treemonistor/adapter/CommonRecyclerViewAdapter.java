package com.xabaizhong.treemonistor.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * @param <T> 需要RecyclerView.ViewHolder 的子类
 * @param <U> List 的泛型 的实际类型
 * @author norwaya
 */

public abstract class CommonRecyclerViewAdapter<T extends RecyclerView.ViewHolder, U> extends RecyclerView.Adapter<T> {
    private String TAG = "";

    private List<U> list = new ArrayList<>();
    private Class<T> clazz;
    private LayoutInflater layoutInflater;
    private int layout;

   public  CommonRecyclerViewAdapter(Context context, int layout) {
        TAG = getClass().getSimpleName();
        layoutInflater = LayoutInflater.from(context);
        this.layout = layout;
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        clazz = (Class<T>) parameterizedType.getActualTypeArguments()[0];
    }

    public void setSource(List<U> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public T onCreateViewHolder(ViewGroup parent, int viewType) {
        T t = null;
        try {
            t = clazz.getConstructor(View.class).newInstance(layoutInflater.inflate(layout, parent, false));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return t;
    }

    @Override
    public void onBindViewHolder(final T holder, final int position) {
        if (callBack != null) {
            callBack.bindView(holder, position, list);
            holder.itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            callBack.onItemClickListener(holder.itemView, position);
                        }
                    }
            );
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private CallBack<T, U> callBack;

    public void setCallBack(CallBack<T, U> callBack) {
        this.callBack = callBack;
    }

    /**
     * @param <T>
     * @param <U>
     */
    public interface CallBack<T, U> {
        void bindView(T holder, int position, List<U> list);

        void onItemClickListener(View view, int position);
    }
}
