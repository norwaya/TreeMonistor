package com.xabaizhong.treemonistor.myview;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.xabaizhong.treemonistor.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by admin on 2017/3/14.
 */

public class C_dialog_date extends Dialog implements View.OnClickListener {
    DatePicker datePicker;
    Button btn;
    TextView tv;
    Button commit;

    public C_dialog_date(Context context) {
        super(context);
        setTitle("选择日期");
        setContentView(R.layout.dialog_date_picker);
        datePicker = (DatePicker) findViewById(R.id.dp_time);
        btn = (Button) findViewById(R.id.btn_current_time);
        commit = (Button) findViewById(R.id.btn_commit);
        tv = (TextView) findViewById(R.id.tv_current_time);
        btn.setOnClickListener(this);
        commit.setOnClickListener(this);
        currentTime = new Date();
        tv.setText(getDate());
    }


    DateDialogListener mListener;

    public void setDateDialogListener(DateDialogListener listener) {
        this.mListener = listener;

    }

    Date currentTime;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_current_time:
                if (mListener != null) {
                    if (currentTime != null)
                        mListener.getDate(new Date());
                    else
                        mListener.getDate(new Date());
                }

                break;
            case R.id.btn_commit:
                if (mListener != null) {
                    String date = String.format("%d-%d-%d", datePicker.getYear(), datePicker.getMonth() + 1,
                            datePicker.getDayOfMonth());
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                    mListener.getDate(calendar.getTime());
                }
                break;
        }
        this.dismiss();
    }

    private String getDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(currentTime);

    }

    public interface DateDialogListener {
        void getDate(Date date);
    }
}

