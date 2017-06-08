package com.xabaizhong.treemonistor.myview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.xabaizhong.treemonistor.utils.MessageEvent;
import com.xabaizhong.treemonistor.utils.RxBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/3/6.
 */

public class C_dialog_radio {
    String TAG = "c-dialog";
    private static C_dialog_radio c_dialog_radio;
    private Context context;
    private CharSequence title;
    private List<String> mList;
    private int mDialog_code;

    AlertDialog.Builder builder;
    int RESULT = -1;
    AlertDialog dialog;
    public C_dialog_radio(Context context, CharSequence title, List<String> list,  int dialog_code) {
        this.context = context;
        this.mList = list;
        this.title = title;
        this.mDialog_code = dialog_code;
        builder = new AlertDialog.Builder(context).setTitle(title);

        initView();
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (RESULT != -1)
                    RxBus.getDefault().send(new MessageEvent(RESULT, mList.get(RESULT),mDialog_code));
            }
        });
        builder.setCancelable(false);
        dialog = builder.create();
        dialog.show();
    }


    private void initView() {
        if (mList != null && mList.size() > 0) {
            Log.d(TAG, "initView: ");
            initRadioGroup();
        }

    }

    private void initRadioGroup() {
        LinearLayout layout = new LinearLayout(context);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(16,0,16,0);


        RadioGroup group = new RadioGroup(context);
        group.setLayoutParams(new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        group.setOrientation(LinearLayout.VERTICAL);
        layout.addView(group);

        RadioButton rb;
        for (int i = 0; i < mList.size(); i++) {
            rb = new RadioButton(context);
            rb.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            rb.setText(mList.get(i));
            rb.setTextSize(18);
            rb.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
            rb.setGravity(Gravity.CENTER_VERTICAL);
            group.addView(rb);
            final int finalI = i;
            rb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RESULT = finalI;
                    RxBus.getDefault().send(new MessageEvent(RESULT, mList.get(RESULT),mDialog_code));
                    dialog.dismiss();
                }
            });
        }
        builder.setView(layout);
    }


}
