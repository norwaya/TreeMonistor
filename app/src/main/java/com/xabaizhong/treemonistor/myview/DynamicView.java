package com.xabaizhong.treemonistor.myview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.activity.add_tree.Activity_add_tree_group;
import com.xabaizhong.treemonistor.activity.add_tree.Activity_tree_cname;
import com.xabaizhong.treemonistor.entity.TreeSpecial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/3/16.
 */

public class DynamicView extends LinearLayout {

    List<MapView> list = new ArrayList<>();
    MapView currentMapView;
    Context context;


    public DynamicView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        MapView mapView = new MapView(context, true, this);
        mapView.setCallback(new MapView.Callback() {
            @Override
            public void onClickListener(View v) {
                addItem();
            }
        });

        list.add(mapView);
        addView(mapView.getView());
    }

    public void setMapViewValue(TreeSpecial treeSpecial) {
        currentMapView.setValue(treeSpecial);
    }

    public List<Map<String, Object>> getTreeMap() {
        List<Map<String, Object>> mapList = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        for (MapView mapView : list
                ) {
            Map<String, Object> l = mapView.getKV();
            if (l != null)
                mapList.add(l);
        }
        return mapList;
    }

    private void addItem() {
        final MapView mapView = new MapView(context, false, this);
        mapView.setCallback(new MapView.Callback() {
            @Override
            public void onClickListener(View v) {
                removeMapView(mapView);
            }
        });
        View view = mapView.getView();
        list.add(mapView);
        addView(view);
    }

    private void removeMapView(MapView view) {
        if (list.contains(view)) {
            list.remove(view);
        }
        removeView(view.getView());
    }


    public static class MapView {
        LayoutInflater inflater;
        private Callback callback;
        Context context;

        DynamicView dynamicView;

        public MapView(Context context, boolean tag, DynamicView dynamicView) {
            this.context = context;
            this.tag = tag;
            inflater = LayoutInflater.from(context);
            this.dynamicView = dynamicView;
            initItem();
        }

        boolean tag;

        public void setValue(TreeSpecial treeSpecial) {
            viewHolder.name.setText(treeSpecial.getCname());
            viewHolder.code.setText(treeSpecial.getTreeSpeId());
        }

        public void setCallback(Callback callback) {
            this.callback = callback;
        }

        View view;
        ViewHolder viewHolder;

        private void initItem() {
            view = inflater.inflate(R.layout.dynamicview_item, null);
            viewHolder = new ViewHolder(view);
            if (tag) {
                viewHolder.btn.setImageDrawable(context.getResources().getDrawable(R.drawable.add));
            } else {
                viewHolder.btn.setImageDrawable(context.getResources().getDrawable(R.drawable.minus));
            }
            viewHolder.btn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callback != null) {
                        callback.onClickListener(v);
                    }
                }
            });
            viewHolder.name.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Activity) context).startActivityForResult(new Intent(context, Activity_tree_cname.class),
                            Activity_add_tree_group.REQUEST_CODE_TREESPECIES);
                    dynamicView.currentMapView = MapView.this;
                }
            });

        }

        private Map<String, Object> getKV() {
            Map<String, Object> map = null;
            String name = viewHolder.code.getText().toString();
            String num = viewHolder.num.getText().toString();
            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(num)) {
                map = new HashMap<>();
                try {
                    System.out.println(name + "\t" + num);
                    map.put("name", name);
                    map.put("num", Integer.parseInt(num));
                } catch (Exception e) {

                }
            }
            return map;
        }

        public View getView() {
            return view;
        }

        private interface Callback {
            void onClickListener(View v);
        }

        static class ViewHolder {
            @BindView(R.id.name)
            Button name;
            @BindView(R.id.tree_code)
            TextView code;
            @BindView(R.id.num)
            EditText num;
            @BindView(R.id.btn)
            ImageButton btn;
            @BindView(R.id.layout)
            LinearLayout layout;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

}
