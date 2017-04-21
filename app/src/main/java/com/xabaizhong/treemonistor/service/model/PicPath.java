package com.xabaizhong.treemonistor.service.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/4/21 0021.
 */

public class PicPath{

    /**
     * message : sus
     * error_code : 0
     * result : ["http://192.168.0.118:8055\\Image\\TreeSpeInfo\\T00376\\1.jpg","http://192.168.0.118:8055\\Image\\TreeSpeInfo\\T00376\\2.jpg","http://192.168.0.118:8055\\Image\\TreeSpeInfo\\T00376\\3.jpg","http://192.168.0.118:8055\\Image\\TreeSpeInfo\\T00376\\4.jpg"]
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
