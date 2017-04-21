package com.xabaizhong.treemonistor.activity.base_data;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.base.App;
import com.xabaizhong.treemonistor.contant.UserSharedField;
import com.xabaizhong.treemonistor.entity.TreeSpecial;
import com.xabaizhong.treemonistor.entity.TreeSpecialDao;
import com.xabaizhong.treemonistor.service.WebserviceHelper;
import com.xabaizhong.treemonistor.service.model.PicPath;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.show_pic)
    Button showPic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_base_detail);
        ButterKnife.bind(this);
        initView();
    }

    long id;
    TreeSpecial treeSpecial;
    ArrayList<String> picList = new ArrayList<>();

    private void initView() {
        id = getIntent().getLongExtra("id", 0);
        if (id == 0)
            return;
        initDao();
        if (treeSpecial != null) {
            fillData();
            showPic();
        } else {
            finish();
        }


    }

    AsyncTask asyncTask;

    private void showPic() {
        asyncTask = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    return WebserviceHelper.GetWebService(
                            "Pic", "SpeciesPicList", getParms());
                } catch (ConnectException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
                if (s == null) {
                    showToast("请求错误");
                    return;
                }
                showToast(s);
                Log.i(TAG, "onPostExecute: " + s);
                PicPath picPath = new Gson().fromJson(s, PicPath.class);
                if (picPath.getResult() != null && picPath.getResult().size() > 0) {
                    picList.addAll(picPath.getResult());
                    showPic.setVisibility(View.VISIBLE);
                }
            }
        }.execute();
    }

    private Map<String, Object> getParms() {
        Map<String, Object> map = new HashMap<>();
//        String user_id = sharedPreferences.getString(UserSharedField.USERID, "");
        /*<UserID>string</UserID>
      <leafShape>int</leafShape>
      <leafColor>int</leafColor>
      <flwerType>int</flwerType>
      <flowerColor>int</flowerColor>
      <fruitType>int</fruitType>
      <fruitColor>int</fruitColor>*/
        map.put("TreeSpeID ", treeSpecial.getTreeSpeId());
        return map;
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


    @OnClick(R.id.show_pic)
    public void onViewClicked() {
        Intent i = new Intent(this,Activity_pic_vp.class);
        i.putStringArrayListExtra("picList", picList);
        startActivity(i);
    }
}
