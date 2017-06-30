package com.xabaizhong.treemonistor.activity.base_data;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.activity.add_tree.Activity_tree_cname;
import com.xabaizhong.treemonistor.adapter.Activity_tree_base_adapter;
import com.xabaizhong.treemonistor.adapter.Activity_tree_cname_adapter;
import com.xabaizhong.treemonistor.adapter.CommonRecyclerViewAdapter;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.base.App;
import com.xabaizhong.treemonistor.entity.TreeSpecial;
import com.xabaizhong.treemonistor.entity.TreeSpecialDao;
import com.xabaizhong.treemonistor.utils.RecycleViewDivider;

import org.greenrobot.greendao.query.Query;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v7.widget.RecyclerView.VERTICAL;

/**
 * Created by admin on 2017/3/17.
 */


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.adapter.Activity_tree_cname_adapter;
import com.xabaizhong.treemonistor.adapter.CommonRecyclerViewAdapter;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.base.App;
import com.xabaizhong.treemonistor.entity.TreeSpecial;
import com.xabaizhong.treemonistor.entity.TreeSpecialDao;
import com.xabaizhong.treemonistor.utils.RecycleViewDivider;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v7.widget.RecyclerView.VERTICAL;

/**
 * 树种 基本信息 查看页 ，类似于 树种查询页；
 */
public class Activity_tree_base extends Activity_base {

    @BindView(R.id.sv)
    SearchView sv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_cname);
        ButterKnife.bind(this);
        initView();
        init();
    }

    TreeSpecialDao dao;

    private void init() {
        dao = ((App) getApplicationContext()).getDaoSession().getTreeSpecialDao();
    }

    Activity_tree_base_adapter adapter;

    private void initView() {
        initSearchView();

        initRecyclerView();


    }

    private void initRecyclerView() {
        adapter = new Activity_tree_base_adapter(this, R.layout.activity_tree_base_item);
        adapter.setCallBack(new CommonRecyclerViewAdapter.CallBack<Activity_tree_base.ViewHolder, TreeSpecial>() {
            @Override
            public void bindView(ViewHolder holder, int position, List<TreeSpecial> list) {
                holder.cname.setText(matcherSearchTitle(Color.RED,list.get(position).getCname(),queryStr));
                holder.alias.setText(matcherSearchTitle(Color.RED,list.get(position).getAlias(),queryStr));
            }

            @Override
            public void onItemClickListener(View view, int position) {
                Intent i = new Intent(Activity_tree_base.this,Activity_tree_base_detail.class);
                i.putExtra("id", list.get(position).getId());
                startActivity(i);
            }
        });
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
    }

    public SpannableString matcherSearchTitle(int color, String text,
                                              String keyword) {
        SpannableString s = new SpannableString(text);
        Pattern p = Pattern.compile(keyword);
        Matcher m = p.matcher(s);
        while (m.find()) {
            int start = m.start();
            int end = m.end();
            s.setSpan(new ForegroundColorSpan(color), start, end,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return s;
    }


    int width;
    String queryStr;

    private void initSearchView() {
        width = sv.getLeft();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sv.setIconified(false);
        sv.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query != null && !query.trim().equals("")) {
                    queryStr = query;
                    Log.i(TAG, "onQueryTextSubmit: " +query);
                    adapter.setSource(getList(query));
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.i(TAG, "onQueryTextChange: ");
                return false;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    List<TreeSpecial> list;

    public List<TreeSpecial> getList(String query) {
        Query<TreeSpecial> queryDao = dao.queryBuilder().whereOr(
                TreeSpecialDao.Properties.Cname.like("%" + query + "%"),
                TreeSpecialDao.Properties.Alias.like("%" + query + "%")).build();
        list = queryDao.list();
        return list;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView cname;
        TextView alias;
        ImageView icon;

        public ViewHolder(View itemView) {
            super(itemView);
            cname = (TextView) itemView.findViewById(R.id.cname);
            icon = (ImageView) itemView.findViewById(R.id.left_icon);
            alias = (TextView) itemView.findViewById(R.id.alias);
        }
    }
}
