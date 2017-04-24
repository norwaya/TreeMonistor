package com.xabaizhong.treemonistor.service.model;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by admin on 2017/3/15.
 */

public class User {


    /**
     * UserID : lizhuang
     * PassWord : admin
     * RoleID : ["3","50"]
     * AreaID : 610000
     * RealName : lizhuang5
     */

    @SerializedName("UserID")
    private String UserID;
    @SerializedName("PassWord")
    private String PassWord;
    @SerializedName("AreaID")
    private String AreaID;
    @SerializedName("RealName")
    private String RealName;
    @SerializedName("RoleID")
    private List<String> RoleID;

    @Override
    public String toString() {
        StringBuilder sbu = new StringBuilder();
        Class clazz = getClass();

        Method[] methods = clazz.getMethods();

        String methodName;
        for (int i = 0; i < methods.length; i++) {
            Log.i("fields", "toString: " + methods);
            methodName = methods[i].getName();
            if (methodName.startsWith("get") && !methodName.equals("getClass"))
                try {
                    Object obj = methods[i].invoke(this);
                    if (obj != null && !obj.toString().equals(""))
                        sbu.append(methods[i].getName().substring(3)).append(":\t").append(obj).append("\n");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
        }

        return sbu.toString();

        /*StringBuilder sbu = new StringBuilder();
        if (!InputVerification.isNull(user_id)) {
            sbu.append("user_id").append(":\t").append(user_id).append("\n");
        }
        if (!InputVerification.isNull(user_id)) {
            sbu.append("real_name").append(":\t").append(real_name).append("\n");
        }
        if (!InputVerification.isNull(user_id)) {
            sbu.append("user_tel").append(":\t").append(user_tel).append("\n");
        }
        if (!InputVerification.isNull(user_id)) {
            sbu.append("area_id").append(":\t").append(area_id).append("\n");
        }
        if (!InputVerification.isNull(user_id)) {
            sbu.append("user_mail").append(":\t").append(user_mail).append("\n");
        }
        if (!InputVerification.isNull(user_id)) {
            sbu.append("user_sex").append(":\t").append(user_sex.equals("0") ? "男" : "女").append("\n");
        }
        if (!InputVerification.isNull(user_id)) {
            sbu.append("explain").append(":\t").append(explain).append("\n");
        }
        return sbu.toString();*/
    }


    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setPassWord(String PassWord) {
        this.PassWord = PassWord;
    }

    public String getAreaID() {
        return AreaID;
    }

    public void setAreaID(String AreaID) {
        this.AreaID = AreaID;
    }

    public String getRealName() {
        return RealName;
    }

    public void setRealName(String RealName) {
        this.RealName = RealName;
    }

    public List<String> getRoleID() {
        return RoleID;
    }

    public void setRoleID(List<String> RoleID) {
        this.RoleID = RoleID;
    }
}
