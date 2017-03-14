package com.xabaizhong.mylibrary;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

/**
 * Created by admin on 2017/3/14.
 */

public class Test {
    public static void main(String[] args){
        User user = new User();
        user.setDate(new Date());
        user.setId("12");
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create() ;
        System.out.println(gson.toJson(user));
    }
}
