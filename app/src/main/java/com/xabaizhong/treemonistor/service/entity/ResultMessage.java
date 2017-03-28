package com.xabaizhong.treemonistor.service.entity;

/**
 * Created by admin on 2017/3/27.
 */

public class ResultMessage<T> {

    /**
     * result_ok : 0
     * message : ok
     * data : {"name":"sa","areaId":"admin","permission":"3"}
     */

    private int error_code;
    private String message;
    private T result;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
