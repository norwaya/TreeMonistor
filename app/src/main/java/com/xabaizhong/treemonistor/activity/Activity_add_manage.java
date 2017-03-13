package com.xabaizhong.treemonistor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.myview.C_dialog_radio;
import com.xabaizhong.treemonistor.utils.MessageEvent;
import com.xabaizhong.treemonistor.utils.RxBus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by admin on 2017/2/28.
 */

public class Activity_add_manage extends Activity_base {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_manager);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_add_manager, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.activity_add_manager_add:
                startActivity(new Intent(this,Activity_add_tree.class));
                break;
            case R.id.activity_add_manager_add_gsq:
                startActivity(new Intent(this,Activity_add_tree_group.class));
                break;
            case R.id.activity_add_manager_upload:
                showToast("上传操作");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    Disposable disposable;

    final static int INTENT_SINGLE_TREE = 0;
    final static int INTENT_GROUP_TREE = 1;
    private void register() {

        disposable = RxBus.getDefault().toObservable(MessageEvent.class).subscribe(new Consumer<MessageEvent>() {
            @Override
            public void accept(MessageEvent messageEvent) throws Exception {
                Log.d(TAG, "accept: " + messageEvent.getId() + "\t" + messageEvent.getText());
                switch (messageEvent.getId()){
                    case INTENT_SINGLE_TREE:
                        Intent i = new Intent();
                        i.setClass(getApplicationContext(), Activity_add_tree.class);
                        startActivity(i);
                        break;
                    case INTENT_GROUP_TREE:

                        break;

                }
            }
        });
    }

    private void displayDialog() {
        List<String> list = new ArrayList<>();
        list.add("新增单棵古树");
        list.add("新增古树群");
        new C_dialog_radio(this,"选择上报的古树类型",list,0);

    }

    @Override
    protected void onStart() {
        super.onStart();
        register();
    }

    @Override
    protected void onPause() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}
