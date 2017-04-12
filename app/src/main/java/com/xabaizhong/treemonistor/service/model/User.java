package com.xabaizhong.treemonistor.service.model;

import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.xabaizhong.treemonistor.utils.InputVerification;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by admin on 2017/3/15.
 */

public class User {

    @SerializedName("RoleID")
    private String role_id;
    @SerializedName("UserID")
    private String user_id; // FIXME check this code
    @SerializedName("AreaID")
    private String area_id;
    /**
     * RealName : lizhuang5
     * UserTel : 23231
     * UserUnit : lizhuang6
     * UserMail : lizhuang7
     * Usersex : 0
     * UserAge : 45
     * UserField : lizhuang1
     * UserAddress : lizhuang2
     * Titles : lizhuang4
     * PicStr :
     */

    @SerializedName("RealName")
    private String real_name;
    @SerializedName("UserTel")
    private String user_tel;
    @SerializedName("UserUnit")
    private String user_unit;
    @SerializedName("UserMail")
    private String user_mail;
    @SerializedName("Usersex")
    private String user_sex;
    @SerializedName("UserAge")
    private String user_age;
    @SerializedName("UserField")
    private String user_field;
    @SerializedName("UserAddress")
    private String user_address;
    @SerializedName("Titles")
    private String titles;
    @SerializedName("PicStr")
    private String pic_str;


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

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getUser_tel() {
        return user_tel;
    }

    public void setUser_tel(String user_tel) {
        this.user_tel = user_tel;
    }

    public String getUser_unit() {
        return user_unit;
    }

    public void setUser_unit(String user_unit) {
        this.user_unit = user_unit;
    }

    public String getUser_mail() {
        return user_mail;
    }

    public void setUser_mail(String user_mail) {
        this.user_mail = user_mail;
    }

    public String getUser_sex() {
        if ("0".equals(user_sex) || "1".equals(user_sex))
            return user_sex.equals("0") ? "男" : "女";
        return "";
    }

    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex;
    }

    public String getUser_age() {
        return user_age;
    }

    public void setUser_age(String user_age) {
        this.user_age = user_age;
    }

    public String getUser_field() {
        return user_field;
    }

    public void setUser_field(String user_field) {
        this.user_field = user_field;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public String getPic_str() {
        return pic_str;
    }

    public void setPic_str(String pic_str) {
        this.pic_str = pic_str;
    }
}
