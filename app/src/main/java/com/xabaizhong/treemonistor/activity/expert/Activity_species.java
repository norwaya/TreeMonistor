package com.xabaizhong.treemonistor.activity.expert;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.activity.base_data.Activity_tree_base_detail;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.base.App;
import com.xabaizhong.treemonistor.contant.UserSharedField;
import com.xabaizhong.treemonistor.entity.TreeSpecial;
import com.xabaizhong.treemonistor.entity.TreeSpecialDao;
import com.xabaizhong.treemonistor.myview.C_dialog_radio;
import com.xabaizhong.treemonistor.myview.C_info_gather_item1;
import com.xabaizhong.treemonistor.service.AsyncTaskRequest;
import com.xabaizhong.treemonistor.service.WebserviceHelper;
import com.xabaizhong.treemonistor.service.model.SpeciesResult;
import com.xabaizhong.treemonistor.utils.FileUtil;
import com.xabaizhong.treemonistor.utils.MessageEvent;
import com.xabaizhong.treemonistor.utils.RxBus;
import com.xabaizhong.treemonistor.utils.ScaleBitmap;

import java.io.File;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

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
    @BindView(R.id.pb)
    RelativeLayout pb;
    @BindView(R.id.btn_image)
    C_info_gather_item1 btnImage;
    @BindView(R.id.layout_need_upload)
    LinearLayout layoutNeedUpload;

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
        pb.setOnClickListener(null);

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
        btnImage.setCallback_mid(this);
    }

    boolean flag = true;

    @OnClick(R.id.submit)
    public void onViewClicked() {
        if (flag) {
            if (params.check()) {
                pb.setVisibility(View.VISIBLE);
                AsyncTaskRequest.instance("CheckUp", "SpeciesIden")
                        .setParams(getParms())
                        .setCallBack(new AsyncTaskRequest.CallBack() {
                            @Override
                            public void execute(String s) {

                                if (s == null) {
                                    showToast("请求错误");
                                    return;
                                }
                                Log.i(TAG, "onPostExecute: " + s);
                                SpeciesResult result = new Gson().fromJson(s, SpeciesResult.class);
                                if (result.getResult() != null && result.getResult().size() > 0) {
                                    speList = result.getResult();
                                    showSpeciesDialog();
                                } else {
                                    showToast("系统没有匹配到合适的树种");
                                }
                            }
                        })
                        .create();
            }
        } else {
            if (list.size() < 1) {
                showToast("添加图片");

            } else {
                FileUtil.clearFileDir();
                int index = 0;
                for (String filePath : list
                        ) {
                    index++;
                    ScaleBitmap.getBitmap(filePath, "a" + index + ".png");
                }

                AsyncTaskRequest.instance("CheckUp", "SpeciesIden")
                        .setParams(getUploadParas())
                        .setCallBack(new AsyncTaskRequest.CallBack() {
                            @Override
                            public void execute(String s) {
                                Log.i(TAG, "execute: " + s);
                            }
                        })
                        .create();

            }
        }
    }


    private Map<String, Object> getUploadParas() {
        Map<String, Object> map = new HashMap<>();
        params.picList.clear();
        for (File file : FileUtil.getFiles()
                ) {
            params.picList.add(FileUtil.bitmapToBase64Str(file));
        }
        String user_id = sharedPreferences.getString(UserSharedField.USERID, "");
        map.put("UserID ", user_id);
        map.put("JsonStr", new Gson().toJson(params));
        return map;
    }

   /* AsyncTask asyncTask;

    private void request() {
        asyncTask = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    return WebserviceHelper.GetWebService(
                            "CheckUp", "SpeciesIden", getParms());
                } catch (ConnectException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
                pb.setVisibility(View.INVISIBLE);

            }
        }.execute();
    }*/

    List<String> speList;


    private Map<String, Object> getParms() {
        Map<String, Object> map = new HashMap<>();
        String user_id = sharedPreferences.getString(UserSharedField.USERID, "");
        /*<UserID>string</UserID>
      <leafShape>int</leafShape>
      <leafColor>int</leafColor>
      <flwerType>int</flwerType>
      <flowerColor>int</flowerColor>
      <fruitType>int</fruitType>
      <fruitColor>int</fruitColor>*/
        map.put("UserID ", user_id);
        map.put("JsonStr", new Gson().toJson(params));
        return map;
    }


    void showSpeciesDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.activity_species_dialog, null);

        initialSpeciesDialogView(view);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("树种鉴定结果")
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("都不是，上报", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        display();

                    }
                }).create().show();
    }

    private void display() {
        //// TODO: 2017/4/21 0021  显示这些
        layoutNeedUpload.setVisibility(View.VISIBLE);
        flag = false;
        submit.setText("上传");
    }

    DialogListAdapter adapter;

    private void initialSpeciesDialogView(View view) {
        adapter = new DialogListAdapter();
        ListView lv = ((ListView) view.findViewById(R.id.lv));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent i = new Intent(Activity_species.this, Activity_tree_base_detail.class);
                i.putExtra("id", getId(speList.get(position)));
                startActivity(i);
            }
        });
        lv.setAdapter(adapter);
    }

    class DialogListAdapter extends BaseAdapter {

        LayoutInflater layoutInflater;

        public DialogListAdapter() {
            layoutInflater = LayoutInflater.from(Activity_species.this);
        }

        @Override
        public int getCount() {
            return speList.size();
        }

        @Override
        public Object getItem(int position) {
            return speList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return speList.get(position).hashCode();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            TextView tv = (TextView) layoutInflater.inflate(R.layout.text1, parent, false);
            String cname = getCname(speList.get(position));
            tv.setText(cname);
            return tv;
        }

        private String getCname(String speId) {
            TreeSpecialDao dao = ((App) getApplicationContext().getApplicationContext()).getDaoSession().getTreeSpecialDao();

            List<TreeSpecial> list = dao.queryBuilder().where(TreeSpecialDao.Properties.TreeSpeId.eq(speId)).build().list();
            if (list != null && list.size() > 0) {
                return list.get(0).getCname();
            }
            return null;
        }


    }

    private Long getId(String speId) {
        TreeSpecialDao dao = ((App) getApplicationContext().getApplicationContext()).getDaoSession().getTreeSpecialDao();

        List<TreeSpecial> list = dao.queryBuilder().where(TreeSpecialDao.Properties.TreeSpeId.eq(speId)).build().list();
        if (list != null && list.size() > 0) {
            return list.get(0).getId();
        }
        return null;
    }


    String[] leafArray = new String[]{"椭圆状", "心形", "掌形", "扇形", "菱形", "披针形", "卵形", "圆形", "针形", "鳞形", "匙形", "三角形"
    };
    String[] leafColorArray = new String[]{"其他", "绿色", "红色", "黄色", "蓝色"};
    String[] flowerArray = new String[]{"乔木花卉", " 灌木花卉", "藤本花卉"};
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
    String[] fruitArray = new String[]{"单果", " 聚合果", " 复果"};
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
    private static final int IMAGE_REQUEST_CDOE = 907;

    ArrayList<String> list = new ArrayList<>();

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
            case R.id.btn_image:
                MultiImageSelector.create(getApplicationContext())
                        .showCamera(true) // show camera or not. true by default
                        .count(5) // max select image size, 9 by default. used width #.multi()
                        .single() // single mode
                        .multi() // multi mode, default mode;
                        .origin(list) // original select data set, used width #.multi()
                        .start(this, IMAGE_REQUEST_CDOE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST_CDOE && resultCode == -1) {
            list = data.getStringArrayListExtra("select_result");
            btnImage.setText(list.size() + "");
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
        @SerializedName("picList")
        List<String> picList = new ArrayList<>();

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
//        if (asyncTask != null && !asyncTask.isCancelled()) {
//            asyncTask.cancel(true);
//        }
    }
}
