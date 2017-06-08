package com.xabaizhong.treemonistor.activity.monitor;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.contant.UserSharedField;
import com.xabaizhong.treemonistor.myview.ProgressDialogUtil;
import com.xabaizhong.treemonistor.service.AsyncTaskRequest;
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

import static com.xabaizhong.treemonistor.activity.monitor.Activity_monitor.PicList;

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
        initSource();
        ButterKnife.bind(this);
        initView();
    }


    PicList picList;

    private void initSource() {
        picList = new PicList();
        Map<String, Object> map = new HashMap<>();
        map.put("UserID", sharedPreferences.getString(UserSharedField.USERID, ""));

        AsyncTaskRequest.instance("CheckUp", "QueryTreeIDList_ImportantTree")
                .setCallBack(new AsyncTaskRequest.CallBack() {
                    @Override
                    public void execute(String s) {
                        Log.d(TAG, "execute: " + s);
                        if (s == null) {
                            return;
                        }
                        ImportTreeList importTreeList = new Gson().fromJson(s, ImportTreeList.class);
                        if (importTreeList.getErrorCode() == 0) {
                            if (importTreeList.getResult().size() == 0) {
                                showToast("没有分配可以监管的古树");
                                finish();
                            } else {
                                initSpinner(importTreeList.getResult());
                            }
                        } else {
                            showToast("没有分配可以监管的古树");
                            finish();
                        }
                    }
                }).setParams(map).create();
    }

    boolean flag;

    private void initSpinner(List<String> list) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        spinnerTreeList.setAdapter(arrayAdapter);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    private void initView() {
        pbLayout.setOnClickListener(null);
        pbLayout.setVisibility(View.INVISIBLE);
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

    DialogInterface mDialog;

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
                    mDialog = ProgressDialogUtil.getInstance(this).initial("UPLOADING...",
                            new ProgressDialogUtil.CallBackListener() {
                                @Override
                                public void callBack(DialogInterface dialog) {
                                    if (disposable != null) {
                                        disposable.dispose();
                                    }
                                    Activity_monitor.this.finish();
                                }
                            });
                    upload();
                }


                break;
        }
    }

    MonitorTree monitorTree = new MonitorTree();
    String json;

    private boolean check() {
        if (hasImage(picList.AllList) &&
                hasImage(picList.EastList) &&
                hasImage(picList.WestList) &&
                hasImage(picList.SouthList) &&
                hasImage(picList.NorthList)) {
            showToast("您没有添加图片呦");
            return false;
        }
        int position = spinnerTreeList.getSelectedItemPosition();
        monitorTree.setTreeID(spinnerTreeList.getSelectedItem().toString().trim());
        return true;
    }

    private boolean hasImage(List list) {
        return (list == null || list.size() == 0);
    }

    private void fillMonitor() {
        monitorTree.setDate(getStringDate());
        monitorTree.setUserID(sharedPreferences.getString(UserSharedField.USERID, ""));
    }

    Disposable disposable;

    private void upload() {


        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {

                String result = null;
                try {
                    FileUtil.clearFileDir();
                    scaleImages();
                    fillImages();
                    result = WebserviceHelper.GetWebService(
                            "UploadTreeInfo", "UploadTreeInfoMethod", getParams());
                    Log.i(TAG, "subscribe: end up;oad+++++++++++++++++++++++");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                if (result == null) {
                    e.onError(new RuntimeException("返回为空"));
                } else {
                    e.onNext(result);
                }
                e.onComplete();
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());

        observable.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(String value) {
                ResultMessage resultMessage = new Gson().fromJson(value, ResultMessage.class);
                if (resultMessage.getError_code() == 0) {
                    showToast(resultMessage.getMessage());
                    if (mDialog != null) {
                        mDialog.dismiss();
                    }
                } else {
                    showToast(resultMessage.getMessage());
                    mDialog.cancel();
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
//                        pbLayout.setVisibility(View.GONE);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (disposable != null) {
            disposable.dispose();
        }
        super.onBackPressed();


    }

    private void fillImages() {
        List<MonitorTree.PicinfoBean> list = new ArrayList<>();

        MonitorTree.PicinfoBean picInfoBean;
        if (flag) {
            ArrayList<String> eastPicList = new ArrayList<>();
            ArrayList<String> westPicList = new ArrayList<>();
            ArrayList<String> southPicList = new ArrayList<>();
            ArrayList<String> northPicList = new ArrayList<>();

            List<File> files = FileUtil.getFiles();
            for (File f : files) {
                if (f.getName().matches("east[\\d].[\\w]+")) {
                    Log.i(TAG, "east: " + f.getName());
                    eastPicList.add(FileUtil.bitmapToBase64Str(f));
                } else if (f.getName().matches("west[\\d].[\\w]+")) {
                    westPicList.add(FileUtil.bitmapToBase64Str(f));
                    Log.i(TAG, "west: " + f.getName());
                } else if (f.getName().matches("south[\\d].[\\w]+")) {
                    southPicList.add(FileUtil.bitmapToBase64Str(f));
                    Log.i(TAG, "south: " + f.getName());
                } else if (f.getName().matches("north[\\d].[\\w]+")) {
                    northPicList.add(FileUtil.bitmapToBase64Str(f));
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
            picInfoBean.setPiclist(northPicList);
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
        Log.i(TAG, "scaleImages: start scale images");
        if (flag) {
            if (picList.EastList != null) {
                for (int i = 0; i < picList.EastList.size(); i++) {
                    ScaleBitmap.getBitmap(picList.EastList.get(i), "east" + i + ".png");
                }
            }
            if (picList.WestList != null) {
                for (int i = 0; i < picList.WestList.size(); i++) {
                    ScaleBitmap.getBitmap(picList.WestList.get(i), "west" + i + ".png");
                }
            }
            if (picList.SouthList != null) {
                for (int i = 0; i < picList.SouthList.size(); i++) {
                    ScaleBitmap.getBitmap(picList.SouthList.get(i), "south" + i + ".png");
                }
            }
            if (picList.NorthList != null) {
                for (int i = 0; i < picList.NorthList.size(); i++) {
                    ScaleBitmap.getBitmap(picList.NorthList.get(i), "north" + i + ".png");
                }
            }
        } else {
            if (picList.AllList != null) {
                for (int i = 0; i < picList.AllList.size(); i++) {
                    ScaleBitmap.getBitmap(picList.AllList.get(i), "all" + i + ".png");
                }
            }
        }
    }


    private Map<String, Object> getParams() {
        Map<String, Object> map = new HashMap<>();
        String user_id = sharedPreferences.getString(UserSharedField.USERID, "");
        String area_id = sharedPreferences.getString(UserSharedField.AREAID, "");

        map.put("UserID ", user_id);
        map.put("TreeType", 1);
        map.put("JsonStr", json);
        map.put("AreaID", area_id);
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
                selectPic(picList.EastList, RquestCode.EASTCODE);
                break;
            case R.id.west:
                selectPic(picList.WestList, RquestCode.WESTCDOE);
                break;
            case R.id.south:
                selectPic(picList.SouthList, RquestCode.SOUTHCODE);
                break;
            case R.id.north:
                selectPic(picList.NorthList, RquestCode.NORTHCODE);
                break;
            case R.id.all:
                selectPic(picList.AllList, RquestCode.ALLCODE, 5);
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
                picList.AllList = data.getStringArrayListExtra("select_result");
                tvAll.setText(picList.AllList == null ? "0" : picList.AllList.size() + "");
                break;
            case RquestCode.EASTCODE:
                picList.EastList = data.getStringArrayListExtra("select_result");
                tvEast.setText(picList.EastList == null ? "0" : picList.EastList.size() + "");
                break;
            case RquestCode.WESTCDOE:
                picList.WestList = data.getStringArrayListExtra("select_result");
                tvWest.setText(picList.WestList == null ? "0" : picList.WestList.size() + "");
                break;
            case RquestCode.SOUTHCODE:
                picList.SouthList = data.getStringArrayListExtra("select_result");
                tvSouth.setText(picList.SouthList == null ? "0" : picList.SouthList.size() + "");
                break;
            case RquestCode.NORTHCODE:
                picList.NorthList = data.getStringArrayListExtra("select_result");
                tvNorth.setText(picList.NorthList == null ? "0" : picList.NorthList.size() + "");
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static class PicList {
        public ArrayList<String> EastList = new ArrayList<>();
        public ArrayList<String> WestList = new ArrayList<>();
        public ArrayList<String> SouthList = new ArrayList<>();
        public ArrayList<String> NorthList = new ArrayList<>();
        public ArrayList<String> AllList = new ArrayList<>();

    }

    public interface RquestCode {
        int EASTCODE = 540;
        int WESTCDOE = 809;
        int SOUTHCODE = 684;
        int NORTHCODE = 32;
        int ALLCODE = 458;
    }

    public static class ImportTreeList {

        /**
         * message : sus
         * error_code : 0
         * result : ["61011100229","61011100109","61011101019","61011100015","61011100019","61011100029","61011100039"]
         */

        @SerializedName("message")
        private String message;
        @SerializedName("error_code")
        private int errorCode;
        @SerializedName("result")
        private List<String> result;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(int errorCode) {
            this.errorCode = errorCode;
        }

        public List<String> getResult() {
            return result;
        }

        public void setResult(List<String> result) {
            this.result = result;
        }
    }
}
