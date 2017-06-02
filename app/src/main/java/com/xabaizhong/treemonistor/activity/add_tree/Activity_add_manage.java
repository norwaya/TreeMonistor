package com.xabaizhong.treemonistor.activity.add_tree;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.adapter.Activity_add_manager_adapter;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.base.App;
import com.xabaizhong.treemonistor.contant.UserSharedField;
import com.xabaizhong.treemonistor.entity.DaoSession;
import com.xabaizhong.treemonistor.entity.TreeGroup;
import com.xabaizhong.treemonistor.entity.TreeGroupDao;
import com.xabaizhong.treemonistor.entity.TreeGroupPic;
import com.xabaizhong.treemonistor.entity.TreeGroupPicDao;
import com.xabaizhong.treemonistor.entity.TreeMap;
import com.xabaizhong.treemonistor.entity.TreePic;
import com.xabaizhong.treemonistor.entity.TreeDao;
import com.xabaizhong.treemonistor.entity.TreePicDao;
import com.xabaizhong.treemonistor.entity.TreeTypeInfo;
import com.xabaizhong.treemonistor.entity.TreeTypeInfoDao;
import com.xabaizhong.treemonistor.service.WebserviceHelper;
import com.xabaizhong.treemonistor.service.model.ResultMessage;
import com.xabaizhong.treemonistor.utils.FileUtil;
import com.xabaizhong.treemonistor.utils.ScaleBitmap;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by admin on 2017/2/28.
 */

public class Activity_add_manage extends Activity_base {

    final static int LOAD_NUM = 20;
    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.layout_pb)
    RelativeLayout layoutPb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_manager);
        ButterKnife.bind(this);
        initView();
        registerForContextMenu(lv);
    }


    int size;

    @Override
    protected void onStart() {
        super.onStart();
        test();
    }


    String[] contentArray;
    Activity_add_manager_adapter adapter;

    private void initAdapter() {
        adapter = new Activity_add_manager_adapter(getApplicationContext());
    }


    private void initView() {
        layoutPb.setOnClickListener(null);
        layoutPb.setVisibility(View.INVISIBLE);
        initAdapter();
        contentArray = getResources().getStringArray(R.array.add_tree_list);
        View view = LayoutInflater.from(this).inflate(R.layout.activity_add_manager_item, null);
        initHeaderView(view);
        lv.addHeaderView(view);
        lv.setAdapter(adapter);

    }

    private void initHeaderView(View view) {
        TextView tv1 = ((TextView) view.findViewById(R.id.tv1));
        TextView tv2 = ((TextView) view.findViewById(R.id.tv2));
        TextView tv3 = ((TextView) view.findViewById(R.id.tv3));
        tv1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv1.setText("古树[群]编号");
        tv2.setText("类型");
        tv3.setText("时间");
    }

    List<TreeTypeInfo> mList;

    private void test() {
        initDaoSession();
        try {
            mList = treeTypeInfoDao.queryBuilder().build().list();
            if (mList == null || mList.size() == 0) {
                btnSubmit.setVisibility(View.GONE);
            } else {
                btnSubmit.setVisibility(View.VISIBLE);
            }
            /*for (TreeTypeInfo type : mList
                    ) {
                Log.i(TAG, "test: " + type.getTreeId());

            }*/
            adapter.setSource(mList);

        } catch (Exception e) {

        }
    }

    TreeTypeInfoDao treeTypeInfoDao;
    TreeDao treeDao;
    TreePicDao picDao;
    TreeGroupPicDao treeGroupPicDao;

    private void initDaoSession() {
        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        treeTypeInfoDao = daoSession.getTreeTypeInfoDao();
        treeDao = daoSession.getTreeDao();
        picDao = daoSession.getTreePicDao();
        treeGroupPicDao = daoSession.getTreeGroupPicDao();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final int index = ContextMenu.FIRST;

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Object obj = adapter.getItem(info.position - 1);
        TreeTypeInfo treeTypeInfo = ((TreeTypeInfo) obj);
        long id = treeTypeInfo.getId();
        int type = treeTypeInfo.getTypeId();
        switch (item.getItemId()) {
            case index + 1:
                delBean(id, treeTypeInfo.getTreeId());
                break;
            case index + 2:
                if (type == 1) {
                    showToast("古树群不支持修改操作");
                } else {
                    updateBean(id);
                }
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void updateBean(long id) {
        Intent i = new Intent(this, Activity_add_tree.class);
        i.putExtra("id", id);
        startActivity(i);
    }

    private void delBean(final Long id, String code) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示").setMessage("确定要删除编号为" + code + "的数据?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        treeTypeInfoDao.load(id).del();
                        test();
                    }
                })
                .setNegativeButton("取消", null);
        builder.setCancelable(true);
        builder.create().show();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("操作");
        menu.add(0, ContextMenu.FIRST + 1, 0, "删除");
        menu.add(0, ContextMenu.FIRST + 2, 0, "查看并修改");
        super.onCreateContextMenu(menu, v, menuInfo);
    }


    @OnClick({R.id.btn_tree, R.id.btn_treeGroup, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_tree:
                startActivity(new Intent(this, Activity_add_tree.class));
                break;
            case R.id.btn_treeGroup:
                startActivity(new Intent(this, Activity_add_tree_group.class));
                break;
            case R.id.btn_submit:

                submit();
                break;
        }
    }

    TreeTypeInfo current;
    Disposable mDisposable;

    private void submit() {
        int v = layoutPb.getVisibility();
        if (v == View.VISIBLE) {
            Log.i(TAG, "submit: it is works");
            return;
        }
        layoutPb.setVisibility(View.VISIBLE);
        Observable.fromIterable(mList)
                .map(new Function<TreeTypeInfo, String>() {

                    @Override
                    public String apply(TreeTypeInfo treeTypeInfo) throws Exception {
                        Map<String, Object> params = pkg(treeTypeInfo);
                        current = treeTypeInfo;
                        Log.i(TAG, "apply: " + Thread.currentThread().getName());
                        String result = WebserviceHelper.GetWebService("UploadTreeInfo", "UploadTreeInfoMethod", params);
                        return result;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;

                    }

                    @Override
                    public void onNext(String msg) {
                        Log.i(TAG, "onNext: " + msg);
                        if (msg == null) {
                            showToast(current.getTreeId() + "error");
                        }
                        ResultMessage rm = new Gson().fromJson(msg, ResultMessage.class);
                        if (rm.getError_code() == 0) {
                            showToast("编号为 " + current.getTreeId() + " 古树[群]完成上传");
                            treeTypeInfoDao.load(current.getId()).del();
                        } else {
                            showToast(current.getTreeId() + " -> " + rm.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        showToast(current.getTreeId() + "error");
                    }

                    @Override
                    public void onComplete() {
                        test();
                        layoutPb.setVisibility(View.INVISIBLE);
                    }
                });


    }

    private Map<String, Object> pkg(TreeTypeInfo treeTypeInfo) {
        Map<String, Object> map = new HashMap<>();
        doPics(treeTypeInfo);
        String user_id = sharedPreferences.getString(UserSharedField.USERID, "");
        String area_id = sharedPreferences.getString(UserSharedField.AREAID, "");
        if (treeTypeInfo.getTypeId() == 1) {
            List<TreeMap> treeMaps = treeTypeInfo.getTreeGroup().getTreeMaps();
            List<Map<String, Object>> treeMap = new ArrayList<>();
            Map<String, Object> maps;
            for (TreeMap item : treeMaps) {
                maps = new HashMap<>();
                treeMap.add(maps);
                maps.put("name", item.getName());
                maps.put("num", item.getNum());

            }
            treeTypeInfo.getTreeGroup().setTreeMap(treeMap);
        }
        String json = new GsonBuilder().setDateFormat("yyyy-MM-dd").excludeFieldsWithoutExposeAnnotation().create().toJson(treeTypeInfo);
        map.put("UserID", user_id);
        map.put("TreeType", 0);
        map.put("JsonStr", json);
        map.put("AreaID", area_id);
        return map;
    }

    @Override
    public void onBackPressed() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            layoutPb.setVisibility(View.INVISIBLE);
            mDisposable.dispose();
            mDisposable = null;
        } else {
            super.onBackPressed();
        }
    }

    private void doPics(TreeTypeInfo treeTypeInfo) {
        int type = treeTypeInfo.getTypeId();
        List<TreeGroupPic> picList;
        List<TreePic> picList2;
        if (type == 0) {
            picList2 = treeTypeInfo.getTree().getPics();
            treeTypeInfo.getTree().setPiclist(picToBast64String(picList2));
        }
        if (type == 1) {
            picList = treeTypeInfo.getTreeGroup().getPics();
            treeTypeInfo.getTreeGroup().setPicList(picToBast64String2(picList));
        }
    }

    private List<String> picToBast64String(List<TreePic> picList) {
        List<String> stringList = new ArrayList<>();
        FileUtil.clearFileDir();
        if (picList != null) {
            for (int i = 0; i < picList.size(); i++) {
                ScaleBitmap.getBitmap(picList.get(i).getPath(), "image" + i + ".png");
            }
        }

        try {
            for (File file : FileUtil.getFiles()) {
                if (file.getName().endsWith(".png")) {
                    stringList.add(FileUtil.bitmapToBase64Str(file));
                }
            }
        } catch (RuntimeException e) {
            Log.i(TAG, "picToBast64String: " + e.getMessage());
        }

        return stringList;
    }

    private List<String> picToBast64String2(List<TreeGroupPic> picList) {
        List<String> stringList = new ArrayList<>();
        FileUtil.clearFileDir();
        if (picList != null) {
            for (int i = 0; i < picList.size(); i++) {
                ScaleBitmap.getBitmap(picList.get(i).getPath(), "image" + i + ".png");
            }
        }

        try {
            for (File file : FileUtil.getFiles()) {
                if (file.getName().endsWith(".png")) {
                    stringList.add(FileUtil.bitmapToBase64Str(file));
                }
            }
        } catch (RuntimeException e) {
            Log.i(TAG, "picToBast64String: " + e.getMessage());
        }

        return stringList;
    }
}
