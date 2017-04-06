package com.xabaizhong.treemonistor.service.model;

import com.google.gson.annotations.SerializedName;
import com.xabaizhong.treemonistor.utils.InputVerification;

/**
 * Created by admin on 2017/3/15.
 */

public class User {

    @SerializedName("Roleid")
    private String role_id;
    @SerializedName("UserID")
    private String user_id; // FIXME check this code
    @SerializedName("RealName")
    private String real_name; // FIXME check this code
    @SerializedName("UserTel")
    private String user_tel; // FIXME check this code
    @SerializedName("AreaID")
    private String area_id; // FIXME check this code
    @SerializedName(" UserMail")
    private String user_mail; // FIXME check this code
    @SerializedName("Usersex")
    private int user_sex; // FIXME check this code
    @SerializedName("Explain")
    private String explain; // FIXME check this code
    @SerializedName("PicStr")
    private String pic_str; // FIXME check this code

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

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getUser_mail() {
        return user_mail;
    }

    public void setUser_mail(String user_mail) {
        this.user_mail = user_mail;
    }

    public int getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(int user_sex) {
        this.user_sex = user_sex;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getPic_str() {
        return pic_str;
    }

    public void setPic_str(String pic_str) {
        this.pic_str = pic_str;
    }

    @Override
    public String toString() {
        StringBuilder sbu = new StringBuilder();
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
            sbu.append("user_sex").append(":\t").append(user_sex == 0 ? "男" : "女").append("\n");
        }
        if (!InputVerification.isNull(user_id)) {
            sbu.append("explain").append(":\t").append(explain).append("\n");
        }
        return sbu.toString();
    }
}
