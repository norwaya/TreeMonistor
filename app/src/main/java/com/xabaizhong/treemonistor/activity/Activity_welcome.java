package com.xabaizhong.treemonistor.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.base.App;
import com.xabaizhong.treemonistor.dbhelper.AreaCodeHelper;
import com.xabaizhong.treemonistor.dbhelper.TreeSpecialHelper;
import com.xabaizhong.treemonistor.entity.AreaCode;
import com.xabaizhong.treemonistor.entity.AreaCodeDao;
import com.xabaizhong.treemonistor.entity.TreeSpecial;
import com.xabaizhong.treemonistor.entity.TreeSpecialDao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

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

import static com.xabaizhong.treemonistor.contant.Contant.KV.FIRST_INIT;

/**
 * Created by admin on 2017/3/3.
 */

public class Activity_welcome extends Activity_base {
    private static final int REQEUST_CODE_WRITER = 0x101;
    @BindView(R.id.activity_welcome_iv)
    ImageView activityWelcomeIv;
    @BindView(R.id.activity_welcome_btn)
    Button btn;

    final static int SECOND = 1;

    TreeSpecialDao treeSpecialDao;
    AreaCodeDao areaCodeDao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        requestPermission();
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.i(TAG, "requestPermission: ");
            request();
        } else {
            initAll();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void request() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQEUST_CODE_WRITER);

        } else {
            initAll();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQEUST_CODE_WRITER && grantResults[0] == 0) {
            Log.i(TAG, "onRequestPermissionsResult: ");
            initAll();
        } else {
            finish();
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    void initAll() {
        initImage();
        long stat = System.nanoTime();
        initDB();
        long end = System.nanoTime();
        Log.i(TAG, "initAll: " + (end - stat));
    }


    private void initImage() {

        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer value) {
                btn.setText(getString(R.string.welcome_hint, value));
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                if (login_suc()) {
                    startActivity(new Intent(Activity_welcome.this, Activity_main.class));
                } else {
                    startActivity(new Intent(Activity_welcome.this, Activity_login2.class));
                }
                finish();
            }
        };
        Observable
                .create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {

//                        getApplicationContext().getFilesDir().listFiles();
                        e.onNext(SECOND);
                        for (int i = SECOND; i > 0; i--) {
                            Thread.sleep(1000);
                            e.onNext(i - 1);
                        }
                        e.onComplete();

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);

    }

    @OnClick(R.id.activity_welcome_btn)
    public void onClick() {

    }

    @Override
    public void onBackPressed() {
    }

    private void initDB() {
        if (hasInit())
            return;
        initDao();
        clearDB();
        wirteTreeSpecial("treeSpecial.txt");
        wirteAreaCode("areaCode.txt");
        writeToShare();
    }

    private void initDao() {
        treeSpecialDao = ((App) getApplicationContext()).getDaoSession().getTreeSpecialDao();
        areaCodeDao = ((App) getApplicationContext()).getDaoSession().getAreaCodeDao();
    }

    private void clearDB() {
        treeSpecialDao.queryBuilder().build().list().clear();
        areaCodeDao.queryBuilder().build().list().clear();

    }

    /**
     * @return true if not init db ,false or else
     */
    private boolean hasInit() {
        return !sharedPreferences.getBoolean(FIRST_INIT, true);
    }

    private void wirteTreeSpecial(String file) {
        String json = getAssetFile(file);
        TreeSpecialHelper treeHelper = new Gson().fromJson(json, TreeSpecialHelper.class);
        TreeSpecial treeSpecial;
        ArrayList<TreeSpecial> treeSpecialList = new ArrayList<>();
        for (TreeSpecialHelper.RECORDSBean bean : treeHelper.getRECORDS()) {
            treeSpecial = bean.convertToEntity();
            treeSpecialList.add(treeSpecial);
        }
        treeSpecialDao.saveInTx(treeSpecialList);
    }

    private void wirteAreaCode(String file) {
        String json = getAssetFile(file);
        AreaCodeHelper areaCodeHelper = new Gson().fromJson(json, AreaCodeHelper.class);
        AreaCode areaCode;
        ArrayList<AreaCode> treeSpecialList = new ArrayList<>();
        for (AreaCodeHelper.RECORDSBean bean : areaCodeHelper.getRECORDS()) {
            areaCode = bean.convertToEntity();
            treeSpecialList.add(areaCode);
        }
        areaCodeDao.saveInTx(treeSpecialList);
    }

    private String getAssetFile(String file) {
        String json = null;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open(file)));
            json = file2string(br);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    void writeToShare() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(FIRST_INIT, false);
        editor.apply();
        editor.commit();
        Log.i(TAG, "initDB: " + sharedPreferences.getBoolean(FIRST_INIT, true));
    }

    private String file2string(BufferedReader br) throws IOException {
        StringBuffer sbu = new StringBuffer();
        byte[] bytes = new byte[1024];
        while (br.ready()) {
            sbu.append(br.readLine());
        }
        return sbu.toString();
    }
}
