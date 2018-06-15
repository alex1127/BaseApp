package com.ly.baseapp.user.model;


import android.app.Activity;
import android.content.Context;

import com.ly.baseapp.bean.LoginResult;
import com.ly.baseapp.http.Api;
import com.ly.baseapp.http.ApiService;
import com.ly.baseapp.user.contract.LoginContract;
import com.ly.httplib.core.BaseObserver;
import com.ly.httplib.core.rxUtils.SwitchSchedulers;


import javax.inject.Inject;

/**
 *
 */
public class LoginModel implements LoginContract.Model {

    @Inject
    ApiService apiService;

    Activity activity;

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

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
                .subscribe(new BaseObserver<LoginResult>(activity) {
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
