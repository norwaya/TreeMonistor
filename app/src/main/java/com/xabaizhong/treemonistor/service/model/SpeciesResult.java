package com.xabaizhong.treemonistor.service.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/4/21 0021.
 */

public class SpeciesResult {

    /**
     * message : sus
     * error_code : 0
     * result : ["T00585","T00376","T00585","T00376","T00585","T00376","T00585","T00376"]
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
