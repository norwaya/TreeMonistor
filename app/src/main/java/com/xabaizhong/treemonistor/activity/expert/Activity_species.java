package com.xabaizhong.treemonistor.activity.expert;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.activity.add_tree.Activity_add_tree;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.contant.UserSharedField;
import com.xabaizhong.treemonistor.myview.C_dialog_radio;
import com.xabaizhong.treemonistor.myview.C_info_gather_item1;
import com.xabaizhong.treemonistor.service.WebserviceHelper;
import com.xabaizhong.treemonistor.service.model.ResultMessage;
import com.xabaizhong.treemonistor.utils.MessageEvent;
import com.xabaizhong.treemonistor.utils.RxBus;

import java.net.ConnectException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by norwaya on 17-4-15.
 */

public class Activity_species extends Activity_base implements C_info_gather_item1.Mid_CallBack {
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.leaf)
    C_info_gather_item1 leaf;
    @BindView(R.id.leaf_color)
    C_info_gather_item1 leafColor;
    @BindView(R.id.flower)
    C_info_gather_item1 flower;
    @BindView(R.id.flower_color)
    C_info_gather_item1 flowerColor;
    @BindView(R.id.fruit)
    C_info_gather_item1 fruit;
    @BindView(R.id.fruit_color)
    C_info_gather_item1 fruitColor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_species);
        ButterKnife.bind(this);
        initialSource();
        initialView();
    }

    Disposable disposable;
    Params params;

    private void initialSource() {
        params = new Params();
        disposable = RxBus.getDefault().toObservable(MessageEvent.class).subscribe(new Consumer<MessageEvent>() {
            @Override
            public void accept(MessageEvent messageEvent) throws Exception {
                switch (messageEvent.getCode()) {
                    case LEAF_CODE:
                        params.leaf = messageEvent.getId() + 1;
                        leaf.setText(messageEvent.getText());
                        break;
                    case LEAF_COLOR_CODE:
                        params.leafColor = messageEvent.getId();
                        leafColor.setText(messageEvent.getText());
                        break;
                    case FLOWER_CODE:
                        params.flower = messageEvent.getId() + 1;
                        flower.setText(messageEvent.getText());
                        break;
                    case FLOWER_COLOR_CODE:
                        params.flowerColor = messageEvent.getId();
                        flowerColor.setText(messageEvent.getText());
                        break;
                    case FRUIT_CODE:
                        params.fruit = messageEvent.getId() + 1;
                        fruit.setText(messageEvent.getText());
                        break;
                    case FRUIT_COLOR_CODE:
                        params.fruitColor = messageEvent.getId();
                        fruitColor.setText(messageEvent.getText());
                        break;
                }
            }
        });
    }

    private void initialView() {
        leaf.setCallback_mid(this);
        leafColor.setCallback_mid(this);
        flower.setCallback_mid(this);
        flowerColor.setCallback_mid(this);
        fruit.setCallback_mid(this);
        fruitColor.setCallback_mid(this);
    }

    @OnClick(R.id.submit)
    public void onViewClicked() {
        if (params.check()) {
            request();
        }
    }

    AsyncTask asyncTask;

    private void request() {
        asyncTask = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    return WebserviceHelper.GetWebService(
                            "CheckUp", "CheckUp1", getParms());
                } catch (ConnectException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            @Override
            protected void onPostExecute(String s) {
                if (s == null) {
                    showToast("请求错误");
                    return;
                }
                Log.i(TAG, "onPostExecute: " + s);
            }
        }.execute();
    }


    private Map<String, Object> getParms() {
        Map<String, Object> map = new HashMap<>();
        String user_id = sharedPreferences.getString(UserSharedField.USERID, "");

        map.put("UserID ", user_id);
        map.put("Type", 0);
        String json = new Gson().toJson(params);
        Log.i(TAG, "getParms: "+json);
        map.put("JsonStr", json);
        return map;
    }




    String[] leafArray = new String[]{"椭圆状", "心形", "掌形", "扇形", "菱形", "披针形", "卵形", "圆形", "针形", "鳞形", "匙形", "三角形"
    };
    String[] leafColorArray = new String[]{"其他", "绿色", "红色", "黄色", "蓝色" };
    String[] flowerArray = new String[]{"乔木花卉", " 灌木花卉", "藤本花卉" };
    String[] flowerColorArray = new String[]{"其他",
            "红色",
            "橙色",
            "黄色",
            "绿色",
            "蓝色",
            "紫色",
            "黑色",
            "褐色",
            "白色",
            "粉红色"
    };
    String[] fruitArray = new String[]{"单果", " 聚合果", " 复果" };
    String[] fruitColorArray = new String[]{"其他",
            "白色",
            "红色",
            "绿色",
            "紫色",
            "黄色",
            "粉色",
            "褐色",
            "黑色"
    };

    private static final int LEAF_CODE = 61;
    private static final int LEAF_COLOR_CODE = 740;
    private static final int FLOWER_CODE = 882;
    private static final int FLOWER_COLOR_CODE = 994;
    private static final int FRUIT_CODE = 362;
    private static final int FRUIT_COLOR_CODE = 235;

    @Override
    public void onClickListener(View et) {
        switch (et.getId()) {
            case R.id.leaf:
                new C_dialog_radio(this, "叶分类", Arrays.asList(leafArray), LEAF_CODE);
                break;
            case R.id.leaf_color:
                new C_dialog_radio(this, "叶颜色", Arrays.asList(leafColorArray), LEAF_COLOR_CODE);
                break;
            case R.id.flower:
                new C_dialog_radio(this, "花", Arrays.asList(flowerArray), FLOWER_CODE);
                break;
            case R.id.flower_color:
                new C_dialog_radio(this, "花颜色", Arrays.asList(flowerColorArray), FLOWER_COLOR_CODE);
                break;
            case R.id.fruit:
                new C_dialog_radio(this, "果实", Arrays.asList(fruitArray), FRUIT_CODE);
                break;
            case R.id.fruit_color:
                new C_dialog_radio(this, "果实颜色", Arrays.asList(fruitColorArray), FRUIT_COLOR_CODE);
                break;
        }
    }

    private static class Params {
        /* int LeafShape;
         int LeafColor;
         int FlowerType;
         int FlowerColor;
         int FruitType;
         int FruitColor;*/
        @SerializedName("LeafShape")
        private int leaf = -1;
        @SerializedName("LeafColor")
        private int leafColor = -1;
        @SerializedName("FlowerType")
        private int flower = -1;
        @SerializedName("FlowerColor")
        private int flowerColor = -1;
        @SerializedName("FruitType")
        private int fruit = -1;
        @SerializedName("FruitColor")
        private int fruitColor = -1;

        private boolean check() {
            Log.i("check", "check: " + leaf + "\n" + leafColor + "\n" + flower + "\n" + flowerColor + "\n" + fruit + "\n" + fruitColor);
            if (leaf == -1 || leafColor == -1 || flower == -1 || flowerColor == -1 || fruit == -1 || fruitColor == -1) {
                return false;
            }
            return true;
        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        if (asyncTask != null && !asyncTask.isCancelled()) {
            asyncTask.cancel(true);
        }
    }
}
