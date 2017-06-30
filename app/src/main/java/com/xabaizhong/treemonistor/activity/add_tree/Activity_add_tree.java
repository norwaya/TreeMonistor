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
import com.xabaizhong.treemonistor.entity.TreePic;
import com.xabaizhong.treemonistor.entity.Tree;
import com.xabaizhong.treemonistor.entity.TreeDao;
import com.xabaizhong.treemonistor.entity.TreePicDao;
import com.xabaizhong.treemonistor.entity.TreeSpecial;
import com.xabaizhong.treemonistor.entity.TreeSpecialDao;
import com.xabaizhong.treemonistor.entity.TreeTypeInfo;
import com.xabaizhong.treemonistor.entity.TreeTypeInfoDao;
import com.xabaizhong.treemonistor.myview.C_dialog_checkbox;
import com.xabaizhong.treemonistor.myview.C_dialog_radio;
import com.xabaizhong.treemonistor.myview.C_info_gather_item1;
import com.xabaizhong.treemonistor.myview.DateDialog;
import com.xabaizhong.treemonistor.service.WebserviceHelper;
import com.xabaizhong.treemonistor.service.model.ResultMessage;
import com.xabaizhong.treemonistor.utils.FileUtil;
import com.xabaizhong.treemonistor.utils.MessageEvent;
import com.xabaizhong.treemonistor.utils.RxBus;
import com.xabaizhong.treemonistor.utils.ScaleBitmap;

import java.io.File;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import static com.xabaizhong.treemonistor.activity.add_tree.Activity_add_tree.ResultCode.REQUEST_CODE_ASPECT;
import static com.xabaizhong.treemonistor.activity.add_tree.Activity_add_tree.ResultCode.REQUEST_CODE_CNAME;
import static com.xabaizhong.treemonistor.activity.add_tree.Activity_add_tree.ResultCode.REQUEST_CODE_ENVIRONMENT;
import static com.xabaizhong.treemonistor.activity.add_tree.Activity_add_tree.ResultCode.REQUEST_CODE_GROWSPACE;
import static com.xabaizhong.treemonistor.activity.add_tree.Activity_add_tree.ResultCode.REQUEST_CODE_GROWTH;
import static com.xabaizhong.treemonistor.activity.add_tree.Activity_add_tree.ResultCode.REQUEST_CODE_GSBZ;
import static com.xabaizhong.treemonistor.activity.add_tree.Activity_add_tree.ResultCode.REQUEST_CODE_OWNER;
import static com.xabaizhong.treemonistor.activity.add_tree.Activity_add_tree.ResultCode.REQUEST_CODE_PROTECTED;
import static com.xabaizhong.treemonistor.activity.add_tree.Activity_add_tree.ResultCode.REQUEST_CODE_RECOVERY;
import static com.xabaizhong.treemonistor.activity.add_tree.Activity_add_tree.ResultCode.REQUEST_CODE_REGION;
import static com.xabaizhong.treemonistor.activity.add_tree.Activity_add_tree.ResultCode.REQUEST_CODE_SLOPE;
import static com.xabaizhong.treemonistor.activity.add_tree.Activity_add_tree.ResultCode.REQUEST_CODE_SLOPEPOS;
import static com.xabaizhong.treemonistor.activity.add_tree.Activity_add_tree.ResultCode.REQUEST_CODE_SOIL;
import static com.xabaizhong.treemonistor.activity.add_tree.Activity_add_tree.ResultCode.REQUEST_CODE_SPECIAL;
import static com.xabaizhong.treemonistor.activity.add_tree.Activity_add_tree.ResultCode.REQUEST_CODE_STATUS;
import static com.xabaizhong.treemonistor.activity.add_tree.Activity_add_tree.ResultCode.REQUEST_CODE_TREEAREA;
import static com.xabaizhong.treemonistor.activity.add_tree.Activity_add_tree.ResultCode.REQUEST_IMAGE;

/**
 *  添加古树 视图页
 */
public class Activity_add_tree extends Activity_base {
    ArrayList<String> mList;

    @BindView(R.id.tree_id)
    C_info_gather_item1 treeId;
    @BindView(R.id.tch)
    C_info_gather_item1 tch;
    @BindView(R.id.research_date)
    C_info_gather_item1 researchDate;
    @BindView(R.id.region)
    C_info_gather_item1 region;
    @BindView(R.id.detail_address)
    C_info_gather_item1 detailAddress;
    @BindView(R.id.cname)
    C_info_gather_item1 cname;
    @BindView(R.id.alias)
    C_info_gather_item1 alias;
    @BindView(R.id.height)
    C_info_gather_item1 height;
    @BindView(R.id.dbh)
    C_info_gather_item1 dbh;
    @BindView(R.id.age)
    C_info_gather_item1 age;
    @BindView(R.id.crownEW)
    C_info_gather_item1 crownEW;
    @BindView(R.id.crownNS)
    C_info_gather_item1 crownNS;
    @BindView(R.id.growth)
    C_info_gather_item1 growth;
    @BindView(R.id.environment)
    C_info_gather_item1 environment;
    @BindView(R.id.status)
    C_info_gather_item1 status;
    @BindView(R.id.special)
    C_info_gather_item1 special;
    @BindView(R.id.gsbz)
    C_info_gather_item1 gsbz;
    @BindView(R.id.owner)
    C_info_gather_item1 owner;
    /*@BindView(R.id.level)
    C_info_gather_item1 level;*/
    @BindView(R.id.aspect)
    C_info_gather_item1 aspect;
    @BindView(R.id.slope)
    C_info_gather_item1 slope;
    @BindView(R.id.slope_pos)
    C_info_gather_item1 slopePos;
    @BindView(R.id.soil)
    C_info_gather_item1 soil;
    @BindView(R.id.protect)
    C_info_gather_item1 protect;
    @BindView(R.id.recovery)
    C_info_gather_item1 recovery;
    @BindView(R.id.manger_unit)
    C_info_gather_item1 mangerUnit;
    @BindView(R.id.manager_person)
    C_info_gather_item1 managerPerson;
    @BindView(R.id.environment_factor)
    C_info_gather_item1 environmentFactor;
    @BindView(R.id.history)
    C_info_gather_item1 history;
    @BindView(R.id.spec_stat_desc)
    C_info_gather_item1 specStatDesc;
    @BindView(R.id.spec_desc)
    C_info_gather_item1 specDesc;
    @BindView(R.id.pic)
    C_info_gather_item1 pic;
    @BindView(R.id.btn)
    Button btn;
    @BindView(R.id.layout)
    CoordinatorLayout layout;
    @BindView(R.id.grow_space)
    C_info_gather_item1 growSpace;
    @BindView(R.id.tree_area)
    C_info_gather_item1 treeArea;
    @BindView(R.id.layout_pb)
    RelativeLayout layoutPb;
    @BindView(R.id.longitude)
    C_info_gather_item1 longitude;
    @BindView(R.id.latitude)
    C_info_gather_item1 latitude;
    @BindView(R.id.town)
    C_info_gather_item1 town;
    @BindView(R.id.village)
    C_info_gather_item1 village;
    @BindView(R.id.elevation)
    C_info_gather_item1 elevation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.bind(this);
        initDaoSession();
        initResource();
        init();
    }

    Tree tree;
    TreeTypeInfo treeTypeInfo;

    private void initResource() {

        long id = getIntent().getLongExtra("id", -1);
        if (id == -1) {
            tree = new Tree();
            treeTypeInfo = new TreeTypeInfo();
            treeId.setText(areaId());
        } else {
            queryBeanFromDb(id);
            initViews();
        }
    }

    private void queryBeanFromDb(long id) {
        treeTypeInfo = treeTypeInfoDao.load(id);
        tree = treeTypeInfo.getTree();
        List<TreePic> pics = tree.getPics();
        if (mList == null) {
            mList = new ArrayList<>();
        }
        mList.clear();
        for (TreePic pic : pics) {
            mList.add(pic.getPath());
        }
        pic.setText(mList.size() + "");
    }

    /**
     * 数据库 -> views
     */
    private void initViews() {
        treeId.setText(tree.getTreeId());
        tch.setText(treeTypeInfo.getIvst());
        researchDate.setText(getStringDate(treeTypeInfo.getDate()));
        region.setText(tree.getRegion());
        detailAddress.setText(tree.getSmallName());
        longitude.setText(tree.getOrdinate());
        latitude.setText(tree.getAbscissa());
        elevation.setText(tree.getEvevation() + "");
        if (tree.getRegion() != null) {
            region.setText(tree.getRegion());
        }
        if (tree.getSmallName() != null) {
            detailAddress.setText(tree.getSmallName());
        }
        town.setText(tree.getTown());
        village.setText(tree.getVillage());
        initialTreeSpeciesInfo();
        height.setText(String.valueOf(tree.getTreeHeight()));
        dbh.setText(String.valueOf(tree.getTreeDBH()));
        age.setText(String.valueOf(tree.getRealAge()));
        crownEW.setText(String.valueOf(tree.getCrownEW()));
        crownNS.setText(String.valueOf(tree.getCrownNS()));

        int grownIndex = Integer.parseInt(tree.getGrowth()) - 1;
        growth.setText(getResources().getStringArray(R.array.growth)[grownIndex]);

        int environmentIndex = Integer.parseInt(tree.getEnviorment()) - 1;
        environment.setText(getResources().getStringArray(R.array.environment)[environmentIndex]);

        int statusIndex = Integer.parseInt(tree.getTreeStatus()) - 1;
        status.setText(getResources().getStringArray(R.array.status)[statusIndex]);

        int specialIndex = Integer.parseInt(tree.getSpecial());
        special.setText(getResources().getStringArray(R.array.special)[specialIndex]);

        int gsbzIndex = tree.getTreetype();
        gsbz.setText(getResources().getStringArray(R.array.gsbz)[gsbzIndex - 1]);

        int ownIndex = Integer.parseInt(tree.getOwner()) - 1;
        owner.setText(getResources().getStringArray(R.array.owner)[ownIndex]);

        int aspectIndex = Integer.parseInt(tree.getAspect());
        aspect.setText(getResources().getStringArray(R.array.aspect)[aspectIndex]);

        int slopeIndex = Integer.parseInt(tree.getSlope());
        slope.setText(getResources().getStringArray(R.array.slope)[slopeIndex]);

        int slopePosIndex = Integer.parseInt(tree.getSlopePos());
        slopePos.setText(getResources().getStringArray(R.array.slope_pos)[slopePosIndex]);

        int soilIndex = Integer.parseInt(tree.getSoil());
        soil.setText(getResources().getStringArray(R.array.soil)[soilIndex]);

        int grownSpaceIndex = Integer.parseInt(tree.getGrownSpace());
        growSpace.setText(getResources().getStringArray(R.array.grown_space)[grownSpaceIndex]);

        int treeAreaIndex = tree.getTreearea();
        treeArea.setText(getResources().getStringArray(R.array.tree_area)[treeAreaIndex]);

        String protecteArrayIndex = tree.getProtecte();
        StringBuilder protecteSbu = new StringBuilder();
        String[] protectArray = getResources().getStringArray(R.array.protect);
        Pattern proPattern = Pattern.compile("\\d");
        Matcher proM = proPattern.matcher(protecteArrayIndex);
        while (proM.find()) {
            final int i = Integer.parseInt(proM.group());
            protecteSbu.append(protectArray[i - 1]).append(",");
        }
        protect.setText(protecteSbu.toString());

        String recoveryArrayIndex = tree.getRecovery();
        StringBuilder recoverySbu = new StringBuilder();
        String[] recoveryArray = getResources().getStringArray(R.array.recovery);
        Pattern recPattern = Pattern.compile("\\d");
        Matcher recM = recPattern.matcher(recoveryArrayIndex);
        while (recM.find()) {
            final int i = Integer.parseInt(recM.group());
            recoverySbu.append(recoveryArray[i - 1]).append(",");
        }
        recovery.setText(recoverySbu.toString());

        mangerUnit.setText(tree.getManagementUnit());
        managerPerson.setText(tree.getManagementPersion());
        environmentFactor.setText(tree.getEnviorFactor());
        history.setText(tree.getTreeHistory());
        specDesc.setText(tree.getSpecDesc());
        specStatDesc.setText(tree.getSpecStatDesc());
    }
    // data  -> view 将树种数据 填充到 视图里面
    private void initialTreeSpeciesInfo() {
        List<TreeSpecial> sList = treeSepcialDao.queryBuilder().where(TreeSpecialDao.Properties.TreeSpeId.eq(tree.getTreespeid())).build().list();
        if (sList.size() > 0) {
            cname.setText(sList.get(0).getCname());
            alias.setText(sList.get(0).getAlias());
        }
    }

    private void init() {
        layoutPb.setOnClickListener(null);
        layoutPb.setVisibility(View.INVISIBLE);
        initCallBack();
    }
    // 回调函数 初始化
    private void initCallBack() {
        //中文名
        cname.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                startActivityForResult(new Intent(Activity_add_tree.this, Activity_tree_cname.class), REQUEST_CODE_CNAME);
            }
        });
        //date
        researchDate.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                showDateDialog();
            }
        });
        status.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                showRadioDialog(REQUEST_CODE_STATUS);
            }
        });
        //生长势
        growth.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                showRadioDialog(REQUEST_CODE_GROWTH);
            }
        });
        //环境
        environment.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                showRadioDialog(REQUEST_CODE_ENVIRONMENT);
            }
        });

        region.setCallback_right(new C_info_gather_item1.Right_CallBack() {
            @Override
            public void onClickListener(View view) {
                Log.i(TAG, "onClickListener: right icon onclick");
                startActivityForResult(new Intent(Activity_add_tree.this, Activity_map.class), REQUEST_CODE_REGION);

            }
        });
        status.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                showRadioDialog(REQUEST_CODE_STATUS);
            }
        });
        special.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                showRadioDialog(REQUEST_CODE_SPECIAL);
            }
        });
        gsbz.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                showRadioDialog(REQUEST_CODE_GSBZ);
            }
        });
        owner.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                showRadioDialog(REQUEST_CODE_OWNER);
            }
        });
       /* level.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                showRadioDialog(REQUEST_CODE_LEVEL);
            }
        });*/
        aspect.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                showRadioDialog(REQUEST_CODE_ASPECT);
            }
        });
        slope.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                showRadioDialog(REQUEST_CODE_SLOPE);
            }
        });
        slopePos.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                showRadioDialog(REQUEST_CODE_SLOPEPOS);
            }
        });
        soil.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                showRadioDialog(REQUEST_CODE_SOIL);
            }
        });
        growSpace.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                showRadioDialog(REQUEST_CODE_GROWSPACE);
            }
        });
        treeArea.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                showRadioDialog(REQUEST_CODE_TREEAREA);
            }
        });
        protect.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {

                new C_dialog_checkbox(Activity_add_tree.this, REQUEST_CODE_PROTECTED)
                        .setList(getResources().getStringArray(R.array.protect))
                        .setTitle("保护现状（多选）")
                        .show();
            }
        });
        recovery.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {

                new C_dialog_checkbox(Activity_add_tree.this, REQUEST_CODE_RECOVERY)
                        .setList(getResources().getStringArray(R.array.recovery))
                        .setTitle("养护现状（多选）")
                        .show();
            }
        });
        pic.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                selectPic();
            }
        });

    }

    // 跳转  选择图片 页面
    private void selectPic() {
        MultiImageSelector.create(getApplicationContext())
                .showCamera(true) // show camera or not. true by default
                .count(4) // max select image size, 9 by default. used width #.multi()
                .single() // single mode
                .multi() // multi mode, default mode;
                .origin(mList) // original select data set, used width #.multi()
                .start(Activity_add_tree.this, REQUEST_IMAGE);
    }
    // 处理 点击事件
    @OnClick(R.id.btn)
    public void onClick() {

        String checkResult = check();
        if (checkResult == null) {
            try {
                fillTree();
            } catch (Exception e) {

                Log.i(TAG, "onClick: +error+++" + e.getMessage());
            }
            checkLevel();
            saveDao();
            showToast("保存成功");
            restartActivity();

//            layoutPb.setVisibility(View.VISIBLE);
           /* checkLevel();
            upload();*/
        } else {
            showToast(checkResult);
        }
    }

    // 开启新的 页面
    private void restartActivity() {

        startActivity(new Intent(this, Activity_add_tree.class));
        finish();
    }

    /**
     * save bean to database 保存 数据 到数据库
     */
    private void saveDao() {
        treeDao.save(tree);
        treeTypeInfo.setTreeTableID(tree.getId());
        treeTypeInfoDao.save(treeTypeInfo);
        Log.i(TAG, "saveDao: " + treeTypeInfo.getId() + "\t" + tree.getId() + "\t" + tree.getPics().size());
        picDao.deleteInTx(tree.getPics());
        TreePic pic;
        if (mList != null)
            for (String str : mList) {
                pic = new TreePic(null, tree.getId(), str);
                picDao.save(pic);
                Log.i(TAG, "saveDao: save pics" + pic.getTree_id());
            }
        Log.i(TAG, "saveDao: " + tree.getPics());
        daoSession.clear();
    }

    TreePicDao picDao;
    TreeDao treeDao;
    TreeTypeInfoDao treeTypeInfoDao;
    TreeSpecialDao treeSepcialDao;
    DaoSession daoSession;
    //
    private void initDaoSession() {
        daoSession = ((App) getApplication()).getDaoSession();
        picDao = daoSession.getTreePicDao();
        treeDao = daoSession.getTreeDao();
        treeTypeInfoDao = daoSession.getTreeTypeInfoDao();
        treeSepcialDao = daoSession.getTreeSpecialDao();
    }
//  检查用户 输入
    String check() {
        if (!treeId.getText().matches("\\d{11}")) {
            return "古树编号为 11 位";
        }
        if (!treeId.getText().startsWith(areaId())) {
            return "古树编号要以 " + areaId() + " 开头";
        }
        if (!tch.getText().matches("\\d{5}")) {
            return "调查号为5位数字";
        }
        if (!researchDate.getText().matches("^\\w{4}-\\w{2}-\\w{2}.*")) {
            return "请选择日期";
        }
        if (detailAddress.getText().equals("")) {
            return "请完善地理信息";
        }
        if (latitude.getText().equals("") || longitude.getText().equals("")) {
            return "经纬度不能为空";
        }
        if (town.getText().trim().equals("") || village.getText().trim().equals("")) {
            return "城镇/村庄不能为空";
        }
        if (cname.getText().equals("")) {
            return "选择树种类";
        }
        if (height.getText().equals("")) {
            return "请填写树高";
        }
        if (dbh.getText().equals("")) {
            return "请填写胸径";
        }
        if (age.getText().equals("")) {
            return "请填写树龄";
        }
        if (crownEW.getText().equals("")) {
            return "请填写东西冠幅";
        }
        if (crownNS.getText().equals("")) {
            return "请填写南北冠幅";
        }
        if (growth.getText().equals("")) {
            return "请选择生长势";
        }
        if (environment.getText().equals("")) {
            return "请选择生长环境";
        }
        if (status.getText().equals("")) {
            return "请选择状况";
        }
        if (special.getText().equals("")) {
            return "请选择散生群状";
        }
        if (gsbz.getText().equals("")) {
            return "请选择古树标志";
        }
        if (owner.getText().equals("")) {
            return "请选择权属";
        }
        if (growSpace.getText().equals("")) {
            return "请选择生长场所";
        }
        if (treeArea.getText().equals("")) {
            return "请选择生长地";
        }
        if (aspect.getText().equals("")) {
            return "请选择坡向";
        }
        if (slope.getText().equals("")) {
            return "请选择坡度";
        }
        if (slopePos.getText().equals("")) {
            return "请选择坡位";
        }
        if (soil.getText().equals("")) {
            return "请选择土壤";
        }
        if (protect.getText().equals("")) {
            return "请选择保护现状";
        }
        if (recovery.getText().equals("")) {
            return "请选择养护现状";
        }
        if (managerPerson.getText().equals("") && mangerUnit.getText().equals("")) {
            return "管护单位和管护人不能同时为空";
        }
        if (history.getText().equals("")) {
            return "请填写历史因素";
        }
        return null;
    }
    // 检查 等级
    private void checkLevel() {

        int treeAge = (int) tree.getRealAge();
        if (treeAge >= 1000) {
            tree.setTreeLevel("1");
            if (tree.getTreetype() == 2) {
                tree.setTreetype(3);
            }
            return;
        }


        if (tree.getTreetype() == 1) {
            switch (treeAge / 100) {
                case 0:
                case 1:
                case 2:
                    tree.setTreeLevel("4");
                    break;
                case 3:
                case 4:
                    tree.setTreeLevel("3");
                    break;
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    tree.setTreeLevel("2");
                    break;
                default:
                    tree.setTreeLevel("4");
                    break;
            }

        } else {
            tree.setTreeLevel("2");
            if (tree.getTreetype() == 2) {
                tree.setTreetype(3);
            }
        }

    }

    @Deprecated
    private Map<String, Object> getParms() {
        Map<String, Object> map = new HashMap<>();
        String user_id = sharedPreferences.getString(UserSharedField.USERID, "");
        map.put("UserID", user_id.trim());
        map.put("TreeType", 0);
        map.put("JsonStr", json);
        return map;
    }

    String json;
    // data -> beans
    private void fillTree() {
        treeTypeInfo.setTypeId(0);
        treeTypeInfo.setAreaId(areaId());
        String id = treeId.getText();
        tree.setTreeId(id);
        treeTypeInfo.setTree(tree);
        treeTypeInfo.setTreeId(id);
        treeTypeInfo.setIvst(tch.getText());

        tree.setSmallName(detailAddress.getText());
        tree.setTown(town.getText());
        tree.setVillage(village.getText());
        tree.setAbscissa(latitude.getText());
        tree.setOrdinate(longitude.getText());
        String elevationStr = elevation.getText();
        if (elevationStr.length() != 0) {
            tree.setEvevation(Double.parseDouble(elevationStr));
        }
        tree.setTreeHeight(Double.parseDouble(height.getText()));
        tree.setTreeDBH(Double.parseDouble(dbh.getText()));
        String strCrownEW = crownEW.getText();
        String strCrownNS = crownNS.getText();
        tree.setCrownEW(Double.parseDouble(strCrownEW));
        tree.setCrownNS(Double.parseDouble(strCrownNS));
        computeCrownAvg(strCrownEW, strCrownNS);

        tree.setManagementPersion(managerPerson.getText());
        tree.setManagementUnit(mangerUnit.getText());
        tree.setTreeHistory(history.getText());
        tree.setRealAge(Double.parseDouble(age.getText()));
        tree.setGuessAge(Double.parseDouble(age.getText()));

        tree.setEnviorFactor(environmentFactor.getText());
        tree.setSpecStatDesc(specStatDesc.getText());
        tree.setSpecDesc(specDesc.getText());


        tree.setUserID(userId());

    }
    // 获取 userid
    private String userId() {
        return sharedPreferences.getString(UserSharedField.USERID, "");
    }
        // 获取 areaid
    private String areaId() {
        return sharedPreferences.getString(UserSharedField.AREAID, "");
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


    private void computeCrownAvg(String ew, String ns) {
        if ("".equals(ew) || "".equals(ns))
            return;
        try {
            tree.setCrownAvg((Double.parseDouble(ew) + Double.parseDouble(ns) / 2));
        } catch (Exception e) {
            //log
        }
    }

    public void setAreaId(Activity_map.LocationBox locationBox) {
        tree.setAbscissa(locationBox.getLat() + "");
        tree.setOrdinate(locationBox.getLon() + "");
        //610329

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case REQUEST_IMAGE:
                if (resultCode == RESULT_OK) {
                    // Get the result mList of select image paths
                    mList = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    // do your logic ....
                    pic.setText(mList.size() + "");
                }
            case REQUEST_CODE_REGION:
                if (resultCode == 100) {
                    Activity_map.LocationBox box = data.getParcelableExtra("location");
                    if (box != null) {
                        tree.setRegion(box.getProvince() + box.getCity() + box.getDistrict());
                        tree.setSmallName(box.getStreet() + box.getSematicDescription());
                        tree.setAbscissa(box.getLat() + "");
                        tree.setOrdinate(box.getLon() + "");

                        region.setText(tree.getRegion());
                        detailAddress.setText(tree.getSmallName());
                        latitude.setText(tree.getAbscissa());
                        longitude.setText(tree.getOrdinate());
                    }
                }
                break;
            case REQUEST_CODE_CNAME:
                if (resultCode == Activity_tree_cname.REQUEST_CODE_CNAME_RESULT) {
                    TreeSpecial treeSpecial = data.getParcelableExtra("special");
                    tree.setTreespeid(treeSpecial.getTreeSpeId());


                    cname.setText(treeSpecial.getCname());
                    alias.setText(treeSpecial.getAlias());
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    // 常量 接口
    public static interface ResultCode {
        int REQUEST_CODE_CNAME = 0x103;


        int REQUEST_CODE_REGION = 0x100;
        int REQUEST_CODE_REGION_RESULT = 100;


        int REQUEST_CODE_GROWTH = 104;
        int REQUEST_CODE_ENVIRONMENT = 105;
        int REQUEST_CODE_STATUS = 106;
        int REQUEST_CODE_SPECIAL = 107;

        int REQUEST_CODE_GSBZ = 108;
        int REQUEST_CODE_OWNER = 109;
        int REQUEST_CODE_LEVEL = 110;
        int REQUEST_CODE_ASPECT = 111;
        int REQUEST_CODE_SLOPE = 112;
        int REQUEST_CODE_SLOPEPOS = 113;
        int REQUEST_CODE_SOIL = 114;
        int REQUEST_CODE_PROTECTED = 115;
        int REQUEST_CODE_RECOVERY = 116;
        int REQUEST_CODE_TREEAREA = 117;
        int REQUEST_CODE_GROWSPACE = 118;


        int REQUEST_IMAGE = 0x123;

    }


    String[] array;
    // 展示 dialog 供 用户选择；
    public void showRadioDialog(int Request) {
        switch (Request) {
            case REQUEST_CODE_GROWTH:
                array = getResources().getStringArray(R.array.growth);
                new C_dialog_radio(this, "生长势", Arrays.asList(array), REQUEST_CODE_GROWTH);
                break;
            case REQUEST_CODE_ENVIRONMENT:
                array = getResources().getStringArray(R.array.environment);
                new C_dialog_radio(this, "生长环境", Arrays.asList(array), REQUEST_CODE_ENVIRONMENT);
                break;
            case REQUEST_CODE_STATUS:
                array = getResources().getStringArray(R.array.status);
                new C_dialog_radio(this, "生长状况", Arrays.asList(array), REQUEST_CODE_STATUS);
                break;
            case REQUEST_CODE_SPECIAL:
                array = getResources().getStringArray(R.array.special);
                new C_dialog_radio(this, "特点", Arrays.asList(array), REQUEST_CODE_SPECIAL);
                break;
            case REQUEST_CODE_GSBZ:
                array = getResources().getStringArray(R.array.gsbz);
                new C_dialog_radio(this, "古树标志", Arrays.asList(array), REQUEST_CODE_GSBZ);
                break;
            case REQUEST_CODE_OWNER:
                array = getResources().getStringArray(R.array.owner);
                new C_dialog_radio(this, "权属", Arrays.asList(array), REQUEST_CODE_OWNER);
                break;
            case REQUEST_CODE_ASPECT:
                array = getResources().getStringArray(R.array.aspect);
                new C_dialog_radio(this, "坡向", Arrays.asList(array), REQUEST_CODE_ASPECT);
                break;
            case REQUEST_CODE_SLOPE:
                array = getResources().getStringArray(R.array.slope);
                new C_dialog_radio(this, "坡度", Arrays.asList(array), REQUEST_CODE_SLOPE);
                break;
            case REQUEST_CODE_SLOPEPOS:
                array = getResources().getStringArray(R.array.slope_pos);
                new C_dialog_radio(this, "坡位", Arrays.asList(array), REQUEST_CODE_SLOPEPOS);
                break;
            case REQUEST_CODE_SOIL:
                array = getResources().getStringArray(R.array.soil);
                new C_dialog_radio(this, "土壤", Arrays.asList(array), REQUEST_CODE_SOIL);
                break;
            case REQUEST_CODE_GROWSPACE:
                array = getResources().getStringArray(R.array.grown_space);
                new C_dialog_radio(this, "生长场所", Arrays.asList(array), REQUEST_CODE_GROWSPACE);
                break;
            case REQUEST_CODE_TREEAREA:
                array = getResources().getStringArray(R.array.tree_area);
                new C_dialog_radio(this, "生长地", Arrays.asList(array), REQUEST_CODE_TREEAREA);
                break;
        }
    }

    public void showDateDialog() {
        DateDialog dateDialog = new DateDialog(this);
        dateDialog.setDateDialogListener(new DateDialog.DateDialogListener() {
            @Override
            public void getDate(Date date) {
                researchDate.setText(getStringDate(date));
                treeTypeInfo.setDate(date);

                tree.setDate(date);
            }
        });
    }


    Disposable disposable;
    // 注册rxbus
    private void register() {

        disposable = RxBus.getDefault().toObservable(MessageEvent.class).subscribe(new Consumer<MessageEvent>() {
            @Override
            public void accept(MessageEvent messageEvent) throws Exception {
                Log.d(TAG, "accept: " + messageEvent.getId() + "\t" + messageEvent.getText());
                switch (messageEvent.getCode()) {
                    case REQUEST_CODE_GROWTH:
                        growth.setText(array[messageEvent.getId()]);
                        tree.setGrowth((messageEvent.getId() + 1) + "");
                        break;
                    case REQUEST_CODE_ENVIRONMENT:
                        environment.setText(array[messageEvent.getId()]);
                        tree.setEnviorment((messageEvent.getId() + 1) + "");
                        break;
                    case REQUEST_CODE_STATUS:
                        status.setText(array[messageEvent.getId()]);

                        tree.setTreeStatus((messageEvent.getId() + 1) + "");
                        break;
                    case REQUEST_CODE_SPECIAL:
                        special.setText(array[messageEvent.getId()]);
                        tree.setSpecial((messageEvent.getId()) + "");
                        break;
                    case REQUEST_CODE_GSBZ:
                        gsbz.setText(array[messageEvent.getId()]);
                        tree.setTreetype((messageEvent.getId() + 1));
                        break;
                    case REQUEST_CODE_OWNER:
                        owner.setText(array[messageEvent.getId()]);
                        tree.setOwner((messageEvent.getId() + 1) + "");
                        break;
                   /* case REQUEST_CODE_LEVEL:
                        level.setText(array[messageEvent.getId()]);
                        break;*/
                    case REQUEST_CODE_ASPECT:
                        aspect.setText(array[messageEvent.getId()]);
                        tree.setAspect((messageEvent.getId()) + "");
                        break;
                    case REQUEST_CODE_SLOPE:
                        slope.setText(array[messageEvent.getId()]);
                        tree.setSlope((messageEvent.getId()) + "");
                        break;
                    case REQUEST_CODE_SLOPEPOS:
                        slopePos.setText(array[messageEvent.getId()]);
                        tree.setSlopePos((messageEvent.getId()) + "");
                        break;
                    case REQUEST_CODE_SOIL:
                        soil.setText(array[messageEvent.getId()]);
                        tree.setSoil((messageEvent.getId()) + "");
                        break;
                    case REQUEST_CODE_PROTECTED:
                        Log.i(TAG, "accept: protected");
                        protect.setText(messageEvent.getText());
                        tree.setProtecte("BH" + messageEvent.getRemark());
                        break;
                    case REQUEST_CODE_RECOVERY:
                        recovery.setText(messageEvent.getText());
                        tree.setRecovery("YH" + messageEvent.getRemark());
                        break;
                    case REQUEST_CODE_GROWSPACE:
                        growSpace.setText(messageEvent.getText());
                        tree.setGrownSpace(messageEvent.getId() + "");
                        break;
                    case REQUEST_CODE_TREEAREA:
                        treeArea.setText(messageEvent.getText());
                        tree.setTreearea(messageEvent.getId());
                        break;
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        register();
    }

    @Override
    protected void onPause() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        super.onPause();
    }
}
