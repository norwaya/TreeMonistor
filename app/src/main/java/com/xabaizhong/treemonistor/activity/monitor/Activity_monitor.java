package com.xabaizhong.treemonistor.activity.monitor;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.contant.UserSharedField;
import com.xabaizhong.treemonistor.service.WebserviceHelper;
import com.xabaizhong.treemonistor.service.model.MonitorTree;
import com.xabaizhong.treemonistor.service.model.ResultMessage;
import com.xabaizhong.treemonistor.utils.FileUtil;
import com.xabaizhong.treemonistor.utils.ScaleBitmap;

import java.io.File;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.nereo.multi_image_selector.MultiImageSelector;

import static com.xabaizhong.treemonistor.activity.monitor.Activity_monitor.PicList.AllList;
import static com.xabaizhong.treemonistor.activity.monitor.Activity_monitor.PicList.EastList;
import static com.xabaizhong.treemonistor.activity.monitor.Activity_monitor.PicList.NorthList;
import static com.xabaizhong.treemonistor.activity.monitor.Activity_monitor.PicList.SouthList;
import static com.xabaizhong.treemonistor.activity.monitor.Activity_monitor.PicList.WestList;

/**
 * Created by admin on 2017/2/28.
 */

public class Activity_monitor extends Activity_base {


    @BindView(R.id.spinner_tree_list)
    Spinner spinnerTreeList;
    @BindView(R.id.rb1)
    RadioButton rb1;
    @BindView(R.id.rb2)
    RadioButton rb2;
    @BindView(R.id.layout1)
    LinearLayout layout1;
    @BindView(R.id.layout2)
    LinearLayout layout2;
    @BindView(R.id.btn)
    Button btn;
    @BindView(R.id.tv_east)
    TextView tvEast;
    @BindView(R.id.tv_west)
    TextView tvWest;
    @BindView(R.id.tv_south)
    TextView tvSouth;
    @BindView(R.id.tv_north)
    TextView tvNorth;
    @BindView(R.id.tv_all)
    TextView tvAll;
    @BindView(R.id.pb_layout)
    RelativeLayout pbLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);
        ButterKnife.bind(this);
        initView();
    }

    boolean flag;

    private void initView() {
        pbLayout.setOnClickListener(null);
        pbLayout.setVisibility(View.GONE);
        flag = true;
        rb1.setChecked(true);
        switchOn(true);
    }


    private void selectPic(ArrayList<String> list, int code, int picNum) {
        MultiImageSelector.create(getApplicationContext())
                .showCamera(true) // show camera or not. true by default
                .count(picNum) // max select image size, 9 by default. used width #.multi()
                .single() // single mode
                .multi() // multi mode, default mode;
                .origin(list) // original select data set, used width #.multi()
                .start(this, code);
    }

    private void selectPic(ArrayList<String> list, int code) {
        MultiImageSelector.create(getApplicationContext())
                .showCamera(true) // show camera or not. true by default
                .count(2) // max select image size, 9 by default. used width #.multi()
                .single() // single mode
                .multi() // multi mode, default mode;
                .origin(list) // original select data set, used width #.multi()
                .start(this, code);
    }

    @OnClick({R.id.rb1, R.id.rb2, R.id.btn})
    public void onViewClicked(View view) {
        Log.i(TAG, "onViewClicked: ");
        switch (view.getId()) {
            case R.id.rb1:
                if (rb1.isChecked()) {
                    flag = true;
                    switchOn(true);
                }
                break;
            case R.id.rb2:
                if (rb2.isChecked()) {
                    flag = false;
                    switchOn(false);
                }
                break;
            case R.id.btn:
                if (check()) {
                    fillMonitor();
                    pbLayout.setVisibility(View.VISIBLE);
                    upload();
                }


                break;
        }
    }

    MonitorTree monitorTree = new MonitorTree();
    String json;

    private boolean check() {
        if (hasImage(PicList.AllList) &&
                hasImage(PicList.EastList) &&
                hasImage(PicList.WestList) &&
                hasImage(PicList.SouthList) &&
                hasImage(PicList.NorthList)) {
            showToast("亲，您没有添加图片呦-_-");
            return false;
        }
        int position = spinnerTreeList.getSelectedItemPosition();
        //// TODO: 2017/4/21 0021  填入treeid
        monitorTree.setTreeID("123123123");
        return true;
    }

    private boolean hasImage(List list) {
        return (list == null || list.size() == 0);
    }

    private void fillMonitor() {
        monitorTree.setDate(getStringDate());
        monitorTree.setUserID(sharedPreferences.getString(UserSharedField.USERID, ""));
    }

    private void upload() {

        Observer<Object> observer = new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object value) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {


                uploadInfo();
            }
        };

        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                FileUtil.clearFileDir();
                scaleImages();
                fillImages();

                e.onComplete();
                Log.i(TAG, "subscribe: over");
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    private void fillImages() {
        List<MonitorTree.PicinfoBean> list = new ArrayList<>();

        MonitorTree.PicinfoBean picInfoBean;
        if (flag) {
            ArrayList<String> eastPicList = new ArrayList<>();
            ArrayList<String> westPicList = new ArrayList<>();
            ArrayList<String> southPicList = new ArrayList<>();
            ArrayList<String> northtPicList = new ArrayList<>();

            List<File> files = FileUtil.getFiles();
            for (File f : files) {
                if (f.getName().matches("east[\\d].[\\w]+")) {
                    Log.i(TAG, "east: " + f.getName());
//                    eastPicList.add("images");
                    eastPicList.add(FileUtil.bitmapToBase64Str(f));
                } else if (f.getName().matches("west[\\d].[\\w]+")) {
//                    westPicList.add("images");
                    westPicList.add(FileUtil.bitmapToBase64Str(f));
                    Log.i(TAG, "west: " + f.getName());
                } else if (f.getName().matches("south[\\d].[\\w]+")) {
//                    southPicList.add("images");
                    southPicList.add(FileUtil.bitmapToBase64Str(f));
                    Log.i(TAG, "south: " + f.getName());
                } else if (f.getName().matches("north[\\d].[\\w]+")) {
//                    northtPicList.add("images");
                    northtPicList.add(FileUtil.bitmapToBase64Str(f));
                    Log.i(TAG, "north: " + f.getName());
                }
            }
            //east
            picInfoBean = new MonitorTree.PicinfoBean();
            picInfoBean.setPicPlace(1);
            picInfoBean.setPiclist(eastPicList);
            picInfoBean.setExplain("dong");
            list.add(picInfoBean);

            //west
            picInfoBean = new MonitorTree.PicinfoBean();
            picInfoBean.setPicPlace(3);
            picInfoBean.setExplain("dong");
            picInfoBean.setPiclist(westPicList);
            list.add(picInfoBean);
            //south
            picInfoBean = new MonitorTree.PicinfoBean();
            picInfoBean.setPicPlace(2);
            picInfoBean.setExplain("dong");
            picInfoBean.setPiclist(southPicList);
            list.add(picInfoBean);
            //north
            picInfoBean = new MonitorTree.PicinfoBean();
            picInfoBean.setPicPlace(4);
            picInfoBean.setExplain("dong");
            picInfoBean.setPiclist(northtPicList);
            list.add(picInfoBean);
        } else {
            ArrayList<String> allPicList = new ArrayList<>();

            List<File> files = FileUtil.getFiles();
            for (File f : files) {
                if (f.getName().matches("all[\\d].[\\w]+")) {
                    Log.i(TAG, "east: " + f.getName());
//                    allPicList.add("image");
                    allPicList.add(FileUtil.bitmapToBase64Str(f));
                }
            }
            //east
            picInfoBean = new MonitorTree.PicinfoBean();
            picInfoBean.setPicPlace(0);
            picInfoBean.setPiclist(allPicList);
            picInfoBean.setExplain("dong");
            list.add(picInfoBean);
        }
        monitorTree.setPicinfo(list);
        json = new Gson().toJson(monitorTree);

    }

    private void scaleImages() {
        if (flag) {
            if (PicList.EastList != null) {
                for (int i = 0; i < PicList.EastList.size(); i++) {
                    ScaleBitmap.getBitmap(PicList.EastList.get(i), "east" + i + ".png");
                }
            }
            if (PicList.WestList != null) {
                for (int i = 0; i < PicList.WestList.size(); i++) {
                    ScaleBitmap.getBitmap(PicList.WestList.get(i), "west" + i + ".png");
                }
            }
            if (PicList.SouthList != null) {
                for (int i = 0; i < PicList.SouthList.size(); i++) {
                    ScaleBitmap.getBitmap(PicList.SouthList.get(i), "south" + i + ".png");
                }
            }
            if (PicList.NorthList != null) {
                for (int i = 0; i < PicList.NorthList.size(); i++) {
                    ScaleBitmap.getBitmap(PicList.NorthList.get(i), "north" + i + ".png");
                }
            }
        } else {
            if (PicList.AllList != null) {
                for (int i = 0; i < PicList.AllList.size(); i++) {
                    ScaleBitmap.getBitmap(PicList.AllList.get(i), "all" + i + ".png");
                }
            }
        }
    }


    AsyncTask asyncTask;

    private void uploadInfo() {
        asyncTask = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    return WebserviceHelper.GetWebService(
                            "UploadTreeInfo", "UploadTreeInfoMethod", getParms());
                } catch (ConnectException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
                if (s == null) {
                    showToast("请求错误,请检查网络是否正常");
                    return;
                }
                Log.i(TAG, "onPostExecute: "+s);
                pbLayout.setVisibility(View.GONE);
                ResultMessage resultMessage = new Gson().fromJson(s, ResultMessage.class);
                if (resultMessage.getError_code() == 0) {
                    showToast(resultMessage.getMessage());
                    finish();
                }else{
                    showToast(resultMessage.getMessage());
                }
            }
        }.execute();
    }


    private Map<String, Object> getParms() {
        Map<String, Object> map = new HashMap<>();
        String user_id = sharedPreferences.getString(UserSharedField.USERID, "");

        map.put("UserID ", user_id);
        map.put("TreeType", 1);
        map.put("JsonStr", json);
        return map;
    }

    /**
     * @param open true if select first button
     */
    private void switchOn(boolean open) {
        if (open) {
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.GONE);
        } else {
            layout1.setVisibility(View.GONE);
            layout2.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.east, R.id.west, R.id.south, R.id.north, R.id.all})
    public void onLayoutClicked(View view) {
        switch (view.getId()) {
            case R.id.east:
                selectPic(PicList.EastList, RquestCode.EASTCODE);
                break;
            case R.id.west:
                selectPic(PicList.WestList, RquestCode.WESTCDOE);
                break;
            case R.id.south:
                selectPic(PicList.SouthList, RquestCode.SOUTHCODE);
                break;
            case R.id.north:
                selectPic(PicList.NorthList, RquestCode.NORTHCODE);
                break;
            case R.id.all:
                selectPic(AllList, RquestCode.ALLCODE, 5);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        Log.i(TAG, "onActivityResult: " + requestCode + "\t" + resultCode + "\t" + data.hasExtra("select_result"));
        switch (requestCode) {
            case RquestCode.ALLCODE:
                AllList = data.getStringArrayListExtra("select_result");
                tvAll.setText(AllList == null ? "0" : AllList.size() + "");
                break;
            case RquestCode.EASTCODE:
                EastList = data.getStringArrayListExtra("select_result");
                tvEast.setText(EastList == null ? "0" : EastList.size() + "");
                break;
            case RquestCode.WESTCDOE:
                WestList = data.getStringArrayListExtra("select_result");
                tvWest.setText(WestList == null ? "0" : WestList.size() + "");
                break;
            case RquestCode.SOUTHCODE:
                SouthList = data.getStringArrayListExtra("select_result");
                tvSouth.setText(SouthList == null ? "0" : SouthList.size() + "");
                break;
            case RquestCode.NORTHCODE:
                NorthList = data.getStringArrayListExtra("select_result");
                tvNorth.setText(NorthList == null ? "0" : NorthList.size() + "");
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static class PicList {
        public static ArrayList<String> EastList = new ArrayList<>();
        public static ArrayList<String> WestList = new ArrayList<>();
        public static ArrayList<String> SouthList = new ArrayList<>();
        public static ArrayList<String> NorthList = new ArrayList<>();
        public static ArrayList<String> AllList = new ArrayList<>();

    }

    public interface RquestCode {
        int EASTCODE = 540;
        int WESTCDOE = 809;
        int SOUTHCODE = 684;
        int NORTHCODE = 32;
        int ALLCODE = 458;
    }
}
