package com.xabaizhong.treemonistor.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xabaizhong.treemonistor.entity.AreaCode;
import com.xabaizhong.treemonistor.entity.AreaCodeDao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

/**
 * fragment  基类
 */
public class Fragment_base extends android.support.v4.app.Fragment {
    protected String TAG;
    protected SharedPreferences sharedPreferences;
    protected Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TAG = getClass().getSimpleName();

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        sharedPreferences = context.getSharedPreferences("dic", MODE_PRIVATE);
    }

    Toast toast;

    protected void showToast(String text) {
        if (toast == null)
            toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        else
            toast.setText(text);
        toast.show();
    }
    protected Date fromStrDate(String strDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        try {
            return sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected String getStringDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return sdf.format(date);
    }
    protected String getAreaName(String areaId) {
        AreaCodeDao areaCodeDao = ((App) getActivity().getApplication()).getDaoSession().getAreaCodeDao();
        List<AreaCode> list = areaCodeDao.queryBuilder().where(AreaCodeDao.Properties.AreaId.eq(areaId)).build().list();
        if (list.size() > 0) {
            return list.get(0).getMergerName();
        }
        return "";
    }
}
