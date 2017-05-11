package com.xabaizhong.treemonistor.myview;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

public class DateDialog {


    private Context mContext;

    public DateDialog(Context context) {
        mContext = context;
        initialDialog();
    }

    private void initialDialog() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(year, month, dayOfMonth);
                mListener.getDate(c.getTime());
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }

    private DateDialogListener mListener;

    public void setDateDialogListener(DateDialogListener listener) {
        this.mListener = listener;
    }

    public interface DateDialogListener {
        void getDate(Date date);
    }
}

