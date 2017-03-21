package com.xabaizhong.treemonistor.activity.base_data;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.base.App;
import com.xabaizhong.treemonistor.entity.TreeSpecial;
import com.xabaizhong.treemonistor.entity.TreeSpecialDao;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/3/17.
 */

public class Activity_tree_base_detail extends Activity_base {
    @BindView(R.id.cname)
    TextView cname;
    @BindView(R.id.layout_name)
    LinearLayout layoutName;
    @BindView(R.id.latin)
    TextView latin;
    @BindView(R.id.layout_latin)
    LinearLayout layoutLatin;
    @BindView(R.id.family)
    TextView family;
    @BindView(R.id.layout_family)
    LinearLayout layoutFamily;
    @BindView(R.id.belong)
    TextView belong;
    @BindView(R.id.layout_belong)
    LinearLayout layoutBelong;
    @BindView(R.id.alias)
    TextView alias;
    @BindView(R.id.layout_alias)
    LinearLayout layoutAlias;
    @BindView(R.id.desc)
    TextView desc;
    @BindView(R.id.layout_desc)
    LinearLayout layoutDesc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_base_detail);
        ButterKnife.bind(this);
        initView();
    }

    long id;
    TreeSpecial treeSpecial;

    private void initView() {
        id = getIntent().getLongExtra("id", 0);
        if (id == 0)
            return;
        initDao();
        if (treeSpecial != null) {
            fillData();
        }
    }

    private void fillData() {
        String cnameStr = treeSpecial.getCname();
        String latinStr = treeSpecial.getLatin();
        String familyStr = treeSpecial.getFamily();
        String belongStr = treeSpecial.getBelong();
        String aliasStr = treeSpecial.getAlias();
        if (cnameStr.equals(""))
            layoutName.setVisibility(View.GONE);
        else
            cname.setText(cnameStr);
        if (cnameStr.equals(""))
            layoutLatin.setVisibility(View.GONE);
        else
            latin.setText(latinStr);
        if (cnameStr.equals(""))
            layoutFamily.setVisibility(View.GONE);
        else
            family.setText(familyStr);
        if (cnameStr.equals(""))
            layoutBelong.setVisibility(View.GONE);
        else
            belong.setText(belongStr);
        if (cnameStr.equals(""))
            layoutAlias.setVisibility(View.GONE);
        else
            alias.setText(aliasStr);
    }

    private void initDao() {


        TreeSpecialDao dao = ((App) getApplicationContext()).getDaoSession().getTreeSpecialDao();
        treeSpecial = dao.load(id);
    }


}
