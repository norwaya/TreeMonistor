package com.xabaizhong.treemonistor.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 2017/2/27.
 */

public class ResultOfExpert {


    /**
     * DateTime : 2017-05-04 04:53:52.00
     * JDResult : {"CheckPerson":"陈教授","CheckTime":"2017-05-08 05:00:29.00","Result":"54545454"}
     * UserID : test1
     * AreaID : 610000
     * TID : 6100002017050404535200
     * AuthType : 0
     */

    @SerializedName("DateTime")
    private String DateTime;
    @SerializedName("checked")
    private boolean checked;
    @SerializedName("UserID")
    private String UserID;
    @SerializedName("AreaID")
    private String AreaID;
    @SerializedName("TID")
    private String TID;
    @SerializedName("AuthType")
    private int AuthType;

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String DateTime) {
        this.DateTime = DateTime;
    }


    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public String getAreaID() {
        return AreaID;
    }

    public void setAreaID(String AreaID) {
        this.AreaID = AreaID;
    }

    public String getTID() {
        return TID;
    }

    public void setTID(String TID) {
        this.TID = TID;
    }

    public int getAuthType() {
        return AuthType;
    }

    public void setAuthType(int AuthType) {
        this.AuthType = AuthType;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public int hashCode() {
        return this.getTID().hashCode() * 3 + 7 * (isChecked() ? 0 : 1);
    }
}
