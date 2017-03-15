package com.xabaizhong.mylibrary;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2017/3/14.
 */

public class User {
    String id;
    Date date;
    Map<String,Object> map = new HashMap();

    public User() {
        id = "1";
        date = new Date();
        map.put("name","aice");
        map.put("age", 12);
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
