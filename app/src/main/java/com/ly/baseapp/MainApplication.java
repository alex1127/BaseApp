package com.ly.baseapp;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Context;


import com.ly.baseapp.application.Component.ApplicationComponent;
import com.ly.baseapp.application.Component.DaggerApplicationComponent;
import com.ly.baseapp.application.module.ApplicationModule;

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

