package com.xabaizhong.treemonitor.utils;

/**
 * Created by admin on 2017/3/6.
 */

public class MessageEvent {

    private int id;
    private String text;

    public MessageEvent() {
    }

    public MessageEvent(int id, String text) {
        this.id = id;
        this.text = text;
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
}
