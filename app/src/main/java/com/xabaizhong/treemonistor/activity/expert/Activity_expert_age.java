package com.xabaizhong.treemonistor.activity.expert;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.base.App;
import com.xabaizhong.treemonistor.entity.Tree_age;
import com.xabaizhong.treemonistor.entity.Tree_ageDao;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/26 0026.
 */

public class Activity_expert_age extends Activity_base {
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.xj)
    EditText xj;
    @BindView(R.id.age)
    TextView age;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_age);
        ButterKnife.bind(this);
        initDb();
        initialView();
    }


    List<String> mList;

    private void initDb() {
        List<String> list = new ArrayList<>();

        Tree_ageDao tree_ageDao = ((App) getApplication()).getDaoSession().getTree_ageDao();
        for (Tree_age tree_age : tree_ageDao.queryBuilder().build().list()) {
            if (list.contains(tree_age.getCname())) {

            } else {
                list.add(tree_age.getCname());
            }
        }
        this.mList = list;
    }

    int mPosition = -1;

    private void initialView() {
        Adapter adapter = new Adapter();

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        xj.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                age.setText("");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() >= 2 && mPosition != -1) {
                    compute(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void compute(String s) {

        float a = Float.parseFloat(s);
        int tree_bj = (int) (a *1.0f/ 2);
        String cname = mList.get(mPosition);
        int bj = (tree_bj / 10 + 1) * 10;
        Log.i(TAG, "compute: " + s + "\t" + tree_bj + "\t" + bj);
        Tree_age treeAge = query(cname, bj);
        if (treeAge == null) {
            showToast("输入的胸径超出此工具能估测的范围。");
            age.setText("");
        } else {
            int endAge = treeAge.getTreeAgeNum() + (tree_bj > 20 ? tree_bj % 10 : tree_bj) * treeAge.getTreeAge();
            age.setText(endAge + "");
        }
    }

    private Tree_age query(String name, int bj) {
        Log.i(TAG, "query: " + name + "\t" + bj);
        if (bj == 10)
            bj = 20;
        Tree_ageDao tree_ageDao = ((App) getApplication()).getDaoSession().getTree_ageDao();
        List<Tree_age> treeList = tree_ageDao.queryBuilder().where(Tree_ageDao.Properties.Cname.eq(mList.get(mPosition)), Tree_ageDao.Properties.TreeBj.eq(bj)).build().list();
        if (treeList.size() > 0) {
            return treeList.get(0);
        } else {
            return null;
        }
    }


    class Adapter extends BaseAdapter {
        LayoutInflater inflater;

        public Adapter() {
            inflater = LayoutInflater.from(Activity_expert_age.this);
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return mList.get(position).hashCode();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Vh vh;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.simple_text, parent, false);
                vh = new Vh(convertView);
                convertView.setTag(vh);

            } else {
                vh = ((Vh) convertView.getTag());
            }

            vh.tv.setText(mList.get(position));
            return convertView;
        }

        class Vh {
            TextView tv;

            public Vh(View itemView) {
                tv = ((TextView) itemView.findViewById(R.id.text1));
            }
        }
    }
}
