package com.ly.baseapp.util;

import android.content.Context;
import android.util.Log;

import com.ly.baseapp.MainApplication;
import com.ly.baseapp.http.Api;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.inject.Inject;


public class DaoUtil {


    @Inject
    SPDao spDao;
    public static DaoUtil daoUtil = null;

    private String currentTime;
    private String ver = String.valueOf(Utils.getVersionName(MainApplication.getAppContext()));
    private String an = String.valueOf(Utils.getVersionCode(MainApplication.getAppContext()));

    public static DaoUtil getInstance() {
        if (daoUtil == null) {
            daoUtil = new DaoUtil();
        }
        return daoUtil;
    }


    public String CurrentTime() {
        long time = System.currentTimeMillis() / 1000;//获取系统时间的10位的时间戳
        currentTime = String.valueOf(time);
        return currentTime;
    }


    /**
     * 网络请求 TOKEN
     */
    public String getToken(Context context, Map<String, String> map) {
        currentTime = CurrentTime();
        if (map == null) {
            map = new TreeMap<String, String>();
        }
        if (map.containsKey("token")) {
            map.remove("token");
        }
        if (map.containsKey("uid")) {
            if ("".equals(spDao.getData(Api.Access_token, "", "".getClass()))) {
                map.put("access_token", spDao.getData(Api.Access_token, "", "".getClass()));
                map.put("expires_time", spDao.getData(Api.Expires_time, "", "".getClass()));
            }
        }
        map.put("refer", Api.REFER);
        map.put("time", currentTime);
//        map.put("ver", ver);
//        map.put("an", an);
        String str = Utils.mapDesc(map) + Api.secret_key;
        String token = Utils.encodeByMD5(str);
        return token;

    }


    /**
     * 网络请求获取Current time
     */
    public String getCurrentTime() {
        return currentTime;
    }

}
