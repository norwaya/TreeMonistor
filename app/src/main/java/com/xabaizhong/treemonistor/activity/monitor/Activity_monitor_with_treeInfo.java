package com.xabaizhong.treemonistor.activity.monitor;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.activity.monitor.fragment.Fragment_group;
import com.xabaizhong.treemonistor.activity.monitor.fragment.Fragment_tree;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.contant.UserSharedField;
import com.xabaizhong.treemonistor.myview.ProgressDialogUtil;
import com.xabaizhong.treemonistor.service.WebserviceHelper;
import com.xabaizhong.treemonistor.service.model.ResultMessage;

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

/**
 * 重点古树【群】 加载，修改 上报。
 */
public class Activity_monitor_with_treeInfo extends Activity_base {
    @BindView(R.id.spinner_tree_list)
    Spinner spinnerTreeList;
    @BindView(R.id.space_text)
    TextView spaceText;
    @BindView(R.id.layout_space)
    LinearLayout layoutSpace;
    @BindView(R.id.fragment)
    FrameLayout fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_with_tree_info);
        ButterKnife.bind(this);
        initialSpinnerView();
        initialListData();
//        initSource();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }

    @Override
    protected void onDestroy() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
        super.onDestroy();
    }

    Disposable disposable;

    private void initialListData() {
        final DialogInterface dialog = ProgressDialogUtil.getInstance(this).initial("loading...", new ProgressDialogUtil.CallBackListener() {
            @Override
            public void callBack(DialogInterface dialog) {

            }
        });
        final Map<String, Object> map = new HashMap<>();
        map.put("UserID", sharedPreferences.getString(UserSharedField.USERID, ""));
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                String result = null;
                try {
                    result = WebserviceHelper.GetWebService(
                            "CheckUp", "QueryTreeIDList_ImportantTree", map);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                if (result == null) {
                    e.onError(new RuntimeException("返回为空"));
                } else {
                    e.onNext(result);
                    e.onComplete();
                }
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
                        ResultM resultM = new Gson().fromJson(value, ResultM.class);
                        if (resultM.getErrorCode() == 0) {
                            initSpinner(resultM.getResult());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        dialog.dismiss();
                    }

                    @Override
                    public void onComplete() {
                        disposable = null;
                        dialog.dismiss();
                    }
                });

    }

    private void initSpinner(List<String> list) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        spinnerTreeList.setAdapter(arrayAdapter);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    Imonitor imonitor;
    String currentItem;

    private void initialSpinnerView() {

        spinnerTreeList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "onItemSelected: " + spinnerTreeList.getSelectedItem());
                Object item = spinnerTreeList.getSelectedItem();
                if (item.toString().equals(currentItem)) {
                    return;
                }
                if (item.toString().matches("^[\\d]{11}$")) {
                    initImportTreeFragment(item.toString());
                } else if (item.toString().matches("^[\\d]{8}$")) {
                    initImportTreeGroupFragment(item.toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spaceText.setText("未查询到此棵古树/群信息");
            }
        });
    }

    private void initImportTreeFragment(String treeId) {
        Fragment_tree fragment = Fragment_tree.instance(treeId);
        imonitor = fragment;
        this.f = fragment;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();

    }

    Fragment f;

    private void initImportTreeGroupFragment(String treeId) {
        Fragment_group fragment = Fragment_group.instance(treeId);
        imonitor = fragment;
        this.f = fragment;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        f.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.btn)
    public void onViewClicked() {
        if (imonitor == null) {
            return;
        }
        String checkResult = imonitor.check();
        if (checkResult == null) {
            request();
//            Log.i(TAG, "onViewClicked: " + imonitor.getJsonStr());
        } else {
            showToast(checkResult);
        }
    }


    Disposable upLoadDisposable;
    // 网络请求 ，解析之后 ，加载 对应的fragment 中去
    private void request() {
        /*<tem:UserID>?</tem:UserID>
         <tem:TreeType>?</tem:TreeType>
         <!--Optional:-->
         <tem:JsonStr>?</tem:JsonStr>
         <!--Optional:-->
         <tem:AreaID>?</tem:AreaID>*/
        final DialogInterface dialog = ProgressDialogUtil.getInstance(this).initial("loading...", new ProgressDialogUtil.CallBackListener() {
            @Override
            public void callBack(DialogInterface dialog) {
                upLoadDisposable = null;
            }
        });
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                String result = null;
                try {
                    Map<String, Object> map = new HashMap<>();
                    map.put("UserID", sharedPreferences.getString(UserSharedField.USERID, ""));
                    map.put("AreaID", sharedPreferences.getString(UserSharedField.AREAID, ""));
                    map.put("TreeType", 1);
                    map.put("JsonStr", imonitor.getJsonStr());

                    result = WebserviceHelper.GetWebService(
                            "UploadTreeInfo", "UploadTreeInfoMethod", map);
                } catch (Exception ex) {
                   ex.printStackTrace();
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
                        upLoadDisposable = d;
                    }

                    @Override
                    public void onNext(String value) {
                        ResultMessage resultMessage = new Gson().fromJson(value, ResultMessage.class);
                        if (resultMessage.getError_code() == 0) {
                            dialog.dismiss();
                            showToast("上传成功");
                            finish();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        showToast("上传失败");
                        e.printStackTrace();
                        dialog.dismiss();
                    }

                    @Override
                    public void onComplete() {


                    }
                });

    }
    //bean
    private static class ResultM {

        /**
         * message : sus
         * error_code : 0
         * result : ["61010200001","61010200002","61010200003","61010200004"]
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
