package com.ly.baseapp.user;


import dagger.Module;
import dagger.Provides;

@Module
public class DefaultModule {


    /**
     * 这是测试性质的提供一个String,实际上并不会使用的
     *
     * @return
     */
    @Provides
    public String provideName() {
        return "NULL,It is DefaultActivityModule";
    }

}
