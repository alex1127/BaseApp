package com.ly.baseapp.user.presenter;


import com.ly.baseapp.base.BaseModel;
import com.ly.baseapp.bean.LoginParams;
import com.ly.baseapp.bean.LoginResult;
import com.ly.baseapp.user.contract.LoginContract;
import com.ly.baseapp.user.model.LoginModel;


import javax.inject.Inject;

/**
 * Login Presenter
 * Created by anylife.zlb@gmail.com on 2018/1/11.
 */
public class LoginPresenter implements LoginContract.Presenter {


    LoginContract.View mLoginView;  // 需要抽象出来

    LoginModel model;       //

    @Inject
    public LoginPresenter(LoginModel model) {
        this.model = model;
    }


    @Override
    public void takeView(LoginContract.View view) {
        mLoginView = view;

    }

    @Override
    public void dropView() {
        mLoginView = null;
    }


    @Override
    public void login(String username, String passwd, String token, String time) {
        model.login(username, passwd, time, token, "android", new BaseModel.DataCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                if (mLoginView != null) {
                    mLoginView.loginSuccess(loginResult);

                }
            }

            @Override
            public void onError(int code, String message) {

                if (null != mLoginView) {
                    mLoginView.loginFail(message);
                }

            }
        });
    }
}
