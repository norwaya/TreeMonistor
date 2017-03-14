package com.xabaizhong.treemonistor.myview;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.utils.MessageEvent;
import com.xabaizhong.treemonistor.utils.RxBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/3/14.
 */

public class C_dialog_checkbox implements DialogInterface.OnClickListener {


    LayoutInflater inflater;
    AlertDialog.Builder builder;

    int request;

    public C_dialog_checkbox(Context context, int request) {
        builder = new AlertDialog.Builder(context);
        inflater = LayoutInflater.from(context);
        this.request = request;
        initView();
    }

    AppCompatCheckedTextView[] array;
    ViewHolder viewHolder;

    private void initView() {
        View view = inflater.inflate(R.layout.dialog_check_box, null);
        viewHolder = new ViewHolder(view);
        builder.setView(view);
        builder.setCancelable(false);
        array = new AppCompatCheckedTextView[]{viewHolder.cb1, viewHolder.cb2, viewHolder.cb3, viewHolder.cb4, viewHolder.cb5, viewHolder.cb6, viewHolder.cb7};
    }

    String[] strArray;

    public C_dialog_checkbox setList(String[] strArray) {
        this.strArray = strArray;
        if (strArray.length < array.length)
            return this;
        for (int i = 0; i < array.length; i++) {
            array[i].setText(strArray[i]);
        }
        return this;
    }

    public C_dialog_checkbox setTitle(String title) {
        builder.setTitle(title);
        return this;
    }

    private void setButton(String text, AlertDialog.OnClickListener onClickListener) {
        builder.setPositiveButton(text, onClickListener);
    }

    public C_dialog_checkbox show() {
        setButton("确定", this);
        builder.show();
        return this;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        StringBuilder code = new StringBuilder();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < viewHolder.checkState.length; i++) {
            Log.i("check", "onClick: " + viewHolder.checkState[i]);
            if (viewHolder.checkState[i] == 1) {
                code.append((i + 1));
                text.append(strArray[i]).append(",");
            }
        }
        RxBus.getDefault().send(new MessageEvent(request, 0, text.toString(), code.toString()));

    }

    static class ViewHolder implements View.OnClickListener {
        int[] checkState = {0, 0, 0, 0, 0, 0, 0};
        @BindView(R.id.cb1)
        AppCompatCheckedTextView cb1;
        @BindView(R.id.cb2)
        AppCompatCheckedTextView cb2;
        @BindView(R.id.cb3)
        AppCompatCheckedTextView cb3;
        @BindView(R.id.cb4)
        AppCompatCheckedTextView cb4;
        @BindView(R.id.cb5)
        AppCompatCheckedTextView cb5;
        @BindView(R.id.cb6)
        AppCompatCheckedTextView cb6;
        @BindView(R.id.cb7)
        AppCompatCheckedTextView cb7;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
            cb1.setOnClickListener(this);
            cb2.setOnClickListener(this);
            cb3.setOnClickListener(this);
            cb4.setOnClickListener(this);
            cb5.setOnClickListener(this);
            cb6.setOnClickListener(this);
            cb7.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            AppCompatCheckedTextView text = (AppCompatCheckedTextView) v;
            boolean check = text.isChecked();

            switch (v.getId()) {
                case R.id.cb1:
                    if (!check)
                        checkState[0] = 1;
                    else
                        checkState[0] = 0;
                    break;
                case R.id.cb2:
                    if (!check)
                        checkState[1] = 1;
                    else
                        checkState[1] = 0;
                    break;
                case R.id.cb3:
                    if (!check)
                        checkState[2] = 1;
                    else
                        checkState[2] = 0;
                    break;
                case R.id.cb4:
                    if (!check)
                        checkState[3] = 1;
                    else
                        checkState[3] = 0;
                    break;
                case R.id.cb5:
                    if (!check)
                        checkState[4] = 1;
                    else
                        checkState[4] = 0;
                    break;
                case R.id.cb6:
                    if (!check)
                        checkState[5] = 1;
                    else
                        checkState[5] = 0;
                    break;
                case R.id.cb7:
                    if (!check)
                        checkState[6] = 1;
                    else
                        checkState[6] = 0;
                    break;
            }
            text.setChecked(!check);
        }
    }
}
