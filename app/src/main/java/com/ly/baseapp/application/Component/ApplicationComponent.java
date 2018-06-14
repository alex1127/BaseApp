package com.ly.baseapp.application.Component;


import com.ly.baseapp.MainApplication;
import com.ly.baseapp.application.module.ApplicationModule;
import com.ly.baseapp.base.BaseActivityModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {ApplicationModule.class, BaseActivityModule.class,
        AndroidInjectionModule.class, AndroidSupportInjectionModule.class,
})
public interface ApplicationComponent {


    void inject(MainApplication application);


}

