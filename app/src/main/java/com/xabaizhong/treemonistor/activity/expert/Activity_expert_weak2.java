package com.xabaizhong.treemonistor.activity.expert;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.activity.Activity_main;
import com.xabaizhong.treemonistor.activity.base_data.Activity_base_weakness_detail;
import com.xabaizhong.treemonistor.adapter.Activity_expert_weak2_adapter;
import com.xabaizhong.treemonistor.adapter.CommonRecyclerViewAdapter;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.base.App;
import com.xabaizhong.treemonistor.contant.UserSharedField;
import com.xabaizhong.treemonistor.entity.DaoSession;
import com.xabaizhong.treemonistor.entity.Weakness;
import com.xabaizhong.treemonistor.entity.WeaknessDao;
import com.xabaizhong.treemonistor.service.WebserviceHelper;
import com.xabaizhong.treemonistor.service.model.ResultMessage;
import com.xabaizhong.treemonistor.utils.FileUtil;
import com.xabaizhong.treemonistor.utils.ScaleBitmap;

import java.util.ArrayList;
import java.util.Arrays;
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
import io.reactivex.schedulers.Schedulers;
import me.nereo.multi_image_selector.MultiImageSelector;


public class Activity_expert_weak2 extends Activity_base implements CommonRecyclerViewAdapter.CallBack<Activity_expert_weak.ViewHolder, Weakness> {
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.pic)
    TextView pic;
    @BindView(R.id.layout)
    LinearLayout layout;
    @BindView(R.id.pb)
    RelativeLayout pb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_weak);
        ButterKnife.bind(this);
        initialView();
        fillData();
    }

    List<Weakness> mList;
    int partId;

    private void fillData() {
        partId = getIntent().getIntExtra("part", -1);
        Log.i(TAG, "fillData: " + partId);
        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        WeaknessDao weaknessDao = daoSession.getWeaknessDao();
        mList = weaknessDao
                .queryBuilder()
                .where(WeaknessDao.Properties.PartId.eq(partId))
                .build()
                .list();
        Log.i(TAG, "fillData: " + mList.size());
        adapter.setSource(mList);
    }

    Activity_expert_weak2_adapter adapter;

    private void initialView() {
        layout.setVisibility(View.GONE);
        pb.setVisibility(View.INVISIBLE);
        pb.setOnClickListener(null);
        //initial adapter;
        adapter = new Activity_expert_weak2_adapter(this, R.layout.activity_expert_weak_item);
        adapter.setCallBack(this);
        //initial RecyclerView
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
    }


    @Override
    public void bindView(Activity_expert_weak.ViewHolder holder, int position, List<Weakness> list) {
        holder.title.setText(list.get(position).getFreature());
    }

    int position;

    @Override
    public void onItemClickListener(View view, int position) {
        this.position = position;
        showDialog();
    }


    private void showDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.simple_text, null);
        initialDialogView(view);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        builder.setTitle("病害结果")
                .setView(view)
                .setPositiveButton("确定", null)
                .setNegativeButton("拍照并上报", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        layout.setVisibility(View.VISIBLE);
                    }
                })
                .create().show();
    }

    private void initialDialogView(View view) {

        TextView tv = ((TextView) view.findViewById(R.id.text1));
        tv.setText(mList.get(position).getName());
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_expert_weak2.this, Activity_base_weakness_detail.class);
                i.putExtra("id", mList.get(position).getId());
                startActivity(i);
            }
        });
    }

    ArrayList<String> picList;
    private static final int IMAGE_REQUEST_CDOE = 575;

    @OnClick({R.id.layout_btn, R.id.btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_btn:
                MultiImageSelector.create(getApplicationContext())
                        .showCamera(true) // show camera or not. true by default
                        .count(5) // max select image size, 9 by default. used width #.multi()
                        .single() // single mode
                        .multi() // multi mode, default mode;
                        .origin(picList) // original select data set, used width #.multi()
                        .start(this, IMAGE_REQUEST_CDOE);
                break;
            case R.id.btn:
                if (picList == null || picList.size() == 0) {
                    showToast("请先选择图片");
                    return;
                }
                if (mDisposable == null || mDisposable.isDisposed()) {
                    pb.setVisibility(View.VISIBLE);
                    upload();
                }

                break;
        }
    }

    Disposable mDisposable;

    private void upload() {

        Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {
                        FileUtil.clearFileDir();
                        if (picList != null) {
                            for (int i = 0; i < picList.size(); i++) {
                                ScaleBitmap.getBitmap(picList.get(i), "image" + i + ".png");
                            }
                        }
                        String result = WebserviceHelper.GetWebService("UploadTreeInfo", "AuthenticateInfoMethod", getUploadParas());
                        if (result != null) {
                            e.onNext(result);
                        } else {
                            e.onError(new RuntimeException("error"));
                        }
                        e.onComplete();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(String value) {
                        Log.i(TAG, "onNext: " + value);
                        ResultMessage rm = new Gson().fromJson(value, ResultMessage.class);
                        if (rm.getError_code() == 0) {
                            showToast("上传成功");
                        } else {
                            showToast(rm.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        startActivity(new Intent(Activity_expert_weak2.this, Activity_main.class));
                    }
                });
    }

    /* <UserID>string</UserID>
       <date>string</date>
       <Type>int</Type>
       <areaId>string</areaId>
       <JsonStr>string</JsonStr>*/
    private Map<String, Object> getUploadParas() {
        Map<String, Object> map = new HashMap<>();
        RequestBean bean = new RequestBean();
        bean.setPicList(FileUtil.getPngFiles());
        bean.setPart(partId);
        bean.setFeature1(position);
        String user_id = sharedPreferences.getString(UserSharedField.USERID, "");
        map.put("UserID", user_id);
        map.put("date", getStringDate());
        map.put("Type", 3);
        map.put("areaId", sharedPreferences.getString(UserSharedField.AREAID, ""));
        String json = new Gson().toJson(bean);
        Log.i(TAG, "getUploadParas: " + json);
        map.put("JsonStr", json);
        return map;
    }

    @Override
    public void onBackPressed() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
            mDisposable = null;
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST_CDOE && resultCode == -1) {
            picList = data.getStringArrayListExtra("select_result");
            pic.setText(picList.size() + "");
        }
    }

    class RequestBean {

        /**
         * picList : [""]
         * Part : 20
         * Feature1 : 1
         * Feature2 : 2
         * Explain : shuoming
         */

        @SerializedName("Part")
        private int Part;
        @SerializedName("Feature1")
        private int Feature1;
        @SerializedName("Feature2")
        private int Feature2;
        @SerializedName("Explain")
        private String Explain = "";
        @SerializedName("picList")
        private List<String> picList = new ArrayList<>();

        public int getPart() {
            return Part;
        }

        public void setPart(int Part) {
            this.Part = Part;
        }

        public int getFeature1() {
            return Feature1;
        }

        public void setFeature1(int Feature1) {
            this.Feature1 = Feature1;
        }

        public int getFeature2() {
            return Feature2;
        }

        public void setFeature2(int Feature2) {
            this.Feature2 = Feature2;
        }

        public String getExplain() {
            return Explain;
        }

        public void setExplain(String Explain) {
            this.Explain = Explain;
        }

        public List<String> getPicList() {
            return picList;
        }

        public void setPicList(List<String> picList) {
            this.picList = picList;
        }
    }
}
