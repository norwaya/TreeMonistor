package com.xabaizhong.treemonistor.activity.expert;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.activity.add_tree.Activity_tree_cname;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.contant.UserSharedField;
import com.xabaizhong.treemonistor.entity.TreeSpecial;
import com.xabaizhong.treemonistor.myview.C_dialog_radio;
import com.xabaizhong.treemonistor.myview.C_info_gather_item1;
import com.xabaizhong.treemonistor.service.WebserviceHelper;
import com.xabaizhong.treemonistor.utils.MessageEvent;
import com.xabaizhong.treemonistor.utils.RxBus;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * Created by norwaya on 17-4-17.
 */

public class Activity_expert_bug extends Activity_base implements C_info_gather_item1.Mid_CallBack {
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.bug_season)
    C_info_gather_item1 bugSeason;
    @BindView(R.id.bug_species)
    C_info_gather_item1 bugSpecies;
    @BindView(R.id.bug_part)
    C_info_gather_item1 bugPart;
    @BindView(R.id.bug_classic)
    C_info_gather_item1 bugClassic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_bug);
        ButterKnife.bind(this);
        initData();
        initDis();
        initView();
    }

    Disposable disposable;
    Param_code params = null;
    private static final int SPECIES_CODE = 671;

    private void initDis() {
        params = new Param_code();
        disposable = RxBus.getDefault().toObservable(MessageEvent.class).subscribe(new Consumer<MessageEvent>() {
            @Override
            public void accept(MessageEvent messageEvent) throws Exception {
                switch (messageEvent.getCode()) {
                    case BUG_SEASON:
                        bugSeason.setText(messageEvent.getText());
                        params.season = messageEvent.getId();
                        break;
                    case BUG_PART:
                        bugPart.setText(messageEvent.getText());
                        params.part = messageEvent.getId();
                        break;
                    case BUG_CLASSIC:
                        bugClassic.setText(messageEvent.getText());
                        params.classic = messageEvent.getId();
                        break;
                }
            }
        });
    }

    private void initData() {

    }

    private void initView() {
        bugSeason.setCallback_mid(this);
        bugPart.setCallback_mid(this);
        bugClassic.setCallback_mid(this);
        bugSpecies.setCallback_mid(this);
    }

    @OnClick(R.id.submit)
    public void onViewClicked() {
        if (checked()) {
            request();
        }
    }

    private boolean checked() {
        // TODO: 2017/4/19 0019  check expert_bug;
        return false;
    }

    AsyncTask asyncTask;

    private void request() {
        asyncTask = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    return WebserviceHelper.GetWebService(
                            "CheckUp", "CheckUp1", getParms());
                } catch (ConnectException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
                if (s == null) {
                    showToast("请求错误");
                    return;
                }
                Log.i(TAG, "onPostExecute: " + s);
            }
        }.execute();
    }


    private Map<String, Object> getParms() {
        Map<String, Object> map = new HashMap<>();
        String user_id = sharedPreferences.getString(UserSharedField.USERID, "");
        //// TODO: 2017/4/19 0019   根据接口修改参数；
        map.put("UserID ", user_id);
        return map;
    }

    Param_list paraList = new Param_list();

    @Override
    public void onClickListener(View et) {
        switch (et.getId()) {
            case R.id.bug_season:
                new C_dialog_radio(this, "季节", paraList.season, BUG_SEASON);
                break;
            case R.id.bug_species:
                startActivityForResult(new Intent(Activity_expert_bug.this, Activity_tree_cname.class), SPECIES_CODE);
                break;
            case R.id.bug_part:
                new C_dialog_radio(this, "部位", paraList.part, BUG_PART);
                break;
            case R.id.bug_classic:
                new C_dialog_radio(this, "类型", paraList.classic, BUG_CLASSIC);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SPECIES_CODE && resultCode == Activity_tree_cname.REQUEST_CODE_CNAME_RESULT) {
            TreeSpecial tree = data.getParcelableExtra("special");
            Log.i(TAG, "onActivityResult: " + tree.getCname() + "\t" + tree.getTreeSpecCode());
            bugSpecies.setText(tree.getCname());
            params.species = tree.getTreeSpecCode();
        }
    }

    static final int BUG_SEASON = 644;
    static final int BUG_SPECIES = 874;
    static final int BUG_PART = 625;
    static final int BUG_CLASSIC = 124;

    private static class Param_code {
        int season = -1;
        String species = null;
        int part = -1;
        int classic = -1;

    }

    private static class Param_list {
         List<String> season = new ArrayList<>();
         List<String> part = new ArrayList<>();
         List<String> classic = new ArrayList<>();

        Param_list() {
            initialData();
        }

        void initialData() {
            season.clear();
            season.add(0, "春季");
            season.add(1, "夏季");
            season.add(2, "秋季");
            season.add(3, "冬季");

            part.clear();
            part.add(0, "叶子");
            part.add(1, "树干");
            part.add(2, "根");
            part.add(3, "果实");

            classic.clear();
            classic.add(0, "品种一");
            classic.add(1, "品种二");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        if (asyncTask != null && !asyncTask.isCancelled()) {
            asyncTask.cancel(true);
        }
    }
}
