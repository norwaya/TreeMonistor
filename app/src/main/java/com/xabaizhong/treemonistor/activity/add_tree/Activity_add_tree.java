package com.xabaizhong.treemonistor.activity.add_tree;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.base.App;
import com.xabaizhong.treemonistor.entity.Tree;
import com.xabaizhong.treemonistor.entity.TreeDao;
import com.xabaizhong.treemonistor.entity.TreeSpecial;
import com.xabaizhong.treemonistor.entity.TreeTypeInfo;
import com.xabaizhong.treemonistor.myview.C_dialog_checkbox;
import com.xabaizhong.treemonistor.myview.C_dialog_date;
import com.xabaizhong.treemonistor.myview.C_dialog_radio;
import com.xabaizhong.treemonistor.myview.C_info_gather_item1;
import com.xabaizhong.treemonistor.utils.MessageEvent;
import com.xabaizhong.treemonistor.utils.RxBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

import static com.xabaizhong.treemonistor.activity.add_tree.Activity_add_tree.ResultCode.REQUEST_CODE_ASPECT;
import static com.xabaizhong.treemonistor.activity.add_tree.Activity_add_tree.ResultCode.REQUEST_CODE_CNAME;
import static com.xabaizhong.treemonistor.activity.add_tree.Activity_add_tree.ResultCode.REQUEST_CODE_CNAME_RESULT;
import static com.xabaizhong.treemonistor.activity.add_tree.Activity_add_tree.ResultCode.REQUEST_CODE_ENVIRONMENT;
import static com.xabaizhong.treemonistor.activity.add_tree.Activity_add_tree.ResultCode.REQUEST_CODE_GROWSPACE;
import static com.xabaizhong.treemonistor.activity.add_tree.Activity_add_tree.ResultCode.REQUEST_CODE_GROWTH;
import static com.xabaizhong.treemonistor.activity.add_tree.Activity_add_tree.ResultCode.REQUEST_CODE_GSBZ;
import static com.xabaizhong.treemonistor.activity.add_tree.Activity_add_tree.ResultCode.REQUEST_CODE_LEVEL;
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
 * Created by admin on 2017/2/28.
 */

public class Activity_add_tree extends Activity_base implements View.OnClickListener {
    ArrayList<String> list;
    @BindView(R.id.tree_id)
    C_info_gather_item1 treeId;
    @BindView(R.id.tch)
    C_info_gather_item1 tch;
    @BindView(R.id.tcr)
    C_info_gather_item1 tcr;
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

    TreeDao treeDao;
    Tree tree = new Tree();
    TreeTypeInfo treeTypeInfo = new TreeTypeInfo();
    @BindView(R.id.grow_space)
    C_info_gather_item1 growSpace;
    @BindView(R.id.tree_area)
    C_info_gather_item1 treeArea;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        treeDao = ((App) getApplicationContext()).getDaoSession().getTreeDao();
        initCallBack();
    }

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
        //地区信息
        region.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
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

    private void initCNameDialog() {

    }

    private void selectPic() {
        MultiImageSelector.create(getApplicationContext())
                .showCamera(true) // show camera or not. true by default
                .count(4) // max select image size, 9 by default. used width #.multi()
                .single() // single mode
                .multi() // multi mode, default mode;
                .origin(list) // original select data set, used width #.multi()
                .start(Activity_add_tree.this, REQUEST_IMAGE);
    }

    @OnClick(R.id.btn)
    public void onClick() {
        fillTree();
        if (tree.check() == null) {
            //deal with pic
        }
    }

    private void fillTree() {
        String id = treeId.getText();
        tree.setTreeId(id);
        treeTypeInfo.setTreeId(id);
        treeTypeInfo.setIvst(tch.getText());
        treeTypeInfo.setRecoredPerson(tcr.getText());

        //
        tree.setTreeHeight(height.getText());
        tree.setTreeDBH(dbh.getText());
        String strCrownEW = crownEW.getText();
        String strCrownNS = crownNS.getText();
        tree.setCrownEW(strCrownEW);
        tree.setCrownNS(strCrownNS);
        computeCrownAvg(strCrownEW, strCrownNS);

        tree.setManagementPersion(managerPerson.getText());
        tree.setManagementUnit(mangerUnit.getText());
        tree.setTreeHistory(history.getText());
        tree.setRealAge(age.getText());
        tree.setGuessAge(age.getText());

        tree.setEnviorFactor(environmentFactor.getText());
        tree.setSpecStatDesc(specStatDesc.getText());
        tree.setSpecDesc(specDesc.getText());
    }

    private void computeCrownAvg(String ew, String ns) {
        if ("".equals(ew) || "".equals(ns))
            return;
        try {
            tree.setCrownAvg((Double.parseDouble(ew) + Double.parseDouble(ns) / 2 + ""));
        } catch (Exception e) {
            //log
        }
    }

    public void setAreaId(Activity_map.LocationBox locationBox) {
        tree.setAbscissa(locationBox.getLat() + "");
        tree.setOrdinate(locationBox.getLon() + "");

        treeTypeInfo.setAreaId("");
    }


    /**
     * result for activity
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case REQUEST_IMAGE:
                if (resultCode == RESULT_OK) {
                    // Get the result list of select image paths
                    list = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    // do your logic ....
                    pic.setText(list.size() + "");
                }
            case REQUEST_CODE_REGION:
                if (resultCode == ResultCode.REQUEST_CODE_REGION_RESULT) {
                    Activity_map.LocationBox box = data.getParcelableExtra("location");
                    if (box != null) {
                        region.setText(box.getProvince() + box.getCity() + box.getDistrict());
                        detailAddress.setText(box.getStreet() + box.getSematicDescription());
                        setAreaId(box);
                        tree.setSmallName(box.getStreet() + box.getSematicDescription());

                    }
                }

                break;
            case REQUEST_CODE_CNAME:
                if (resultCode == REQUEST_CODE_CNAME_RESULT) {
                    TreeSpecial treeSpecial = data.getParcelableExtra("special");
                    tree.setTreeSpeID(treeSpecial.getCode() + "");
                    cname.setText(treeSpecial.getCname());
                    alias.setText(treeSpecial.getAlias());
                }
                break;

            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static interface ResultCode {
        int REQUEST_CODE_CNAME = 0x103;
        int REQUEST_CODE_CNAME_RESULT = 103;


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

    @Override
    public void onClick(View v) {
        /*switch (v.getId()) {
            case R.id.tree_code:
                // 古树编号
                break;
            case R.id.cname:
                startActivityForResult(new Intent(this, Activity_map.class), REQUEST_CODE_REGION);
                break;
            case R.id.region:
                //地区信息
                startActivityForResult(new Intent(this, Activity_map.class), REQUEST_CODE_REGION);
                break;
        }*/
    }

    String[] array;

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
        C_dialog_date dateDialog = new C_dialog_date(this);
        dateDialog.setDateDialogListener(new C_dialog_date.DateDialogListener() {
            @Override
            public void getDate(Date date) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                researchDate.setText(format.format(date));
                treeTypeInfo.setDate(date);
            }
        });
        dateDialog.show();
    }

    public void showgrowth() {

    }


    Disposable disposable;

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
                        tree.setEnviorFactor((messageEvent.getId() + 1) + "");
                        break;
                    case REQUEST_CODE_STATUS:
                        status.setText(array[messageEvent.getId()]);
                        tree.setTreeStatus((messageEvent.getId() + 1) + "");
                        break;
                    case REQUEST_CODE_SPECIAL:
                        special.setText(array[messageEvent.getId()]);
                        tree.setSpecial((messageEvent.getId() + 1) + "");
                        break;
                    case REQUEST_CODE_GSBZ:
                        gsbz.setText(array[messageEvent.getId()]);
                        tree.setTreeType((messageEvent.getId() + 1) + "");
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
                        tree.setTreeArea(messageEvent.getId() + "");
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
