package com.xabaizhong.treemonistor.activity.expert;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.activity.base_data.Activity_base_pest_detail;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.base.App;
import com.xabaizhong.treemonistor.contant.UserSharedField;
import com.xabaizhong.treemonistor.entity.Pest;
import com.xabaizhong.treemonistor.entity.PestDao;
import com.xabaizhong.treemonistor.myview.C_dialog_radio;
import com.xabaizhong.treemonistor.myview.C_info_gather_item1;
import com.xabaizhong.treemonistor.myview.ProgressDialogUtil;
import com.xabaizhong.treemonistor.service.WebserviceHelper;
import com.xabaizhong.treemonistor.service.model.ResultMessage;
import com.xabaizhong.treemonistor.utils.FileUtil;
import com.xabaizhong.treemonistor.utils.MessageEvent;
import com.xabaizhong.treemonistor.utils.RxBus;
import com.xabaizhong.treemonistor.utils.ScaleBitmap;

import java.io.File;
import java.util.ArrayList;
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
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;


/**
 * Created by norwaya on 17-4-17.
 */

public class Activity_expert_bug extends Activity_base implements C_info_gather_item1.Mid_CallBack {
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.bug_season)
    C_info_gather_item1 bugSeason;
    @BindView(R.id.bug_part)
    C_info_gather_item1 bugPart;
    @BindView(R.id.bug_classic)
    C_info_gather_item1 bugClassic;
    @BindView(R.id.pics)
    C_info_gather_item1 pics;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_bug);
        ButterKnife.bind(this);
        initData();
        initDis();
        initView();
    }

    Disposable disposable;
    Param_code params = null;
    private static final int SPECIES_CODE = 671;
    Bean bean;

    private void initDis() {
        params = new Param_code();
        bean = new Bean();
        disposable = RxBus.getDefault().toObservable(MessageEvent.class).subscribe(new Consumer<MessageEvent>() {
            @Override
            public void accept(MessageEvent messageEvent) throws Exception {
                switch (messageEvent.getCode()) {
                    case BUG_SEASON:
                        bugSeason.setText(messageEvent.getText());
                        params.season = messageEvent.getId();
                        bean.setDiscoverySeason(messageEvent.getId());
                        break;
                    case BUG_PART:
                        bugPart.setText(messageEvent.getText());
                        params.part = messageEvent.getId();
                        bean.setPart(messageEvent.getId());
                        break;
                    case BUG_CLASSIC:
                        bugClassic.setText(messageEvent.getText());
                        params.classic = messageEvent.getId();
                        bean.setHexapodType(messageEvent.getId());
                        break;
                }
            }
        });
    }

    private void initData() {

    }

    private void initView() {
        bugSeason.setCallback_mid(this);
        bugPart.setCallback_mid(this);
        bugClassic.setCallback_mid(this);
//        bugSpecies.setCallback_mid(this);
        pics.setCallback_mid(this);
        pics.setVisibility(View.GONE);
    }

    boolean upload = false;

    @OnClick(R.id.submit)
    public void onViewClicked() {
        if (params.check()) {
            if (upload) {
                if (mList != null && mList.size() > 0) {
                    uploadWithPics();
                } else {
                    showToast("请先选择图片");
                }
            } else {
                request();
            }
        }else{
            showToast("填写完整信息");
        }
    }

    private void uploadWithPics() {
        final DialogInterface dialog = ProgressDialogUtil.getInstance(this).initial(null, new ProgressDialogUtil.CallBackListener() {
            @Override
            public void callBack(DialogInterface dialog) {

            }
        });



        Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {
                        FileUtil.clearFileDir();
                        scaleImages();
                        fillStrImages();
                        String result = WebserviceHelper.GetWebService(
                                "UploadTreeInfo", "AuthenticateInfoMethod", getUploadParams());
                        if (result == null) {
                            e.onError(new RuntimeException("服务器无返回值"));
                        } else {
                            e.onNext(result);
                        }
                        e.onComplete();
                    }
                }).subscribeOn(Schedulers.io())
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
                        showToast(rm.getMessage());
                        mDisposable = null;
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onComplete() {

                        if (dialog != null) {
                            dialog.dismiss();
                        }
                        if (mDisposable == null) {
                            Activity_expert_bug.this.finish();
                        }
                    }
                });
    }
//    <UserID>string</UserID>
//      <date>string</date>
//      <Type>int</Type>
//      <areaId>string</areaId>
//      <JsonStr>string</JsonStr>
    private Map<String, Object> getUploadParams() {
        Map<String, Object> map = new HashMap<>();
        map.put("UserID", sharedPreferences.getString(UserSharedField.USERID, ""));
        map.put("date", getStringDate());
        map.put("Type", 4);
        map.put("areaId", sharedPreferences.getString(UserSharedField.AREAID, ""));
        map.put("JsonStr", new Gson().toJson(bean));
        return map;
    }


    private void initialDialog(String value, List<String> list) {
        View view = LayoutInflater.from(this).inflate(R.layout.listview, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (list != null && list.size() > 0) {
            adapter = new MyAdapter(this);
            adapter.setmList(list);
            initialBugListView(view);
            builder.setView(view);
        } else {
            builder.setMessage("系统无法鉴定该虫害");
        }
        builder.setTitle("虫害鉴定结果")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setNegativeButton("上报给专家", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        display();
                    }
                }).create().show();
    }

    private void display() {
        upload = true;
        pics.setVisibility(View.VISIBLE);
    }

    MyAdapter adapter;

    private void initialBugListView(View view) {
        ListView lv = ((ListView) view.findViewById(R.id.lv));
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object item = ((MyAdapter) parent.getAdapter()).getItem(position);
                PestDao pestDao = ((App) getApplication()).getDaoSession().getPestDao();
                List<Pest> list = pestDao.queryBuilder().where(PestDao.Properties.HexapodId.eq(item)).build().list();
                if (list.size() > 0) {
                    Intent i = new Intent(getApplicationContext(), Activity_base_pest_detail.class);
                    i.putExtra("id", list.get(0).getId());
                    startActivity(i);
                }

            }
        });
    }

    static class MyAdapter extends BaseAdapter {
        List<String> mList = new ArrayList<>();
        LayoutInflater inflater;

        public MyAdapter(Context context) {
            inflater = LayoutInflater.from(context);
            initDao(context);
        }

        PestDao pestDao;

        private void initDao(Context con) {
            pestDao = ((App) con.getApplicationContext()).getDaoSession().getPestDao();


        }

        public void setmList(List<String> mList) {
            this.mList = mList;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return mList.get(position).hashCode();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv = (TextView) inflater.inflate(R.layout.simple_text, parent, false);
            String cname = getCname(mList.get(position));
            tv.setText(cname);
            return tv;
        }

        private String getCname(String id) {
            List<Pest> list = pestDao.queryBuilder().where(PestDao.Properties.HexapodId.eq(id)).build().list();
            if (list.size() > 0) {
                return list.get(0).getHexapodname();
            }
            return id;
        }
    }


    private void fillStrImages() {
        bean.setPicList(FileUtil.getPngFiles());
    }

    private void scaleImages() {
        if (mList != null) {
            for (int i = 0; i < mList.size(); i++) {
                ScaleBitmap.getBitmap(mList.get(i), "image" + i + ".png");
            }
        }
    }

    Disposable mDisposable;
    ProgressDialog progressDialog;

    private void request() {
        final DialogInterface dialog = ProgressDialogUtil.getInstance(this).initial(null, new ProgressDialogUtil.CallBackListener() {
            @Override
            public void callBack(DialogInterface dialog) {

            }
        });
        Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {
                        String result = WebserviceHelper.GetWebService(
                                "CheckUp", "GetHexapod", getParams());
                        if (result == null) {
                            e.onError(new RuntimeException("服务器无返回值"));
                        } else {
                            e.onNext(result);
                        }
                        e.onComplete();
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(String value) {
                        Log.i(TAG, "onNext: " + value);
                        JdResult jdResult = new Gson().fromJson(value, JdResult.class);
                        if (jdResult.getErrorCode() == 0) {
                            initialDialog(value, jdResult.getResult());
                        } else {

                            initialDialog(value, null);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }
                });

    }


    private Map<String, Object> getParams() {
        Map<String, Object> map = new HashMap<>();
//        String user_id = sharedPreferences.getString(UserSharedField.USERID, "");
        map.put("HexapodType", params.classic);
        map.put("Part", params.part + "");
        map.put("Season", params.season + "");
        return map;
    }

    Param_list paraList = new Param_list();
    ArrayList<String> mList = new ArrayList<>();

    @Override
    public void onClickListener(View et) {
        switch (et.getId()) {
            case R.id.bug_season:
                new C_dialog_radio(this, "季节", paraList.season, BUG_SEASON);
                break;
//            case R.id.bug_species:
//                startActivityForResult(new Intent(Activity_expert_bug.this, Activity_tree_cname.class), SPECIES_CODE);
//                break;
            case R.id.bug_part:
                new C_dialog_radio(this, "部位", paraList.part, BUG_PART);
                break;
            case R.id.bug_classic:
                new C_dialog_radio(this, "类型", paraList.classic, BUG_CLASSIC);
                break;
            case R.id.pics:
                MultiImageSelector.create(getApplicationContext())
                        .showCamera(true) // show camera or not. true by default
                        .count(5) // max select image size, 9 by default. used width #.multi()
                        .single() // single mode
                        .multi() // multi mode, default mode;
                        .origin(mList) // original select data set, used width #.multi()
                        .start(Activity_expert_bug.this, IMAGE_CHOOSE_CODE);
                break;
        }

    }

    private static final int IMAGE_CHOOSE_CODE = 467;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == SPECIES_CODE && resultCode == Activity_tree_cname.REQUEST_CODE_CNAME_RESULT) {
//            TreeSpecial tree = data.getParcelableExtra("special");
//            bugSpecies.setText(tree.getCname());
//            params.species = tree.getTreeSpecCode();
//        } else
        if (IMAGE_CHOOSE_CODE == requestCode) {
            if (resultCode == RESULT_OK) {
                mList = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                if (mList != null) {
                    pics.setText("" + mList.size());
                }
            }
        }
    }

    static final int BUG_SEASON = 644;
    static final int BUG_SPECIES = 874;
    static final int BUG_PART = 625;
    static final int BUG_CLASSIC = 124;

    private static class Param_code {
        int season = -1;
        String species = null;
        int part = -1;
        int classic = -1;

        private boolean check() {
            return season != -1 && part != -1 && classic != -1;
        }
    }

    private static class Param_list {
        List<String> season = new ArrayList<>();
        List<String> part = new ArrayList<>();
        List<String> classic = new ArrayList<>();

        Param_list() {
            initialData();
        }

        void initialData() {
            season.clear();
            season.add(0, "其他");
            season.add(1, "春季");
            season.add(2, "夏季");
            season.add(3, "秋季");
            season.add(4, "冬季");

            part.clear();
            part.add(0, "其他");
            part.add(1, "叶子");
            part.add(2, "树干");
            part.add(3, "根");
            part.add(4, "果实");

            classic.clear();
            classic.add(0, "咀嚼式");
            classic.add(1, "刺吸式");
            classic.add(2, "其他");
        }
    }

    static class Bean {

        /**
         * HexapodType : 1
         * picList : [""]
         * Part : 20
         * DiscoverySeason : 5
         * Explain : shuoming
         */

        @SerializedName("HexapodType")
        private int HexapodType;
        @SerializedName("Part")
        private int Part;
        @SerializedName("DiscoverySeason")
        private int DiscoverySeason;
        @SerializedName("Explain")
        private String Explain = "";
        @SerializedName("picList")
        private List<String> picList;

        public int getHexapodType() {
            return HexapodType;
        }

        public void setHexapodType(int HexapodType) {
            this.HexapodType = HexapodType;
        }

        public int getPart() {
            return Part;
        }

        public void setPart(int Part) {
            this.Part = Part;
        }

        public int getDiscoverySeason() {
            return DiscoverySeason;
        }

        public void setDiscoverySeason(int DiscoverySeason) {
            this.DiscoverySeason = DiscoverySeason;
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

    static class JdResult {

        /**
         * message : sus
         * error_code : 0
         * result : ["1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43"]
         */

        @SerializedName("message")
        private String message;
        @SerializedName("error_code")
        private int errorCode;
        @SerializedName("result")
        private List<String> result;

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

        public List<String> getResult() {
            return result;
        }

        public void setResult(List<String> result) {
            this.result = result;
        }
    }
}
