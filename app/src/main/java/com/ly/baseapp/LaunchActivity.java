package com.ly.baseapp;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.ly.baseapp.base.BaseActivity;

import com.ly.baseapp.user.ui.LoginActivity;
import com.ly.baseapp.util.SPDao;

import javax.inject.Inject;


public class LaunchActivity extends BaseActivity {

    @Inject
    SPDao spDao;


    private static final int FINISH_LAUNCHER = 0;
    private Handler UiHandler = new MyHandler();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_launch;
    }

    @Override
    protected void initViews() {
        UiHandler.sendEmptyMessageDelayed(FINISH_LAUNCHER, 2500);  //测试内存泄漏,只为测试
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        UiHandler.removeCallbacksAndMessages(null);
    }


    class MyHandler extends Handler {

        public MyHandler() {

        }

        // 子类必须重写此方法，接受数据
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case FINISH_LAUNCHER:

                    String accessToken = spDao.getData("test", "", String.class);
                    Log.e("SAX", "accessToken: " + accessToken);
                    Intent i1 = new Intent();
                    if (TextUtils.isEmpty(accessToken)) {
                        i1.putExtra("isFromLaunch", true);
                        i1.setClass(LaunchActivity.this, LoginActivity.class);
                    } else {
                        i1.setClass(LaunchActivity.this, MainActivity.class);
                    }
                    startActivity(i1);
                    LaunchActivity.this.finish();
                    break;
                default:
                    break;
            }
        }
    }


}
