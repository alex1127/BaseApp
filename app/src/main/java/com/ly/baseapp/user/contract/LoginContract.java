package com.ly.baseapp.user.contract;


import com.ly.baseapp.base.BaseModel;
import com.ly.baseapp.base.BasePresenter;
import com.ly.baseapp.base.BaseView;
import com.ly.baseapp.bean.LoginParams;
import com.ly.baseapp.bean.LoginResult;

import java.util.Observable;

import retrofit2.http.Query;

/**
 *
 *
 */

public interface LoginContract {

    /**
     * 对UI 的操作的接口有哪些，一看就只明白了
     */
    interface View extends BaseView {
        void loginSuccess(LoginResult loginBean);

        void loginFail(String failMsg);
    }


    interface Presenter extends BasePresenter<View> {
        void login(String username, String passwd, String token, String time);
    }


    interface Model extends BaseModel {

        void login(String username, String passwd, String token, String time, String refer, DataCallback callback);

    }
}



