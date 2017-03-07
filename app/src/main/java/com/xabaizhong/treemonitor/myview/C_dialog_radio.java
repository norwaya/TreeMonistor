package com.xabaizhong.treemonitor.myview;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.StyleRes;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.xabaizhong.treemonitor.utils.MessageEvent;
import com.xabaizhong.treemonitor.utils.RxBus;

/**
 * Created by admin on 2017/3/6.
 */

public class C_dialog_radio extends AlertDialog {
    private Context context;

    public C_dialog_radio(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    private void initView() {
        setTitle("");


        RadioGroup group = new RadioGroup(context);
        group.setLayoutParams(new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        group.setOrientation(LinearLayout.VERTICAL);

        RadioButton rb;
        for (int i = 0; i < 3; i++) {
            rb = new RadioButton(context);
            rb.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 30));
            rb.setText("title" + i);
            group.addView(rb);
            final int finalI = i;
            rb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    RxBus.getDefault().send(new MessageEvent(finalI, "title" + finalI));
                }
            });
        }
        setView(group);

    }

    public C_dialog_radio(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

}
