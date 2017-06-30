package com.xabaizhong.treemonistor.activity.monitor.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.activity.add_tree.Activity_add_tree;
import com.xabaizhong.treemonistor.activity.add_tree.Activity_map;
import com.xabaizhong.treemonistor.activity.add_tree.Activity_tree_cname;
import com.xabaizhong.treemonistor.activity.base_data.Activity_base_weakness_detail;
import com.xabaizhong.treemonistor.activity.base_data.Activity_pic_vp;
import com.xabaizhong.treemonistor.activity.monitor.Imonitor;
import com.xabaizhong.treemonistor.base.App;
import com.xabaizhong.treemonistor.base.Fragment_base;
import com.xabaizhong.treemonistor.contant.UserSharedField;
import com.xabaizhong.treemonistor.entity.AreaCode;
import com.xabaizhong.treemonistor.entity.AreaCodeDao;
import com.xabaizhong.treemonistor.entity.Tree;
import com.xabaizhong.treemonistor.entity.TreeSpecial;
import com.xabaizhong.treemonistor.entity.TreeSpecialDao;
import com.xabaizhong.treemonistor.entity.TreeTypeInfo;
import com.xabaizhong.treemonistor.myview.C_dialog_checkbox;
import com.xabaizhong.treemonistor.myview.C_dialog_radio;
import com.xabaizhong.treemonistor.myview.C_info_gather_item1;
import com.xabaizhong.treemonistor.myview.DateDialog;
import com.xabaizhong.treemonistor.myview.ProgressDialogUtil;
import com.xabaizhong.treemonistor.service.WebserviceHelper;
import com.xabaizhong.treemonistor.utils.FileUtil;
import com.xabaizhong.treemonistor.utils.MessageEvent;
import com.xabaizhong.treemonistor.utils.RxBus;
import com.xabaizhong.treemonistor.utils.ScaleBitmap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import me.nereo.multi_image_selector.utils.FileUtils;

import static android.app.Activity.RESULT_OK;
import static com.xabaizhong.treemonistor.activity.monitor.fragment.Fragment_tree.ResultCode;
import static com.xabaizhong.treemonistor.activity.monitor.fragment.Fragment_tree.ResultCode.REQUEST_CODE_ASPECT;
import static com.xabaizhong.treemonistor.activity.monitor.fragment.Fragment_tree.ResultCode.REQUEST_CODE_CNAME;
import static com.xabaizhong.treemonistor.activity.monitor.fragment.Fragment_tree.ResultCode.REQUEST_CODE_ENVIRONMENT;
import static com.xabaizhong.treemonistor.activity.monitor.fragment.Fragment_tree.ResultCode.REQUEST_CODE_GROWSPACE;
import static com.xabaizhong.treemonistor.activity.monitor.fragment.Fragment_tree.ResultCode.REQUEST_CODE_GROWTH;
import static com.xabaizhong.treemonistor.activity.monitor.fragment.Fragment_tree.ResultCode.REQUEST_CODE_GSBZ;
import static com.xabaizhong.treemonistor.activity.monitor.fragment.Fragment_tree.ResultCode.REQUEST_CODE_OWNER;
import static com.xabaizhong.treemonistor.activity.monitor.fragment.Fragment_tree.ResultCode.REQUEST_CODE_PROTECTED;
import static com.xabaizhong.treemonistor.activity.monitor.fragment.Fragment_tree.ResultCode.REQUEST_CODE_RECOVERY;
import static com.xabaizhong.treemonistor.activity.monitor.fragment.Fragment_tree.ResultCode.REQUEST_CODE_REGION;
import static com.xabaizhong.treemonistor.activity.monitor.fragment.Fragment_tree.ResultCode.REQUEST_CODE_SLOPE;
import static com.xabaizhong.treemonistor.activity.monitor.fragment.Fragment_tree.ResultCode.REQUEST_CODE_SLOPEPOS;
import static com.xabaizhong.treemonistor.activity.monitor.fragment.Fragment_tree.ResultCode.REQUEST_CODE_SOIL;
import static com.xabaizhong.treemonistor.activity.monitor.fragment.Fragment_tree.ResultCode.REQUEST_CODE_SPECIAL;
import static com.xabaizhong.treemonistor.activity.monitor.fragment.Fragment_tree.ResultCode.REQUEST_CODE_STATUS;
import static com.xabaizhong.treemonistor.activity.monitor.fragment.Fragment_tree.ResultCode.REQUEST_CODE_TREEAREA;
import static com.xabaizhong.treemonistor.activity.monitor.fragment.Fragment_tree.ResultCode.REQUEST_IMAGE;

/**
 * 管理 当前id  古树信息，修改，和序列化
 */
public class Fragment_tree extends Fragment_base implements Imonitor, C_info_gather_item1.Mid_CallBack {
    String mTreeId;
    ViewHolder mViewHolder;

    public static Fragment_tree instance(String treeId) {
        Fragment_tree fragment = new Fragment_tree();
        fragment.mTreeId = treeId;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tree_layout, container, false);
        mViewHolder = new ViewHolder(view);
        initial();
        initialView();
        return view;
    }

    private void initial() {
        treeTypeInfo = new TreeTypeInfo();
        tree = new Tree();
        treeTypeInfo.setTree(tree);
        treeTypeInfo.setTypeId(0);
        treeTypeInfo.setTreeId(mTreeId);
        tree.setTreeId(mTreeId);
        tree.setUserID(sharedPreferences.getString(UserSharedField.USERID, ""));
    }

    Disposable disposable;
    ResultMessage resultMessage;

    private void initialView() {
        final DialogInterface dialog = ProgressDialogUtil.getInstance(getActivity()).initial("loading...", new ProgressDialogUtil.CallBackListener() {
            @Override
            public void callBack(DialogInterface dialog) {

            }
        });

        final Map<String, Object> map = new HashMap<>();
        map.put("UserID", sharedPreferences.getString(UserSharedField.USERID, ""));
        map.put("TreeType", 0);
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
                        resultMessage = new Gson().fromJson(value, ResultMessage.class);
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

    TreeTypeInfo treeTypeInfo;
    Tree tree;

    private void initBean() {
        ResultMessage.ResultBean bean = resultMessage.getResult();
        treeTypeInfo.setIvst(resultMessage.getResult().IVST);
        Date date = fromStrDate(resultMessage.getResult().RecordTime);
        if (date != null) {
            treeTypeInfo.setDate(date);
            tree.setDate(date);
        }
        treeTypeInfo.setAreaId(resultMessage.areaid);

        tree.setRegion(getAreaName(resultMessage.areaid));
        tree.setSmallName(bean.smallname);
        tree.setEvevation(bean.evevation);
        tree.setTown(bean.town);
        tree.setVillage(bean.village);
        tree.setTreespeid(bean.treespeid);
        tree.setTreeHeight(bean.treeheight);
        tree.setTreeDBH(bean.treeDBH);
        tree.setOrdinate(bean.ordinate);
        tree.setAbscissa(bean.abscissa);
        tree.setRealAge(bean.realage);
        tree.setCrownEW(bean.crownew);
        tree.setCrownNS(bean.crownns);
        tree.setGrowth(bean.growth);
        tree.setEnviorment(bean.enviorment);
        tree.setTreeStatus(bean.TreeStatus);
        tree.setSpecial(bean.special);
        tree.setTreetype(bean.treetype);
        tree.setOwner(bean.Owner);
        tree.setGrownSpace(bean.grownspace);
        tree.setTreearea(bean.treearea);
        tree.setAspect(bean.aspect);
        tree.setSlopePos(bean.slopepos);
        tree.setSlope(bean.Slope);
        tree.setSoil(bean.soil);
        tree.setProtecte(bean.Protected);
        tree.setRecovery(bean.Recovery);
        tree.setManagementUnit(bean.managementunit);
        tree.setManagementPersion(bean.managementpersion);
        tree.setEnviorFactor(bean.enviorfactor);
        tree.setTreeHistory(bean.treehistory);
        tree.setSpecDesc(bean.specstatdesc);
        tree.setSpecDesc(bean.specdesc);
        Log.e(TAG, "initBean: " + new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(treeTypeInfo));
    }


    private void fillData() {

        ResultMessage.ResultBean tree = resultMessage.result;
        TreeSpecial treeSpcial = getTreeSpecies(tree.getTreespeid());
        String cname = treeSpcial.getCname();
        String alias = treeSpcial.getAlias();
        int grownIndex = Integer.parseInt(tree.getGrowth()) - 1;
        String grownth = getResources().getStringArray(R.array.growth)[grownIndex];

        int environmentIndex = Integer.parseInt(tree.getEnviorment()) - 1;
        String environment = getResources().getStringArray(R.array.environment)[environmentIndex];

        int statusIndex = Integer.parseInt(tree.getTreeStatus()) - 1;
        String status = getResources().getStringArray(R.array.status)[statusIndex];

        int specialIndex = Integer.parseInt(tree.getSpecial());
        String special = getResources().getStringArray(R.array.special)[specialIndex];

        int gsbzIndex = tree.getTreetype();
        String gsbz = getResources().getStringArray(R.array.gsbz)[gsbzIndex - 1];

        int ownIndex = Integer.parseInt(tree.getOwner()) - 1;
        String owner = getResources().getStringArray(R.array.owner)[ownIndex];

        int aspectIndex = Integer.parseInt(tree.getAspect());
        String aspect = getResources().getStringArray(R.array.aspect)[aspectIndex];

        int slopeIndex = Integer.parseInt(tree.getSlope());
        String slope = getResources().getStringArray(R.array.slope)[slopeIndex];

        int slopePosIndex = Integer.parseInt(tree.getSlopepos());
        String slopePos = getResources().getStringArray(R.array.slope_pos)[slopePosIndex];

        int soilIndex = Integer.parseInt(tree.getSoil());
        String soil = getResources().getStringArray(R.array.soil)[soilIndex];

        int grownSpaceIndex = Integer.parseInt(tree.getGrownspace());
        String growSpace = getResources().getStringArray(R.array.grown_space)[grownSpaceIndex];

        int treeAreaIndex = tree.getTreearea();
        String treeArea = getResources().getStringArray(R.array.tree_area)[treeAreaIndex];

        String protecteArrayIndex = tree.getProtected();
        StringBuilder protecteSbu = new StringBuilder();
        String[] protectArray = getResources().getStringArray(R.array.protect);
        Pattern proPattern = Pattern.compile("\\d");
        Matcher proM = proPattern.matcher(protecteArrayIndex);
        while (proM.find()) {
            final int i = Integer.parseInt(proM.group());
            protecteSbu.append(protectArray[i - 1]).append(",");
        }
        String protect = protecteSbu.toString();

        String recoveryArrayIndex = tree.getRecovery();
        StringBuilder recoverySbu = new StringBuilder();
        String[] recoveryArray = getResources().getStringArray(R.array.recovery);
        Pattern recPattern = Pattern.compile("\\d");
        Matcher recM = recPattern.matcher(recoveryArrayIndex);
        while (recM.find()) {
            final int i = Integer.parseInt(recM.group());
            recoverySbu.append(recoveryArray[i - 1]).append(",");
        }
        String recovery = recoverySbu.toString();
        mViewHolder.tch.setText(tree.IVST);
        mViewHolder.region.setText(getAreaName(resultMessage.areaid));
        mViewHolder.detailAddress.setText(tree.smallname);
        mViewHolder.elevation.setText(tree.evevation + "");
        mViewHolder.town.setText(tree.town);
        mViewHolder.village.setText(tree.village);
        mViewHolder.cname.setText(cname);
        mViewHolder.alias.setText(alias);
        mViewHolder.height.setText(tree.treeheight + "");
        mViewHolder.dbh.setText(tree.treeDBH + "");
        mViewHolder.longitude.setText(tree.ordinate);
        mViewHolder.latitude.setText(tree.abscissa);
        mViewHolder.age.setText(tree.realage + "");
        mViewHolder.crownEW.setText(tree.crownew + "");
        mViewHolder.crownNS.setText(tree.crownns + "");
        mViewHolder.growth.setText(grownth);
        mViewHolder.environment.setText(environment);
        mViewHolder.status.setText(status);
        mViewHolder.special.setText(special);
        mViewHolder.gsbz.setText(gsbz);
        mViewHolder.owner.setText(owner);
        mViewHolder.growSpace.setText(growSpace);
        mViewHolder.treeArea.setText(treeArea);
        mViewHolder.aspect.setText(aspect);
        mViewHolder.slopePos.setText(slopePos);
        mViewHolder.slope.setText(slope);
        mViewHolder.soil.setText(soil);
        mViewHolder.protect.setText(protect);
        mViewHolder.recovery.setText(recovery);
        mViewHolder.mangerUnit.setText(tree.managementunit);
        mViewHolder.managerPerson.setText(tree.managementpersion);
        mViewHolder.environmentFactor.setText(tree.enviorfactor);
        mViewHolder.history.setText(tree.treehistory);
        mViewHolder.specStatDesc.setText(tree.specstatdesc);
        mViewHolder.specDesc.setText(tree.specdesc);
        initialPicView();
    }

    private void initialPicView() {
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
    public void onClickListener(View et) {
        switch (et.getId()) {
        }
    }

    private void initCallBack() {
        //中文名
        mViewHolder.cname.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                startActivityForResult(new Intent(getActivity(), Activity_tree_cname.class), REQUEST_CODE_CNAME);
            }
        });
        //date
        mViewHolder.researchDate.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                showDateDialog();
            }
        });
        mViewHolder.status.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                showRadioDialog(REQUEST_CODE_STATUS);
            }
        });
        //生长势
        mViewHolder.growth.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                showRadioDialog(REQUEST_CODE_GROWTH);
            }
        });
        //环境
        mViewHolder.environment.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                showRadioDialog(REQUEST_CODE_ENVIRONMENT);
            }
        });

        mViewHolder.region.setCallback_right(new C_info_gather_item1.Right_CallBack() {
            @Override
            public void onClickListener(View view) {
                Log.i(TAG, "onClickListener: right icon onclick");
                startActivityForResult(new Intent(getActivity(), Activity_map.class), REQUEST_CODE_REGION);

            }
        });
        mViewHolder.status.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                showRadioDialog(REQUEST_CODE_STATUS);
            }
        });
        mViewHolder.special.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                showRadioDialog(REQUEST_CODE_SPECIAL);
            }
        });
        mViewHolder.gsbz.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                showRadioDialog(REQUEST_CODE_GSBZ);
            }
        });
        mViewHolder.owner.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
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
        mViewHolder.aspect.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                showRadioDialog(REQUEST_CODE_ASPECT);
            }
        });
        mViewHolder.slope.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                showRadioDialog(REQUEST_CODE_SLOPE);
            }
        });
        mViewHolder.slopePos.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                showRadioDialog(REQUEST_CODE_SLOPEPOS);
            }
        });
        mViewHolder.soil.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                showRadioDialog(REQUEST_CODE_SOIL);
            }
        });
        mViewHolder.growSpace.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                showRadioDialog(REQUEST_CODE_GROWSPACE);
            }
        });
        mViewHolder.treeArea.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                showRadioDialog(REQUEST_CODE_TREEAREA);
            }
        });
        mViewHolder.protect.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {

                new C_dialog_checkbox(getActivity(), REQUEST_CODE_PROTECTED)
                        .setList(getResources().getStringArray(R.array.protect))
                        .setTitle("保护现状（多选）")
                        .show();
            }
        });
        mViewHolder.recovery.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {

                new C_dialog_checkbox(getActivity(), REQUEST_CODE_RECOVERY)
                        .setList(getResources().getStringArray(R.array.recovery))
                        .setTitle("养护现状（多选）")
                        .show();
            }
        });
        mViewHolder.picNew.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                selectPic();
            }
        });

    }

    private void selectPic() {

        Intent intent = new Intent(getActivity(), MultiImageSelectorActivity.class);
// whether show camera
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
// max select image amount
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 5);
// select mode (MultiImageSelectorActivity.MODE_SINGLE OR MultiImageSelectorActivity.MODE_MULTI)
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
// default select images (support array list)
        intent.putStringArrayListExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mList);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    String[] array;

    public void showRadioDialog(int Request) {
        switch (Request) {
            case REQUEST_CODE_GROWTH:
                array = getResources().getStringArray(R.array.growth);
                new C_dialog_radio(getActivity(), "生长势", Arrays.asList(array), REQUEST_CODE_GROWTH);
                break;
            case REQUEST_CODE_ENVIRONMENT:
                array = getResources().getStringArray(R.array.environment);
                new C_dialog_radio(getActivity(), "生长环境", Arrays.asList(array), REQUEST_CODE_ENVIRONMENT);
                break;
            case REQUEST_CODE_STATUS:
                array = getResources().getStringArray(R.array.status);
                new C_dialog_radio(getActivity(), "生长状况", Arrays.asList(array), REQUEST_CODE_STATUS);
                break;
            case REQUEST_CODE_SPECIAL:
                array = getResources().getStringArray(R.array.special);
                new C_dialog_radio(getActivity(), "特点", Arrays.asList(array), REQUEST_CODE_SPECIAL);
                break;
            case REQUEST_CODE_GSBZ:
                array = getResources().getStringArray(R.array.gsbz);
                new C_dialog_radio(getActivity(), "古树标志", Arrays.asList(array), REQUEST_CODE_GSBZ);
                break;
            case REQUEST_CODE_OWNER:
                array = getResources().getStringArray(R.array.owner);
                new C_dialog_radio(getActivity(), "权属", Arrays.asList(array), REQUEST_CODE_OWNER);
                break;
            case REQUEST_CODE_ASPECT:
                array = getResources().getStringArray(R.array.aspect);
                new C_dialog_radio(getActivity(), "坡向", Arrays.asList(array), REQUEST_CODE_ASPECT);
                break;
            case REQUEST_CODE_SLOPE:
                array = getResources().getStringArray(R.array.slope);
                new C_dialog_radio(getActivity(), "坡度", Arrays.asList(array), REQUEST_CODE_SLOPE);
                break;
            case REQUEST_CODE_SLOPEPOS:
                array = getResources().getStringArray(R.array.slope_pos);
                new C_dialog_radio(getActivity(), "坡位", Arrays.asList(array), REQUEST_CODE_SLOPEPOS);
                break;
            case REQUEST_CODE_SOIL:
                array = getResources().getStringArray(R.array.soil);
                new C_dialog_radio(getActivity(), "土壤", Arrays.asList(array), REQUEST_CODE_SOIL);
                break;
            case REQUEST_CODE_GROWSPACE:
                array = getResources().getStringArray(R.array.grown_space);
                new C_dialog_radio(getActivity(), "生长场所", Arrays.asList(array), REQUEST_CODE_GROWSPACE);
                break;
            case REQUEST_CODE_TREEAREA:
                array = getResources().getStringArray(R.array.tree_area);
                new C_dialog_radio(getActivity(), "生长地", Arrays.asList(array), REQUEST_CODE_TREEAREA);
                break;
        }
    }

    Disposable rxBusDisposable;

    private void register() {

        rxBusDisposable = RxBus.getDefault().toObservable(MessageEvent.class).subscribe(new Consumer<MessageEvent>() {
            @Override
            public void accept(MessageEvent messageEvent) throws Exception {
                Log.d(TAG, "accept: " + messageEvent.getId() + "\t" + messageEvent.getText());
                switch (messageEvent.getCode()) {
                    case Activity_add_tree.ResultCode.REQUEST_CODE_GROWTH:
                        mViewHolder.growth.setText(array[messageEvent.getId()]);
                        tree.setGrowth((messageEvent.getId() + 1) + "");
                        break;
                    case Activity_add_tree.ResultCode.REQUEST_CODE_ENVIRONMENT:
                        mViewHolder.environment.setText(array[messageEvent.getId()]);
                        tree.setEnviorment((messageEvent.getId() + 1) + "");
                        break;
                    case Activity_add_tree.ResultCode.REQUEST_CODE_STATUS:
                        mViewHolder.status.setText(array[messageEvent.getId()]);

                        tree.setTreeStatus((messageEvent.getId() + 1) + "");
                        break;
                    case Activity_add_tree.ResultCode.REQUEST_CODE_SPECIAL:
                        mViewHolder.special.setText(array[messageEvent.getId()]);
                        tree.setSpecial((messageEvent.getId()) + "");
                        break;
                    case Activity_add_tree.ResultCode.REQUEST_CODE_GSBZ:
                        mViewHolder.gsbz.setText(array[messageEvent.getId()]);
                        tree.setTreetype((messageEvent.getId()+1));
                        break;
                    case Activity_add_tree.ResultCode.REQUEST_CODE_OWNER:
                        mViewHolder.owner.setText(array[messageEvent.getId()]);
                        tree.setOwner((messageEvent.getId() + 1) + "");
                        break;
                   /* case REQUEST_CODE_LEVEL:
                        level.setText(array[messageEvent.getId()]);
                        break;*/
                    case Activity_add_tree.ResultCode.REQUEST_CODE_ASPECT:
                        mViewHolder.aspect.setText(array[messageEvent.getId()]);
                        tree.setAspect((messageEvent.getId()) + "");
                        break;
                    case Activity_add_tree.ResultCode.REQUEST_CODE_SLOPE:
                        mViewHolder.slope.setText(array[messageEvent.getId()]);
                        tree.setSlope((messageEvent.getId()) + "");
                        break;
                    case Activity_add_tree.ResultCode.REQUEST_CODE_SLOPEPOS:
                        mViewHolder.slopePos.setText(array[messageEvent.getId()]);
                        tree.setSlopePos((messageEvent.getId()) + "");
                        break;
                    case Activity_add_tree.ResultCode.REQUEST_CODE_SOIL:
                        mViewHolder.soil.setText(array[messageEvent.getId()]);
                        tree.setSoil((messageEvent.getId()) + "");
                        break;
                    case Activity_add_tree.ResultCode.REQUEST_CODE_PROTECTED:
                        Log.i(TAG, "accept: protected");
                        mViewHolder.protect.setText(messageEvent.getText());
                        tree.setProtecte("BH" + messageEvent.getRemark());
                        break;
                    case Activity_add_tree.ResultCode.REQUEST_CODE_RECOVERY:
                        mViewHolder.recovery.setText(messageEvent.getText());
                        tree.setRecovery("YH" + messageEvent.getRemark());
                        break;
                    case Activity_add_tree.ResultCode.REQUEST_CODE_GROWSPACE:
                        mViewHolder.growSpace.setText(messageEvent.getText());
                        tree.setGrownSpace(messageEvent.getId() + "");
                        break;
                    case Activity_add_tree.ResultCode.REQUEST_CODE_TREEAREA:
                        mViewHolder.treeArea.setText(messageEvent.getText());
                        tree.setTreearea(messageEvent.getId());
                        break;
                }
            }
        });
    }

    public void showDateDialog() {
        DateDialog dateDialog = new DateDialog(getActivity());
        dateDialog.setDateDialogListener(new DateDialog.DateDialogListener() {
            @Override
            public void getDate(Date date) {
                mViewHolder.researchDate.setText(getStringDate(date));
                treeTypeInfo.setDate(date);
                tree.setDate(date);
            }
        });
    }

    ArrayList<String> mList = new ArrayList<>();

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case REQUEST_IMAGE:
                if (resultCode == RESULT_OK) {
                    // Get the result mList of select image paths
                    mList = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    // do your logic ....
                    mViewHolder.picNew.setText(mList.size() + "");
                }
            case REQUEST_CODE_REGION:
                if (resultCode == 100) {
                    Activity_map.LocationBox box = data.getParcelableExtra("location");
                    if (box != null) {
                        tree.setRegion(box.getProvince() + box.getCity() + box.getDistrict());
                        tree.setSmallName(box.getStreet() + box.getSematicDescription());
                        tree.setAbscissa(box.getLat() + "");
                        tree.setOrdinate(box.getLon() + "");

                        mViewHolder.region.setText(tree.getRegion());
                        mViewHolder.detailAddress.setText(tree.getSmallName());
                        mViewHolder.latitude.setText(tree.getAbscissa());
                        mViewHolder.longitude.setText(tree.getOrdinate());
                    }
                }
                break;
            case REQUEST_CODE_CNAME:
                if (resultCode == Activity_tree_cname.REQUEST_CODE_CNAME_RESULT) {
                    TreeSpecial treeSpecial = data.getParcelableExtra("special");
                    tree.setTreespeid(treeSpecial.getTreeSpeId());
                    mViewHolder.cname.setText(treeSpecial.getCname());
                    mViewHolder.alias.setText(treeSpecial.getAlias());
                }
                break;
            default:
                break;
        }
//        super.onActivityResult(requestCode, resultCode, data);
    }


    private TreeSpecial getTreeSpecies(String treeSpeciesId) {
        TreeSpecialDao areaCodeDao = ((App) getActivity().getApplication()).getDaoSession().getTreeSpecialDao();
        List<TreeSpecial> list = areaCodeDao.queryBuilder().where(TreeSpecialDao.Properties.TreeSpeId.eq(treeSpeciesId)).build().list();
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public String check() {
        if (!mViewHolder.tch.getText().matches("\\d{5}")) {
            return "调查号为5位数字";
        }
        if (!mViewHolder.researchDate.getText().matches("^\\w{4}-\\w{2}-\\w{2}.*")) {
            return "请选择日期";
        }
        if (mViewHolder.detailAddress.getText().equals("")) {
            return "请完善地理信息";
        }
        if (mViewHolder.latitude.getText().equals("") || mViewHolder.longitude.getText().equals("")) {
            return "经纬度不能为空";
        }
        if (mViewHolder.town.getText().trim().equals("") || mViewHolder.village.getText().trim().equals("")) {
            return "城镇/村庄不能为空";
        }
        if (mViewHolder.cname.getText().equals("")) {
            return "选择树种类";
        }
        if (mViewHolder.height.getText().equals("")) {
            return "请填写树高";
        }
        if (mViewHolder.dbh.getText().equals("")) {
            return "请填写胸径";
        }
        if (mViewHolder.age.getText().equals("")) {
            return "请填写树龄";
        }
        if (mViewHolder.crownEW.getText().equals("")) {
            return "请填写东西冠幅";
        }
        if (mViewHolder.crownNS.getText().equals("")) {
            return "请填写南北冠幅";
        }
        if (mViewHolder.growth.getText().equals("")) {
            return "请选择生长势";
        }
        if (mViewHolder.environment.getText().equals("")) {
            return "请选择生长环境";
        }
        if (mViewHolder.status.getText().equals("")) {
            return "请选择状况";
        }
        if (mViewHolder.special.getText().equals("")) {
            return "请选择散生群状";
        }
        if (mViewHolder.gsbz.getText().equals("")) {
            return "请选择古树标志";
        }
        if (mViewHolder.owner.getText().equals("")) {
            return "请选择权属";
        }
        if (mViewHolder.growSpace.getText().equals("")) {
            return "请选择生长场所";
        }
        if (mViewHolder.treeArea.getText().equals("")) {
            return "请选择生长地";
        }
        if (mViewHolder.aspect.getText().equals("")) {
            return "请选择坡向";
        }
        if (mViewHolder.slope.getText().equals("")) {
            return "请选择坡度";
        }
        if (mViewHolder.slopePos.getText().equals("")) {
            return "请选择坡位";
        }
        if (mViewHolder.soil.getText().equals("")) {
            return "请选择土壤";
        }
        if (mViewHolder.protect.getText().equals("")) {
            return "请选择保护现状";
        }
        if (mViewHolder.recovery.getText().equals("")) {
            return "请选择养护现状";
        }
        if (mViewHolder.managerPerson.getText().equals("") && mViewHolder.mangerUnit.getText().equals("")) {
            return "管护单位和管护人不能同时为空";
        }
        if (mViewHolder.history.getText().equals("")) {
            return "请填写历史因素";
        }
        if (mList == null || mList.size() == 0) {
            return "请选择图片";
        }
        return null;

    }

    @Override
    public String getJsonStr() {
        viewToBean();
        picToString();
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat("yyyy-MM-dd").create().toJson(treeTypeInfo);
    }

    private void viewToBean() {
        treeTypeInfo.setIvst(mViewHolder.tch.getText());
        tree.setTown(mViewHolder.town.getText());
        tree.setVillage(mViewHolder.village.getText());
        tree.setAbscissa(mViewHolder.latitude.getText());
        tree.setOrdinate(mViewHolder.longitude.getText());
        tree.setEvevation(Double.parseDouble(mViewHolder.elevation.getText()));
        tree.setTreeHeight(Double.parseDouble(mViewHolder.height.getText()));
        tree.setTreeDBH(Double.parseDouble(mViewHolder.dbh.getText()));
        String strCrownEW = mViewHolder.crownEW.getText();
        String strCrownNS = mViewHolder.crownNS.getText();
        tree.setCrownEW(Double.parseDouble(strCrownEW));
        tree.setCrownNS(Double.parseDouble(strCrownNS));
        tree.setCrownAvg((tree.getCrownEW() + tree.getCrownEW()) / 2);
        tree.setManagementPersion(mViewHolder.managerPerson.getText());
        tree.setManagementUnit(mViewHolder.mangerUnit.getText());
        tree.setTreeHistory(mViewHolder.history.getText());
        tree.setRealAge(Double.parseDouble(mViewHolder.age.getText()));
        tree.setGuessAge(Double.parseDouble(mViewHolder.age.getText()));

        tree.setEnviorFactor(mViewHolder.environmentFactor.getText());
        tree.setSpecStatDesc(mViewHolder.specStatDesc.getText());
        tree.setSpecDesc(mViewHolder.specDesc.getText());
    }

    private void picToString() {
        FileUtil.clearFileDir();
        for (int i = 0; i < mList.size(); i++) {
            ScaleBitmap.getBitmap(mList.get(i), "image" + i + ".png");
        }
        tree.setPiclist(FileUtil.getPngFiles());
//        tree.setPiclist(new ArrayList<String>());
    }

    @Override
    public void onDetach() {
        rxBusDisposable.dispose();
        super.onDetach();
    }

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

    static class ViewHolder {
        @BindView(R.id.tch)
        C_info_gather_item1 tch;
        @BindView(R.id.research_date)
        C_info_gather_item1 researchDate;
        @BindView(R.id.region)
        C_info_gather_item1 region;
        @BindView(R.id.detail_address)
        C_info_gather_item1 detailAddress;
        @BindView(R.id.longitude)
        C_info_gather_item1 longitude;
        @BindView(R.id.latitude)
        C_info_gather_item1 latitude;
        @BindView(R.id.elevation)
        C_info_gather_item1 elevation;
        @BindView(R.id.town)
        C_info_gather_item1 town;
        @BindView(R.id.village)
        C_info_gather_item1 village;
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
        @BindView(R.id.grow_space)
        C_info_gather_item1 growSpace;
        @BindView(R.id.tree_area)
        C_info_gather_item1 treeArea;
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
        @BindView(R.id.pic_new)
        C_info_gather_item1 picNew;
        @BindView(R.id.tree_layout)
        LinearLayout treeLayout;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ResultMessage {

        /**
         * message : sus
         * error_code : 0
         * treeid : 61010200001
         * areaid : 610102
         * userid : 610102001
         * treetype : 0
         * result : {"treeid":"61010200001","treearea":0,"treetype":0,"treespeid":"T00222","town":"街道办","village":"街","smallname":"东半截巷长乐西路国税所附近19米","ordinate":"108.98564296806003","abscissa":"34.277625389592956","specialcode":0,"treeheight":10,"treeDBH":50,"crownavg":15,"crownew":10,"crownns":10,"managementunit":"无","managementpersion":"无","treehistory":"无","grownspace":"0","special":"0","growth":"1","enviorment":"1","slopepos":"2","enviorfactor":"无","specstatdesc":"无","specdesc":"无","explain":null,"realage":400,"guessage":400,"evevation":500,"Slope":"2","Protected":"BH23","Recovery":"YH34","Owner":"1","TreeLevel":"3","TreeStatus":"1","UserID":"张三","RecordTime":"2017-06-07 00:00:00","IVST":"12345","TypeID":0,"Emphasis":0,"Audit":0,"AreaID":"610102","aspect":"4","soil":"4","picInfo":["http://117.34.105.28:8055/Image/TreeInfo/610000/610100/610102/61010200112/1.jpg","http://117.34.105.28:8055/Image/TreeInfo/610000/610100/610102/61010200112/2.jpg"]}
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
             * treeid : 61010200001
             * treearea : 0
             * treetype : 0
             * treespeid : T00222
             * town : 街道办
             * village : 街
             * smallname : 东半截巷长乐西路国税所附近19米
             * ordinate : 108.98564296806003
             * abscissa : 34.277625389592956
             * specialcode : 0
             * treeheight : 10
             * treeDBH : 50
             * crownavg : 15
             * crownew : 10
             * crownns : 10
             * managementunit : 无
             * managementpersion : 无
             * treehistory : 无
             * grownspace : 0
             * special : 0
             * growth : 1
             * enviorment : 1
             * slopepos : 2
             * enviorfactor : 无
             * specstatdesc : 无
             * specdesc : 无
             * explain : null
             * realage : 400
             * guessage : 400
             * evevation : 500
             * Slope : 2
             * Protected : BH23
             * Recovery : YH34
             * Owner : 1
             * TreeLevel : 3
             * TreeStatus : 1
             * UserID : 张三
             * RecordTime : 2017-06-07 00:00:00
             * IVST : 12345
             * TypeID : 0
             * Emphasis : 0
             * Audit : 0
             * AreaID : 610102
             * aspect : 4
             * soil : 4
             * picInfo : ["http://117.34.105.28:8055/Image/TreeInfo/610000/610100/610102/61010200112/1.jpg","http://117.34.105.28:8055/Image/TreeInfo/610000/610100/610102/61010200112/2.jpg"]
             */

            @SerializedName("treeid")
            private String treeid;
            @SerializedName("treearea")
            private int treearea;
            @SerializedName("treetype")
            private int treetype;
            @SerializedName("treespeid")
            private String treespeid;
            @SerializedName("town")
            private String town;
            @SerializedName("village")
            private String village;
            @SerializedName("smallname")
            private String smallname;
            @SerializedName("ordinate")
            private String ordinate;
            @SerializedName("abscissa")
            private String abscissa;
            @SerializedName("specialcode")
            private int specialcode;
            @SerializedName("treeheight")
            private double treeheight;
            @SerializedName("treeDBH")
            private double treeDBH;
            @SerializedName("crownavg")
            private double crownavg;
            @SerializedName("crownew")
            private double crownew;
            @SerializedName("crownns")
            private double crownns;
            @SerializedName("managementunit")
            private String managementunit;
            @SerializedName("managementpersion")
            private String managementpersion;
            @SerializedName("treehistory")
            private String treehistory;
            @SerializedName("grownspace")
            private String grownspace;
            @SerializedName("special")
            private String special;
            @SerializedName("growth")
            private String growth;
            @SerializedName("enviorment")
            private String enviorment;
            @SerializedName("slopepos")
            private String slopepos;
            @SerializedName("enviorfactor")
            private String enviorfactor;
            @SerializedName("specstatdesc")
            private String specstatdesc;
            @SerializedName("specdesc")
            private String specdesc;
            @SerializedName("explain")
            private Object explain;
            @SerializedName("realage")
            private double realage;
            @SerializedName("guessage")
            private double guessage;
            @SerializedName("evevation")
            private double evevation;
            @SerializedName("Slope")
            private String Slope;
            @SerializedName("Protected")
            private String Protected;
            @SerializedName("Recovery")
            private String Recovery;
            @SerializedName("Owner")
            private String Owner;
            @SerializedName("TreeLevel")
            private String TreeLevel;
            @SerializedName("TreeStatus")
            private String TreeStatus;
            @SerializedName("UserID")
            private String UserID;
            @SerializedName("RecordTime")
            private String RecordTime;
            @SerializedName("IVST")
            private String IVST;
            @SerializedName("TypeID")
            private int TypeID;
            @SerializedName("Emphasis")
            private int Emphasis;
            @SerializedName("Audit")
            private int Audit;
            @SerializedName("AreaID")
            private String AreaID;
            @SerializedName("aspect")
            private String aspect;
            @SerializedName("soil")
            private String soil;
            @SerializedName("picInfo")
            private List<String> picInfo;

            public String getTreeid() {
                return treeid;
            }

            public void setTreeid(String treeid) {
                this.treeid = treeid;
            }

            public int getTreearea() {
                return treearea;
            }

            public void setTreearea(int treearea) {
                this.treearea = treearea;
            }

            public int getTreetype() {
                return treetype;
            }

            public void setTreetype(int treetype) {
                this.treetype = treetype;
            }

            public String getTreespeid() {
                return treespeid;
            }

            public void setTreespeid(String treespeid) {
                this.treespeid = treespeid;
            }

            public String getTown() {
                return town;
            }

            public void setTown(String town) {
                this.town = town;
            }

            public String getVillage() {
                return village;
            }

            public void setVillage(String village) {
                this.village = village;
            }

            public String getSmallname() {
                return smallname;
            }

            public void setSmallname(String smallname) {
                this.smallname = smallname;
            }

            public String getOrdinate() {
                return ordinate;
            }

            public void setOrdinate(String ordinate) {
                this.ordinate = ordinate;
            }

            public String getAbscissa() {
                return abscissa;
            }

            public void setAbscissa(String abscissa) {
                this.abscissa = abscissa;
            }

            public int getSpecialcode() {
                return specialcode;
            }

            public void setSpecialcode(int specialcode) {
                this.specialcode = specialcode;
            }

            public double getTreeheight() {
                return treeheight;
            }

            public void setTreeheight(int treeheight) {
                this.treeheight = treeheight;
            }

            public double getTreeDBH() {
                return treeDBH;
            }

            public void setTreeDBH(int treeDBH) {
                this.treeDBH = treeDBH;
            }

            public double getCrownavg() {
                return crownavg;
            }

            public void setCrownavg(int crownavg) {
                this.crownavg = crownavg;
            }

            public double getCrownew() {
                return crownew;
            }

            public void setCrownew(int crownew) {
                this.crownew = crownew;
            }

            public double getCrownns() {
                return crownns;
            }

            public void setCrownns(int crownns) {
                this.crownns = crownns;
            }

            public String getManagementunit() {
                return managementunit;
            }

            public void setManagementunit(String managementunit) {
                this.managementunit = managementunit;
            }

            public String getManagementpersion() {
                return managementpersion;
            }

            public void setManagementpersion(String managementpersion) {
                this.managementpersion = managementpersion;
            }

            public String getTreehistory() {
                return treehistory;
            }

            public void setTreehistory(String treehistory) {
                this.treehistory = treehistory;
            }

            public String getGrownspace() {
                return grownspace;
            }

            public void setGrownspace(String grownspace) {
                this.grownspace = grownspace;
            }

            public String getSpecial() {
                return special;
            }

            public void setSpecial(String special) {
                this.special = special;
            }

            public String getGrowth() {
                return growth;
            }

            public void setGrowth(String growth) {
                this.growth = growth;
            }

            public String getEnviorment() {
                return enviorment;
            }

            public void setEnviorment(String enviorment) {
                this.enviorment = enviorment;
            }

            public String getSlopepos() {
                return slopepos;
            }

            public void setSlopepos(String slopepos) {
                this.slopepos = slopepos;
            }

            public String getEnviorfactor() {
                return enviorfactor;
            }

            public void setEnviorfactor(String enviorfactor) {
                this.enviorfactor = enviorfactor;
            }

            public String getSpecstatdesc() {
                return specstatdesc;
            }

            public void setSpecstatdesc(String specstatdesc) {
                this.specstatdesc = specstatdesc;
            }

            public String getSpecdesc() {
                return specdesc;
            }

            public void setSpecdesc(String specdesc) {
                this.specdesc = specdesc;
            }

            public Object getExplain() {
                return explain;
            }

            public void setExplain(Object explain) {
                this.explain = explain;
            }

            public double getRealage() {
                return realage;
            }

            public void setRealage(double realage) {
                this.realage = realage;
            }

            public double getGuessage() {
                return guessage;
            }

            public void setGuessage(int guessage) {
                this.guessage = guessage;
            }

            public double getEvevation() {
                return evevation;
            }

            public void setEvevation(int evevation) {
                this.evevation = evevation;
            }

            public String getSlope() {
                return Slope;
            }

            public void setSlope(String Slope) {
                this.Slope = Slope;
            }

            public String getProtected() {
                return Protected;
            }

            public void setProtected(String Protected) {
                this.Protected = Protected;
            }

            public String getRecovery() {
                return Recovery;
            }

            public void setRecovery(String Recovery) {
                this.Recovery = Recovery;
            }

            public String getOwner() {
                return Owner;
            }

            public void setOwner(String Owner) {
                this.Owner = Owner;
            }

            public String getTreeLevel() {
                return TreeLevel;
            }

            public void setTreeLevel(String TreeLevel) {
                this.TreeLevel = TreeLevel;
            }

            public String getTreeStatus() {
                return TreeStatus;
            }

            public void setTreeStatus(String TreeStatus) {
                this.TreeStatus = TreeStatus;
            }

            public String getUserID() {
                return UserID;
            }

            public void setUserID(String UserID) {
                this.UserID = UserID;
            }

            public String getRecordTime() {
                return RecordTime;
            }

            public void setRecordTime(String RecordTime) {
                this.RecordTime = RecordTime;
            }

            public String getIVST() {
                return IVST;
            }

            public void setIVST(String IVST) {
                this.IVST = IVST;
            }

            public int getTypeID() {
                return TypeID;
            }

            public void setTypeID(int TypeID) {
                this.TypeID = TypeID;
            }

            public int getEmphasis() {
                return Emphasis;
            }

            public void setEmphasis(int Emphasis) {
                this.Emphasis = Emphasis;
            }

            public int getAudit() {
                return Audit;
            }

            public void setAudit(int Audit) {
                this.Audit = Audit;
            }

            public String getAreaID() {
                return AreaID;
            }

            public void setAreaID(String AreaID) {
                this.AreaID = AreaID;
            }

            public String getAspect() {
                return aspect;
            }

            public void setAspect(String aspect) {
                this.aspect = aspect;
            }

            public String getSoil() {
                return soil;
            }

            public void setSoil(String soil) {
                this.soil = soil;
            }

            public List<String> getPicInfo() {
                return picInfo;
            }

            public void setPicInfo(List<String> picInfo) {
                this.picInfo = picInfo;
            }
        }
    }
}
