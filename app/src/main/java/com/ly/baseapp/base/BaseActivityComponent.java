package com.ly.baseapp.base;


import dagger.Subcomponent;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

/**
 * 中被使用
 * 不要在每个Activity 中建立一个ActivitySubComponent，麻烦而且重复的无聊代码
 * <p>

 */
@Subcomponent(modules = {AndroidInjectionModule.class,})

public interface BaseActivityComponent extends AndroidInjector<BaseActivity> {

    //每一个继承BaseActivity的Activity，都共享同一个SubComponent
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<BaseActivity> {

    }

}
