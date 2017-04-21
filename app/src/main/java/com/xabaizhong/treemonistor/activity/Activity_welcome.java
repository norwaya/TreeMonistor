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
import com.xabaizhong.treemonistor.dbhelper.PestHelper;
import com.xabaizhong.treemonistor.dbhelper.TreeSpecialHelper;
import com.xabaizhong.treemonistor.entity.AreaCode;
import com.xabaizhong.treemonistor.entity.AreaCodeDao;
import com.xabaizhong.treemonistor.entity.Pest;
import com.xabaizhong.treemonistor.entity.PestClass;
import com.xabaizhong.treemonistor.entity.PestClassDao;
import com.xabaizhong.treemonistor.entity.PestDao;
import com.xabaizhong.treemonistor.entity.TreeSpecial;
import com.xabaizhong.treemonistor.entity.TreeSpecialDao;
import com.xabaizhong.treemonistor.entity.Tree_weak_part;
import com.xabaizhong.treemonistor.entity.Tree_weak_partDao;
import com.xabaizhong.treemonistor.entity.Weakness;
import com.xabaizhong.treemonistor.entity.WeaknessDao;
import com.xabaizhong.treemonistor.entity.Weakness_f1;
import com.xabaizhong.treemonistor.entity.Weakness_f1Dao;
import com.xabaizhong.treemonistor.entity.Weakness_f2;
import com.xabaizhong.treemonistor.entity.Weakness_f2Dao;
import com.xabaizhong.treemonistor.entity.json_entity.Json_tree_f1;
import com.xabaizhong.treemonistor.entity.json_entity.Json_tree_f2;
import com.xabaizhong.treemonistor.entity.json_entity.Json_tree_weak_part;
import com.xabaizhong.treemonistor.entity.json_entity.Json_tree_weak;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
import static com.xabaizhong.treemonistor.contant.Contant.KV.NOTICE_PUSH;

/**
 * Created by admin on 2017/3/3.
 */

public class Activity_welcome extends Activity_base {
    private static final int REQEUST_CODE_WRITER = 0x101;
    @BindView(R.id.activity_welcome_iv)
    ImageView activityWelcomeIv;
    @BindView(R.id.activity_welcome_btn)
    Button btn;

    final static int SECOND = 3;

    TreeSpecialDao treeSpecialDao;
    AreaCodeDao areaCodeDao;
    PestDao pestDao;
    PestClassDao pestClassDao;
    WeaknessDao weaknessDao;
    Tree_weak_partDao treePartWeakDao;
    Weakness_f1Dao weaknessF1Dao;
    Weakness_f2Dao weaknessF2Dao;


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

        long stat = System.nanoTime();
        initDB();
        long end = System.nanoTime();
        Log.i(TAG, "initAll: " + (end - stat));
        initImage();
    }

    Disposable disposable;

    private void initImage() {

        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
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
//                if (login_suc()) {
                work();
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

    private void work() {
        if (true) {
            startActivity(new Intent(Activity_welcome.this, Activity_main.class));
        } else {
            startActivity(new Intent(Activity_welcome.this, Activity_login2.class));
        }
        finish();
    }

    boolean flag = false;

    @OnClick(R.id.activity_welcome_btn)
    public void onClick() {
       /* if(flag){
            if(disposable != null){
                disposable.dispose();
                work();
            }
        }*/
    }

    @Override
    public void onBackPressed() {
    }

    private void initDB() {
        if (hasInit()) {
            flag = true;
            return;
        }
        initDao();
        clearDB();
        writeTreeSpecial("tree_special.json");
        writeAreaCode("area_code.json");
        writePest("pest_classify.json");
        writePestClass();

        writeTreePart();
        writeWeakness_f1();
        writeWeakness_f2();
        writeWeakness();

        writeToShare();
        flag = true;

        check();
    }

    private void check() {
        List<Weakness> list = weaknessDao
                .queryBuilder()
                .build()
                .list();
        for (Weakness w : list
                ) {
            String f2 = w.getF2Id() == 0 ? "null" : w.getWeakness_f2().getFId()+ w.getWeakness_f2().getName();
            Log.i(TAG, "check: " + w.getName() + "\t" + w.getTreePart().getName() + "\t" +w.getWeakness_f1()+ w.getWeakness_f1().getName() + "\t" + f2);
        }
    }

    private void initDao() {
        treeSpecialDao = ((App) getApplicationContext()).getDaoSession().getTreeSpecialDao();
        areaCodeDao = ((App) getApplicationContext()).getDaoSession().getAreaCodeDao();
        pestClassDao = ((App) getApplicationContext()).getDaoSession().getPestClassDao();
        pestDao = ((App) getApplicationContext()).getDaoSession().getPestDao();
        weaknessDao = ((App) getApplicationContext()).getDaoSession().getWeaknessDao();
        treePartWeakDao = ((App) getApplicationContext()).getDaoSession().getTree_weak_partDao();
        weaknessF1Dao = ((App) getApplicationContext()).getDaoSession().getWeakness_f1Dao();
        weaknessF2Dao = ((App) getApplicationContext()).getDaoSession().getWeakness_f2Dao();

    }

    private void clearDB() {
        treeSpecialDao.queryBuilder().build().list().clear();
        areaCodeDao.queryBuilder().build().list().clear();
        pestClassDao.queryBuilder().build().list().clear();
        pestDao.queryBuilder().build().list().clear();
        weaknessDao.queryBuilder().build().list().clear();
        treePartWeakDao.queryBuilder().build().list().clear();
        weaknessF1Dao.queryBuilder().build().list().clear();
        weaknessF2Dao.queryBuilder().build().list().clear();
    }

    /**
     * @return true if not init db ,false or else
     */
    private boolean hasInit() {
        return !sharedPreferences.getBoolean(FIRST_INIT, true);
    }

    private void writeTreeSpecial(String file) {
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

    private void writeAreaCode(String file) {
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

    private void writePest(String file) {
        String json = getAssetFile(file);
        PestHelper pestHelper = new Gson().fromJson(json, PestHelper.class);
        Pest pest;
        ArrayList<Pest> pestList = new ArrayList<>();
        for (PestHelper.RECORDSBean bean : pestHelper.getRECORDS()) {
            pest = bean.toPest();
            pestList.add(pest);
        }
        pestDao.saveInTx(pestList);
    }

    private void writeTreePart() {
        String json = getAssetFile("tree_weak_part.json");
        Log.i(TAG, "writeTreePart: " + json);
        Json_tree_weak_part treePart = new Gson().fromJson(json, Json_tree_weak_part.class);

        ArrayList<Tree_weak_part> list = new ArrayList<>();
        for (Json_tree_weak_part.RECORDSBean bean : treePart.getRECORDS()) {
            list.add(bean.toTreePart());
        }
        treePartWeakDao.saveInTx(list);
    }

    private void writeWeakness_f1() {
        String json = getAssetFile("weakness_f1.json");
        Json_tree_f1 jsonTreeF1 = new Gson().fromJson(json, Json_tree_f1.class);


        ArrayList<Weakness_f1> weakList = new ArrayList<>();
        for (Json_tree_f1.RECORDSBean bean : jsonTreeF1.getRECORDS()) {
            weakList.add(bean.toWeaknessF1());
        }
        weaknessF1Dao.saveInTx(weakList);
    }

    private void writeWeakness_f2() {
        String json = getAssetFile("weakness_f2.json");
        Json_tree_f2 jsonTreeF2 = new Gson().fromJson(json, Json_tree_f2.class);


        ArrayList<Weakness_f2> list = new ArrayList<>();
        for (Json_tree_f2.RECORDSBean bean : jsonTreeF2.getRECORDS()) {
            list.add(bean.toWeaknessF2());
        }
        weaknessF2Dao.saveInTx(list);
    }


    private void writeWeakness() {


        String json = getAssetFile("tree_weak.json");
        Json_tree_weak json_tree_weak = new Gson().fromJson(json, Json_tree_weak.class);


        ArrayList<Weakness> weakList = new ArrayList<>();
        for (Json_tree_weak.RECORDSBean bean : json_tree_weak.getRECORDS()) {
            weakList.add(convertToWeakness(bean));
        }
        weaknessDao.saveInTx(weakList);
    }

    private Weakness convertToWeakness(Json_tree_weak.RECORDSBean bean) {
        long f2Id = bean.getExpression2() == 0 ? 0 : loadWeaknessF2(bean.getExpression1(), bean.getExpression2()).getId();
        return new Weakness(null, bean.getIllName(),
                loadTreePart(bean.getPart()).getId(),
                loadWeaknessF1(bean.getExpression1()).getId(),
                f2Id,
                bean.getTrait(), bean.getMethod());

    }

    private Tree_weak_part loadTreePart(int tree_part_id) {
        Log.i(TAG, "loadTreePart: " + tree_part_id);
        List<Tree_weak_part> list = treePartWeakDao
                .queryBuilder()
                .where(Tree_weak_partDao.Properties.PartId.eq(tree_part_id))
                .build()
                .list();
        return list.get(0);
    }

    private Weakness_f1 loadWeaknessF1(int weakness_f1_id) {
        Log.i(TAG, "loadWeaknessF1: " + weakness_f1_id);
        List<Weakness_f1> list = weaknessF1Dao
                .queryBuilder()
                .where(Weakness_f1Dao.Properties.FId.eq(weakness_f1_id))
                .build()
                .list();
        return list.get(0);
    }

    private Weakness_f2 loadWeaknessF2(int weakness_f1_id, int weakness_f2_id) {
        Log.i(TAG, "loadWeaknessF2: " + weakness_f1_id + "\t" + weakness_f2_id);
        List<Weakness_f2> list = weaknessF2Dao
                .queryBuilder()
                .where(Weakness_f2Dao.Properties.FId.eq(weakness_f2_id), Weakness_f2Dao.Properties.F1Id.eq(weakness_f1_id))
                .build()
                .list();
        return list.get(0);
    }

    private void writePestClass() {
       /* 食叶性害虫	1
        刺吸性害虫	2
        蛀食性害虫	3
        食根性害虫	4*/
        String[] array = {"食叶性害虫", "刺吸性害虫", "蛀食性害虫", "食根性害虫"};
        ArrayList<PestClass> pestClassList = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            pestClassList.add(new PestClass(null, (i + 1), array[i]));
        }
        pestClassDao.saveInTx(pestClassList);
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
        editor.putBoolean(NOTICE_PUSH, true);
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
