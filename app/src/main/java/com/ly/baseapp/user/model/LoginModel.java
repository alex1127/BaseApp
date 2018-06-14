package com.ly.baseapp.user.model;


import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.ly.baseapp.base.BaseModel;
import com.ly.baseapp.bean.LoginParams;
import com.ly.baseapp.bean.LoginResult;
import com.ly.baseapp.http.Api;
import com.ly.baseapp.http.ApiService;
import com.ly.baseapp.user.contract.LoginContract;
import com.ly.httplib.core.BaseObserver;
import com.ly.httplib.core.rxUtils.SwitchSchedulers;

import java.util.Observable;

import javax.inject.Inject;

/**
 *
 */
public class LoginModel implements LoginContract.Model {

    @Inject
    ApiService apiService;

    @Inject
    Context context;

    @Inject
    public LoginModel() {

    }

    public LoginModel(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public void login(String username, String passwd, String token, String time, String refer, DataCallback callback) {

        apiService.getLogin(Api.getCacheControl(), passwd, refer, time, token, username)
                .compose(SwitchSchedulers.applySchedulers())
                .subscribe(new BaseObserver<LoginResult>(context) {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        if (callback != null) {
                            callback.onSuccess(loginResult);
                        }

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        if (callback != null) {
                            callback.onError(code, message);
                        }
                        super.onFailure(code, message);

                    }
                });

    }
}
