package com.xabaizhong.treemonistor.activity.base_data;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.base.App;
import com.xabaizhong.treemonistor.entity.Pest;
import com.xabaizhong.treemonistor.entity.PestDao;
import com.xabaizhong.treemonistor.myview.C_info_gather_item1;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/3/17.
 */

public class Activity_base_pest_detail extends Activity_base {
    long id;
    @BindView(R.id.cname)
    C_info_gather_item1 cname;
    @BindView(R.id.type)
    C_info_gather_item1 type;
    @BindView(R.id.explain)
    C_info_gather_item1 explain;
    @BindView(R.id.harm)
    C_info_gather_item1 harm;
    @BindView(R.id.step)
    C_info_gather_item1 step;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getLongExtra("id", -1);
        setContentView(R.layout.activity_base_pest_detail);
        ButterKnife.bind(this);
        initDb();
        initialView();
    }

    private void initialView() {
        cname.setText(pest.getCname());
        type.setText(pest.getPestClass().getCname());
        explain.setText(pest.getExplain());
        harm.setText(pest.getSpecial());
        step.setText(pest.getStep());
    }

    Pest pest;

    private void initDb() {
        PestDao pestDao = ((App) getApplicationContext()).getDaoSession().getPestDao();
        pest = pestDao.load(id);
    }

}
