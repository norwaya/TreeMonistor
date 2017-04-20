package com.xabaizhong.treemonistor.activity.add_tree;

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

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v7.widget.RecyclerView.VERTICAL;

/**
 * Created by admin on 2017/3/13.
 */

public class Activity_tree_cname extends Activity_base implements View.OnClickListener {

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
        dao = ((App)getApplicationContext()).getDaoSession().getTreeSpecialDao();
    }

    Activity_tree_cname_adapter adapter;
    private void initView() {
        initSearchView();

        initRecyclerView();




    }

    public static final int REQUEST_CODE_CNAME_RESULT = 155;
    
    
    private void initRecyclerView() {
        adapter = new Activity_tree_cname_adapter(this,R.layout.activity_tree_cname_item);
        adapter.setCallBack(new CommonRecyclerViewAdapter.CallBack<ViewHolder, TreeSpecial>() {
            @Override
            public void bindView(ViewHolder holder, int position, List<TreeSpecial> list) {
                holder.cname.setText(list.get(position).getCname());
                holder.alias.setText(list.get(position).getAlias());
            }

            @Override
            public void onItemClickListener(View view, int position) {
                Intent i = new Intent();
                i.putExtra("special", list.get(position));
                setResult(REQUEST_CODE_CNAME_RESULT,i);
                Log.i(TAG, "onItemClickListener: "+position);
                Activity_tree_cname.this.finish();
            }
        });
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new RecycleViewDivider(this, VERTICAL, R.drawable.divider2));
        rv.setAdapter(adapter);
    }
    int width;
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
                Log.i(TAG, "onQueryTextSubmit: ");
                adapter.setSource(getList(query));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.i(TAG, "onQueryTextChange: ");
                return false;
            }
        });
        sv.setOnSearchClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
    List<TreeSpecial> list;
    public List<TreeSpecial> getList(String query) {
        Query<TreeSpecial> queryDao = dao.queryBuilder().whereOr(
                TreeSpecialDao.Properties.Cname.like("%"+query+"%"),
                TreeSpecialDao.Properties.Alias.like("%"+query+"%")).limit(20).build();
        list = queryDao.list();
        return list;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
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
