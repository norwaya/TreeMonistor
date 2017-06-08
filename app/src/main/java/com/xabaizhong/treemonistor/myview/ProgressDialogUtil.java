package com.xabaizhong.treemonistor.myview;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.KeyEvent;

/**
 * Created by Administrator on 2017/6/6 0006.
 */

public class ProgressDialogUtil {

    public static ProgressDialogUtil getInstance(Context context) {
        return new ProgressDialogUtil(context);
    }


    private Context context;

    private ProgressDialogUtil(Context context) {
        this.context = context;
    }

    public ProgressDialog initial(String msg, final CallBackListener callBack) {
        Log.i("initial", "initial: ");
        if (msg == null) {
            msg = "UPLOADING...";
        }
        final ProgressDialog  progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage(msg);
        progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });
        progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Log.i("progress dialog", "onDismiss: ");
                if (callBack != null) {
                    callBack.callBack(dialog);
                }
            }
        });
//        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//            @Override
//            public void onCancel(DialogInterface dialog) {
//                if (callBack != null) {
//                    callBack.callBack();
//                }
//            }
//        });
        progressDialog.show();
        return progressDialog;
    }
    public interface CallBackListener {
        public void callBack(DialogInterface dialog);
    }
}
