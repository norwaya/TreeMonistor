package com.xabaizhong.treemonistor.activity.add_tree;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
import com.xabaizhong.treemonistor.entity.TreeDao;
import com.xabaizhong.treemonistor.entity.TreeGroupPic;
import com.xabaizhong.treemonistor.entity.TreeGroupPicDao;
import com.xabaizhong.treemonistor.entity.TreeMap;
import com.xabaizhong.treemonistor.entity.TreePic;
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
import io.reactivex.functions.Predicate;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.schedulers.Schedulers;
import terranovaproductions.newcomicreader.FloatingActionMenu;

/**
 * Created by admin on 2017/2/28.
 */

public class Activity_add_manage extends Activity_base {

    final static int LOAD_NUM = 20;
    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.fab_father)
    FloatingActionMenu fabFather;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_manager);
        ButterKnife.bind(this);
        initDaoSession();
        initView();
        registerForContextMenu(lv);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
        test();
    }


    String[] contentArray;
    Activity_add_manager_adapter adapter;

    private void initAdapter() {
        adapter = new Activity_add_manager_adapter(getApplicationContext());
    }


    private void initView() {
        initialFabMenu();
        initAdapter();
        contentArray = getResources().getStringArray(R.array.add_tree_list);
        View view = LayoutInflater.from(this).inflate(R.layout.activity_add_manager_item, null);
        initHeaderView(view);
        lv.addHeaderView(view);
        lv.setAdapter(adapter);

    }

    private void initialFabMenu() {
        fabFather.setIsCircle(false);
        fabFather.setmItemGap(20);
        fabFather.setOnMenuItemClickListener(new FloatingActionMenu.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(FloatingActionMenu floatingActionMenu, int i, FloatingActionButton floatingActionButton) {
                switch (floatingActionButton.getId()) {
                    case R.id.fab_main:
                        if (fabFather.isOpened()) {
                            fabFather.close();
                        } else {
                            fabFather.open();
                        }
                        break;
                    case R.id.tree_add:
                        startActivity(new Intent(Activity_add_manage.this, Activity_add_tree.class));
                        break;
                    case R.id.group_add:
                        startActivity(new Intent(Activity_add_manage.this, Activity_add_tree_group.class));
                        break;
                    case R.id.show_group:
                        if (mType == 1)
                            return;
                        setType(1);
                        test();
                        break;
                    case R.id.show_tree:
                        if (mType == 0)
                            return;
                        setType(0);
                        test();
                        break;
                }
            }
        });
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
    int mType = 0;

    public void setType(int type) {
        mType = type;
    }

    private void test() {
        try {
            mList = treeTypeInfoDao.queryBuilder().where(TreeTypeInfoDao.Properties.TypeId.eq(mType)).build().list();
            if (mMenu != null)
                if (mList == null || mList.size() == 0) {
                    mMenu.setGroupVisible(R.id.choose_and_upload, false);
                } else {
                    mMenu.setGroupVisible(R.id.choose_and_upload, true);
                }
            /*for (TreeTypeInfo type : mList
                    ) {
                Log.i(TAG, "test: " + type.getTreeId());

            }*/
            adapter.setSource(mList);
            Log.i(TAG, "test:++ " + mType);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "test: " + e.getMessage());
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


    Menu mMenu;

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tree_manager, menu);
        mMenu = menu;
        if (mList == null || mList.size() == 0) {
            mMenu.setGroupVisible(R.id.choose_and_upload, false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.select:
                adapter.setFlag();
                if (adapter.getFlag()) {
                    fabFather.setVisibility(View.INVISIBLE);
                    item.setTitle("取消选择");
                } else {
                    fabFather.setVisibility(View.VISIBLE);
                    item.setTitle("选择");
                }
                break;
            case R.id.all:
                if (adapter.getFlag())
                    adapter.setChooseAll(true);
                break;
            case R.id.upload:
                if (adapter.getFlag()) {
                    showProgressDialog();
                    submit();
                } else {
                    showToast("请先选择需要上传的古树/群");
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    ProgressDialog progressDialog;

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("uploading...");
        progressDialog.show();
        progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                    Log.i(TAG, "onKey: progress dialog");
                    if (mDisposable != null && !mDisposable.isDisposed()) {
                        mDisposable.dispose();
                        mDisposable = null;
                    }
                    progressDialog.dismiss();
                    return true;
                }
                return false;
            }
        });

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


    TreeTypeInfo current;
    Disposable mDisposable;

    private void submit() {
        Observable.fromIterable(mList)
                .filter(new Predicate<TreeTypeInfo>() {
                    @Override
                    public boolean test(TreeTypeInfo treeTypeInfo) throws Exception {
//                        Log.i(TAG, "test: " + adapter.getCheckItem().contains(mList.indexOf(treeTypeInfo)));
                        return adapter.getCheckItem().contains(mList.indexOf(treeTypeInfo));
                    }
                })
                .map(new Function<TreeTypeInfo, String>() {

                    @Override
                    public String apply(TreeTypeInfo treeTypeInfo) throws Exception {
                        Log.i(TAG, "apply: " + "map++");
                        Map<String, Object> params = pkg(treeTypeInfo);
                        current = treeTypeInfo;
                        Log.i(TAG, "apply: " + Thread.currentThread().getName());
                        String result = null;
                        try {
                            result = WebserviceHelper.GetWebService("UploadTreeInfo", "UploadTreeInfoMethod", params);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        if (result == null) {
                            return "-1";
                        }else{
                            return result;
                        }
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
                        if ("-1".equals(msg)) {
                            showToast(current.getTreeId() + "error");
                            return;
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
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                            progressDialog = null;
                        }
                    }

                    @Override
                    public void onComplete() {
                        test();
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                            progressDialog = null;
                        }
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

//        if (mDisposable != null && !mDisposable.isDisposed()) {
//            mDisposable.dispose();
//            mDisposable = null;
//        }
        super.onBackPressed();

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
