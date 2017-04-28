package com.xabaizhong.treemonistor.activity.base_data;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.base.App;
import com.xabaizhong.treemonistor.entity.Weakness;
import com.xabaizhong.treemonistor.entity.WeaknessDao;
import com.xabaizhong.treemonistor.myview.C_info_gather_item1;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/3/17.
 */

public class Activity_base_weakness_detail extends Activity_base {

    long id;
    @BindView(R.id.cname)
    C_info_gather_item1 cname;
    @BindView(R.id.part)
    C_info_gather_item1 part;
    @BindView(R.id.trait)
    C_info_gather_item1 trait;
    @BindView(R.id.featrue)
    C_info_gather_item1 featrue;
    @BindView(R.id.method)
    C_info_gather_item1 method;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        id = getIntent().getLongExtra("id", -1);
        setContentView(R.layout.activity_base_weakness_detail);
        ButterKnife.bind(this);
        initDb();
        initialView();
    }

    private void initDb() {

        WeaknessDao weaknessDao = ((App) getApplicationContext()).getDaoSession().getWeaknessDao();
        weakness = weaknessDao.load(id);
    }

    Weakness weakness;

    private void initialView() {
        cname.setText(weakness.getName());
        part.setText(weakness.getPartName());
        trait.setText(weakness.getTrait());
        featrue.setText(weakness.getFreature());
        method.setText(weakness.getMethod());
    }

}
