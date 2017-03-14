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
import com.xabaizhong.treemonistor.myview.C_dialog_radio;
import com.xabaizhong.treemonistor.myview.C_info_gather_item1;
import com.xabaizhong.treemonistor.utils.MessageEvent;
import com.xabaizhong.treemonistor.utils.RxBus;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

import static com.xabaizhong.treemonistor.activity.add_tree.Activity_add_tree.ResultCode.REQUEST_CODE_CNAME;
import static com.xabaizhong.treemonistor.activity.add_tree.Activity_add_tree.ResultCode.REQUEST_CODE_CNAME_RESULT;
import static com.xabaizhong.treemonistor.activity.add_tree.Activity_add_tree.ResultCode.REQUEST_CODE_ENVIRONMENT;
import static com.xabaizhong.treemonistor.activity.add_tree.Activity_add_tree.ResultCode.REQUEST_CODE_GROWTH;
import static com.xabaizhong.treemonistor.activity.add_tree.Activity_add_tree.ResultCode.REQUEST_CODE_REGION;

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
    @BindView(R.id.level)
    C_info_gather_item1 level;
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
        cname.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                startActivityForResult(new Intent(Activity_add_tree.this, Activity_tree_cname.class), REQUEST_CODE_CNAME);
            }
        });
        growth.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
               showRadioDialog(REQUEST_CODE_GROWTH);
            }
        });
        environment.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                showRadioDialog(REQUEST_CODE_ENVIRONMENT);
            }
        });
        region.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                startActivityForResult(new Intent(Activity_add_tree.this, Activity_map.class), REQUEST_CODE_REGION);
            }
        });

    }

    private void initCNameDialog() {

    }


    @OnClick(R.id.btn)
    public void onClick() {
//        Snackbar.make(layout,"abc",Snackbar.LENGTH_LONG).show();
       /* MultiImageSelector.create(getApplicationContext())
                .showCamera(true) // show camera or not. true by default
                .count(4) // max select image size, 9 by default. used width #.multi()
                .single() // single mode
                .multi() // multi mode, default mode;
                .origin(list) // original select data set, used width #.multi()
                .start(Activity_add_tree.this, REQUEST_IMAGE);*/


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

            case ResultCode.REQUEST_IMAGE:
                if (resultCode == RESULT_OK) {
                    // Get the result list of select image paths
                    list = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    // do your logic ....
                    Log.d(TAG, "onActivityResult: " + list.size());
                }
            case REQUEST_CODE_REGION:
                if (resultCode == ResultCode.REQUEST_CODE_REGION_RESULT) {
                    showToast("back from map");
                    Activity_map.LocationBox box = data.getParcelableExtra("location");
                    if (box != null) {
                        region.setText(box.getProvince() + box.getCity() + box.getDistrict());
                        detailAddress.setText(box.getStreet() + box.getSematicDescription());
                    }
                }

                break;
            case REQUEST_CODE_CNAME:
                if(resultCode == REQUEST_CODE_CNAME_RESULT){
                    TreeSpecial treeSpecial = data.getParcelableExtra("special");
                    tree.setTreeSpeID(treeSpecial.getCode()+"");
                    cname.setText(treeSpecial.getCname());
                    alias.setText(treeSpecial.getAlias());
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public interface ResultCode {
        int REQUEST_CODE_CNAME = 0x103;
        int REQUEST_CODE_CNAME_RESULT = 103;


        int REQUEST_CODE_REGION = 0x100;
        int REQUEST_CODE_REGION_RESULT = 100;


        int REQUEST_CODE_GROWTH = 104;
        int REQUEST_CODE_ENVIRONMENT = 105;
        int REQUEST_CODE_ENVIRONMENT2 = 106;
        int REQUEST_CODE_ENVIRONMENT3 = 107;
        int REQUEST_CODE_ENVIRONMENT4 = 108;
        int REQUEST_CODE_ENVIRONMENT5 = 109;
        int REQUEST_CODE_ENVIRONMENT6 = 110;
        int REQUEST_CODE_ENVIRONMENT7 = 111;
        int REQUEST_CODE_ENVIRONMENT8 = 112;
        int REQUEST_CODE_ENVIRONMENT9 = 113;


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
        }
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
