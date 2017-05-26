package com.xabaizhong.treemonistor.activity.base_data;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.activity.query_treeOrGroup.Activity_tree_detailInfo;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.base.App;
import com.xabaizhong.treemonistor.entity.TreeSpecial;
import com.xabaizhong.treemonistor.entity.TreeSpecialDao;
import com.xabaizhong.treemonistor.myview.C_info_gather_item1;
import com.xabaizhong.treemonistor.service.WebserviceHelper;
import com.xabaizhong.treemonistor.service.model.PicPath;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 2017/3/17.
 */

public class Activity_tree_base_detail extends Activity_base {

    @BindView(R.id.cname)
    C_info_gather_item1 cname;
    @BindView(R.id.latin)
    C_info_gather_item1 latin;
    @BindView(R.id.family)
    C_info_gather_item1 family;
    @BindView(R.id.belong)
    C_info_gather_item1 belong;
    @BindView(R.id.alias)
    C_info_gather_item1 alias;
    @BindView(R.id.explain)
    C_info_gather_item1 explain;
    @BindView(R.id.pic)
    C_info_gather_item1 pic;

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
        pic.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                if (pic.getText().equals("0")) {
                    return;
                }
                Intent i = new Intent(Activity_tree_base_detail.this, Activity_pic_vp.class);
                i.putStringArrayListExtra("picList", picList);
                startActivity(i);
            }
        });
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
                            "Pic", "GetTreeSpeInfoPic", getParms());
                } catch (ConnectException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
                Log.i(TAG, "onPostExecute: " + s);
                if (s == null) {
                    return;
                }
                PicPath picPath = new Gson().fromJson(s, PicPath.class);
                if (picPath.getResult() != null && picPath.getResult().size() > 0) {
                    picList.addAll(picPath.getResult());
                    pic.setText(picList.size() + "");
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
        map.put("TreeSpeID", treeSpecial.getTreeSpeId());
        return map;
    }

    private void fillData() {
        String cnameStr = treeSpecial.getCname();
        String latinStr = treeSpecial.getLatin();
        String familyStr = treeSpecial.getFamily();
        String belongStr = treeSpecial.getBelong();
        String aliasStr = treeSpecial.getAlias();
        cname.setText(cnameStr);
        latin.setText(latinStr);
        family.setText(familyStr);
        belong.setText(belongStr);
        alias.setText(aliasStr);
        pic.setText("0");
    }

    private void initDao() {
        TreeSpecialDao dao = ((App) getApplicationContext()).getDaoSession().getTreeSpecialDao();
        treeSpecial = dao.load(id);
    }
}
