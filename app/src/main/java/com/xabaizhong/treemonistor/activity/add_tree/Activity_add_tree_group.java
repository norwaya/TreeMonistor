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
import com.xabaizhong.treemonistor.base.App;
import com.xabaizhong.treemonistor.contant.UserSharedField;
import com.xabaizhong.treemonistor.entity.DaoSession;
import com.xabaizhong.treemonistor.entity.TreeGroupPic;
import com.xabaizhong.treemonistor.entity.TreeGroupPicDao;
import com.xabaizhong.treemonistor.entity.TreePic;
import com.xabaizhong.treemonistor.entity.TreeGroup;
import com.xabaizhong.treemonistor.entity.TreeGroupDao;
import com.xabaizhong.treemonistor.entity.TreeMap;
import com.xabaizhong.treemonistor.entity.TreeMapDao;
import com.xabaizhong.treemonistor.entity.TreeSpecial;
import com.xabaizhong.treemonistor.entity.TreeTypeInfo;
import com.xabaizhong.treemonistor.entity.TreeTypeInfoDao;
import com.xabaizhong.treemonistor.myview.DateDialog;
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
        initialDao();
        initClickListener();
        init();


    }

    private static final int CNAME_CODE = 831;
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
        mainTreeName.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                startActivityForResult(new Intent(Activity_add_tree_group.this,Activity_tree_cname.class),CNAME_CODE);
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

    public static final int REQUEST_CODE_TREESPECIES = 0x105;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Result_Code.REQUEST_IMAGE:
                if (resultCode == RESULT_OK) {
                    // Get the result mList of select image paths
                    list = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    // do your logic ....
                    explain.setText(list.size() + "");
                }
            case Result_Code.REQUEST_CODE_REGION:
                if (resultCode == 100) {
                    Activity_map.LocationBox box = data.getParcelableExtra("location");
                    if (box != null) {
                        
                        treeGroup.setPlaceName(box.getStreet() + box.getSematicDescription());
                        treeGroup.setRegion(box.getProvince() + box.getCity() + box.getDistrict());

                        region.setText(treeGroup.getRegion());
                        placeName.setText(treeGroup.getPlaceName());
                    }
                }

                break;
            case CNAME_CODE:
                TreeSpecial tree = data.getParcelableExtra("special");
                if (tree != null) {
                    mainTreeName.setText(tree.getCname());

                    treeGroup.setMainTreeName(tree.getCname());
                    treeGroup.setAimsBelong(tree.getBelong());
                    treeGroup.setAimsTree(tree.getCname());
                    treeGroup.setAimsFamily(tree.getFamily());
                }
                break;

            case REQUEST_CODE_TREESPECIES:
                TreeSpecial treeSpecial = data.getParcelableExtra("special");
                if (treeSpecial != null) {
                    treeMap.setMapViewValue(treeSpecial);
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.submit)
    public void onClick() {
        String checkStr = check();
        if (checkStr == null) {
            fillData();
            saveDao();
            showToast("保存成功");
            startActivity(new Intent(this,Activity_add_tree_group.class));
            finish();
        } else {
            showToast(checkStr);
        }
    }

    private void saveDao() {
        treeGroupDao.save(treeGroup);
        long id = treeGroup.getId();
        treeTypeInfo.setTreeGroup_id(id);
        treeTypeInfoDao.save(treeTypeInfo);

        TreeGroupPic pic;
        if (list != null) {
            for (String path : list) {
                pic = new TreeGroupPic();
                pic.setTree_id(id);
                pic.setPath(path);
                picDao.save(pic);
            }
        }

        List<Map<String, Object>> maps = treeMap.getTreeMap();
        TreeMap treeMap;
        for (Map<String, Object> map : maps) {
            treeMap = new TreeMap(null, id, map.get("name").toString(), Integer.parseInt(map.get("num").toString()));
            treeMapDao.save(treeMap);
        }
    }

    TreeTypeInfoDao treeTypeInfoDao;
    TreeGroupDao treeGroupDao;
    TreeGroupPicDao picDao;
    TreeMapDao treeMapDao;
    private void initialDao() {
        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        treeTypeInfoDao = daoSession.getTreeTypeInfoDao();
        treeGroupDao = daoSession.getTreeGroupDao();
        picDao = daoSession.getTreeGroupPicDao();
        treeMapDao = daoSession.getTreeMapDao();
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
                layoutPb.setVisibility(View.INVISIBLE);
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
                    showToast("请求出现错误，连接服务器失败");
                    return;
                }
                Observable.just(s)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Consumer<String>() {
                                       @Override
                                       public void accept(String s) throws Exception {
                                           ResultMessage msg = new Gson().fromJson(s, ResultMessage.class);
                                           Log.i(TAG, "onPostExecute: " + msg.getMessage());
                                           if (msg.getError_code() == 0) {
                                               showToast("suc");
                                           } else {
                                               showToast(msg.getMessage());
                                           }
                                       }
                                   },
                                new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        showToast("解析失败");
                                    }
                                });

            }
        }.execute();
    }

    private Map<String, Object> getParms() {
        Map<String, Object> map = new HashMap<>();
        String user_id = sharedPreferences.getString(UserSharedField.USERID, "");
        map.put("UserID ", user_id);
        map.put("TreeType", 0);
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
        if (!treeId.getText().matches("\\d{8}")) {
            return "古树群编号位 8 位数字（6位地区编号+2位序号）";
        }
        if (researchDate.getText().equals("")) {
            return "选择调查日期";
        }
        if (region.getText().equals("")) {
            return "请选择地理信息";
        }
        if (!evevation.getText().matches("^\\d+[^\\d]+\\d+$")) {
            return "海拔格式错误";
        }
        if (gSTreeNum.getText().equals("")) {
            return "请填写古树数量";
        }
        if (mainTreeName.getText().equals("")) {
            return "请填写主要树种";
        }
        if (szjx.getText().equals("")) {
            return "请填写四至界限";
        }
        if (yBDInfo.getText().equals("")) {
            return "请填写郁闭度";
        }
        if (xiaMuDensity.getText().equals("")) {
            return "请填写下木密度";
        }
        if (xiaMuType.getText().equals("")) {
            return "请填写下木种类";
        }
        if (dBWDensity.getText().equals("")) {
            return "请填写地被物密度";
        }
        if (dBWType.getText().equals("")) {
            return "请填写地被物种类";
        }
        if (slope.getText().equals("")) {
            return "请选择坡度";
        }
        if (aspect.getText().equals("")) {
            return "请选择坡向";
        }
        if (averageAge.getText().equals("")) {
            return "请填写平均树龄";
        }
        if (averageDiameter.getText().equals("")) {
            return "请填写平均胸径";
        }
        if (averageHeight.getText().equals("")) {
            return "请填写平均树高";
        }
        if (soil.getText().equals("")) {
            return "请选择土壤种类";
        }
        if (soilHeight.getText().equals("")) {
            return "请填写土壤厚度";
        }
        if (area.getText().equals("")) {
            return "请填写面积";
        }
        if (managementUnit.getText().equals("")) {
            return "请填写管护单位";
        }
        return null;
    }

    String json;

    private void fillData() {


        treeTypeInfo.setTypeId(1);
//        treeTypeInfo.setRecoredPerson(researchPersion.getText());
        treeTypeInfo.setTreeId(treeId.getText());
        initialElvation();
        treeGroup.setMainTreeName(mainTreeName.getText());
        treeGroup.setSZJX(szjx.getText());
        treeGroup.setXiaMuType(xiaMuType.getText());
        treeGroup.setDBWType(dBWType.getText());
        treeGroup.setManagementUnit(managementUnit.getText());
        treeGroup.setManagementState(managementState.getText());
        treeGroup.setRWJYInfo(rWJYInfo.getText());
        treeGroup.setSuggest(suggest.getText());
        treeTypeInfo.setTreeGroup(treeGroup);
        treeTypeInfo.setAreaId(areaId());
        treeGroup.setUserID(userId());
        treeGroup.setTreeId(treeId.getText());
        treeGroup.setGSTreeNum(Double.parseDouble(gSTreeNum.getText()));
        treeGroup.setYBDInfo(Double.parseDouble(yBDInfo.getText()));
        treeGroup.setXiaMuDensity(Double.parseDouble(xiaMuDensity.getText()));
        treeGroup.setDBWDensity(Double.parseDouble(dBWDensity.getText()));
        treeGroup.setAverageAge(Double.parseDouble(averageAge.getText()));
        treeGroup.setAverageDiameter(Double.parseDouble(averageDiameter.getText()));
        treeGroup.setAverageHeight(Double.parseDouble(averageHeight.getText()));
        treeGroup.setArea(Double.parseDouble(area.getText()));
    }

    private void initialElvation() {
        treeGroup.setEvevation(evevation.getText().replaceFirst("[^\\d]+",":"));
    }

    private String userId() {
        return sharedPreferences.getString(UserSharedField.USERID, "");
    }

    private String areaId() {
        return sharedPreferences.getString(UserSharedField.AREAID, "");
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
        DateDialog dateDialog = new DateDialog(this);
        dateDialog.setDateDialogListener(new DateDialog.DateDialogListener() {
            @Override
            public void getDate(Date date) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                researchDate.setText(format.format(date));
                treeTypeInfo.setDate(date);
                treeGroup.setDate(date);
            }
        });
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
