package com.xabaizhong.treemonistor.service.model;
import com.google.gson.annotations.SerializedName;

/**
 *
 * @param <T> T -> "result"
 */
public class ResultMessage<T> {


    @SerializedName("message")
    private String message; // FIXME check this code
    @SerializedName("error_code")
    private int error_code; // FIXME check this code
    @SerializedName("result")
    private T result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
