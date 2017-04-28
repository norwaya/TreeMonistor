package com.xabaizhong.treemonistor.activity.add_tree;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.contant.UserSharedField;
import com.xabaizhong.treemonistor.entity.TreeGroup;
import com.xabaizhong.treemonistor.entity.TreeTypeInfo;
import com.xabaizhong.treemonistor.myview.C_dialog_date;
import com.xabaizhong.treemonistor.myview.C_dialog_radio;
import com.xabaizhong.treemonistor.myview.C_info_gather_item1;
import com.xabaizhong.treemonistor.myview.DynamicView;
import com.xabaizhong.treemonistor.service.WebserviceHelper;
import com.xabaizhong.treemonistor.service.model.ResultMessage;
import com.xabaizhong.treemonistor.utils.FileUtil;
import com.xabaizhong.treemonistor.utils.MessageEvent;
import com.xabaizhong.treemonistor.utils.RxBus;
import com.xabaizhong.treemonistor.utils.ScaleBitmap;

import java.io.File;
import java.net.ConnectException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;


/**
 * Created by admin on 2017/2/28.
 */

public class Activity_add_tree_group extends Activity_base {
    private static final int REQUEST_IMAGE = 0x100;
    ArrayList<String> list;
    Disposable disposable;
    @BindView(R.id.tree_id)
    C_info_gather_item1 treeId;
    @BindView(R.id.research_persion)
    C_info_gather_item1 researchPersion;
    @BindView(R.id.research_date)
    C_info_gather_item1 researchDate;
    @BindView(R.id.region)
    C_info_gather_item1 region;
    @BindView(R.id.placeName)
    C_info_gather_item1 placeName;
    @BindView(R.id.evevation)
    C_info_gather_item1 evevation;
    @BindView(R.id.gSTreeNum)
    C_info_gather_item1 gSTreeNum;
    @BindView(R.id.mainTreeName)
    C_info_gather_item1 mainTreeName;
    @BindView(R.id.szjx)
    C_info_gather_item1 szjx;
    @BindView(R.id.averageAge)
    C_info_gather_item1 averageAge;
    @BindView(R.id.yBDInfo)
    C_info_gather_item1 yBDInfo;
    @BindView(R.id.xiaMuDensity)
    C_info_gather_item1 xiaMuDensity;
    @BindView(R.id.xiaMuType)
    C_info_gather_item1 xiaMuType;
    @BindView(R.id.dBWDensity)
    C_info_gather_item1 dBWDensity;
    @BindView(R.id.dBWType)
    C_info_gather_item1 dBWType;
    @BindView(R.id.slope)
    C_info_gather_item1 slope;
    @BindView(R.id.aspect)
    C_info_gather_item1 aspect;
    @BindView(R.id.averageDiameter)
    C_info_gather_item1 averageDiameter;
    @BindView(R.id.averageHeight)
    C_info_gather_item1 averageHeight;
    @BindView(R.id.soil)
    C_info_gather_item1 soil;
    @BindView(R.id.soilHeight)
    C_info_gather_item1 soilHeight;
    @BindView(R.id.area)
    C_info_gather_item1 area;
    @BindView(R.id.managementUnit)
    C_info_gather_item1 managementUnit;
    @BindView(R.id.managementState)
    C_info_gather_item1 managementState;
    @BindView(R.id.rWJYInfo)
    C_info_gather_item1 rWJYInfo;
    @BindView(R.id.suggest)
    C_info_gather_item1 suggest;
    @BindView(R.id.explain)
    C_info_gather_item1 explain;
    @BindView(R.id.layout)
    CoordinatorLayout layout;


    TreeGroup treeGroup = new TreeGroup();
    TreeTypeInfo treeTypeInfo = new TreeTypeInfo();
    @BindView(R.id.tree_map)
    DynamicView treeMap;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.layout_pb)
    RelativeLayout layoutPb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);
        ButterKnife.bind(this);
        initClickListener();
        init();


    }

    private void initClickListener() {
        researchDate.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                showDateDialog();
            }
        });
        region.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                startActivityForResult(new Intent(Activity_add_tree_group.this, Activity_map.class), Result_Code.REQUEST_CODE_REGION);
            }
        });
        aspect.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                showRadioDialog(Result_Code.REQUEST_CODE_ASPECT);
            }
        });
        slope.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                showRadioDialog(Result_Code.REQUEST_CODE_SLOPE);
            }
        });
        soil.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                showRadioDialog(Result_Code.REQUEST_CODE_SOIL);
            }
        });
        explain.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                selectPhoto();
            }
        });
    }

    String[] array;

    public void showRadioDialog(int Request) {
        switch (Request) {
            case Result_Code.REQUEST_CODE_ASPECT:
                array = getResources().getStringArray(R.array.aspect);
                new C_dialog_radio(this, "坡向", Arrays.asList(array), Result_Code.REQUEST_CODE_ASPECT);
                break;
            case Result_Code.REQUEST_CODE_SLOPE:
                array = getResources().getStringArray(R.array.slope);
                new C_dialog_radio(this, "坡度", Arrays.asList(array), Result_Code.REQUEST_CODE_SLOPE);
                break;
            case Result_Code.REQUEST_CODE_SOIL:
                array = getResources().getStringArray(R.array.soil);
                new C_dialog_radio(this, "土壤", Arrays.asList(array), Result_Code.REQUEST_CODE_SOIL);
                break;
        }
    }

    private void init() {
        layoutPb.setOnClickListener(null);
        layoutPb.setVisibility(View.INVISIBLE);
    }

    interface Result_Code {
        int REQUEST_IMAGE = 0x101;
        int REQUEST_CODE_REGION = 0x102;
        int REQUEST_CODE_ASPECT = 0x103;
        int REQUEST_CODE_SLOPE = 0x104;
        int REQUEST_CODE_SOIL = 0x105;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Result_Code.REQUEST_IMAGE:
                if (resultCode == RESULT_OK) {
                    // Get the result list of select image paths
                    list = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    // do your logic ....
                    explain.setText(list.size() + "");
                }
            case Result_Code.REQUEST_CODE_REGION:
                if (resultCode == 100) {
                    Activity_map.LocationBox box = data.getParcelableExtra("location");
                    if (box != null) {
                        region.setText(box.getProvince() + box.getCity() + box.getDistrict());
                        placeName.setText(box.getStreet() + box.getSematicDescription());
                        treeGroup.setPlaceName(box.getStreet() + box.getSematicDescription());

                    }
                }

                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.submit)
    public void onClick() {
        try {
            fillData();
        } catch (Exception e) {

        }
        if (check() == null) {
            layoutPb.setVisibility(View.VISIBLE);
            upload();
        } else {

        }
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
                treeGroup.setPicList(fillPic());
                json = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(treeTypeInfo);
                Log.i(TAG, "fillData: " + json);
                submitTreeInfo();
            }
        };

        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                FileUtil.clearFileDir();
                if (list != null)
                    for (int i = 0; i < list.size(); i++) {
                        ScaleBitmap.getBitmap(list.get(i), "image" + i + ".png");
                    }
                e.onComplete();
                Log.i(TAG, "subscribe: over");
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);

    }

    AsyncTask asyncTask;

    private void submitTreeInfo() {
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
                layoutPb.setVisibility(View.INVISIBLE);
                if (s == null) {
                    Log.i(TAG, "onPostExecute: error");
                    return;
                }
                ResultMessage msg = new Gson().fromJson(s, ResultMessage.class);
                Log.i(TAG, "onPostExecute: " + msg.getMessage());
                if (msg.getError_code() == 0) {
                    showToast("suc");
                }
            }
        }.execute();
    }


    private Map<String, Object> getParms() {
        Map<String, Object> map = new HashMap<>();
        String user_id = sharedPreferences.getString(UserSharedField.USERID, "");
        map.put("UserID ", user_id);
        map.put("TreeType", "0");
        map.put("JsonStr", json);
        return map;
    }


    private List<String> fillPic() {
        List<String> list = new ArrayList<>();
        for (File file : FileUtil.getFiles()) {
            if (!file.getName().equals(".nomedia")) {
                list.add(FileUtil.bitmapToBase64Str(file));
            }

        }
        return list;
    }

    private String check() {
        return null;
    }

    String json;

    private void fillData() {
        treeTypeInfo.setTypeId(1);
//        treeTypeInfo.setRecoredPerson(researchPersion.getText());
        treeTypeInfo.setTreeId(treeId.getText());
        treeGroup.setEvevation(evevation.getText());
        treeGroup.setMainTreeName(mainTreeName.getText());
        treeGroup.setTreeMap(treeMap.getTreeMap());
        treeGroup.setSZJX(szjx.getText());
        treeGroup.setXiaMuType(xiaMuType.getText());
        treeGroup.setdBWType(dBWType.getText());
        treeGroup.setManagementUnit(managementUnit.getText());
        treeGroup.setManagementState(managementState.getText());
        treeGroup.setrWJYInfo(rWJYInfo.getText());
        treeGroup.setSuggest(suggest.getText());
        treeTypeInfo.setTreeGroup(treeGroup);
        treeTypeInfo.setAreaId("610322");
        treeGroup.setUserID(userId());
        treeGroup.setTreeId(sharedPreferences.getString(UserSharedField.USERID, ""));
        treeGroup.setgSTreeNum(Double.parseDouble(gSTreeNum.getText()));
        treeGroup.setyBDInfo(Double.parseDouble(yBDInfo.getText()));
        treeGroup.setXiaMuDensity(Double.parseDouble(xiaMuDensity.getText()));
        treeGroup.setdBWDensity(Double.parseDouble(dBWDensity.getText()));
        treeGroup.setAverageAge(Double.parseDouble(averageAge.getText()));
        treeGroup.setAverageDiameter(Double.parseDouble(averageDiameter.getText()));
        treeGroup.setAverageHeight(Double.parseDouble(averageHeight.getText()));
        treeGroup.setArea(Double.parseDouble(area.getText()));
    }

    private String userId() {
        return sharedPreferences.getString(UserSharedField.USERID, "");
    }

    private void selectPhoto() {
        MultiImageSelector.create(getApplicationContext())
                .showCamera(true) // show camera or not. true by default
                .count(4) // max select image size, 9 by default. used width #.multi()
                .single() // single mode
                .multi() // multi mode, default mode;
                .origin(list) // original select data set, used width #.multi()
                .start(this, Result_Code.REQUEST_IMAGE);
    }

    public void showDateDialog() {
        C_dialog_date dateDialog = new C_dialog_date(this);
        dateDialog.setDateDialogListener(new C_dialog_date.DateDialogListener() {
            @Override
            public void getDate(Date date) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                researchDate.setText(format.format(date));
                treeTypeInfo.setDate(date);
                treeGroup.setDate(date);
            }
        });
        dateDialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        register();
    }

    private void register() {

        disposable = RxBus.getDefault().toObservable(MessageEvent.class).subscribe(new Consumer<MessageEvent>() {
            @Override
            public void accept(MessageEvent messageEvent) throws Exception {
                switch (messageEvent.getCode()) {
                    case Result_Code.REQUEST_CODE_ASPECT:
                        aspect.setText(array[messageEvent.getId()]);
                        treeGroup.setAspect((messageEvent.getId()) + "");
                        break;
                    case Result_Code.REQUEST_CODE_SLOPE:
                        slope.setText(array[messageEvent.getId()]);
                        treeGroup.setSlope((messageEvent.getId()));
                        break;
                    case Result_Code.REQUEST_CODE_SOIL:
                        soil.setText(array[messageEvent.getId()]);
                        treeGroup.setSoilName((messageEvent.getId()) + "");
                        break;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (disposable != null && !disposable.isDisposed())
            disposable.dispose();
        super.onDestroy();
    }
}
