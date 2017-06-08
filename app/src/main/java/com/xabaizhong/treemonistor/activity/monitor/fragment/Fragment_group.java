package com.xabaizhong.treemonistor.activity.monitor.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.activity.add_tree.Activity_add_tree_group;
import com.xabaizhong.treemonistor.activity.add_tree.Activity_map;
import com.xabaizhong.treemonistor.activity.add_tree.Activity_tree_cname;
import com.xabaizhong.treemonistor.activity.base_data.Activity_pic_vp;
import com.xabaizhong.treemonistor.activity.monitor.Imonitor;
import com.xabaizhong.treemonistor.base.Fragment_base;
import com.xabaizhong.treemonistor.contant.UserSharedField;
import com.xabaizhong.treemonistor.entity.TreeGroup;
import com.xabaizhong.treemonistor.entity.TreeSpecial;
import com.xabaizhong.treemonistor.entity.TreeTypeInfo;
import com.xabaizhong.treemonistor.myview.C_dialog_radio;
import com.xabaizhong.treemonistor.myview.C_info_gather_item1;
import com.xabaizhong.treemonistor.myview.DateDialog;
import com.xabaizhong.treemonistor.myview.DynamicView;
import com.xabaizhong.treemonistor.myview.ProgressDialogUtil;
import com.xabaizhong.treemonistor.service.WebserviceHelper;
import com.xabaizhong.treemonistor.utils.MessageEvent;
import com.xabaizhong.treemonistor.utils.RxBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

import static android.app.Activity.RESULT_OK;
import static com.xabaizhong.treemonistor.activity.add_tree.Activity_add_tree_group.REQUEST_CODE_TREESPECIES;
import static com.xabaizhong.treemonistor.activity.monitor.fragment.Fragment_group.Result_Code.CNAME_CODE;
import static com.xabaizhong.treemonistor.activity.monitor.fragment.Fragment_tree.ResultCode.REQUEST_IMAGE;

/**
 * Created by Administrator on 2017/6/7 0007.
 */

public class Fragment_group extends Fragment_base implements Imonitor {
    String mTreeId;
    ViewHolder mViewHolder;

    public static Fragment_group instance(String treeId) {
        Fragment_group fragment = new Fragment_group();
        fragment.mTreeId = treeId;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tree_group_layout, container, false);
        mViewHolder = new ViewHolder(view);
        initObject();
        initialViewWithData();
        return view;
    }

    TreeTypeInfo treeTypeInfo;
    TreeGroup group;

    private void initObject() {
        treeTypeInfo = new TreeTypeInfo();
        group = new TreeGroup();
        treeTypeInfo.setTreeId(mTreeId);
        group.setTreeId(mTreeId);
        treeTypeInfo.setTreeGroup(group);
        treeTypeInfo.setTypeId(1);
    }

    Disposable disposable;
    Fragment_group.Result resultMessage;

    private void initialViewWithData() {

        final DialogInterface dialog = ProgressDialogUtil.getInstance(getActivity()).initial("loading...", new ProgressDialogUtil.CallBackListener() {
            @Override
            public void callBack(DialogInterface dialog) {

            }
        });

        final Map<String, Object> map = new HashMap<>();
        map.put("UserID", sharedPreferences.getString(UserSharedField.USERID, ""));
        map.put("TreeType", 1);
        map.put("TreeID", mTreeId);
        map.put("AreaID", sharedPreferences.getString(UserSharedField.AREAID, ""));

        Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                String result = null;
                try {
                    result = WebserviceHelper.GetWebService(
                            "DataQuerySys", "TreeDelInfo", map);
                } catch (Exception ex) {
                    e.onError(ex);
                }
                if (result == null) {
                    e.onError(new RuntimeException("返回为空"));
                } else {
                    e.onNext(result);
                }
                e.onComplete();
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(String value) {
                        Log.i(TAG, "onNext: " + value);
                        resultMessage = new Gson().fromJson(value, Result.class);
                        if (resultMessage.getErrorCode() == 0) {
                            initBean();
                            fillData();
                        } else {
                            showToast(resultMessage.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        dialog.dismiss();
                        disposable = null;
                        initCallBack();
                        register();
                    }
                });

    }


    private void initCallBack() {
        mViewHolder.researchDate.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                showDateDialog();
            }
        });
        mViewHolder.region.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                startActivityForResult(new Intent(getActivity(), Activity_map.class), Fragment_group.Result_Code.REQUEST_CODE_REGION);
            }
        });
        mViewHolder.mainTreeName.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                startActivityForResult(new Intent(getActivity(), Activity_tree_cname.class), CNAME_CODE);
            }
        });
        mViewHolder.aspect.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                showRadioDialog(Fragment_group.Result_Code.REQUEST_CODE_ASPECT);
            }
        });
        mViewHolder.slope.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                showRadioDialog(Fragment_group.Result_Code.REQUEST_CODE_SLOPE);
            }
        });
        mViewHolder.soil.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                showRadioDialog(Fragment_group.Result_Code.REQUEST_CODE_SOIL);
            }
        });
        mViewHolder.picNew.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                selectPic();
            }
        });
    }

    private void fillData() {
        Result.ResultBean bean = resultMessage.getResult();
        mViewHolder.researchDate.setText(bean.RecordTime.substring(0, 10));
        mViewHolder.region.setText(getAreaName(resultMessage.areaid));
        mViewHolder.placeName.setText(bean.PlaceName);
        mViewHolder.evevation.setText(bean.Evevation);
        mViewHolder.gSTreeNum.setText(bean.GSTreeNum + "");
        mViewHolder.mainTreeName.setText(bean.MainTreeName);
        fillTreeMap();
        mViewHolder.szjx.setText(bean.SZJX);
        mViewHolder.yBDInfo.setText(bean.YBDInfo + "");
        mViewHolder.xiaMuDensity.setText(bean.XiaMuDensity);
        mViewHolder.xiaMuType.setText(bean.XiaMuType);
        mViewHolder.dBWDensity.setText(bean.DBWDensity);
        mViewHolder.dBWType.setText(bean.DBWType);
        String slope = bean.Slope;
        if (slope.matches("\\d+")) {
            slope = getActivity().getResources().getStringArray(R.array.slope)[Integer.parseInt(slope)];
        }
        mViewHolder.slope.setText(slope);
        String aspect = bean.Aspect;
        if (aspect.matches("\\d+")) {
            aspect = getActivity().getResources().getStringArray(R.array.aspect)[Integer.parseInt(aspect)];
        }
        mViewHolder.aspect.setText(aspect);
        mViewHolder.averageAge.setText(bean.getAverageAge() + "");
        mViewHolder.averageDiameter.setText(bean.AverageDiameter + "");
        mViewHolder.averageHeight.setText(bean.AverageHeight + "");
        String soil = bean.SoilName;
        if (soil.matches("\\d+")) {
            soil = getActivity().getResources().getStringArray(R.array.soil)[Integer.parseInt(soil)];
        }
        mViewHolder.soil.setText(soil);
        mViewHolder.soilHeight.setText(bean.SoilHeight + "");
        mViewHolder.area.setText(bean.Area + "");
        mViewHolder.managementUnit.setText(bean.ManagementUnit);
        mViewHolder.managementState.setText(bean.ManagementState);
        mViewHolder.rWJYInfo.setText(bean.RWJYInfo);
        mViewHolder.suggest.setText(bean.Suggest);
        initialOldPics();
    }

    private void initialOldPics() {
        final List<String> picInfo = resultMessage.getResult().getPicInfo();
        if (picInfo != null && picInfo.size() > 0) {
            mViewHolder.pic.setText(picInfo.size() + "");
            mViewHolder.pic.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
                @Override
                public void onClickListener(View et) {
                    ArrayList<String> picList = (ArrayList<String>) picInfo;
                    Intent i = new Intent(getActivity(), Activity_pic_vp.class);
                    i.putStringArrayListExtra("picList", picList);
                    startActivity(i);
                }
            });
        } else {
            mViewHolder.pic.setText("0");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Result_Code.REQUEST_IMAGE:
                if (resultCode == RESULT_OK) {
                    // Get the result mList of select image paths
                    mList = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    // do your logic ....
                    mViewHolder.picNew.setText(mList.size() + "");
                }
            case Result_Code.REQUEST_CODE_REGION:
                if (resultCode == 100) {
                    Activity_map.LocationBox box = data.getParcelableExtra("location");
                    if (box != null) {

                        group.setPlaceName(box.getStreet() + box.getSematicDescription());
                        group.setRegion(box.getProvince() + box.getCity() + box.getDistrict());

                        mViewHolder.region.setText(group.getRegion());
                        mViewHolder.placeName.setText(group.getPlaceName());
                    }
                }

                break;
            case Result_Code.CNAME_CODE:
                TreeSpecial tree = data.getParcelableExtra("special");
                if (tree != null) {
                    mViewHolder.mainTreeName.setText(tree.getCname());
                    group.setMainTreeName(tree.getCname());
                    group.setAimsBelong(tree.getBelong());
                    group.setAimsTree(tree.getCname());
                    group.setAimsFamily(tree.getFamily());
                }
                break;

            case REQUEST_CODE_TREESPECIES:
                TreeSpecial treeSpecial = data.getParcelableExtra("special");
                if (treeSpecial != null) {
                    mViewHolder.treeMap.setMapViewValue(treeSpecial);
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void fillTreeMap() {
        List<Result.ResultBean.TreeMapBean> list = resultMessage.getResult().getTreeMap();
        List<Map<String, String>> pkgList = new ArrayList<>();
        Map<String, String> map;
        for (Result.ResultBean.TreeMapBean treeMap : list
                ) {
            map = new HashMap<>();
            map.put("name", treeMap.TreeSpeID);
            map.put("num", treeMap.TreeNum);
            pkgList.add(map);
        }
        mViewHolder.treeMap.initWithData(pkgList);
    }

    public void showDateDialog() {
        DateDialog dateDialog = new DateDialog(getActivity());
        dateDialog.setDateDialogListener(new DateDialog.DateDialogListener() {
            @Override
            public void getDate(Date date) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
                mViewHolder.researchDate.setText(format.format(date));
                treeTypeInfo.setDate(date);
                group.setDate(date);
            }
        });
    }

    String[] array;

    public void showRadioDialog(int Request) {
        switch (Request) {
            case Fragment_group.Result_Code.REQUEST_CODE_ASPECT:
                array = getResources().getStringArray(R.array.aspect);
                new C_dialog_radio(getActivity(), "坡向", Arrays.asList(array), Fragment_group.Result_Code.REQUEST_CODE_ASPECT);
                break;
            case Fragment_group.Result_Code.REQUEST_CODE_SLOPE:
                array = getResources().getStringArray(R.array.slope);
                new C_dialog_radio(getActivity(), "坡度", Arrays.asList(array), Fragment_group.Result_Code.REQUEST_CODE_SLOPE);
                break;
            case Fragment_group.Result_Code.REQUEST_CODE_SOIL:
                array = getResources().getStringArray(R.array.soil);
                new C_dialog_radio(getActivity(), "土壤", Arrays.asList(array), Fragment_group.Result_Code.REQUEST_CODE_SOIL);
                break;
        }
    }

    private void register() {
        disposable = RxBus.getDefault().toObservable(MessageEvent.class).subscribe(new Consumer<MessageEvent>() {
            @Override
            public void accept(MessageEvent messageEvent) throws Exception {
                switch (messageEvent.getCode()) {
                    case Fragment_group.Result_Code.REQUEST_CODE_ASPECT:
                        mViewHolder.aspect.setText(array[messageEvent.getId()]);
                        group.setAspect((messageEvent.getId()) + "");
                        break;
                    case Fragment_group.Result_Code.REQUEST_CODE_SLOPE:
                        mViewHolder.slope.setText(array[messageEvent.getId()]);
                        group.setSlope((messageEvent.getId()));
                        break;
                    case Fragment_group.Result_Code.REQUEST_CODE_SOIL:
                        mViewHolder.soil.setText(array[messageEvent.getId()]);
                        group.setSoilName((messageEvent.getId()) + "");
                        break;
                }
            }
        });
    }

    ArrayList<String> mList = new ArrayList<>();

    private void selectPic() {
        Intent intent = new Intent(getActivity(), MultiImageSelectorActivity.class);
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 5);
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
        intent.putStringArrayListExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mList);
        startActivityForResult(intent,Result_Code.REQUEST_IMAGE );
    }


    private void initBean() {
        Result.ResultBean bean = resultMessage.getResult();
        Date date = fromStrDate(bean.RecordTime);
        treeTypeInfo.setDate(date);
        group.setDate(date);
        treeTypeInfo.setAreaId(resultMessage.areaid);

        String slope = bean.Slope;
        if (slope.matches("\\d+.\\d+")) {
            group.setSlope(Integer.parseInt(slope));
        }
        group.setAspect(bean.Aspect);
        String soil = bean.SoilName;
        if (soil.matches("\\d+.\\d+")) {
            group.setSlope(Double.parseDouble(soil));
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        disposable.dispose();
    }

    @Override
    public String check() {
        if (mViewHolder.researchDate.getText().equals("")) {
            return "选择调查日期";
        }
        if (mViewHolder.region.getText().equals("")) {
            return "请选择地理信息";
        }
        if (!mViewHolder.evevation.getText().matches("^\\d+[^\\d]+\\d+$")) {
            return "海拔格式错误";
        }
        if (mViewHolder.gSTreeNum.getText().equals("")) {
            return "请填写古树数量";
        }
        if (mViewHolder.mainTreeName.getText().equals("")) {
            return "请填写主要树种";
        }
        if (mViewHolder.szjx.getText().equals("")) {
            return "请填写四至界限";
        }
        if (mViewHolder.yBDInfo.getText().equals("")) {
            return "请填写郁闭度";
        }
        if (mViewHolder.xiaMuDensity.getText().equals("")) {
            return "请填写下木密度";
        }
        if (mViewHolder.xiaMuType.getText().equals("")) {
            return "请填写下木种类";
        }
        if (mViewHolder.dBWDensity.getText().equals("")) {
            return "请填写地被物密度";
        }
        if (mViewHolder.dBWType.getText().equals("")) {
            return "请填写地被物种类";
        }
        if (mViewHolder.slope.getText().equals("")) {
            return "请选择坡度";
        }
        if (mViewHolder.aspect.getText().equals("")) {
            return "请选择坡向";
        }
        if (mViewHolder.averageAge.getText().equals("")) {
            return "请填写平均树龄";
        }
        if (mViewHolder.averageDiameter.getText().equals("")) {
            return "请填写平均胸径";
        }
        if (mViewHolder.averageHeight.getText().equals("")) {
            return "请填写平均树高";
        }
        if (mViewHolder.soil.getText().equals("")) {
            return "请选择土壤种类";
        }
        if (mViewHolder.soilHeight.getText().equals("")) {
            return "请填写土壤厚度";
        }
        if (mViewHolder.area.getText().equals("")) {
            return "请填写面积";
        }
        if (mViewHolder.managementUnit.getText().equals("")) {
            return "请填写管护单位";
        }
        if (mList == null || mList.size() == 0) {
            return "请选择图片";
        }
        return null;
    }

    @Override
    public String getJsonStr() {
        picToString();
        fzBean();
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat("yyyy-MM-dd").create().toJson(treeTypeInfo);

    }

    private void fzBean() {
        initialElvation();
        group.setMainTreeName(mViewHolder.mainTreeName.getText());
        group.setSZJX(mViewHolder.szjx.getText());
        group.setXiaMuType(mViewHolder.xiaMuType.getText());
        group.setDBWType(mViewHolder.dBWType.getText());
        group.setManagementUnit(mViewHolder.managementUnit.getText());
        group.setManagementState(mViewHolder.managementState.getText());
        group.setRWJYInfo(mViewHolder.rWJYInfo.getText());
        group.setSuggest(mViewHolder.suggest.getText());
        group.setGSTreeNum(Double.parseDouble(mViewHolder.gSTreeNum.getText()));
        group.setYBDInfo(Double.parseDouble(mViewHolder.yBDInfo.getText()));
        group.setXiaMuDensity(Double.parseDouble(mViewHolder.xiaMuDensity.getText()));
        group.setDBWDensity(Double.parseDouble(mViewHolder.dBWDensity.getText()));
        group.setAverageAge(Double.parseDouble(mViewHolder.averageAge.getText()));
        group.setAverageDiameter(Double.parseDouble(mViewHolder.averageDiameter.getText()));
        group.setAverageHeight(Double.parseDouble(mViewHolder.averageHeight.getText()));
        group.setArea(Double.parseDouble(mViewHolder.area.getText()));
        group.setTreeMap(mViewHolder.treeMap.getTreeMap());
    }

    private void initialElvation() {
    }

    private void picToString() {

    }

    interface Result_Code {
        int REQUEST_IMAGE = 0x101;
        int REQUEST_CODE_REGION = 0x102;
        int REQUEST_CODE_ASPECT = 0x103;
        int REQUEST_CODE_SLOPE = 0x104;
        int REQUEST_CODE_SOIL = 0x105;


        int CNAME_CODE = 0x1237;
    }

    static class ViewHolder {
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
        @BindView(R.id.tree_map)
        DynamicView treeMap;
        @BindView(R.id.szjx)
        C_info_gather_item1 szjx;
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
        @BindView(R.id.averageAge)
        C_info_gather_item1 averageAge;
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
        @BindView(R.id.pic)
        C_info_gather_item1 pic;
        @BindView(R.id.pic_new)
        C_info_gather_item1 picNew;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class Result {


        /**
         * message : sus
         * error_code : 0
         * treeid : 61010201
         * areaid : 610102
         * userid : 610102001
         * treetype : 1
         * result : {"TreeListID":"61010201","PlaceName":"索菲特人民大厦　","MainTreeName":"油橄榄　","TreeSpelNum":"1","SZJX":"西安索菲特人民大厦后楼东西两侧","Area":0,"GSTreeNum":18,"AverageHeight":4.5,"AverageDiameter":35,"AverageAge":55,"YBDInfo":0,"Evevation":"410:410","Aspect":"0","Slope":"0","SoilName":"0","SoilHeight":0,"XiaMuType":"(NULL)","XiaMuDensity":"0","DBWType":"(NULL)","DBWDensity":"0","ManagementUnit":"西安索菲特人民大厦","ManagementState":"根深叶茂","AimsTree":"油橄榄","AimsFamily":"木犀科","AimsBelong":"木犀榄属","RWJYInfo":"根深叶茂，树冠完整，主干通直，无伤痕","Suggest":"(NULL)","Explain":null,"RecordPerson":"李缠生","RecordTime":"2016-06-27 00:00:00","UserID":"610102001","TreeMap":[{"TreeSpeID":"T00494","TreeNum":"18"}],"picInfo":[]}
         */

        @SerializedName("message")
        private String message;
        @SerializedName("error_code")
        private int errorCode;
        @SerializedName("treeid")
        private String treeid;
        @SerializedName("areaid")
        private String areaid;
        @SerializedName("userid")
        private String userid;
        @SerializedName("treetype")
        private int treetype;
        @SerializedName("result")
        private ResultBean result;

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

        public String getTreeid() {
            return treeid;
        }

        public void setTreeid(String treeid) {
            this.treeid = treeid;
        }

        public String getAreaid() {
            return areaid;
        }

        public void setAreaid(String areaid) {
            this.areaid = areaid;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public int getTreetype() {
            return treetype;
        }

        public void setTreetype(int treetype) {
            this.treetype = treetype;
        }

        public ResultBean getResult() {
            return result;
        }

        public void setResult(ResultBean result) {
            this.result = result;
        }

        public static class ResultBean {
            /**
             * TreeListID : 61010201
             * PlaceName : 索菲特人民大厦
             * MainTreeName : 油橄榄
             * TreeSpelNum : 1
             * SZJX : 西安索菲特人民大厦后楼东西两侧
             * Area : 0
             * GSTreeNum : 18
             * AverageHeight : 4.5
             * AverageDiameter : 35
             * AverageAge : 55
             * YBDInfo : 0
             * Evevation : 410:410
             * Aspect : 0
             * Slope : 0
             * SoilName : 0
             * SoilHeight : 0
             * XiaMuType : (NULL)
             * XiaMuDensity : 0
             * DBWType : (NULL)
             * DBWDensity : 0
             * ManagementUnit : 西安索菲特人民大厦
             * ManagementState : 根深叶茂
             * AimsTree : 油橄榄
             * AimsFamily : 木犀科
             * AimsBelong : 木犀榄属
             * RWJYInfo : 根深叶茂，树冠完整，主干通直，无伤痕
             * Suggest : (NULL)
             * Explain : null
             * RecordPerson : 李缠生
             * RecordTime : 2016-06-27 00:00:00
             * UserID : 610102001
             * TreeMap : [{"TreeSpeID":"T00494","TreeNum":"18"}]
             * picInfo : []
             */

            @SerializedName("TreeListID")
            private String TreeListID;
            @SerializedName("PlaceName")
            private String PlaceName;
            @SerializedName("MainTreeName")
            private String MainTreeName;
            @SerializedName("TreeSpelNum")
            private String TreeSpelNum;
            @SerializedName("SZJX")
            private String SZJX;
            @SerializedName("Area")
            private int Area;
            @SerializedName("GSTreeNum")
            private int GSTreeNum;
            @SerializedName("AverageHeight")
            private double AverageHeight;
            @SerializedName("AverageDiameter")
            private int AverageDiameter;
            @SerializedName("AverageAge")
            private int AverageAge;
            @SerializedName("YBDInfo")
            private int YBDInfo;
            @SerializedName("Evevation")
            private String Evevation;
            @SerializedName("Aspect")
            private String Aspect;
            @SerializedName("Slope")
            private String Slope;
            @SerializedName("SoilName")
            private String SoilName;
            @SerializedName("SoilHeight")
            private int SoilHeight;
            @SerializedName("XiaMuType")
            private String XiaMuType;
            @SerializedName("XiaMuDensity")
            private String XiaMuDensity;
            @SerializedName("DBWType")
            private String DBWType;
            @SerializedName("DBWDensity")
            private String DBWDensity;
            @SerializedName("ManagementUnit")
            private String ManagementUnit;
            @SerializedName("ManagementState")
            private String ManagementState;
            @SerializedName("AimsTree")
            private String AimsTree;
            @SerializedName("AimsFamily")
            private String AimsFamily;
            @SerializedName("AimsBelong")
            private String AimsBelong;
            @SerializedName("RWJYInfo")
            private String RWJYInfo;
            @SerializedName("Suggest")
            private String Suggest;
            @SerializedName("Explain")
            private Object Explain;
            @SerializedName("RecordPerson")
            private String RecordPerson;
            @SerializedName("RecordTime")
            private String RecordTime;
            @SerializedName("UserID")
            private String UserID;
            @SerializedName("TreeMap")
            private List<TreeMapBean> TreeMap;
            @SerializedName("picInfo")
            private List<String> picInfo;

            public String getTreeListID() {
                return TreeListID;
            }

            public void setTreeListID(String TreeListID) {
                this.TreeListID = TreeListID;
            }

            public String getPlaceName() {
                return PlaceName;
            }

            public void setPlaceName(String PlaceName) {
                this.PlaceName = PlaceName;
            }

            public String getMainTreeName() {
                return MainTreeName;
            }

            public void setMainTreeName(String MainTreeName) {
                this.MainTreeName = MainTreeName;
            }

            public String getTreeSpelNum() {
                return TreeSpelNum;
            }

            public void setTreeSpelNum(String TreeSpelNum) {
                this.TreeSpelNum = TreeSpelNum;
            }

            public String getSZJX() {
                return SZJX;
            }

            public void setSZJX(String SZJX) {
                this.SZJX = SZJX;
            }

            public int getArea() {
                return Area;
            }

            public void setArea(int Area) {
                this.Area = Area;
            }

            public int getGSTreeNum() {
                return GSTreeNum;
            }

            public void setGSTreeNum(int GSTreeNum) {
                this.GSTreeNum = GSTreeNum;
            }

            public double getAverageHeight() {
                return AverageHeight;
            }

            public void setAverageHeight(double AverageHeight) {
                this.AverageHeight = AverageHeight;
            }

            public int getAverageDiameter() {
                return AverageDiameter;
            }

            public void setAverageDiameter(int AverageDiameter) {
                this.AverageDiameter = AverageDiameter;
            }

            public int getAverageAge() {
                return AverageAge;
            }

            public void setAverageAge(int AverageAge) {
                this.AverageAge = AverageAge;
            }

            public int getYBDInfo() {
                return YBDInfo;
            }

            public void setYBDInfo(int YBDInfo) {
                this.YBDInfo = YBDInfo;
            }

            public String getEvevation() {
                return Evevation;
            }

            public void setEvevation(String Evevation) {
                this.Evevation = Evevation;
            }

            public String getAspect() {
                return Aspect;
            }

            public void setAspect(String Aspect) {
                this.Aspect = Aspect;
            }

            public String getSlope() {
                return Slope;
            }

            public void setSlope(String Slope) {
                this.Slope = Slope;
            }

            public String getSoilName() {
                return SoilName;
            }

            public void setSoilName(String SoilName) {
                this.SoilName = SoilName;
            }

            public int getSoilHeight() {
                return SoilHeight;
            }

            public void setSoilHeight(int SoilHeight) {
                this.SoilHeight = SoilHeight;
            }

            public String getXiaMuType() {
                return XiaMuType;
            }

            public void setXiaMuType(String XiaMuType) {
                this.XiaMuType = XiaMuType;
            }

            public String getXiaMuDensity() {
                return XiaMuDensity;
            }

            public void setXiaMuDensity(String XiaMuDensity) {
                this.XiaMuDensity = XiaMuDensity;
            }

            public String getDBWType() {
                return DBWType;
            }

            public void setDBWType(String DBWType) {
                this.DBWType = DBWType;
            }

            public String getDBWDensity() {
                return DBWDensity;
            }

            public void setDBWDensity(String DBWDensity) {
                this.DBWDensity = DBWDensity;
            }

            public String getManagementUnit() {
                return ManagementUnit;
            }

            public void setManagementUnit(String ManagementUnit) {
                this.ManagementUnit = ManagementUnit;
            }

            public String getManagementState() {
                return ManagementState;
            }

            public void setManagementState(String ManagementState) {
                this.ManagementState = ManagementState;
            }

            public String getAimsTree() {
                return AimsTree;
            }

            public void setAimsTree(String AimsTree) {
                this.AimsTree = AimsTree;
            }

            public String getAimsFamily() {
                return AimsFamily;
            }

            public void setAimsFamily(String AimsFamily) {
                this.AimsFamily = AimsFamily;
            }

            public String getAimsBelong() {
                return AimsBelong;
            }

            public void setAimsBelong(String AimsBelong) {
                this.AimsBelong = AimsBelong;
            }

            public String getRWJYInfo() {
                return RWJYInfo;
            }

            public void setRWJYInfo(String RWJYInfo) {
                this.RWJYInfo = RWJYInfo;
            }

            public String getSuggest() {
                return Suggest;
            }

            public void setSuggest(String Suggest) {
                this.Suggest = Suggest;
            }

            public Object getExplain() {
                return Explain;
            }

            public void setExplain(Object Explain) {
                this.Explain = Explain;
            }

            public String getRecordPerson() {
                return RecordPerson;
            }

            public void setRecordPerson(String RecordPerson) {
                this.RecordPerson = RecordPerson;
            }

            public String getRecordTime() {
                return RecordTime;
            }

            public void setRecordTime(String RecordTime) {
                this.RecordTime = RecordTime;
            }

            public String getUserID() {
                return UserID;
            }

            public void setUserID(String UserID) {
                this.UserID = UserID;
            }

            public List<TreeMapBean> getTreeMap() {
                return TreeMap;
            }

            public void setTreeMap(List<TreeMapBean> TreeMap) {
                this.TreeMap = TreeMap;
            }

            public List<String> getPicInfo() {
                return picInfo;
            }

            public void setPicInfo(List<String> picInfo) {
                this.picInfo = picInfo;
            }

            public static class TreeMapBean {
                /**
                 * TreeSpeID : T00494
                 * TreeNum : 18
                 */

                @SerializedName("TreeSpeID")
                private String TreeSpeID;
                @SerializedName("TreeNum")
                private String TreeNum;

                public String getTreeSpeID() {
                    return TreeSpeID;
                }

                public void setTreeSpeID(String TreeSpeID) {
                    this.TreeSpeID = TreeSpeID;
                }

                public String getTreeNum() {
                    return TreeNum;
                }

                public void setTreeNum(String TreeNum) {
                    this.TreeNum = TreeNum;
                }
            }
        }
    }
}
