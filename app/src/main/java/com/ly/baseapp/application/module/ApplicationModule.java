package com.ly.baseapp.application.module;


import android.app.Application;
import android.content.Context;


import com.kingja.loadsir.core.LoadSir;
import com.ly.baseapp.http.ApiService;
import com.ly.baseapp.http.HttpRetrofit;
import com.ly.baseapp.http.status.EmptyCallback;
import com.ly.baseapp.http.status.ErrorCallback;
import com.ly.baseapp.http.status.LoadingCallback;
import com.ly.baseapp.http.status.TimeoutCallback;
import com.ly.baseapp.util.SPDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    Application mContext;

    public ApplicationModule(Application mContext) {
        this.mContext = mContext;
    }


    /***
     * @return
     */
    @Provides
    @Singleton
    Context provideContext() {
        return mContext;
    }




    @Provides
    @Singleton
        //在这加了Singleton 的注解就是单例的了，打出内存地址查看一下
    SPDao provideSPDao() {
        return new SPDao(mContext);
    }


    /**
     * 网络访问
     *
     * @return
     */
    @Provides
    @Singleton
    ApiService provideApiService(SPDao spDao, Context mContext) {
        //Retrofit 的create 真是精华所在啊！
        return HttpRetrofit.getRetrofit(spDao, mContext).create(ApiService.class);
    }

    /**
     * 增加Error，empty,Loading,timeout,等通用的场景处理，一处Root注入，处处可用
     *
     * @return
     */
    @Provides
    @Singleton
    LoadSir provideCommonStatusService() {
        return new LoadSir.Builder()
                .addCallback(new LoadingCallback())
                .addCallback(new EmptyCallback())
                .addCallback(new ErrorCallback())
                .addCallback(new TimeoutCallback())
                .build();
    }


}