package com.xabaizhong.treemonistor.activity.expert;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.activity.add_tree.Activity_add_tree;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.myview.C_dialog_radio;
import com.xabaizhong.treemonistor.myview.C_info_gather_item1;
import com.xabaizhong.treemonistor.utils.MessageEvent;
import com.xabaizhong.treemonistor.utils.RxBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

import static com.xabaizhong.treemonistor.activity.monitor.fragment.Fragment_tree.ResultCode.REQUEST_CODE_GROWTH;

/**
 * 生长势 鉴定
 */
public class Activity_monitor_growth extends Activity_base {
    @BindView(R.id.c1)
    C_info_gather_item1 c1;
    @BindView(R.id.c2)
    C_info_gather_item1 c2;
    @BindView(R.id.c3)
    C_info_gather_item1 c3;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_growth);
        ButterKnife.bind(this);
        init();
        initView();
        register();
    }

    GrowthIndexs growthIndexs;

    private void init() {
        growthIndexs = new GrowthIndexs();
    }

    private void initView() {

        c1.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                showRadioDialog(RequestCode.LEAF_CODE);
            }
        });
        c2.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                showRadioDialog(RequestCode.ZT_CODE);
            }
        });
        c3.setCallback_mid(new C_info_gather_item1.Mid_CallBack() {
            @Override
            public void onClickListener(View et) {
                showRadioDialog(RequestCode.SG_CODE);
            }
        });

    }

    static class RequestCode {
        private static final int LEAF_CODE = 127;
        private static final int ZT_CODE = 309;
        private static final int SG_CODE = 100;
    }

    String[] levelArray = {"正常株", "衰弱株", "濒危株", "死亡株"};
    String[] array;
    int level = 0;

    public void showRadioDialog(int Request) {
        switch (Request) {
            case RequestCode.LEAF_CODE:
                array = new String[]{"正常叶片量占叶片总量大于95%", "正常叶片量占叶片总量的95%-50%", "正常叶片量占叶片总量小于50%", "无正常叶片。", "枝条枯死，无新梢和萌条"};
                new C_dialog_radio(this, "叶片", Arrays.asList(array), RequestCode.LEAF_CODE);
                break;
            case RequestCode.ZT_CODE:
                array = new String[]{"枝条生长正常、新枝数量多，无枯枝枯梢", "新梢生长偏弱，枝条有少量枯死", "枝杈枯死较多。", "枝条枯死，无新梢和萌条"};
                new C_dialog_radio(this, "枝条", Arrays.asList(array), RequestCode.ZT_CODE);
                break;
            case RequestCode.SG_CODE:
                array = new String[]{"树干基本完好无坏死。", "树干局部有轻伤或少量坏死", "树干多为坏死，干枯或成凹洞", "树干坏死，损伤，腐朽现象严重，无活树皮"};
                new C_dialog_radio(this, "树干", Arrays.asList(array), RequestCode.SG_CODE);
                break;
        }
    }

    public void register() {
        RxBus.getDefault().toObservable(MessageEvent.class).subscribe(new Consumer<MessageEvent>() {
            @Override
            public void accept(MessageEvent messageEvent) throws Exception {
                Log.d(TAG, "accept: " + messageEvent.getId() + "\t" + messageEvent.getText());
                switch (messageEvent.getCode()) {
                    case RequestCode.LEAF_CODE:
                        c1.setText(array[messageEvent.getId()]);
                        growthIndexs.c1 = messageEvent.getId();
                        break;
                    case RequestCode.ZT_CODE:
                        c2.setText(array[messageEvent.getId()]);
                        growthIndexs.c2 = messageEvent.getId();
                        break;
                    case RequestCode.SG_CODE:
                        c3.setText(array[messageEvent.getId()]);
                        growthIndexs.c3 = messageEvent.getId();
                        break;
                }
            }
        });
    }

    static class GrowthIndexs {
        int c1 = -1;
        int c2 = -1;
        int c3 = -1;

        boolean check() {
            return c1 != -1 && c2 != -1 && c3 != -1;
        }
    }

    int result;

    @OnClick(R.id.submit)
    public void onViewClicked() {
        if (growthIndexs.check()) {
            result = Math.max(growthIndexs.c1, Math.max(growthIndexs.c2, growthIndexs.c3));
            resultDialog(levelArray[result]);
        }else{
            showToast("select all");
        }
    }

    private void resultDialog(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("结果")
                .setMessage(msg)
                .setNegativeButton("查看", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Activity_monitor_growth.this, Activity_expert_growth_detail.class);
                        intent.putExtra("type", result);
                        startActivity(intent);
                    }
                }).setPositiveButton("关闭", null);
        builder.create().show();
    }


}
