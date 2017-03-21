package com.xabaizhong.treemonistor.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by admin on 2017/3/3.
 */

public class Fragment_base extends android.support.v4.app.Fragment {
    protected String TAG ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TAG = getClass().getSimpleName();
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
