package com.xabaizhong.treemonistor.activity.monitor;


/**
 *  古树  古树群 展示页面 接口；
 */
public interface Imonitor {
    String check();// 检查 用户输入 ，如果为null  -> 正确，否则 ，输入错误
    String getJsonStr();// 获取 json
}
