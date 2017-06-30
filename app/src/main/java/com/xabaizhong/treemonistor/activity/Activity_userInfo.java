package com.xabaizhong.treemonistor.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.base.App;
import com.xabaizhong.treemonistor.contant.UserSharedField;
import com.xabaizhong.treemonistor.entity.AreaCode;
import com.xabaizhong.treemonistor.entity.AreaCodeDao;
import com.xabaizhong.treemonistor.myview.C_info_gather_item1;
import com.xabaizhong.treemonistor.service.WebserviceHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 *用户详情
 */
public class Activity_userInfo extends Activity_base {
    @BindView(R.id.layout)
    LinearLayout layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        getUserInfo();
    }

    Disposable mDisposable;

    @Override
    public void onBackPressed() {
        if (mDisposable != null) {
            mDisposable.dispose();
            mDisposable = null;
        } else {
            super.onBackPressed();
        }
    }

    private void getUserInfo() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                String result = null;
                try {
                    result = WebserviceHelper.GetWebService(
                            "Login", "userDetailInfo ", requestMap());
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
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(String value) {
                        ResultMessage loginResultMessage = new Gson().fromJson(value, ResultMessage.class);
                        Log.i(TAG, "onNext: " + value);
                        if (loginResultMessage.getErrorCode() == 0) {
                            initInfo(loginResultMessage.getResult());
                        } else {
                            addView("错误信息", "获取用户信息失败");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mDisposable = null;
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        mDisposable = null;
                    }
                });

    }

    private String getAreaName(String areaId) {
        AreaCodeDao areaCodeDao = ((App) getApplication()).getDaoSession().getAreaCodeDao();
        List<AreaCode> list = areaCodeDao.queryBuilder().where(AreaCodeDao.Properties.AreaId.eq(areaId)).build().list();
        if (list.size() > 0) {
            return list.get(0).getMergerName();
        }
        return "";
    }

    private void initInfo(ResultMessage.ResultBean result) {
        addView("账号", result.getUserID());
        addView("地理位置", getAreaName(result.getAreaID()));
        addView("地理编号", result.getAreaID());
        addView("姓名", result.getRealName());
        addView("性别", result.getUsersex() == 0 ? "男" : "女");
        addView("手机", result.getUserTel());
        addView("邮箱", result.getUserMail());
    }

    private void addView(String left, String mid) {
        layout.addView(getView(left, mid));
    }


    LayoutInflater inflater;

    private View getView(String left, String mid) {
        if (inflater == null) {
            inflater = LayoutInflater.from(this);
        }

        View view = inflater.inflate(R.layout.c_view, null);
        C_info_gather_item1 cv = (C_info_gather_item1) view.findViewById(R.id.cv);
        cv.setLeftText(left);
        cv.setText(mid);
        return view;
    }

    private Map<String, Object> requestMap() {
        Map<String, Object> map = new HashMap<>();
        String userid = sharedPreferences.getString(UserSharedField.USERID, "");
        map.put("UserID", userid);
        map.put("Type", 1);
        return map;
    }

    static class ResultMessage {


        /**
         * message : 登陆成功
         * error_code : 0
         * result : {"UserID":"610102001","RealName":"李丛峰","UserTel":"12345678910","AreaID":"610102","AreaName":"陕西省,西安市,新城区","UserMail":"123456789@qq.com","Usersex":0,"Explain":"(NULL)","PicStr":"~/Image/UserDetInfo/T003"}
         */

        @SerializedName("message")
        private String message;
        @SerializedName("error_code")
        private int errorCode;
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

        public ResultBean getResult() {
            return result;
        }

        public void setResult(ResultBean result) {
            this.result = result;
        }

        public static class ResultBean {
            /**
             * UserID : 610102001
             * RealName : 李丛峰
             * UserTel : 12345678910
             * AreaID : 610102
             * AreaName : 陕西省,西安市,新城区
             * UserMail : 123456789@qq.com
             * Usersex : 0
             * Explain : (NULL)
             * PicStr : ~/Image/UserDetInfo/T003
             */

            @SerializedName("UserID")
            private String UserID;
            @SerializedName("RealName")
            private String RealName;
            @SerializedName("UserTel")
            private String UserTel;
            @SerializedName("AreaID")
            private String AreaID;
            @SerializedName("AreaName")
            private String AreaName;
            @SerializedName("UserMail")
            private String UserMail;
            @SerializedName("Usersex")
            private int Usersex;
            @SerializedName("Explain")
            private String Explain;
            @SerializedName("PicStr")
            private String PicStr;

            public String getUserID() {
                return UserID;
            }

            public void setUserID(String UserID) {
                this.UserID = UserID;
            }

            public String getRealName() {
                return RealName;
            }

            public void setRealName(String RealName) {
                this.RealName = RealName;
            }

            public String getUserTel() {
                return UserTel;
            }

            public void setUserTel(String UserTel) {
                this.UserTel = UserTel;
            }

            public String getAreaID() {
                return AreaID;
            }

            public void setAreaID(String AreaID) {
                this.AreaID = AreaID;
            }

            public String getAreaName() {
                return AreaName;
            }

            public void setAreaName(String AreaName) {
                this.AreaName = AreaName;
            }

            public String getUserMail() {
                return UserMail;
            }

            public void setUserMail(String UserMail) {
                this.UserMail = UserMail;
            }

            public int getUsersex() {
                return Usersex;
            }

            public void setUsersex(int Usersex) {
                this.Usersex = Usersex;
            }

            public String getExplain() {
                return Explain;
            }

            public void setExplain(String Explain) {
                this.Explain = Explain;
            }

            public String getPicStr() {
                return PicStr;
            }

            public void setPicStr(String PicStr) {
                this.PicStr = PicStr;
            }
        }
    }
}
