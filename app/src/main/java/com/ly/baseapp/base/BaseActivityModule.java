package com.ly.baseapp.base;


import com.ly.baseapp.LaunchActivity;
import com.ly.baseapp.user.DefaultModule;
import com.ly.baseapp.scroe.ActivityScope;
import com.ly.baseapp.user.ui.LoginActivity;
import com.ly.baseapp.user.ui.RegisterActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * 全部放在这里来统一的管理 ！
 * 新建了一个Activity 的并且需要inject 的只需要添加两行代码
 * <p>
 * 大部分的页面都不需要再额外的提供对象的话只需要DefaultActivityModule 就好了，否则自定义XXActivityModule
 * <p>
 * <p>
 * 对个人而言，这样的好处在于：
 * 1.每次不再需要额外声明一个SubCompoent，再次减少模板代码
 * 2.每个Activity的Module都放在同一个AllActivitysModule中进行统一管理，每次修改只需要修改这一个类即可
 * 3.每个Activity所单独需要的依赖，依然由各自的Module进行管理和实例化，依然没有任何耦合
 */
@Module(subcomponents = {
        BaseActivityComponent.class  //1111111111 subcomponent=BaseActivityComponent
})
public abstract class BaseActivityModule {
//    @ActivityScope
//    @ContributesAndroidInjector(modules =BaseActivityModule.class)
//    abstract AnyLifeFragment contributeTabsFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector(modules = DefaultModule.class)
    abstract LoginActivity contributeLoginActivityInjector();



    @ActivityScope
    @ContributesAndroidInjector(modules = DefaultModule.class)
    abstract LaunchActivity contributeLaunchActivityInjector();




    @ActivityScope
    @ContributesAndroidInjector(modules = DefaultModule.class)
    abstract RegisterActivity contributeRegisterActivityInjector();
}
