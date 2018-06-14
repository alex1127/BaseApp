package com.ly.baseapp.http;



import com.ly.baseapp.bean.LoginParams;
import com.ly.baseapp.bean.LoginResult;
import com.ly.httplib.core.HttpResponse;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 *
 *
 *
 * Created by joker
 */
public interface ApiService {


    /**
     * Login ,尝试使用Flowable 来处理，
     */
    @Headers("NeedOauthFlag: NeedOauthFlag")
    @POST("/userlogin/user_login")
    Observable<HttpResponse<LoginResult>> goLoginByRxjavaObserver(@Body LoginParams loginRequest);



    //登陆
    @GET("/userlogin/user_login")
    Observable<HttpResponse<LoginResult>> getLogin(
            @Header("Cache-Control") String cacheControl,
            @Query("passwd") String pwd,
            @Query("refer") String status,
            @Query("time") String time,
            @Query("token") String token,
            @Query("username") String username);




}

