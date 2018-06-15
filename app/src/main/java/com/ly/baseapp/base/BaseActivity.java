package com.ly.baseapp.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * 需要依赖注入extends this ，其实可以不要这么多Base, 在
 * Application 中完成所有的就好了，是不是 ！～
 * <p>
 * Created by ly on 2017/8/20.
 */
public abstract class BaseActivity extends AppCompatActivity implements HasSupportFragmentInjector {
    public Context mContext;


    public LoadService mBaseLoadService; //Http Error，empty,Loading,timeout状态管理器

    @Inject
    DispatchingAndroidInjector<Fragment> supportFragmentInjector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //一处声明，处处依赖注入，before calling super.onCreate();:
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        mContext = BaseActivity.this;


        View rootView = View.inflate(this, getLayoutId(), null);
        ButterKnife.bind(this, rootView);   //ButterKnife 绑定,只要在这一处地方写好就可以了


        setContentView(rootView);
        mBaseLoadService = LoadSir.getDefault().register(mContext, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                onHttpReload(v);
            }
        });
        initViews();
        loadHttp();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return supportFragmentInjector;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    /**
     * Http 请求的重新加载
     */
    protected void onHttpReload(View v) {
    }

    protected abstract int getLayoutId(); //获取相应的布局啊

    protected abstract void initViews();


    protected  void loadHttp(){
        mBaseLoadService.showSuccess();
    }
}
