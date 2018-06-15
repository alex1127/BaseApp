package com.ly.baseapp;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Context;


import com.kingja.loadsir.core.LoadSir;
import com.ly.baseapp.application.Component.ApplicationComponent;
import com.ly.baseapp.application.Component.DaggerApplicationComponent;
import com.ly.baseapp.application.module.ApplicationModule;
import com.ly.baseapp.http.status.CustomCallback;
import com.ly.baseapp.http.status.EmptyCallback;
import com.ly.baseapp.http.status.ErrorCallback;
import com.ly.baseapp.http.status.LoadingCallback;
import com.ly.baseapp.http.status.TimeoutCallback;


import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasServiceInjector;


public class MainApplication extends Application implements HasActivityInjector, HasServiceInjector {


    @Inject
    DispatchingAndroidInjector<Activity> dispatchingActivityInjector;

    @Inject
    DispatchingAndroidInjector<Service> dispatchingServiceInjector;


    private static Context context;


    public static Context getAppContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build().inject(this);

        LoadSir.beginBuilder()
                .addCallback(new ErrorCallback())//添加各种状态页
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new TimeoutCallback())
                .addCallback(new CustomCallback())
                .setDefaultCallback(LoadingCallback.class)//设置默认状态页
                .commit();

    }


    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingActivityInjector;
    }

    @Override
    public AndroidInjector<Service> serviceInjector() {
        return dispatchingServiceInjector;
    }
}

