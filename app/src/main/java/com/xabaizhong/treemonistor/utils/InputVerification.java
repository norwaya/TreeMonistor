package com.xabaizhong.treemonistor.utils;

import android.text.TextUtils;
import android.widget.EditText;

/**
 * Created by Administrator on 2017/4/6 0006.
 */

/**
 * 输入验证 类
 */
public class InputVerification {
    /**
     *
     * @param editText
     * @return true if text is null,false otherwise
     */
    public static boolean isNull(EditText editText){
        if(TextUtils.isEmpty(editText.getText()))
            return true;
        return false;
    }
    public static boolean isNull(CharSequence charSequence){
        if(charSequence == null && "".equals(charSequence)){
            return true;
        }
        return false;
    }
    public static boolean isNull(String charSequence){
        if(charSequence == null && "".equals(charSequence)){
            return true;
        }
        return false;
    }
}
