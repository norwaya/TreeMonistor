package com.xabaizhong.treemonistor.utils;

/**
 * Created by admin on 2017/3/6.
 */

public class MessageEvent {
    private int code;
    private int id;
    private String text;
    private String remark;

    public MessageEvent() {
    }

    public MessageEvent(int id, String text, int code) {
        this.id = id;
        this.text = text;
        this.code = code;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public MessageEvent(int code, int id, String text, String remark) {

        this.code = code;
        this.id = id;
        this.text = text;
        this.remark = remark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
